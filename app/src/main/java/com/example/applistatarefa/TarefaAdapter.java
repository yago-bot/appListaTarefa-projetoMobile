package com.example.applistatarefa;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
// Remova o Toast e AlertDialog, o Adapter não vai mais lidar com isso
// import android.widget.Toast;
// import androidx.appcompat.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    // --- 1. CRIE A INTERFACE ("PONTE") ---
    // Esta interface será a "ponte" para a MainActivity
    public interface OnTarefaClickListener {
        void onEditClick(Tarefa tarefa, int position);
        void onConcluirClick(Tarefa tarefa, int position);
    }
    // ------------------------------------

    // --- 2. ADICIONE UMA VARIÁVEL PARA O LISTENER ---
    private OnTarefaClickListener listener;
    // ------------------------------------


    // --- 3. ATUALIZE O CONSTRUTOR ---
    // Ele agora precisa receber o 'listener' da MainActivity
    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas, OnTarefaClickListener listener) {
        super(context, 0, tarefas);
        this.listener = listener; // Salva o listener
    }
    // ------------------------------------

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Tarefa tarefa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tarefa, parent, false);
        }

        TextView tvTexto = convertView.findViewById(R.id.tv_item_texto);
        TextView tvData = convertView.findViewById(R.id.tv_item_data);
        TextView tvHora = convertView.findViewById(R.id.tv_item_hora);
        Button btnEditar = convertView.findViewById(R.id.btn_item_editar);
        Button btnConcluir = convertView.findViewById(R.id.btn_item_concluir);

        tvTexto.setText(tarefa.getTexto());
        tvData.setText(tarefa.getData());
        tvHora.setText(tarefa.getHora());

        // Seu código para riscar o texto (está correto)
        if (tarefa.isConcluida()) {
            tvTexto.setPaintFlags(tvTexto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            btnConcluir.setText("Restaurar");
            // Corrija esta linha - você tinha holo_green_light
            btnConcluir.setTextColor(getContext().getResources().getColor(android.R.color.holo_orange_light));
        } else {
            tvTexto.setPaintFlags(tvTexto.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            btnConcluir.setText("Concluir");
            btnConcluir.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark)); // Mudei para dark
        }


        // --- 4. ATUALIZE OS LISTENERS ---
        btnConcluir.setOnClickListener(v -> {
            if (listener != null) {
                // Avisa a MainActivity que o botão "Concluir" foi clicado
                listener.onConcluirClick(tarefa, position);
            }
        });

        btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                // Avisa a MainActivity que o botão "Editar" foi clicado
                listener.onEditClick(tarefa, position);
            }
        });
        // ------------------------------------

        return convertView;
    }
}