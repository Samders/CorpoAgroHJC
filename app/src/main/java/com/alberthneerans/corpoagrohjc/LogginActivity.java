package com.alberthneerans.corpoagrohjc;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LogginActivity extends AppCompatActivity  {
    private LoginButton loginButton;        //Login con Facebook
    private CallbackManager callbackManager;

    Button bEntrar;
    TextView tRegistrar;
    EditText eUsuario, eContrasena;
    private Cursor fila;

    private FirebaseAuth firebaseAuth;      //Configuracion de Firebase
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private static final int RC_SIGN_IN = 1;    //Login con Google
    //private static final String TAG ="MainActivity" ;

    ContentValues dataDB;
    SQLiteDatabase dbContactos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_loggin);



        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        bEntrar=(Button) findViewById(R.id.bEntrar);
        tRegistrar=(TextView) findViewById(R.id.tRegistro);

        eUsuario=(EditText)findViewById(R.id.eUsuario);
        eContrasena=(EditText) findViewById(R.id.eContrasena);


        tRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSingUpActivity();
            }
        });




        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error login Facebook", Toast.LENGTH_LONG).show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    goMainActivity();
                }
            }
        };
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }

    private void goMainActivity() {
        Intent intent = new Intent (getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void goSingUpActivity(){
        Intent intent = new Intent(LogginActivity.this, SingUpActivity.class);
        startActivity(intent);
    }


    public void Entrar(View v) {
        DBHelper admin = new DBHelper(this, "instituto", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String usuario = eUsuario.getText().toString();
        String contrasena = eContrasena.getText().toString();
        fila = db.rawQuery("select usuario,contrasena from usuarios where usuario='" + usuario + "' and contrasena='" + contrasena + "'", null);

        if (fila.moveToFirst()) {
            String usua = fila.getString(0);
            String pass = fila.getString(1);
            if (usuario.equals(usua) && contrasena.equals(pass)) {
                goMainActivities();
                eUsuario.setText("");
                eContrasena.setText("");
            }else{
                Toast.makeText(getApplicationContext(), "Usuario y contraseña no coinciden", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(getApplicationContext(), "Usuario no existe", Toast.LENGTH_LONG).show();
            goSingUpActivity();
        }
    }
    private void goMainActivities(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

}
