package com.example.calculadoradenotas;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calculadoradenotas.variables.Nota;

import java.util.ArrayList;

public class AdapterMostrarFaltantes extends RecyclerView.Adapter<AdapterMostrarFaltantes.ViewHolder> {

    ArrayList<Nota> notas;

    public AdapterMostrarFaltantes(ArrayList<Nota> notas) {
        this.notas = notas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notas_faltantes_view,null,false);
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
        TextView textViewNombreNota,textViewSeekBar;
        SeekBar seekBarNota;

        final int MINIMO_SEEKBAR = 10;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreNota = itemView.findViewById(R.id.textViewNomNotaFaltante);
            textViewSeekBar = itemView.findViewById(R.id.textViewSeekbarNotasFaltantes);
            seekBarNota = itemView.findViewById(R.id.seekBarNotasFaltantes);

            seekBarNota.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int progreso = MINIMO_SEEKBAR + seekBar.getProgress();
                    textViewSeekBar.setText(String.valueOf(progreso));
                    notas.get(getAdapterPosition()).setNota(progreso);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });



        }

        public void asignarDatos(Nota nota) {
            textViewNombreNota.setText(nota.getNombreNota());
            seekBarNota.setProgress((int) nota.getNota()-MINIMO_SEEKBAR);

            if (nota.getNota() == MINIMO_SEEKBAR){
                textViewSeekBar.setText(String.valueOf(MINIMO_SEEKBAR));
            }


        }
    }
}
