package com.example.lab2_20216256;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2_20216256.R;

import java.util.*;

public class QuizActivity extends AppCompatActivity {

    TextView txtPregunta, txtPuntaje;
    Button btnOpcion1, btnOpcion2, btnOpcion3, btnSiguiente, btnAnterior;
    int puntaje = 0;
    int preguntaActual = 0;
    int[] respuestasUsuario = {-1, -1, -1, -1, -1};
    ArrayList<Integer> indicesAleatorios = new ArrayList<>();
    String[] preguntas;
    String[][] opciones;
    int[] respuestas;

    private long tiempoInicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tiempoInicio = System.currentTimeMillis();

        txtPregunta = findViewById(R.id.txtPregunta);
        txtPuntaje = findViewById(R.id.txtPuntaje);
        btnOpcion1 = findViewById(R.id.btnOpcion1);
        btnOpcion2 = findViewById(R.id.btnOpcion2);
        btnOpcion3 = findViewById(R.id.btnOpcion3);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);

        String tema = getIntent().getStringExtra("tema");
        cargarPreguntas(tema);

        for (int i = 0; i < preguntas.length; i++) {
            indicesAleatorios.add(i);
        }
        Collections.shuffle(indicesAleatorios);
        mostrarPregunta();

        View.OnClickListener listener = v -> {
            int index = indicesAleatorios.get(preguntaActual);
            int seleccion = -1;
            if (v == btnOpcion1) seleccion = 0;
            if (v == btnOpcion2) seleccion = 1;
            if (v == btnOpcion3) seleccion = 2;

            if (seleccion == respuestas[index]) {
                puntaje += 2;
                v.setBackgroundColor(Color.parseColor("#A5D6A7")); // verde
            } else {
                puntaje -= 2;
                v.setBackgroundColor(Color.parseColor("#EF9A9A")); // rojo

            }

            // Desactivar todos
            btnOpcion1.setEnabled(false);
            btnOpcion2.setEnabled(false);
            btnOpcion3.setEnabled(false);
            btnSiguiente.setEnabled(true);
            txtPuntaje.setText("Puntaje: " + puntaje);
            respuestasUsuario[preguntaActual] = seleccion;
        };

        btnOpcion1.setOnClickListener(listener);
        btnOpcion2.setOnClickListener(listener);
        btnOpcion3.setOnClickListener(listener);

        //------------------------
        btnAnterior.setOnClickListener(v -> {
            if (preguntaActual > 0) {
                preguntaActual--;
                mostrarPregunta();
            }
        });
        //---------------------------


        btnSiguiente.setOnClickListener(v -> {
            preguntaActual++;
            if (preguntaActual < 5) {
                mostrarPregunta();
            } else {
                long tiempoFin = System.currentTimeMillis();
                long tiempoTotal = (tiempoFin - tiempoInicio) / 1000; // tiempo en segundos

                Intent intent = new Intent(QuizActivity.this, ResultadoActivity.class);
                intent.putExtra("tiempo", tiempoTotal);
                intent.putExtra("puntaje", puntaje);
                intent.putExtra("tema", tema);
                startActivity(intent);
                finish();
            }
        });
    }

    void mostrarPregunta() {
        int index = indicesAleatorios.get(preguntaActual);
        txtPregunta.setText("Pregunta " + (preguntaActual + 1) + ": " + preguntas[index]);
        btnOpcion1.setText(opciones[index][0]);
        btnOpcion2.setText(opciones[index][1]);
        btnOpcion3.setText(opciones[index][2]);

        // Reset
        btnOpcion1.setEnabled(true);
        btnOpcion2.setEnabled(true);
        btnOpcion3.setEnabled(true);
        btnSiguiente.setEnabled(false);

        btnOpcion1.setBackgroundColor(Color.LTGRAY);
        btnOpcion2.setBackgroundColor(Color.LTGRAY);
        btnOpcion3.setBackgroundColor(Color.LTGRAY);


        // si ya respondió
        int seleccion = respuestasUsuario[preguntaActual];
        if (seleccion != -1) {
            btnSiguiente.setEnabled(true);
            btnOpcion1.setEnabled(false);
            btnOpcion2.setEnabled(false);
            btnOpcion3.setEnabled(false);

            Button[] botones = {btnOpcion1, btnOpcion2, btnOpcion3};
            for (int i = 0; i < 3; i++) {
                if (i == seleccion) {
                    if (i == respuestas[index]) {
                        botones[i].setBackgroundColor(Color.parseColor("#A5D6A7")); // verde
                    } else {
                        botones[i].setBackgroundColor(Color.parseColor("#EF9A9A")); // rojo
                        botones[respuestas[index]].setBackgroundColor(Color.parseColor("#A5D6A7"));
                    }
                }
            }
        }
    }

    void cargarPreguntas(String tema) {
        if (tema.equals("Microondas")) {
            preguntas = new String[]{
                    "¿Qué frecuencias usa el microondas?",
                    "¿Qué tipo de antena usa el radar?",
                    "¿Qué medio usa el microondas?",
                    "¿Qué protocolo se usa en backhaul?",
                    "¿Cuál es el rango más usado en microondas?"
            };

            opciones = new String[][]{
                    {"10 GHz y 20 GHz", "2.4 GHz y 5 GHz", "900 MHz y 1.8 GHz"},
                    {"Parabólica", "Dipolo", "Patch"},
                    {"Aire", "Fibra óptica", "Cable coaxial"},
                    {"IP/MPLS", "HTTP", "LoRa"},
                    {"6 GHz a 42 GHz", "1 GHz a 2 GHz", "100 MHz a 500 MHz"}
            };

            respuestas = new int[]{1, 0, 0, 0, 0}; // índices de la opción correcta
        } else if (tema.equals("Redes")) {
            preguntas = new String[]{
                    "¿Qué protocolo se usa para páginas web?",
                    "¿Cuál es una IP privada?",
                    "¿Qué dispositivo conecta redes?",
                    "¿Qué capa maneja direcciones IP?",
                    "¿Qué significa DNS?"
            };

            opciones = new String[][]{
                    {"HTTP", "FTP", "SSH"},
                    {"192.168.1.1", "8.8.8.8", "200.1.1.1"},
                    {"Router", "Switch", "Hub"},
                    {"Capa de red", "Capa de transporte", "Capa física"},
                    {"Domain Name System", "Data Net Structure", "Digital Network Server"}
            };

            respuestas = new int[]{0, 0, 0, 0, 0};
        } else if (tema.equals("CiberSeguridad")) {
            preguntas = new String[]{
                    "¿Qué es un firewall?",
                    "¿Qué significa HTTPS?",
                    "¿Qué es el phishing?",
                    "¿Qué herramienta escanea vulnerabilidades?",
                    "¿Qué clave es más segura?"
            };

            opciones = new String[][]{
                    {"Filtro de red", "Virus", "Sistema operativo"},
                    {"Protocolo seguro", "Enlace de datos", "Servidor interno"},
                    {"Engaño digital", "Red privada", "Virus oculto"},
                    {"Nmap", "Excel", "PuTTY"},
                    {"123456", "P@ssw0rd2024!", "admin"}
            };

            respuestas = new int[]{0, 0, 0, 0, 1};
        } else {
            preguntas = new String[]{"Tema no disponible aún"};
            opciones = new String[][]{{"A", "B", "C"}};
            respuestas = new int[]{0};
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        SharedPreferences prefs = getSharedPreferences("historial", MODE_PRIVATE);
        int n = prefs.getInt("total_juegos", 0) + 1;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("juego_" + n, "Juego " + n + ": Canceló");
        editor.putInt("total_juegos", n);
        editor.apply();
        finish(); // Cierra esta Activity
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_estadisticas) {
            // Aquí abres la pantalla de estadísticas
            Intent intent = new Intent(this, EstadisticasActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        registrarCancelacion();
        super.onBackPressed();
    }

    private void registrarCancelacion() {
        SharedPreferences prefs = getSharedPreferences("historial", MODE_PRIVATE);
        int n = prefs.getInt("total_juegos", 0) + 1;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("juego_" + n, "Juego " + n + ": Canceló");
        editor.putInt("total_juegos", n);
        editor.apply();
    }


}
