package com.example.applistatarefa;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    
    public interface OnTarefaClickListener {
        void onEditClick(Tarefa tarefa, int position);
        void onConcluirClick(Tarefa tarefa, int position);
    }
    

    
    private OnTarefaClickListener listener;
    


    
    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas, OnTarefaClickListener listener) {
        super(context, 0, tarefas);
        this.listener = listener; 
    }
    

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

      
        if (tarefa.isConcluida()) {
            tvTexto.setPaintFlags(tvTexto.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            btnConcluir.setText("Restaurar");
            
            btnConcluir.setTextColor(getContext().getResources().getColor(android.R.color.holo_orange_light));
        } else {
            tvTexto.setPaintFlags(tvTexto.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            btnConcluir.setText("Concluir");
            btnConcluir.setTextColor(getContext().getResources().getColor(android.R.color.holo_green_dark)); 
        }


       
        btnConcluir.setOnClickListener(v -> {
            if (listener != null) {
                
                listener.onConcluirClick(tarefa, position);
            }
        });

        btnEditar.setOnClickListener(v -> {
            if (listener != null) {
               
                listener.onEditClick(tarefa, position);
            }
        });
        

        return convertView;
    }
}
