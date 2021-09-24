package com.example.easy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;


public class Agenda extends AppCompatActivity {

    private ImageButton btn_exit;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        btn_exit = (ImageButton) findViewById(R.id.imageButton);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.getDefault());
            }
        });
        check_tareas();
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void check_tareas() {
        tts.speak("No hay tareas para hoy", TextToSpeech.QUEUE_FLUSH, null, "hola");
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