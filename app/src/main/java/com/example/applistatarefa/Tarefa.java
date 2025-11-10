package com.example.applistatarefa;

public class Tarefa {

    private String texto;
    private String data;
    private String hora;
    private boolean concluida;

    public Tarefa(String texto, String data, String hora) {
        this.texto = texto;
        this.data = data;
        this.hora = hora;
        this.concluida = false;
    }


    public String getTexto() { return texto; }
    public String getData() { return data; }
    public String getHora() { return hora; }
    public boolean isConcluida() { return concluida; }


    public void setTexto(String texto) { this.texto = texto; }
    public void setData(String data) { this.data = data; }
    public void setHora(String hora) { this.hora = hora; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }
}