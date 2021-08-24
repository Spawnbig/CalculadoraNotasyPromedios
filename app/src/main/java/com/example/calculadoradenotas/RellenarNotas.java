package com.example.calculadoradenotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calculadoradenotas.herramientas.CalculadoraNotas;
import com.example.calculadoradenotas.variables.Asignatura;
import com.example.calculadoradenotas.variables.Nota;

import java.util.ArrayList;

public class RellenarNotas extends AppCompatActivity {

    Asignatura asignatura;
    RecyclerView recyclerView;
    Button buttonRellenar;
    EditText editTextPorcentajeTeorica;
    EditText editTextPorcentajePractica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rellenar_notas);

        asignatura = (Asignatura) getIntent().getSerializableExtra("ASIGNATURA");
        recyclerView = findViewById(R.id.recyclerViewRellenarNotas);

        editTextPorcentajeTeorica = findViewById(R.id.editTextPorcentajeTeoricas);
        editTextPorcentajePractica = findViewById(R.id.editTextPorcentajePracticas);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        AdapterRellenarNotas adapterRellenarNotas = new AdapterRellenarNotas(asignatura.getNotasAsignatura());
        recyclerView.setAdapter(adapterRellenarNotas);


        buttonRellenar = findViewById(R.id.buttonRellenar);
        buttonRellenar.setOnClickListener(view -> {
            boolean datosCorrectos = true;
            boolean notasCompletas = true;

            double porcentajeTeorico = Double.parseDouble(editTextPorcentajeTeorica.getText().toString());
            double porcentajePractico = Double.parseDouble(editTextPorcentajePractica.getText().toString());


            //Verificar Porcentaje
            if ((porcentajePractico+porcentajeTeorico) != 100 ){
                String error = "Porcentajes deben sumar 100";
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                datosCorrectos = false;
            }

            ArrayList<Nota> notasFinal = adapterRellenarNotas.getNotas();
            int porcentajeTeoricoAcum, porcentajePracticoAcum;

            //Iniciamos la calculadora
            CalculadoraNotas calculadoraNotas = new CalculadoraNotas();


            porcentajeTeoricoAcum = calculadoraNotas.obtenerPorcentajeTeoricoAcum(notasFinal);
            porcentajePracticoAcum = calculadoraNotas.obtenerPorcentajePracticoAcum(notasFinal);


            if (porcentajeTeoricoAcum != 100){
                //Error en el porcentaje de notas teoricas
                String error = "Error en los porcentajes de notas Teóricas";
                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                datosCorrectos = false;

            }else if (porcentajePracticoAcum != 100){
                //Error en el porcentaje de notas Practicas
                String error = "Error en los porcentajes de notas Prácticas";

                Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
                datosCorrectos = false;

            }

            //Verificar notas desconocidas
            for (Nota n: asignatura.getNotasAsignatura()) {
                if (!n.isNotaConocida()) {
                    notasCompletas = false;
                    break;
                }
            }


            if (datosCorrectos) {
                //Verificar las notas no conocidas
                asignatura.porcentajeTeorico = porcentajeTeorico;
                asignatura.porcentajePractico = porcentajePractico;

                if (notasCompletas) {
                    //Calculo normal
                    asignatura.setNotasAsignatura(notasFinal);
                    //Iniciar Activity dando el resultado del promedio

                    asignatura.setPromedioTeorico(
                            Math.round(calculadoraNotas.calcularPromedioTeorico
                            (notasFinal)));
                    asignatura.setPromedioPractica(
                            Math.round(calculadoraNotas.calcularPromedioPractico
                            (notasFinal)));
                    asignatura.calcularPromedioFinal();

                    Intent intent = new Intent(getApplicationContext(),ResultadoPromedio.class);
                    intent.putExtra("ASIGNATURA",asignatura);
                    startActivity(intent);


                } else {
                    //Iniciar Activity calculo de promedio
                    Intent intent = new Intent(getApplicationContext(),CalcularPromedio.class);
                    intent.putExtra("ASIGNATURA",asignatura);


                    startActivity(intent);



                }



            }



        });



    }
}