package com.example.easy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class Contactos extends AppCompatActivity {

    private Button btn_contacto;
    private Button btn_contacto_uno;
    private Button btn_contacto_dos;
    private Button btn_contacto_tres;
    private ImageButton btn_return;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        btn_contacto = (Button) findViewById(R.id.btn_contacto);
        btn_contacto_uno = (Button) findViewById(R.id.btn_contacto_uno);
        btn_contacto_dos = (Button) findViewById(R.id.btn_contacto_dos);
        btn_contacto_tres = (Button) findViewById(R.id.btn_contacto_tres);
        btn_return = (ImageButton) findViewById(R.id.imgbtn_return);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.getDefault());
            }
        });
        btn_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_call("LOLA");
            }
        });
        btn_contacto_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_call("PACO");
            }
        });
        btn_contacto_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_call("VECINA");
            }
        });
        btn_contacto_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_call("HIJO");
            }
        });
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void make_call(String str) {
        tts.speak("Llamar a "+str+". Pulse el botón de si", TextToSpeech.QUEUE_FLUSH, null, "hola");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pregunta");
        builder.setMessage("Desea llamar");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String phone = null;
                if (str.equalsIgnoreCase("LOLA"))
                    phone = "tel:666666666";
                else if (str.equalsIgnoreCase("PACO"))
                    phone = "tel:999999999";
                else if (str.equalsIgnoreCase("VECINA"))
                    phone = "tel:777777777";
                else if (str.equalsIgnoreCase("HIJO"))
                    phone = "tel:888888888";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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