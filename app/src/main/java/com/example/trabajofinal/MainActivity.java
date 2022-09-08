package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIngresar = findViewById(R.id.btnIngresar);
        EditText usuario = findViewById(R.id.etUser);
        EditText clave = findViewById(R.id.etPassword);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usuario.getText().toString();
                String password = clave.getText().toString();
                if(user.equals("")||password.equals("")){
                    Toast.makeText(MainActivity.this,"Ingrese un usuario y contraseña",Toast.LENGTH_LONG).show();
                }else{
                    if(iniciarSesion(user,password)){
                        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean iniciarSesion(String user, String pass){
        AdminSQLiteData admin = new AdminSQLiteData(this,"sesion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select clave from usuarios where usuario='"+user+"'",null);
        if(fila.moveToFirst()){
            if(pass.equals(fila.getString(0))){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}