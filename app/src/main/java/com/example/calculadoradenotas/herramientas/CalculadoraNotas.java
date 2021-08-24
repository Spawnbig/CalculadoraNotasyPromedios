package com.example.calculadoradenotas.herramientas;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.calculadoradenotas.variables.Asignatura;
import com.example.calculadoradenotas.variables.Nota;
import java.util.ArrayList;


public class CalculadoraNotas {
    private final int NOTA_MAX_CONS=55;
    private final int NOTA_MAX = 70;
    private final int NOTA_MINIMA=10;
    public Asignatura asignatura;

    public double calcularPromedioTeorico(ArrayList<Nota> notas){
        double promedio = 0;

        for (Nota n : notas) {
            if (n.isTeorica()){
                promedio += (n.getNota() * ((double) n.getPorcentaje() / 100));
            }
        }

        return promedio;
    }

    public double calcularPromedioPractico(ArrayList<Nota> notas){
        double promedio = 0;

        for (Nota n : notas) {
            if (!n.isTeorica()){
                promedio += (n.getNota() * ((double) n.getPorcentaje() / 100));
            }
        }

        return promedio;
    }

    public int obtenerPorcentajeTeoricoAcum(ArrayList<Nota> notas){
        int porcentaje = 0;

        for (Nota n:notas) {
            if (n.isTeorica()) porcentaje += n.getPorcentaje() ;
        }
        return porcentaje;
    }

    public int obtenerPorcentajePracticoAcum(ArrayList<Nota> notas){
        int porcentaje = 0;

        for (Nota n:notas) {
            if (!n.isTeorica()) porcentaje += n.getPorcentaje() ;
        }
        return porcentaje;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Nota> obtenerNotasFaltantes(Asignatura a) {

        this.asignatura = a;
        ArrayList<Nota> notasFaltantes = new ArrayList<>();

        for (Nota n: this.asignatura.getNotasAsignatura()) {
            if (!n.isNotaConocida()) notasFaltantes.add(n);
        }

        if (notasFaltantes.size() == 1) {
            calcularUnaNota(notasFaltantes);
        } else {
            ordenarNotasFaltantes(notasFaltantes);
            calcularNotasFaltantes(notasFaltantes);
        }
        return notasFaltantes;
    }

    private void calcularUnaNota(ArrayList<Nota> notasFaltantes) {
        int posNota = 0;

        for (int i = 0; i < asignatura.getNotasAsignatura().size(); i++) {
            if (notasFaltantes.get(0).getNombreNota().equals(asignatura.getNotasAsignatura().get(i).getNombreNota())){
                posNota = i;
            }
        }

        double PROMEDIO_APROBADO = 39.5;
        if (asignatura.getNotasAsignatura().get(posNota).isTeorica()){
            asignatura.setPromedioPractica(calcularPromedioPractico(asignatura.getNotasAsignatura()));
            int nota_test = NOTA_MINIMA;

            do {
                asignatura.getNotasAsignatura().get(posNota).setNota(nota_test);
                notasFaltantes.get(0).setNota(nota_test);
                asignatura.setPromedioTeorico(calcularPromedioTeorico(asignatura.getNotasAsignatura()));
                asignatura.calcularPromedioFinal();

                if (asignatura.getPromedioTotal() < PROMEDIO_APROBADO){
                    nota_test++;
                }
            }while (!asignatura.isRamoAprobado() && nota_test <= 70);


        }else {
            asignatura.setPromedioTeorico(calcularPromedioTeorico(asignatura.getNotasAsignatura()));

            int nota_test = NOTA_MINIMA;

            do {
                asignatura.getNotasAsignatura().get(posNota).setNota(nota_test);
                notasFaltantes.get(0).setNota(nota_test);
                asignatura.setPromedioPractica(calcularPromedioPractico(asignatura.getNotasAsignatura()));
                asignatura.calcularPromedioFinal();
                if (asignatura.getPromedioTotal() < PROMEDIO_APROBADO){
                    nota_test++;
                }

            }while (!asignatura.isRamoAprobado() && nota_test <= 70);
        }






    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ordenarNotasFaltantes(ArrayList<Nota> notasFaltantes) {
        //Ordenar Notas de mayor a menor incidencia

        notasFaltantes.forEach(nota -> {
            int incidencia;
            if (nota.isTeorica()){
                incidencia = (int) asignatura.porcentajeTeorico;
            }else {
                incidencia = (int) asignatura.porcentajePractico;
            }
            incidencia = incidencia * nota.getPorcentaje();
            nota.setNota(NOTA_MINIMA);
            nota.setIncidenciaNota(incidencia);
        });


        notasFaltantes.sort((nota1, nota2) -> (int) (nota2.getIncidenciaNota()-nota1.getIncidenciaNota()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calcularNotasFaltantes(ArrayList<Nota> notasFaltantes) {

        notasFaltantes.forEach(notaActualizada -> {

            for (Nota notaAnterior: asignatura.getNotasAsignatura()){
                int nota_test = NOTA_MINIMA;
                if (notaActualizada.getNombreNota().equals(notaAnterior.getNombreNota())){
                    int indexAsignatura = asignatura.getNotasAsignatura().indexOf(notaAnterior);
                    int indexNotasFaltantes = notasFaltantes.indexOf(notaActualizada);
                    do {
                        asignatura.getNotasAsignatura().get(indexAsignatura).setNota(nota_test);
                        notasFaltantes.get(indexNotasFaltantes).setNota(nota_test);
                        asignatura.setPromedioTeorico(
                                calcularPromedioTeorico(asignatura.getNotasAsignatura()));
                        asignatura.setPromedioPractica(
                                calcularPromedioPractico(asignatura.getNotasAsignatura()));
                        asignatura.calcularPromedioFinal();

                        if(!asignatura.isRamoAprobado()){
                            nota_test++;
                        }
                    }while (!asignatura.isRamoAprobado() && nota_test <= NOTA_MAX_CONS);
                }
            }
        });

        if (!asignatura.isRamoAprobado()){

            //Re-hacer loop con nota max 70
            notasFaltantes.forEach(notaActualizada -> {

                for (Nota notaAnterior: asignatura.getNotasAsignatura()){
                    int nota_test = NOTA_MINIMA;
                    if (notaActualizada.getNombreNota().equals(notaAnterior.getNombreNota())){
                        int indexAsignatura = asignatura.getNotasAsignatura().indexOf(notaAnterior);
                        int indexNotasFaltantes = notasFaltantes.indexOf(notaActualizada);
                        do {
                            asignatura.getNotasAsignatura().get(indexAsignatura).setNota(nota_test);
                            notasFaltantes.get(indexNotasFaltantes).setNota(nota_test);
                            asignatura.setPromedioTeorico(
                                    calcularPromedioTeorico(asignatura.getNotasAsignatura()));
                            asignatura.setPromedioPractica(
                                    calcularPromedioPractico(asignatura.getNotasAsignatura()));
                            asignatura.calcularPromedioFinal();

                            if(!asignatura.isRamoAprobado()){
                                nota_test++;
                            }
                        }while (!asignatura.isRamoAprobado() && nota_test <= NOTA_MAX);
                    }
                }
            });

        }


    }


}
