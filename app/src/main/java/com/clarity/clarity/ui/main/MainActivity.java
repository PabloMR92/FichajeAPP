package com.clarity.clarity.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.clarity.clarity.BuildConfig;
import com.clarity.clarity.R;
import com.clarity.clarity.ui.base.BaseActivity;
import com.clarity.clarity.utils.StorageKeys;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.orhanobut.hawk.Hawk;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.MultiFallbackProvider;
import io.nlopez.smartlocation.rx.ObservableFactory;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity implements MainContract.View {
    @Inject
    MainContract.Presenter mPresenter;

    @BindView(R.id.lastSuccesfulClockIn)
    TextView txt_lastSuccesfulClockIn;

    @BindView(R.id.btnManualClockIn)
    TextView btn_manualClockIn;

    private static final int LOCATION_PERMISSION_ID = 1001;
    final static int REQUEST_LOCATION = 199;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.attach(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
            return;
        }
        if (!isGpsEnabled()) {
            requestGps();
        }
        btn_manualClockIn.setOnClickListener((View v) -> mPresenter.clockIn());
        if(Hawk.contains(StorageKeys.LastSuccesfulClockIn)){
            txt_lastSuccesfulClockIn.setText(Hawk.get(StorageKeys.LastSuccesfulClockIn));
        }

        mPresenter.scheduleClockInJob();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public DateTimeFormatter getDateFormat(){
        return DateTimeFormat.forPattern("HH:mm:ss dd-MM-yyyy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGpsEnabled()) {
            requestGps();
        }
    }

    private boolean isGpsEnabled() {
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.scheduleClockInJob();
        }
    }

    @Override
    public void setLastSuccesfulClockIn(DateTime time) {
        txt_lastSuccesfulClockIn.setText(getDateFormat().print(time));
    }

    @Override
    public void showErrorMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public Observable<Location> getLocationObs() {
        return ObservableFactory.from(SmartLocation.with(this)
                .location(new MultiFallbackProvider.Builder()
                        .withGooglePlayServicesProvider().withDefaultProvider().build())
                .oneFix());
    }

    private void requestGps() {
        setGoogleApiClient();
        googleApiClient.connect();
        LocationSettingsRequest.Builder builder = getLocationRequest();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                }
            }
        });
    }

    private LocationSettingsRequest.Builder getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);
        return builder;
    }

    private void setGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        googleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                    }
                }).build();
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }
}
