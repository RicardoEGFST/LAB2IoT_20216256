package com.example.lab2_20216256;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    TextView txtPuntajeFinal, txtTema;
    Button btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        // Referenciar vistas
        txtPuntajeFinal = findViewById(R.id.txtPuntajeFinal);
        txtTema = findViewById(R.id.txtTema);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        // Obtener datos del intent
        int puntaje = getIntent().getIntExtra("puntaje", 0);
        String tema = getIntent().getStringExtra("tema");
        long tiempo = getIntent().getLongExtra("tiempo", 0);
        // Mostrar el tema
        txtTema.setText(tema);

        // Mostrar el puntaje
        txtPuntajeFinal.setText(String.valueOf(puntaje));

        // Cambiar el color según positivo o negativo
        if (puntaje >= 0) {
            txtPuntajeFinal.setBackgroundColor(Color.parseColor("#C8E6C9")); // Verde claro
            txtPuntajeFinal.setTextColor(Color.BLACK);
        } else {
            txtPuntajeFinal.setBackgroundColor(Color.parseColor("#FFCDD2")); // Rojo claro
            txtPuntajeFinal.setTextColor(Color.BLACK);
        }

        //----------------------------------------------
        SharedPreferences prefs = getSharedPreferences("historial", MODE_PRIVATE);
        int n = prefs.getInt("total_juegos", 0) + 1;

        String nuevo = "Juego " + n + ": " + tema + " | Tiempo: " + tiempo + "s | Puntaje: " + puntaje;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("juego_" + n, nuevo);
        editor.putInt("total_juegos", n);
        editor.apply();

        //------------------------------------------


        // Botón Anterior: volver al quiz o main
        btnAnterior.setOnClickListener(v -> {
            finish(); // Regresa a la actividad anterior
        });

        // Botón Siguiente: deshabilitado por ahora
        btnSiguiente.setEnabled(false);
    }
}
