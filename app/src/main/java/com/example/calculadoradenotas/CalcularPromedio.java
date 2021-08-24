package com.example.calculadoradenotas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculadoradenotas.herramientas.CalculadoraNotas;
import com.example.calculadoradenotas.variables.Asignatura;
import com.example.calculadoradenotas.variables.Nota;

import java.util.ArrayList;

public class CalcularPromedio extends AppCompatActivity {

    Asignatura asignatura;
    ArrayList<Nota> notasFaltantes;
    CalculadoraNotas calculadoraNotas = new CalculadoraNotas();
    TextView textViewResultado,textViewPromedioActual;
    RecyclerView recyclerView;
    Button buttonRecalcular;
    AdapterMostrarFaltantes adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_promedio);

        Intent intent = getIntent();
        asignatura = (Asignatura) intent.getSerializableExtra("ASIGNATURA");
        notasFaltantes = calculadoraNotas.obtenerNotasFaltantes(asignatura);
        asignatura = calculadoraNotas.asignatura;
        textViewResultado = findViewById(R.id.textViewResCalcularPromedio);
        textViewPromedioActual = findViewById(R.id.textViewCalcularPromedioProm);
        buttonRecalcular = findViewById(R.id.buttonCalcularPromedio);


        String resultado;
        if (asignatura.isRamoAprobado()){
            resultado = "Aún es posible aprobar";

        }else {
            resultado = "No es posible aprobar";
        }
        textViewResultado.setText(resultado);
        textViewPromedioActual.setText(String.valueOf(asignatura.getPromedioTotal()));



        recyclerView = findViewById(R.id.recyclerViewCalcularPromedio);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new AdapterMostrarFaltantes(notasFaltantes);
        recyclerView.setAdapter(adapter);


        buttonRecalcular.setOnClickListener(view -> {
            ArrayList<Nota> notasActualizadas = adapter.notas;
            asignatura.actualizarNotas(notasActualizadas);
            asignatura.setPromedioTeorico(
                    calculadoraNotas.calcularPromedioTeorico(asignatura.getNotasAsignatura()));
            asignatura.setPromedioPractica(
                    calculadoraNotas.calcularPromedioPractico(asignatura.getNotasAsignatura()));
            asignatura.calcularPromedioFinal();
            textViewPromedioActual.setText(String.valueOf(asignatura.getPromedioTotal()));
        });



    }

}