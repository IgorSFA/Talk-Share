package io.github.igorsfa.talkshare;

import java.util.ArrayList;

//import android.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

        protected static final int RESULT_SPEECH = 1;
        private ImageButton btnSpeak, btnSend;
        private TextView txtText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                txtText = (TextView)findViewById(R.id.txtText);
                btnSpeak = (ImageButton)findViewById(R.id.btnSpeak);

                btnSpeak.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // TODO Auto-generated method stub
                                Intent intent =
                                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
                                try{
                                        startActivityForResult(intent, RESULT_SPEECH);
                                        txtText.setText("");
                                }catch(ActivityNotFoundException a){
                                        Toast t = Toast.makeText(getApplicationContext(),
                                                "Opps! Your device doesn't support Speech to Text",
                                                Toast.LENGTH_SHORT);
                                        t.show();
                                }
                        }
                });

                btnSend = (ImageButton)findViewById(R.id.btnSend);
                btnSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, txtText.getText());
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
                        }
                });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode,
                                     Intent data) {
                // TODO Auto-generated method stub
                switch(requestCode){
                        case RESULT_SPEECH: {
                                if(resultCode == RESULT_OK && null != data){
                                        ArrayList<String> text = data
                                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                                        txtText.setText(text.get(0));
                                }
                        }
                }
        }
}

