package com.felepo.pelota;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        if( dific.equals("Standard") ) dificultad = 2;
        if( dific.equals("Dificult") ) dificultad = 3;

        Intent in = new Intent(this, Gestion.class);
        in.putExtra("DIFICULTAD", dificultad);

        startActivity(in);
    }
}
