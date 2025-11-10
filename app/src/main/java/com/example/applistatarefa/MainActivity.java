package com.example.applistatarefa;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements TarefaAdapter.OnTarefaClickListener {


    private EditText etNovaTarefa;
    private Button btnAdicionar;
    private ListView lvTarefas;
    private Button btnSair;
    private TextView tvBoasVindas;
    private TextView tvDataSelecionada;
    private TextView tvHoraSelecionada;

    private String dataSelecionada = null;
    private String horaSelecionada = null;

    private ArrayList<Tarefa> tarefas;
    private TarefaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etNovaTarefa = findViewById(R.id.et_nova_tarefa);
        btnAdicionar = findViewById(R.id.btn_adicionar);
        lvTarefas = findViewById(R.id.lv_tarefas);
        btnSair = findViewById(R.id.btn_sair);
        tvBoasVindas = findViewById(R.id.tv_boas_vindas);
        tvDataSelecionada = findViewById(R.id.tv_data_selecionada);
        tvHoraSelecionada = findViewById(R.id.tv_hora_selecionada);

        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        String usuario = prefs.getString("USER_LOGIN", "Usuário");
        tvBoasVindas.setText("Olá, " + usuario + "!");

        tarefas = new ArrayList<>();



        adapter = new TarefaAdapter(this, tarefas, this);


        lvTarefas.setAdapter(adapter);


        btnAdicionar.setOnClickListener(v -> adicionarTarefa());
        btnSair.setOnClickListener(v -> fazerLogout());
        tvDataSelecionada.setOnClickListener(v -> abrirSeletorData());
        tvHoraSelecionada.setOnClickListener(v -> abrirSeletorHora());
    }


    private void abrirSeletorData() {
        Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, anoSelecionado, mesSelecionado, diaSelecionado) -> {

                    dataSelecionada = String.format(Locale.getDefault(), "%02d/%02d/%d", diaSelecionado, (mesSelecionado + 1), anoSelecionado);
                    tvDataSelecionada.setText(dataSelecionada);
                }, ano, mes, dia);

        datePickerDialog.show();
    }

    private void abrirSeletorHora() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, horaSelecionada, minutoSelecionado) -> {

                    this.horaSelecionada = String.format(Locale.getDefault(), "%02d:%02d", horaSelecionada, minutoSelecionado);
                    tvHoraSelecionada.setText(this.horaSelecionada);
                }, hora, minuto, true);

        timePickerDialog.show();
    }


    private void adicionarTarefa() {
        String textoTarefa = etNovaTarefa.getText().toString();

        if (textoTarefa.isEmpty()) {
            Toast.makeText(this, R.string.main_error_no_text, Toast.LENGTH_SHORT).show();
            return;
        }
        if (dataSelecionada == null) {
            Toast.makeText(this, R.string.main_error_no_date, Toast.LENGTH_SHORT).show();
            return;
        }
        if (horaSelecionada == null) {
            Toast.makeText(this, R.string.main_error_no_time, Toast.LENGTH_SHORT).show();
            return;
        }

        Tarefa novaTarefa = new Tarefa(textoTarefa, dataSelecionada, horaSelecionada);
        tarefas.add(novaTarefa);
        adapter.notifyDataSetChanged();


        etNovaTarefa.setText("");
        tvDataSelecionada.setText(R.string.main_set_date);
        tvHoraSelecionada.setText(R.string.main_set_time);
        dataSelecionada = null;
        horaSelecionada = null;

        Toast.makeText(this, R.string.main_task_added_success, Toast.LENGTH_SHORT).show();
    }

    private void fazerLogout() {
        SharedPreferences prefs = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("LEMBRAR_DE_MIM", false);

        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    public void onEditClick(Tarefa tarefa, int position) {

        mostrarDialogoEditar(tarefa);
    }

    @Override
    public void onConcluirClick(Tarefa tarefa, int position) {

        tarefa.setConcluida(!tarefa.isConcluida());
        adapter.notifyDataSetChanged();

    }



    private void mostrarDialogoEditar(Tarefa tarefa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarefa");


        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_task, null);
        builder.setView(dialogView);


        EditText etEditarTexto = dialogView.findViewById(R.id.et_edit_texto);
        TextView tvEditarData = dialogView.findViewById(R.id.tv_edit_data);
        TextView tvEditarHora = dialogView.findViewById(R.id.tv_edit_hora);


        final String[] novaDataHolder = { tarefa.getData() };
        final String[] novaHoraHolder = { tarefa.getHora() };


        etEditarTexto.setText(tarefa.getTexto());
        tvEditarData.setText(tarefa.getData());
        tvEditarHora.setText(tarefa.getHora());


        tvEditarData.setOnClickListener(v -> {
            Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, anoSelecionado, mesSelecionado, diaSelecionado) -> {
                        String dataFormatada = String.format(Locale.getDefault(), "%02d/%02d/%d", diaSelecionado, (mesSelecionado + 1), anoSelecionado);
                        novaDataHolder[0] = dataFormatada;
                        tvEditarData.setText(dataFormatada);
                    }, ano, mes, dia);
            datePickerDialog.show();
        });


        tvEditarHora.setOnClickListener(v -> {
            Calendar calendario = Calendar.getInstance();
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, horaSelecionada, minutoSelecionado) -> {
                        String horaFormatada = String.format(Locale.getDefault(), "%02d:%02d", horaSelecionada, minutoSelecionado);
                        novaHoraHolder[0] = horaFormatada;
                        tvEditarHora.setText(horaFormatada);
                    }, hora, minuto, true);
            timePickerDialog.show();
        });



        builder.setPositiveButton("Salvar", (dialog, which) -> {

            String novoTexto = etEditarTexto.getText().toString();
            String novaData = novaDataHolder[0];
            String novaHora = novaHoraHolder[0];


            if (novoTexto.isEmpty()) {
                Toast.makeText(this, "O texto não pode ficar vazio.", Toast.LENGTH_SHORT).show();
                return;
            }


            tarefa.setTexto(novoTexto);
            tarefa.setData(novaData);
            tarefa.setHora(novaHora);


            adapter.notifyDataSetChanged();


            Toast.makeText(this, "Tarefa atualizada!", Toast.LENGTH_SHORT).show();
        });


        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
