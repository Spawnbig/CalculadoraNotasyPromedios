package com.example.calculadoradenotas;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadoradenotas.variables.Nota;

import java.util.ArrayList;

public class AdapterRellenarNotas extends RecyclerView.Adapter<AdapterRellenarNotas.ViewHolder> {

    ArrayList<Nota> notas;


    public AdapterRellenarNotas(ArrayList<Nota> notas) {
        this.notas = notas;

    }
    public ArrayList<Nota> getNotas() {
        return notas;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rellenar_notas_view,null,false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignarDatos(notas.get(position));

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombreNota,textViewValorNota,textViewPorcentaje;
        CheckBox checkBoxSaberNota;
        SeekBar seekBarNota,seekBarPorcentaje;

        final int MINIMO_SEEKBAR = 10;
        final int MINIMO_PORCENTAJE = 1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreNota = itemView.findViewById(R.id.textViewNombreNota);
            checkBoxSaberNota = itemView.findViewById(R.id.checkBoxSaberNota);
            checkBoxSaberNota.setText("?");
            seekBarNota = itemView.findViewById(R.id.seekBarNota);

            textViewPorcentaje = itemView.findViewById(R.id.textViewPorcentaje);

            textViewValorNota = itemView.findViewById(R.id.textViewValorNota);
            textViewValorNota.setText(String.valueOf(MINIMO_SEEKBAR));

            seekBarPorcentaje = itemView.findViewById(R.id.seekBarPorcentaje);


            seekBarNota.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    int progreso = MINIMO_SEEKBAR + seekBar.getProgress();
                    textViewValorNota.setText(String.valueOf(progreso));
                    notas.get(getAdapterPosition()).setNota(progreso);


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            seekBarPorcentaje.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int progreso = MINIMO_PORCENTAJE + seekBar.getProgress();
                    String strProgreso = (progreso) + " %";
                    textViewPorcentaje.setText(strProgreso);


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    int progreso = MINIMO_PORCENTAJE + seekBar.getProgress();
                    notas.get(getAdapterPosition()).setPorcentaje(progreso);

                }
            });



            checkBoxSaberNota.setOnCheckedChangeListener((compoundButton, b) -> {
                if (checkBoxSaberNota.isChecked()){
                 //En caso de no saber nota
                    textViewValorNota.setText(String.valueOf(MINIMO_SEEKBAR));
                    seekBarNota.setProgress(0);
                    notas.get(getAdapterPosition()).setNotaConocida(false);
                    seekBarNota.setEnabled(false);

                }else {
                    seekBarNota.setEnabled(true);
                    seekBarNota.setProgress(0);
                    textViewValorNota.setText(String.valueOf(10));
                    notas.get(getAdapterPosition()).setNotaConocida(true);


                }
                notas.get(getAdapterPosition()).setNota(Integer.parseInt(textViewValorNota.getText().toString()));


            });


        }

        public void asignarDatos(Nota nota) {
            textViewNombreNota.setText(nota.getNombreNota());
        }
    }
}
