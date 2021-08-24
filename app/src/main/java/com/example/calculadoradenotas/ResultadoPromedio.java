package com.example.calculadoradenotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.calculadoradenotas.variables.Asignatura;

public class ResultadoPromedio extends AppCompatActivity {

    TextView textView;
    Asignatura asignatura;
    String textoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_promedio);
        textView = findViewById(R.id.textViewResPromedio);
        asignatura = (Asignatura) getIntent().getSerializableExtra("ASIGNATURA");



        if (asignatura.isRamoAprobado()){
            textoTextView = "Felicidades!! \n" +
                    "Haz aprobado: "+asignatura.nombreAsignatura +"\n" +
                    "Promedio Teórico: "+asignatura.getPromedioTeorico()+"\n" +
                    "Promedio Práctico: "+asignatura.getPromedioPractica()+"\n" +
                    "Promedio Final: "+asignatura.getPromedioTotal();

        }else {
            textoTextView = "Haz reprobado \n" +
                    "Asignatura: "+asignatura.nombreAsignatura+ "\n" +
                    "Promedio Teórico: "+asignatura.getPromedioTeorico()+"\n" +
                    "Promedio Práctico: "+asignatura.getPromedioPractica()+"\n" +
                    "Promedio Final: "+asignatura.getPromedioTotal();
        }
        textView.setText(textoTextView);
    }
}