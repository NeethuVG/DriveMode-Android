
package com.example.drivemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Menu;

import android.widget.TextView;
import android.widget.Toast;
public class SMSReceiver extends BroadcastReceiver {

   
       //create the Activity
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {   
	        //---get the SMS message passed in---
	        Bundle bundle = intent.getExtras();
	        SmsMessage[] msgs = null;
	        String str = "";
	        
	        if (bundle != null)
	        { 
	            //---retrieve the SMS message received---
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
	                str += "SMS from " + msgs[i].getOriginatingAddress();
	                str += ":";
	                str += msgs[i].getMessageBody().toString();
	                str += "\n";
	                
	              System.out.println(str);
	               
	            }
	            
	         Toast tost=Toast.makeText(context,str,Toast.LENGTH_LONG);
	         tost.show();
	         
	            Intent broadcastIntent = new Intent();
	            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
	            broadcastIntent.putExtra("sms", str);
	            context.sendBroadcast(broadcastIntent);

	        }
	    }
	     
   
	}