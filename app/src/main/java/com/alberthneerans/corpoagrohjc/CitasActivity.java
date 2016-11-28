package com.alberthneerans.corpoagrohjc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CitasActivity extends AppCompatActivity implements View.OnClickListener{
private String nombre, correo, razon, codigo;
    EditText eUsuario, eCorreo, eRazon;
    Button bEnviar, bCancelar;
    private String FIREBASE_URL="https://corpoagro-hjc.firebaseio.com/";
    private Firebase firebasedatos;
    ArrayList<Citas> info;
    Integer id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_citas);

        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);
        info = new ArrayList<Citas>();

        eUsuario=(EditText) findViewById(R.id.eUsuario);
        eCorreo=(EditText) findViewById(R.id.eCorreo);
        eRazon=(EditText) findViewById(R.id.eRazon);
        bEnviar=(Button) findViewById(R.id.bEnviar);
        bCancelar=(Button) findViewById(R.id.bCancelar);

        bEnviar.setOnClickListener(this);
        bCancelar.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            eUsuario.setText(name);
            eCorreo.setText(email);

        }

    }

    @Override
    public void onClick(View view) {

        nombre=eUsuario.getText().toString();
        razon = eRazon.getText().toString();
        correo = eCorreo.getText().toString();
        Firebase firebd;
        switch (view.getId()) {
            case R.id.bEnviar:
                Citas citas = new Citas(nombre,correo,razon, codigo);
                firebd =firebasedatos.child("cita"+ id);
                firebd.setValue(citas);
                id++;
                Toast.makeText(getApplicationContext(), "Gracias por comunicarse con nosotros", Toast.LENGTH_LONG).show();
                limpiar();
                break;
            case R.id.bCancelar:
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG).show();
                goMainActicity();

        }


    }
    private void limpiar() {
        eUsuario.setText("");
        eCorreo.setText("");
        eRazon.setText("");

    }
    private void goMainActicity(){
        Intent intent = new Intent(CitasActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
