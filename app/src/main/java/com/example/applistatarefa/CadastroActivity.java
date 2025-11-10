package com.example.applistatarefa;

import android.content.Intent; 
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; 
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {


    private EditText etUsuario, etSenha, etConfirmarSenha;
    private Button btnCadastrar;
    private TextView tvFazerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        etUsuario = findViewById(R.id.et_usuario_cadastro);
        etSenha = findViewById(R.id.et_senha_cadastro);

        etConfirmarSenha = findViewById(R.id.et_confirmar_senha_cadastro);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        tvFazerLogin = findViewById(R.id.tv_fazer_login);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String senha = etSenha.getText().toString();
                String confirmarSenha = etConfirmarSenha.getText().toString();


                if (usuario.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!senha.equals(confirmarSenha)) {
                    Toast.makeText(CadastroActivity.this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
                    return;
                }


                SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("USER_LOGIN", usuario);
                editor.putString("USER_PASSWORD", senha);
                editor.putBoolean("LEMBRAR_DE_MIM", false);

                editor.apply();

                Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();


                finish();
            }
        });


        tvFazerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);


                startActivity(intent);


            }
        });
    }
}
