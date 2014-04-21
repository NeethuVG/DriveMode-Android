package com.example.drivemode;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class Ace extends Activity implements  OnInitListener {
	private TextView result,tv1,tv2;
	private SensorManager sensorManager;
	private Sensor sensor;
	private float x=0;
	private float y=0;
	private float z=0;
	int sh=0;
	int an=0;
	int time =20;
	Timer t;
	TimerTask task;
	public int count;
	int fl;
 int counti=0;
	 MediaPlayer player;
	 Button bt;
	 IntentFilter intentFilter;//TTS object
	    private TextToSpeech myTTS;
	    //status check code
	private int MY_DATA_CHECK_CODE = 0;
	int flagsms=0;
	    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            //---display the SMS received in the TextView---
	        	
	            if(fl==1){
	            speakWords(intent.getExtras().getString("sms"));}
	        }
	    };
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ace);
		Bundle bundle =getIntent().getExtras();
		 fl=bundle.getInt("flagsms");
		 fl=bundle.getInt("flagsms");
		 sh=bundle.getInt("sh");
		 an=bundle.getInt("an");
		 intentFilter = new IntentFilter();
	        intentFilter.addAction("SMS_RECEIVED_ACTION");
	        Intent checkTTSIntent = new Intent();
	        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
	        
	        
	        
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
			 player = MediaPlayer.create(this, R.raw.zo);
			 
			 
		result = (TextView) findViewById(R.id.textView1);
		tv1= (TextView) findViewById(R.id.textView2);
		tv2= (TextView) findViewById(R.id.textView3);
		
		result.setText("No result yet");
		 
		 bt=(Button) findViewById(R.id.button1);
		 bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				player.stop();
			}
		});
	}
 
	private void startTimer() {
		t=new Timer();
		time=20;
		task=new TimerTask(){
			@Override
			public void run(){
             runOnUiThread(new Runnable(){
            	 @Override
                 public void run(){
            		 
     				tv1.setText(time + "");
            		 if(time>0)
     					time-=1;
            		 else{
     					if(((count>8)&&(time==0))||(counti>8)&&(time==0)){
     					tv2.setText("danger");
     					sensorManager.unregisterListener(accelerationListener);
     					if(an==1){
     					player.start();}
     					
     					
     					}
     					else{count=0;
     					counti=0;}
     				} 
                 } 
             });
             
             
			}
		};
		t.scheduleAtFixedRate(task, 0,500);
	
	}

	@Override
	protected void onResume() {
		
		sensorManager.registerListener(accelerationListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		 registerReceiver(intentReceiver, intentFilter);
		 super.onResume();
	}
 
	@Override
	protected void onStop() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
	}
	@Override
	protected void onPause() {
		sensorManager.unregisterListener(accelerationListener);
		unregisterReceiver(intentReceiver);
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
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

	private SensorEventListener accelerationListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int acc) {
		}
 
		@Override
		public void onSensorChanged(SensorEvent event) {
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			if((((x>-8.0)&&(x<-6.0))&&((z<7.0)&&(z>5.5))))  //||
			{ 
	

		count++;
		if (count==2)
		{
			startTimer();
		}
		
		
		String output = Integer.toString(count);
		result.setText(output);
			}

			else if((((x>7.0)&&(x<4.0))&&((z>7.0)&&(z>5.5))))
			{
				counti++;
				if(count==0&&counti==2)
				{
					startTimer();
				}
				
				
			}

			else if((z>-9.8)&&(z<1.0))
			{ //count+=1;
				if(sh==1){
				Intent nw= new Intent(Ace.this,Send.class);
				startActivity(nw);}
			}
			else {}	
			}

		
	};}