package com.example.notasapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    double promedio = 0;
    int contador = 0;
    LinearLayout llHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CicloVida", "onCreate: se crea la pantalla");
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etNota1 = findViewById(R.id.etNota1);
        EditText etNota2 = findViewById(R.id.etNota2);
        EditText etNota3 = findViewById(R.id.etNota3);
        RadioButton rb35 = findViewById(R.id.rb35);
        CheckBox cbHistorial = findViewById(R.id.cbHistorial);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        TextView tvResultado = findViewById(R.id.tvResultado);
        Button btnDetalle = findViewById(R.id.btnDetalle);
        llHistorial = findViewById(R.id.llHistorial);

        etNota3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvResultado.setVisibility(View.GONE);
                btnDetalle.setVisibility(View.GONE);
                return false;
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto1 = etNota1.getText().toString();
                String texto2 = etNota2.getText().toString();
                String texto3 = etNota3.getText().toString();

                if (texto1.isEmpty() || texto2.isEmpty() || texto3.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Escribe las tres notas", Toast.LENGTH_SHORT).show();
                    tvResultado.setVisibility(View.GONE);
                    btnDetalle.setVisibility(View.GONE);
                } else {
                    double nota1 = Double.parseDouble(texto1);
                    double nota2 = Double.parseDouble(texto2);
                    double nota3 = Double.parseDouble(texto3);

                    promedio = (nota1 + nota2 + nota3) / 3;

                    double notaMinima = 3.0;
                    if (rb35.isChecked()) {
                        notaMinima = 3.5;
                    }

                    String mensaje;
                    if (promedio >= notaMinima) {
                        mensaje = "¡Aprobaste! ";
                        tvResultado.setTextColor(Color.parseColor("#15803D"));
                        btnDetalle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#16A34A")));
                    } else {
                        mensaje = "Sigue intentando ";
                        tvResultado.setTextColor(Color.parseColor("#B91C1C"));
                        btnDetalle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DC2626")));
                    }

                    tvResultado.setTextSize(20);
                    tvResultado.setText("Tu promedio es: " + String.format("%.2f", promedio) + "\n" + mensaje);
                    tvResultado.append("\nNota mínima: " + notaMinima);
                    tvResultado.setVisibility(View.VISIBLE);
                    btnDetalle.setVisibility(View.VISIBLE);

                    if (cbHistorial.isChecked()) {
                        contador++;
                        print(contador + ". Promedio: " + String.format("%.2f", promedio));
                    }
                }
            }
        });

        btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultadoActivity.class);
                intent.putExtra("promedio", promedio);
                startActivity(intent);
            }
        });
    }

    private void print(String texto) {
        TextView linea = new TextView(this);
        linea.setText(texto);
        linea.setTextSize(15);
        linea.setTextColor(Color.parseColor("#334155"));
        linea.setPadding(0, 8, 0, 8);
        llHistorial.addView(linea);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CicloVida", "onStart: la pantalla se va a mostrar");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CicloVida", "onResume: la pantalla está activa");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CicloVida", "onPause: la pantalla pierde el foco");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CicloVida", "onStop: la pantalla ya no se ve");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CicloVida", "onDestroy: la pantalla se destruye");
    }
}