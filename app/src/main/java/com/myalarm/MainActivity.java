package com.myalarm;

import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int counter = 0;

    private TextView contadorTextView;
    private FloatingActionButton botonIncrementar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular las vistas con el layout XML
        contadorTextView = findViewById(R.id.counterText);
        botonIncrementar = findViewById(R.id.counterButton);

        // Configurar el evento de clic en el botón
        botonIncrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Incrementar el contador
                counter++;

                // Actualizar el texto del TextView
                contadorTextView.setText("Counter: " + counter);
            }
        });
    }
}
