package com.example.easy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private Button btn_sos;
    private Button btn_contacto;
    private Button btn_tareas;
    private ImageButton btn_exit;
    private TextToSpeech tts;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_sos = (Button) findViewById(R.id.btn_sos);
        btn_contacto = (Button) findViewById(R.id.btn_contacto);
        btn_tareas = (Button) findViewById(R.id.btn_tare);
        btn_exit = (ImageButton) findViewById(R.id.imgbt_exit);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.getDefault());
            }
        });
        btn_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_sos();
            }
        });
        
        btn_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_contact();
            }
        });
        
        btn_tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch_tareas();
            }
        });
        
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void call_sos() {
        tts.speak("Desea llamar al 112", TextToSpeech.QUEUE_FLUSH, null, "hola");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Pregunta");
        builder.setMessage("¿Desea llamar al 112?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
               String phone = "tel:112";
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse(phone));
               startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void call_contact() {
        Intent intent = new Intent(this, Contactos.class);
        startActivity(intent);
        finish();
    }

    private void launch_tareas() {
        tts.speak("Para abrir la agenda seleccione el botón de la derecha. Para programar una tarea el boton de la izquierda", TextToSpeech.QUEUE_FLUSH, null, "hola");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Pregunta");
        builder.setMessage("Abrir agenda o programar una tarea");

        builder.setPositiveButton("Agenda", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                launch_agenda();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Añadir tarea", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                add_tarea();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void add_tarea() {
        Intent intent = new Intent(this, Nueva_tarea.class);
        startActivity(intent);
        finish();
    }

    private void launch_agenda() {
        Intent intent = new Intent(this, Agenda.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}