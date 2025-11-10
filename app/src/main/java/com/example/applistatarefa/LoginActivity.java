package com.example.applistatarefa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etSenha;
    private Button btnLogin;
    private CheckBox cbLembrar;
    private TextView tvCadastrar;


    public static final String PREFS_NAME = "AppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean lembrar = prefs.getBoolean("LEMBRAR_DE_MIM", false);

        if (lembrar) {

            irParaMainActivity();
            return;
        }


        setContentView(R.layout.activity_login);


        etUsuario = findViewById(R.id.et_usuario_login);
        etSenha = findViewById(R.id.et_senha_login);
        btnLogin = findViewById(R.id.btn_login);
        cbLembrar = findViewById(R.id.cb_lembrar);
        tvCadastrar = findViewById(R.id.tv_cadastrar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });


        tvCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fazerLogin() {
        String usuario = etUsuario.getText().toString();
        String senha = etSenha.getText().toString();

        if (usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha usuário e senha", Toast.LENGTH_SHORT).show();
            return;
        }


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String usuarioSalvo = prefs.getString("USER_LOGIN", null);
        String senhaSalva = prefs.getString("USER_PASSWORD", null);


        if (usuario.equals(usuarioSalvo) && senha.equals(senhaSalva)) {



            if (cbLembrar.isChecked()) {

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("LEMBRAR_DE_MIM", true);
                editor.apply();
            }


            irParaMainActivity();

        } else if (usuarioSalvo == null) {
            Toast.makeText(this, "Nenhum usuário cadastrado. Por favor, cadastre-se.", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    private void irParaMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}