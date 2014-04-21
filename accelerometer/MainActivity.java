package com.example.acel;



import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
 
public class MainActivity extends Activity {
	private TextView result;
	private SensorManager sensorManager;
	private Sensor sensor;
	private float x, y, z;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
 
		result = (TextView) findViewById(R.id.textView);
		result.setText("No result yet");
	}
 
	private void refreshDisplay() {
		String output = String	.format("x is: %f / y is: %f / z is: %f", x, y, z);
		result.setText(output);
	}
 
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(accelerationListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
	}
 
	@Override
	protected void onStop() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
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
if(((x>-6.0)&&(x<-7.5))&&((z<6.0)&&(z>5.0)))
			{
				s="DRIVE CAREFULLY";
				TextView tvX= (TextView)findViewById(R.id.textView1);
					
			}
			
		}
			refreshDisplay();
			TextView tvX= (TextView)findViewById(R.id.text);
		
		tvX.setText(Float.toString(x));
		}
 
	
	

	};
}
