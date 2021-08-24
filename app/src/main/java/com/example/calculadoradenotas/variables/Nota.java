package com.example.calculadoradenotas.variables;

import java.io.Serializable;

public class Nota implements Serializable {
    private final String nombreNota;
    private int nota;
    private boolean notaConocida;
    private final boolean teorica;
    private int porcentaje;

    private int incidenciaNota;

    public int getIncidenciaNota() {
        return incidenciaNota;
    }

    public void setIncidenciaNota(int incidenciaNota) {
        this.incidenciaNota = incidenciaNota;
    }





    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Nota(String nombreNota, boolean teorica) {
        this.nombreNota = nombreNota;
        this.teorica = teorica;
        this.notaConocida = true;
        this.nota = 10;
        this.porcentaje = 0;
    }


    public double getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public boolean isNotaConocida() {
        return notaConocida;
    }

    public void setNotaConocida(boolean notaConocida) {
        this.notaConocida = notaConocida;
    }


    public String getNombreNota() {
        return nombreNota;
    }

    public boolean isTeorica() {
        return teorica;
    }
}




