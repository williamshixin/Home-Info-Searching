package com.example.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.facebook.*;




import android.R.string;

import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	 private static final String DEBUG_TAG = "HttpExample";
     public Bundle extras;
     private String stringUrl;
     private Intent intent;
     private EditText add;
	 private EditText cit;
	 private Spinner sta;
	 private TextView address_null;
	 private TextView city_null;	
	 private TextView state_null;	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void sendMessage(View view) {
		
	    add = (EditText) findViewById(R.id.address);
	    cit = (EditText) findViewById(R.id.city);
	    sta = (Spinner) findViewById(R.id.state);
	    
	    add.addTextChangedListener(new TextWatcher() {          
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                                   
                   //here is your code
                  checkaddress();

            }                       
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub                          
            }                       
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub                          

            }
		});
	    cit.addTextChangedListener(new TextWatcher() {          
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {                                   
                   //here is your code
                  checkcity();

            }                       
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub                          
            }                       
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub                          

            }
		});
	    sta.setOnItemSelectedListener(new OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	            // your code here
	        	checkstate();
	        }

	        @Override
	        public void onNothingSelected(AdapterView<?> parentView) {
	            // your code here
	        }

	    });
	    
	    String address_value = add.getText().toString();
	    String city_value = cit.getText().toString();
	    String state_value = sta.getSelectedItem().toString();
	    extras = new Bundle();
	  
	    extras.putString("address",address_value);
	    extras.putString("city",city_value);
	    extras.putString("state",state_value);
	  
	   
		
		
		stringUrl = "http://propertysearchapp-env.us-west-2.elasticbeanstalk.com/?streetInput="+address_value+"&cityInput="+city_value+"&stateInput="+state_value+"";
		stringUrl=stringUrl.replace(" ", "%20");
		intent = new Intent(this, ResultActivity.class);	
  		
	     new DownloadTask().execute(stringUrl);
         
         
      	  
     	    

	    
	}
	 public class DownloadTask extends AsyncTask<String, Void, String> {
	        
	        
	        String result;
	        @Override
	        protected String doInBackground(String... params) {
	        	try {
	            	//textView.setText(urls[0]);
	                return download(params[0]);
	                
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	        
	       
	        protected void onPostExecute(String result) {
	        	
              
	        	
	        	
	        	
	        	
	        	TextView tv=(TextView)findViewById(R.id.invalid);
	           if(add.length()!=0 && cit.length()!=0 && !sta.getSelectedItem().toString().equals("Choose State")) {
	        	   String s = result;
	     	   if(result.length()>0){
	     		  extras.putString("json", result);
	     		  intent.putExtras(extras);
	     		  startActivity(intent);
	     		  tv.setVisibility(View.INVISIBLE);
	     	   }
	     	   else{
	     		   
	     		  tv.setVisibility(View.VISIBLE);
	     		   
	     	   }
	           }
	           else{
	        	   checkaddress();
	        	   checkcity();
	        	   checkstate();
	        	   
	        	   
	           }
	        	
	        	
	        }
} 
	private String download(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 14000;
	        
	    try {
	       // URL url = new URL(myurl);
	    	String str = myurl;
	        String response = "";
	        DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(myurl);
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}
				return response;
	        //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        //conn.setReadTimeout(10000 /* milliseconds */);
	        //conn.setConnectTimeout(15000 /* milliseconds */);
	        //conn.setRequestMethod("GET");
	        //conn.setDoInput(true);
	        // Starts the query
	        //conn.connect();
	        //int response = conn.getResponseCode();
	        //Log.d(DEBUG_TAG, "The response is: " + response);
	        //is = conn.getInputStream();

	        // Convert the InputStream into a string
	       // String contentAsString = convertinputStreamToString(is);
	        //return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	public static String convertinputStreamToString(InputStream ists)
	        throws IOException {
	    if (ists != null) {
	        StringBuilder sb = new StringBuilder();
	        String line;

	        try {
	            BufferedReader r1 = new BufferedReader(new InputStreamReader(
	                    ists, "UTF-8"));
	            while ((line = r1.readLine()) != null) {
	                sb.append(line).append("\n");
	            }
	        } finally {
	            ists.close();
	        }
	        return sb.toString();
	    } else {
	        return "";
	    }
	}
	
	protected void onResume() {
		  super.onResume();

		  // Logs 'install' and 'app activate' App Events.
		  AppEventsLogger.activateApp(this);
		}
	
	protected void onPause() {
		  super.onPause();

		  // Logs 'app deactivate' App Event.
		  AppEventsLogger.deactivateApp(this);
		}
	
	public void checkaddress(){
		   address_null=(TextView) findViewById(R.id.address_error);	
		  
		   if(add.length()==0){
		    	address_null.setVisibility(View.VISIBLE);	    	
		    }
		    else{
		    	address_null.setVisibility(View.INVISIBLE);
		    	
		    }
		  
		    
		   
	   }
	public void checkcity(){
		 
		   city_null=(TextView) findViewById(R.id.city_error);
		   
		
		    if(cit.length()==0){
		    	city_null.setVisibility(View.VISIBLE);	    	
		    }
		    else{
		    	city_null.setVisibility(View.INVISIBLE);
		    	
		    }
	}
	public void checkstate(){
				  
				   state_null=(TextView) findViewById(R.id.state_error);	
				   
				    if(sta.getSelectedItem().toString().equals("Choose State")){
				    	state_null.setVisibility(View.VISIBLE);	    	
				    }
				    else{
				    	state_null.setVisibility(View.INVISIBLE);
				    	
				    }
		  }
	
}
