package com.example.lab2_20216256;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EstadisticasActivity extends AppCompatActivity {

    TextView txtHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estadisticas);

        // Aplica padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Habilita flecha atrás en la ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtHistorial = findViewById(R.id.txtHistorial);

        SharedPreferences prefs = getSharedPreferences("historial", MODE_PRIVATE);
        int total = prefs.getInt("total_juegos", 0);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        for (int i = 1; i <= total; i++) {
            String linea = prefs.getString("juego_" + i, "");

            SpannableString textoColor = new SpannableString(linea + "\n\n");

            if (linea.contains("Canceló")) {
                textoColor.setSpan(new ForegroundColorSpan(Color.GRAY), 0, textoColor.length(), 0);
            } else if (linea.contains("Puntaje:")) {
                int index = linea.indexOf("Puntaje:");
                String puntajeStr = linea.substring(index + 9).trim()
                        .replace("s", "")
                        .replace(" ", "")
                        .replace("\n", "");

                int puntaje;
                try {
                    puntaje = Integer.parseInt(puntajeStr);
                } catch (NumberFormatException e) {
                    puntaje = 0;
                }

                int color = puntaje >= 0 ? Color.parseColor("#388E3C") : Color.parseColor("#D32F2F");
                textoColor.setSpan(new ForegroundColorSpan(color), 0, textoColor.length(), 0);
            }

            builder.append(textoColor);
        }

        txtHistorial.setText(builder);
    }

    // Flecha atrás del ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
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
}
