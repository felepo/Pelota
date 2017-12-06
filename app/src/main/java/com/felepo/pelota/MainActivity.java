package com.felepo.pelota;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    int record;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ayuda(View vista)
    {
        Intent intencion = new Intent(this, AyudaActivity.class);
        startActivity(intencion);
    }

    public void dificultad(View vista)
    {
        String dific = (String) ((Button) vista).getText();

        int dificultad = 1;

        if( dific.equals(getString(R.string.medio)) ) dificultad = 2;
        if( dific.equals(getString(R.string.dificil)) ) dificultad = 3;

        Intent in = new Intent(this, Gestion.class);
        in.putExtra("DIFICULTAD", dificultad);

        //startActivity(in);
        startActivityForResult(in, 1);
    }

    //Todo esto se ejecuta cuando se termina de jugar
    protected void onActivityResult(int peticion, int codigo, Intent in)
    {
        if( (peticion != 1) || (codigo != RESULT_OK) )
        {
            return;
        }

        int resultado = in.getIntExtra("PUNTUACION", 0);

        //Se verifica si se ha roto el record anterior
        if( resultado > record )
        {
            record = resultado;

            TextView caja = (TextView) findViewById(R.id.record);
            caja.setText("Record: " + record);

            guardarRecord();
        }
        else
        {
            String puntuacionPartida = " " + resultado;

            Toast miToast = Toast.makeText(this, puntuacionPartida, Toast.LENGTH_SHORT);
            miToast.setGravity(Gravity.CENTER, 0, 0);
            miToast.show();
        }
    }

    public void onResume()
    {
        super.onResume();

        leerRecord();
    }

    private void guardarRecord()
    {
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor miEditor = datos.edit();
        miEditor.putInt("RECORD", record);

        miEditor.apply();
    }

    private void leerRecord()
    {
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        record = datos.getInt("RECORD", 0);

        TextView caja = (TextView) findViewById(R.id.record);
        caja.setText("Record: " + record);
    }
}
