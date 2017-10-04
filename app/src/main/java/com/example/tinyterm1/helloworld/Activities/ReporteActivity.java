package com.example.tinyterm1.helloworld.Activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.Adapter.ReporteListAdapter;
import com.example.tinyterm1.helloworld.ApiCall.EstablecimientosSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.GetEstablecimientosApiCall;
import com.example.tinyterm1.helloworld.ApiCall.GetTiposHorarioApiCall;
import com.example.tinyterm1.helloworld.ApiCall.ReporteApiCall;
import com.example.tinyterm1.helloworld.ApiCall.ReporteSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.TipoHorarioSuccessInterface;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.Models.Reporte;
import com.example.tinyterm1.helloworld.Models.TipoDeHorario;
import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.Services.FichadoPost;
import com.example.tinyterm1.helloworld.Services.GPSTracker;
import com.example.tinyterm1.helloworld.Services.GeoLocationService;
import com.example.tinyterm1.helloworld.SharedPreferences.DireccionSV;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReporteActivity extends AppCompatActivity {
    private ListView monthsListView;
    private ArrayAdapter arrayAdapter;
    private Context ctx;
    @BindView(R.id.usuario)
    TextView usuario;
    @BindView(R.id.valorTotalEntrada)
    TextView totalEntrada;
    @BindView(R.id.valorTotalSalida)
    TextView totalSalida;
    @BindView(R.id.valorTotalTiempo)
    TextView totalTiempo;
    @BindView(R.id.fichadas_list_view)
    ListView listadoReporte;
    PendingIntent pendingIntent;

    @BindView(R.id.establecimientoSpinner)
    Spinner EstablecimientoSpinner;
    @BindView(R.id.tipoHorarioSpinner)
    Spinner TipoDeHorarioSpinner;
    @BindView(R.id.desdeDtp)
    TextView fechaDesde;
    @BindView(R.id.hastDtp)
    TextView fechaHasta;
    @BindView(R.id.btnListar)
    Button listar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ctx = this;
        ButterKnife.bind(this);
        GetEstablecimientosApiCall establecimientosApiCall = new GetEstablecimientosApiCall();
        GetTiposHorarioApiCall getTiposHorarioApiCall = new GetTiposHorarioApiCall();

        establecimientosApiCall.getEstablecimientos(this,
                new EstablecimientosSuccessInterface() {
                    @Override
                    public void GetEstablecimientos(ArrayList<Establecimiento> establecimientos) {
                        ArrayAdapter<Establecimiento> adapter = new ArrayAdapter<Establecimiento>(ctx, android.R.layout.simple_spinner_dropdown_item, establecimientos);
                        EstablecimientoSpinner.setAdapter(adapter);
                    }
                });

        getTiposHorarioApiCall.getTiposHorario(this,
                new TipoHorarioSuccessInterface() {
                    @Override
                    public void GetTiposHorario(ArrayList<TipoDeHorario> tiposDeHorario) {
                        ArrayAdapter<TipoDeHorario> adapter = new ArrayAdapter<TipoDeHorario>(ctx, android.R.layout.simple_spinner_dropdown_item, tiposDeHorario);
                        TipoDeHorarioSpinner.setAdapter(adapter);
                    }
                });

        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TipoDeHorario tipoHorario = (TipoDeHorario) TipoDeHorarioSpinner.getSelectedItem();
                Establecimiento establecimiento = (Establecimiento) EstablecimientoSpinner.getSelectedItem();
                Map<String, String> data = new HashMap<>();
                Date desde, hasta;
                String[] formats = {"dd/MM/yyyy", "dd-MM-yyyy"};
                for(String formatString : formats){
                    try {
                    desde = new SimpleDateFormat(formatString).parse(String.valueOf(fechaDesde.getText()));
                    hasta = new SimpleDateFormat(formatString).parse(String.valueOf(fechaHasta.getText()));
                    data.put("desde", new SimpleDateFormat("yyyy-MM-dd").format(desde));
                    data.put("hasta", new SimpleDateFormat("yyyy-MM-dd").format(hasta));
                    data.put("locationid", Integer.toString(establecimiento.getEstablecimientoID()));
                    data.put("tipoHorarioID", Integer.toString(tipoHorario.getTipoHorarioID()));
                    ReporteApiCall reporteApiCall = new ReporteApiCall();;
                    reporteApiCall.getReporte(data, ctx,
                            new ReporteSuccessInterface() {
                                @Override
                                public void MostrarReporte(ListadoRequest listado) {
                                    if (listado.getUsuario() != null)
                                        usuario.setText(listado.getUsuario());
                                    totalEntrada.setText(listado.getTotalEntrada());
                                    totalSalida.setText(listado.getTotalSalida());
                                    totalTiempo.setText(listado.getTotalTiempo());
                                    ArrayList<Reporte> reportes = listado.getReportes();
                                    listadoReporte.setAdapter(new ReporteListAdapter(ctx, reportes));
                                }
                            });
                        break;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
