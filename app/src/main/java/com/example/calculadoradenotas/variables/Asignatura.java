package com.example.calculadoradenotas.variables;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.Serializable;
import java.util.ArrayList;

public class Asignatura implements Serializable {
    public String nombreAsignatura;
    private ArrayList<Nota> notasAsignatura;

    private double promedioTeorico,promedioPractica,promedioTotal;
    public double porcentajeTeorico,porcentajePractico;

    private boolean ramoAprobado;

    public Asignatura(String nombreAsignatura, ArrayList<Nota> notasAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
        this.notasAsignatura = notasAsignatura;
        this.ramoAprobado = false;
    }

    public ArrayList<Nota> getNotasAsignatura() {
        return notasAsignatura;
    }

    public double getPromedioTeorico() {
        return promedioTeorico;
    }

    public double getPromedioPractica() {
        return promedioPractica;
    }

    public double getPromedioTotal() {
        return promedioTotal;
    }

    public void setNotasAsignatura(ArrayList<Nota> notasAsignatura) {
        this.notasAsignatura = notasAsignatura;
    }

    public void setPromedioTeorico(double promedioTeorico) {
        this.promedioTeorico = promedioTeorico;
    }

    public void setPromedioPractica(double promedioPractica) {
        this.promedioPractica = promedioPractica;
    }

    public void calcularPromedioFinal (){
        this.promedioTotal = 0;
        promedioTeorico = Math.round(promedioTeorico);
        promedioPractica = Math.round(promedioPractica);

        double porTeorico = this.porcentajeTeorico/100;
        double porPractico = this.porcentajePractico/100;

        this.promedioTotal = (porTeorico * this.promedioTeorico) +
                (porPractico * this.promedioPractica);

        //this.promedioTotal = Math.round(this.promedioTotal);

        if (this.promedioTotal >= 39.5){
            this.ramoAprobado = true;
        }else {
            this.ramoAprobado = false;
        }


    }

    public boolean isRamoAprobado() {
        return ramoAprobado;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void actualizarNotas(ArrayList<Nota> notasActualizadas){
        notasAsignatura.forEach(nota -> {
            for (Nota notaActualizada:notasActualizadas) {
                if (notaActualizada.getNombreNota().equals(nota.getNombreNota())){
                    nota.setNota((int) notaActualizada.getNota());
                }
            }
        });



    }

}
