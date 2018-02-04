package com.example.tinyterm1.helloworld.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.ApiCall.DomainSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.EstablecimientosSuccessInterface;
import com.example.tinyterm1.helloworld.ApiCall.GetDomainsApiCall;
import com.example.tinyterm1.helloworld.ApiCall.GetTiposHorarioApiCall;
import com.example.tinyterm1.helloworld.ApiCall.LoginApiCall;
import com.example.tinyterm1.helloworld.ApiCall.LoginErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.LoginSuccessInterface;
import com.example.tinyterm1.helloworld.Models.Domain;
import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.LoginErrorRequest;
import com.example.tinyterm1.helloworld.Models.TipoDeHorario;
import com.example.tinyterm1.helloworld.Models.UserLogin;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.SharedPreferences.DireccionSV;
import com.example.tinyterm1.helloworld.SharedPreferences.GeoLocationPostInterval;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.usuario)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.dni)
    EditText dni;
    @BindView(R.id.btnLogin)
    Button loginButton;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        ButterKnife.bind(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loguear();
            }
        });

        //DireccionSV.setDireccion(this, "http://192.168.1.8:3000");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.reporte);
        item.setVisible(false);
        return true;
    }

    void Loguear() {
        LoginApiCall loginApi = new LoginApiCall();
        String domainPrepend = "";
        loginApi.LogIn(new UserLogin(username.getText().toString(), password.getText().toString(), dni.getText().toString()), this,
                new LoginErrorInterface() {

                    @Override
                    public void MostrarError(LoginErrorRequest error) {
                        Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                new LoginSuccessInterface() {

                    @Override
                    public void IrAPrincipal() {
                        Intent inicioActivityIntent = new Intent(ctx, PrincipalActivity.class);
                        inicioActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ctx.startActivity(inicioActivityIntent);
                    }
                }
        );
    }
}



