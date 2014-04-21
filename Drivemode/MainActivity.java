package com.example.drivemode;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;
import android.speech.tts.*;
import java.util.Locale;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import java.util.Locale;



import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

import android.widget.Button;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.widget.TextView;

public class MainActivity extends Activity implements OnInitListener {
ToggleButton tg1,tg2;
Intent in1;
CheckBox ch1,ch2;
IntentFilter intentFilter;//TTS object
private TextToSpeech myTTS;
//status check code
private int MY_DATA_CHECK_CODE = 0;
int flagsms=0;
int sh=0;
int an=0;
private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        //---display the SMS received in the TextView---
        
        if(flagsms==1){
        speakWords(intent.getExtras().getString("sms"));}
    }
};






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        
		tg1=(ToggleButton) findViewById(R.id.toggleButton1);
		tg2=(ToggleButton) findViewById(R.id.toggleButton2);
		tg1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			in1=new Intent(MainActivity.this,Ace.class);
			in1.putExtra("flagsms", flagsms);
			in1.putExtra("sh", sh);
			in1.putExtra("an", an);
			
				startActivity(in1);
			}
		});
		
tg2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			flagsms=1;
			}
		});
		ch1= (CheckBox) findViewById(R.id.checkBox1);
		ch1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				sh=1;
				
			}
		});
		ch2= (CheckBox) findViewById(R.id.checkBox2);
		ch2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				an=1;
				
			}
		});
			
		
			
		Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
		
	}
	
	 @Override
	    protected void onResume() {
	        //---register the receiver---
	        registerReceiver(intentReceiver, intentFilter);
	        super.onResume();
	    }
	    @Override
	    protected void onPause() {
	        //---unregister the receiver---
	        unregisterReceiver(intentReceiver);
	        super.onPause();
	    }

	   
	    private void speakWords(String speech) {
	        //speak straight away
	        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
	}
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == MY_DATA_CHECK_CODE) {
	            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
	                //the user has the necessary data - create the TTS
	            myTTS = new TextToSpeech(this, this);
	            }
	            else {
	                    //no data - install it now
	                Intent installTTSIntent = new Intent();
	                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	                startActivity(installTTSIntent);
	            }
	        }
	    }
	        //setup TTS
	    public void onInit(int initStatus) {
	            //check for successful instantiation
	        if (initStatus == TextToSpeech.SUCCESS) {
	            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
	                myTTS.setLanguage(Locale.US);
	        }
	        else if (initStatus == TextToSpeech.ERROR) {
	            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
	        }
	    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
