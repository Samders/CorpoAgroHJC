package com.alberthneerans.corpoagrohjc;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpActivity extends AppCompatActivity  {


    EditText eUsuario, eContrasena, eCorreo;
    Button bAceptar, bCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        eUsuario = (EditText) findViewById(R.id.eUsuario);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        bAceptar = (Button) findViewById(R.id.bAceptar);
        bCancelar= (Button) findViewById(R.id.bCancelar);



    }
    public void goLogginActivity(){
Intent intent = new Intent(SingUpActivity.this, LogginActivity.class);
        startActivity(intent);
    }


    public void onSignUpClicked(View view){

        DBHelper admin=new DBHelper(this,"instituto",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        String usuario=eUsuario.getText().toString();
        String contraseña=eContrasena.getText().toString();
        String correo=eCorreo.getText().toString();

        ContentValues values=new ContentValues();
        values.put("usuario",usuario);
        values.put("contrasena",contraseña);
        values.put("correo", correo);

        db.insert("usuarios",null,values);
        db.close();
        goLogginActivity();
        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();



    }

    public void onCancel(View view){
        goLogginActivity();

    }
}
