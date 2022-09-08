package com.example.trabajofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsuario, etClave, etConfirmClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsuario = findViewById(R.id.etRegUser);
        etClave = findViewById(R.id.etRegPassword);
        etConfirmClave = findViewById(R.id.etRegPassword2);

        Button btnRegistrar = findViewById(R.id.btnRegistrar2);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUsuario.getText().toString();
                String pass = etClave.getText().toString();
                String pass2 = etConfirmClave.getText().toString();
                if(user.equals("") || pass.equals("") || pass2.equals("")){
                    Toast.makeText(RegisterActivity.this,"Complete todos los campos",Toast.LENGTH_LONG).show();
                }else{
                    if(pass.equals(pass2)) {
                        if(exist(user)){
                            Toast.makeText(RegisterActivity.this,"Ese usuario ya está registrado",Toast.LENGTH_LONG).show();
                        }else{
                            registrar(user,pass);
                            finish();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Las contraseñas no son iguales",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean exist(String user){
        AdminSQLiteData admin = new AdminSQLiteData(this,"sesion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from usuarios where usuario='"+user+"'",null);
        if(fila.getCount()>0){
            fila.close();
            return true;
        }else{
            fila.close();
            return false;
        }
    }

    public void registrar(String user, String pass){
        AdminSQLiteData admin = new AdminSQLiteData(this,"sesion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("usuario",user);
        registro.put("clave",pass);

        bd.insert("usuarios",null,registro);
        bd.close();

        etUsuario.setText("");
        etClave.setText("");
        etConfirmClave.setText("");

        Toast.makeText(RegisterActivity.this,"Usuario registrado correctamente",Toast.LENGTH_LONG).show();
    }
}