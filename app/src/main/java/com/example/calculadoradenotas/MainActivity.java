package com.example.calculadoradenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calculadoradenotas.variables.Asignatura;
import com.example.calculadoradenotas.variables.Nota;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonNext = findViewById(R.id.buttonSiguiente);


        buttonNext.setOnClickListener(view -> {
            boolean datosCorrectos = true;
            int cantNotasTeoricas = 1,cantNotasPracticas = 0;
            String nombreAsignatura = "";
            //Declarar los EditText a usar
            EditText editTextNombreAsignatura = findViewById(R.id.editTextNombreAsignatura);
            EditText editTextNotasTeoricas = findViewById(R.id.editTextNotasTeoricas);
            EditText editTextNotasPracticas = findViewById(R.id.editTextNotasPracticas);


            //Obtener valores y transformarlos segun lo necesitado

            String[] errores = getResources().getStringArray(R.array.error_falta_informacion);


            if (TextUtils.isEmpty(editTextNombreAsignatura.getText().toString())){
                datosCorrectos = false;
                Toast.makeText(getApplicationContext(),errores[0],Toast.LENGTH_SHORT).show();
            }else {
                nombreAsignatura = editTextNombreAsignatura.getText().toString();
            }


            if (TextUtils.isEmpty(editTextNotasTeoricas.getText().toString())){
                datosCorrectos = false;
                Toast.makeText(getApplicationContext(),errores[1],Toast.LENGTH_SHORT).show();
            }else {
                cantNotasTeoricas = Integer.parseInt(editTextNotasTeoricas.getText().toString());
            }


            if (TextUtils.isEmpty(editTextNotasPracticas.getText().toString())){
                datosCorrectos = false;
                Toast.makeText(getApplicationContext(),errores[2],Toast.LENGTH_SHORT).show();
            }else {
                cantNotasPracticas = Integer.parseInt(editTextNotasPracticas.getText().toString());
            }

            //Crear Notas Teoricas con nombres
            ArrayList<Nota> notas = new ArrayList<>();

            for (int i = 0 ; i < cantNotasTeoricas ; i++ ){
                String nombreNotaTeorica = "Nota teórica "+(i+1);
                Nota nota = new Nota(nombreNotaTeorica,true);
                notas.add(nota);
            }

            //Crear Notas Practicas con nombres

            for (int i = 0; i < cantNotasPracticas; i++){
                String nombreNotaPractica = "Nota Práctica "+(i+1);
                Nota nota = new Nota(nombreNotaPractica,false);
                notas.add(nota);
            }

            //Crear variable asignatura

            Asignatura asignatura = new Asignatura(nombreAsignatura,notas);



            Intent intent = new Intent(getApplicationContext(),RellenarNotas.class);
            intent.putExtra("ASIGNATURA",asignatura);

            if (datosCorrectos){
                startActivity(intent);
            }



        });

    }
}