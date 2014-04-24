package com.example.badner.using.talker;
import java.util.Locale;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {

	   private String istr;
	
	//משתנים לדיבור
		private static int TTS_DATA_CHECK = 1;
	    private TextToSpeech tts = null;
	    private boolean ttsIsInit = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView txt =(TextView)findViewById(R.id.textView1);
		istr=txt.getText().toString();
		
		//Open talker device
		initTextToSpeech();//פונקציה דיבור
	
		
		//When you click on the button
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this,"The device speaks", Toast.LENGTH_SHORT).show();
				
				//Send to a function we want to speak
				speak(istr);
				
				//return false;
				
			}
			// קבלה של הטקסט שנרצה שידבר
			private void speak(String inputString) {
		       
		       tts.speak(inputString, TextToSpeech.QUEUE_ADD, null);
		    
		    }
		});
	
	}

	//פונציה לפתיחת הדברן של המכשיר
		private void initTextToSpeech() {
		Intent intents = new Intent( TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	    startActivityForResult(intents, TTS_DATA_CHECK);
		}
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			
			if (requestCode == TTS_DATA_CHECK) {
				
	            configTextToSpeech();
	        };
			
		}
		//פונקציה שקובעת תצורה של הדברן
		private void configTextToSpeech(){
		        tts = new TextToSpeech(this, new OnInitListener() {
		            public void onInit(int status) {
		                if (status == TextToSpeech.SUCCESS) {
		                    ttsIsInit = true;
		                    if (tts.isLanguageAvailable(Locale.ENGLISH) >= 0)
		                        tts.setLanguage(Locale.ENGLISH);
		                    tts.setPitch(1);
		                    tts.setSpeechRate(1);
		                }
		            }
		        });
		    }
		  
		   
		    //פונקציה שהדף ימות לסגור דברן   
		    @Override
		    public void onDestroy() {
		        if (tts != null) {
		            tts.stop();
		            tts.shutdown();
		        }
		        super.onDestroy();
		    }
}
