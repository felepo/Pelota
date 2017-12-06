package com.felepo.pelota;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class Gestion extends AppCompatActivity
{
    private Partida partida;
    private int dificultad;
    private int FPS = 30;
    private Handler temporizador;
    private int toques;             //Variable para guardar el record de toques

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        toques = 0;

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("DIFICULTAD");

        partida = new Partida(getApplicationContext(), dificultad);
        setContentView(partida);

        //Esto sirve para especificar en cuanto tiempo la pelota empieza a caer
        temporizador = new Handler();
        temporizador.postDelayed(elHilo, 2000);
    }

    private Runnable elHilo = new Runnable() {
        @Override
        public void run() {
            if( partida.movimientoBola() )
            {
                fin();
            }
            else
            {
                //Elimina el contedio del ImageView y llama de nuevo a onDraw()
                partida.invalidate();

                temporizador.postDelayed(elHilo, 1000/FPS);
            }
        }
    };

    public boolean onTouchEvent(MotionEvent evento)
    {
        int x = (int) evento.getX();
        int y = (int) evento.getY();

        //Si el toque es correcto, entonces se aumenta la variable
        if( partida.toque(x, y) )
        {
            toques++;
        }

        return false;
    }

    public void fin()
    {
        //Limpia el hilo que se usó
        temporizador.removeCallbacks(elHilo);

        //Antes de destruir el activity, se manda el record con el bundle
        Intent in = new Intent();
        in.putExtra("PUNTUACION", toques);

        setResult(RESULT_OK, in);

        //Destruye el activity actual
        finish();
    }
}
