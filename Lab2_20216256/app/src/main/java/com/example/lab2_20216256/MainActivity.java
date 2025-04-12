package com.example.lab2_20216256;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //SharedPreferences prefs = getSharedPreferences("historial", MODE_PRIVATE);
        //prefs.edit().clear().apply();
        //Estaas dos lineas son para borrar el historial cada que cierras y abres la app
        //me pareció interesante xd
        setTitle("AppSIoT - Lab 2");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //----------------------------------------------------------------
        Button btnRedes = findViewById(R.id.btnRedes);
        Button btnCiber = findViewById(R.id.btnCiber);
        Button btnMicro = findViewById(R.id.btnMicro);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tema = "";

                if (v.getId() == R.id.btnRedes) {
                    tema = "Redes";
                } else if (v.getId() == R.id.btnCiber) {
                    tema = "CiberSeguridad";
                } else if (v.getId() == R.id.btnMicro) {
                    tema = "Microondas";
                }

                // Crear el Intent explícito
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                // Enviar el tema como "extra" (clave-valor)
                intent.putExtra("tema", tema);
                startActivity(intent); // Lanzar nueva actividad
                Log.d("MainActivity", "Button Clicked");

            }
        };

        btnRedes.setOnClickListener(listener);
        btnCiber.setOnClickListener(listener);
        btnMicro.setOnClickListener(listener);
        //-----------------------------------------------------------------------



    }



}