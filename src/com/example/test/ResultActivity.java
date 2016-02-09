package com.example.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;


import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
//import android.util.Size;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class ResultActivity extends Activity implements
ActionBar.TabListener {
	private UiLifecycleHelper uiHelper;
	 private ActionBar actionBar;
	   private String[] tabs = { "BASIC INFO", "HISTORICAL ZESTIMATES" };
	 private static final String DEBUG_TAG = "HttpExample";
	
	    private TextView propertytype_value;;
	    private TextView yearbuilt_value;
	    private TextView lotsize_value;
	    private TextView finishedarea_value;
	    private TextView bathrooms_value;
	    private TextView bedrooms_value;
	    private TextView taxassessmentyear_value;
	    private TextView taxassessment_value;
	    private TextView lastsoldprice_value;
	    private TextView lastsolddate_value;
	    private TextView zpe_value;
	    private TextView overallchange_value;
	    private TextView alltimepropertyrange_value;
	    private TextView rzpe_value;
	    private TextView rentchange_value;
	    private TextView alltimerentrange_value;
	    private TextView zpe;
	    private TextView rzpe;
	    private TextView hyperlink;
	    private String href;
	    private String html;
	    private String address;
	    private String city;
	    private String state;
	    private ImageView img;
	    private ImageView rimg;
	    private ImageSwitcher imageSwitcher;
	    private TextSwitcher textSwitcher;
	    private Button facebook;
	    private Button btnPrevious;
	    private Button btnNext;
	    private int currentIndex=0; 
	    private int messageCount=3;
	    private TextView foot1;
	    private TextView foot2;
	    private String result;
	    private JSONObject json;
	    private String name = null;
	  	private String lastsoldprice = null;
	  	private String overallchange = null;
	  	private String sign=null;
	  	private String picture=null;
	  	private Animation in;
	  	private Animation out;
	   // private String message = "Hello there!";
	    final Context context = this;
	 private Session.StatusCallback callback = new Session.StatusCallback() {
		
		 @Override
	    public void call(Session session, SessionState state,
	    Exception exception) {
	    onSessionStateChange(session, state, exception);
	    }
	    };
	    private void onSessionStateChange(Session session, SessionState state,
	    Exception exception) {
	    if (state.isOpened()) {
	    // System.out.println("Logged in...");
	    } else if (state.isClosed()) {
	    	
	    }
	    }
	    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data,
	        new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall,
	        Exception error, Bundle data) {
	        Log.e("Activity",
	        String.format("Error: %s", error.toString()));
	        }
	        @Override
	        public void onComplete(
	        FacebookDialog.PendingCall pendingCall, Bundle data) {
	        Log.i("Activity", "Post Success!");
	        }
	        });
	        }
	
	public void onCreate(Bundle savedInstanceState) {
		
		callFacebookLogout(this);
	    super.onCreate(savedInstanceState);
	  
	    setContentView(R.layout.activity_main);
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	        "com.example.test",
	        PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	        MessageDigest md = MessageDigest.getInstance("SHA");
	        md.update(signature.toByteArray());
	        System.out.println("KeyHash : "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        }
	        } catch (NameNotFoundException e) {
	        } catch (NoSuchAlgorithmException e) {
	        }
	    
	    
	    
	    
	    
	    
	    setContentView(R.layout.activity_display_message);  	    
	    actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);   
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
	    // Get the message from the intent
	    Intent intent = getIntent();
	    Bundle extras=intent.getExtras();
	    address = extras.getString("address");
	    city = extras.getString("city");
	    state = extras.getString("state");
	    String stringUrl = "http://propertysearchapp-env.us-west-2.elasticbeanstalk.com/?streetInput="+address+"&cityInput="+city+"&stateInput="+state+"";
	    stringUrl=stringUrl.replace(" ", "%20");
	    result=extras.getString("json");
    // Create the text view
	 
	    uiHelper = new UiLifecycleHelper(this, null);
	    uiHelper.onCreate(savedInstanceState);
	    
	    propertytype_value=(TextView) findViewById(R.id.propertytype_value);    
	    yearbuilt_value=(TextView) findViewById(R.id.yearbuilt_value);   
	    lotsize_value=(TextView)findViewById(R.id.lotsize_value);
	    finishedarea_value=(TextView)findViewById(R.id.finishedarea_value);
	    bathrooms_value=(TextView)findViewById(R.id.bathrooms_value);
	    bedrooms_value=(TextView)findViewById(R.id.bedrooms_value);
	    taxassessmentyear_value=(TextView)findViewById(R.id.taxassessmentyear_value);
	    taxassessment_value=(TextView)findViewById(R.id.taxassessment_value);
	    lastsoldprice_value=(TextView)findViewById(R.id.lastsoldprice_value);
	    lastsolddate_value=(TextView)findViewById(R.id.lastsolddate_value);
	    zpe_value=(TextView)findViewById(R.id.zpe_value);
	    overallchange_value=(TextView)findViewById(R.id.overallchange_value);
	    alltimepropertyrange_value=(TextView)findViewById(R.id.alltimepropertyrange_value);
	    rzpe_value=(TextView)findViewById(R.id.rzpe_value);
	    rentchange_value=(TextView)findViewById(R.id.rentchange_value);
	    alltimerentrange_value=(TextView)findViewById(R.id.alltimerentrange_value);
	    zpe=(TextView)findViewById(R.id.zpe);
	    rzpe=(TextView)findViewById(R.id.rzpe);
	    hyperlink=(TextView)findViewById(R.id.hyperlink);
	    img=(ImageView)findViewById(R.id.arror);
	    rimg=(ImageView)findViewById(R.id.rarror);
	    facebook =(Button)findViewById(R.id.facebook_b); 
	    btnNext=(Button)findViewById(R.id.buttonNext);
	    btnPrevious=(Button)findViewById(R.id.buttonPrevious);
	    imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
	    textSwitcher = (TextSwitcher)findViewById(R.id.textSwitcher);
	    foot1=(TextView)findViewById(R.id.foot1);
	    foot2=(TextView)findViewById(R.id.foot2);
	 
		
		facebook.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
	 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				
	 
				// set dialog message
				alertDialogBuilder
					.setMessage(R.string.facebook_alert)
					.setCancelable(false)
					.setPositiveButton(R.string.facebook_post,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							facebook();
						}
					  })
					.setNegativeButton(R.string.facebook_cancel,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
				}
			});
		
		
//	    facebook.setOnClickListener(new OnClickListener() {
//	        @Override
//	        public void onClick(View v) {
//	        //facebook();
//	        	
//	        	
//	        }
//	        });
		
        imageSwitcher.setFactory(new ViewFactory() {
        	   public View makeView() {
        	      ImageView myView = new ImageView(getApplicationContext());
        	    
        	      return myView;
        	   }
        	});
        
       textSwitcher.setFactory(new ViewFactory() {

            public View makeView() {
            	TextView myText = new TextView(ResultActivity.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(14);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        });
        
        in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);    
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);    
	  
	
	    // Set the text view as the activity layout
	    foot1.setText(Html.fromHtml("© Zillow, Inc., 2006-2014.<br/> Use is subject to <a href=http://www.zillow.com/corp/Terms.htm>term of use</a><br/><a href=http://www.zillow.com/zestimate/>What's a Zestimate</a>"));
	    foot2.setText(Html.fromHtml("© Zillow, Inc., 2006-2014.<br/> Use is subject to <a href=http://www.zillow.com/corp/Terms.htm>term of use</a><br/><a href=http://www.zillow.com/zestimate/>What's a Zestimate</a>"));
	    foot1.setMovementMethod(LinkMovementMethod.getInstance());
	    foot2.setMovementMethod(LinkMovementMethod.getInstance());
	    try {
    		
		String rt = result;	
		json=new JSONObject(result);
		JSONObject re = json.getJSONObject("result");
		JSONObject charts = json.getJSONObject("chart");
		//href="http://www.zillow.com/homedetails/"+json.getJSONObject("result").getString("street")+"-"+json.getJSONObject("result").getString("city")+"-"+json.getJSONObject("result").getString("state")+"-"+json.getJSONObject("result").getString("zipcode")+"/"+json.getJSONObject("result").getString("zpid")+"_zpid/";
		//href=href.replace(" ", "-"); 	
		href = re.getString("homedetails");
		propertytype_value.setText(json.getJSONObject("result").getString("useCode").length()!=0?json.getJSONObject("result").getString("useCode"):"N/A");
		yearbuilt_value.setText(json.getJSONObject("result").getString("yearBuilt").length()!=0?json.getJSONObject("result").getString("yearBuilt"):"N/A");	
        lotsize_value.setText(json.getJSONObject("result").getString("lotSizeSqft")!="null"?json.getJSONObject("result").getString("lotSizeSqft"):"N/A");
        finishedarea_value.setText(json.getJSONObject("result").getString("finishedSqFt")!="null"?json.getJSONObject("result").getString("finishedSqFt"):"N/A");	
        bathrooms_value.setText(json.getJSONObject("result").getString("bathrooms").length()!=0?json.getJSONObject("result").getString("bathrooms"):"N/A");	
        bedrooms_value.setText(json.getJSONObject("result").getString("bedrooms").length()!=0?json.getJSONObject("result").getString("bedrooms"):"N/A");	
        taxassessmentyear_value.setText(json.getJSONObject("result").getString("taxAssessmentYear").length()!=0?json.getJSONObject("result").getString("taxAssessmentYear"):"N/A");	
        taxassessment_value.setText(json.getJSONObject("result").getString("taxAssessment")!="null"?json.getJSONObject("result").getString("taxAssessment"):"N/A");	
        lastsoldprice_value.setText(json.getJSONObject("result").getString("lastSoldPrice")!="null"?json.getJSONObject("result").getString("lastSoldPrice"):"N/A");	
        lastsolddate_value.setText(json.getJSONObject("result").getString("lastSoldDate")!="null"?json.getJSONObject("result").getString("lastSoldDate"):"N/A");	
        zpe_value.setText(json.getJSONObject("result").getString("estimateAmount")!="null"?json.getJSONObject("result").getString("estimateAmount"):"N/A");
        overallchange_value.setText(json.getJSONObject("result").getString("estimateValueChange")!="null"?json.getJSONObject("result").getString("estimateValueChange"):"N/A");	
        alltimepropertyrange_value.setText((json.getJSONObject("result").getString("estimateValuationRangeLow")!="null"?json.getJSONObject("result").getString("estimateValuationRangeLow"):"N/A")+"-"+(json.getJSONObject("result").getString("estimateValuationRangeHigh")!="null"?json.getJSONObject("result").getString("estimateValuationRangeHigh"):"N/A"));
        rzpe_value.setText(json.getJSONObject("result").getString("restimateAmount")!=null?json.getJSONObject("result").getString("restimateAmount"):"N/A");
        rentchange_value.setText(json.getJSONObject("result").getString("restimateValueChange")!="null"?json.getJSONObject("result").getString("restimateValueChange"):"N/A");	
        alltimerentrange_value.setText((json.getJSONObject("result").getString("restimateValuationRangeLow")!="null"?json.getJSONObject("result").getString("restimateValuationRangeLow"):"N/A")+"-"+(json.getJSONObject("result").getString("restimateValuationRangeHigh")!="null"?json.getJSONObject("result").getString("restimateValuationRangeHigh"):"N/A"));
        zpe.setText("Zestimate©  Property Estimate as of"+(json.getJSONObject("result").getString("estimateLastUpdate")!="null"?json.getJSONObject("result").getString("estimateLastUpdate"):"N/A"));
        rzpe.setText("Rent Zestimate©  Property Estimate as of"+(json.getJSONObject("result").getString("estimateLastUpdate")!="null"?json.getJSONObject("result").getString("estimateLastUpdate"):"N/A"));
        hyperlink.setText(Html.fromHtml("<a href="+href+">" +json.getJSONObject("result").getString("street")+","+json.getJSONObject("result").getString("city")+","+json.getJSONObject("result").getString("state")+"-"+json.getJSONObject("result").getString("zipcode")));
        hyperlink.setMovementMethod(LinkMovementMethod.getInstance());
        img.setImageResource(json.getJSONObject("result").getString("estimateValueChangeSign").equals("-")?R.drawable.down_r:R.drawable.up_g);
        rimg.setImageResource(json.getJSONObject("result").getString("restimateValueChangeSign").equals("-")?R.drawable.down_r:R.drawable.up_g);
        
       // imageSwitcher.setImageResource(R.drawable.zillow);
       String bitmapUrl1=charts.getString("year1");
       String bitmapUrl5=charts.getString("years5");
       String bitmapUrl10=charts.getString("years10");
       String details1="<big><strong>Historical Zestimate for the past 1 year</strong></big><br/><small>"+json.getJSONObject("result").getString("street")+"-"+json.getJSONObject("result").getString("city")+"-"+json.getJSONObject("result").getString("state")+"</small>"; 
       String details2="<big><strong>Historical Zestimate for the past 5 year</strong></big><br/><small>"+json.getJSONObject("result").getString("street")+"-"+json.getJSONObject("result").getString("city")+"-"+json.getJSONObject("result").getString("state")+"</small>";
       String details3="<big><strong>Historical Zestimate for the past 10 year</strong></big><br/><small>"+json.getJSONObject("result").getString("street")+"-"+json.getJSONObject("result").getString("city")+"-"+json.getJSONObject("result").getString("state")+"</small>";
      new DownloadBitmapTask().execute(bitmapUrl1,bitmapUrl5,bitmapUrl10,details1,details2,details3);
       
       //final String text[]={details1,details2,details3};
      textSwitcher.setText(Html.fromHtml(details1));
			
      
		try {
			name = json.getJSONObject("result").getString("street")+","+json.getJSONObject("result").getString("city")+","+json.getJSONObject("result").getString("state")+"-"+json.getJSONObject("result").getString("zipcode");
	        lastsoldprice= json.getJSONObject("result").getString("lastSoldPrice")!="null"?json.getJSONObject("result").getString("lastSoldPrice"):"N/A";
	        overallchange=json.getJSONObject("result").getString("estimateValueChange")!="null"?json.getJSONObject("result").getString("estimateValueChange"):"N/A";
		    sign=json.getJSONObject("result").getString("estimateValueChangeSign");
		    //picture="http://www.zillow.com/app?chartDuration=1year&chartType=partner&height=300&page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+json.getJSONObject("result").getString("zpid");
		    picture = charts.getJSONObject("year1").getString("url");
		    System.out.println("get pic");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
     
       
       
       
       
       
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//tablelayout.setVisibility(View.INVISIBLE);
			//parseCharts.setVisibility(View.INVISIBLE);
			e.printStackTrace();
		}

	} 
 
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	
	
	
	
	protected void onDestroy() {
	    uiHelper.onDestroy();
	    super.onDestroy();
	    }
	    @Override
	    protected void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    }
	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	    }
	    @Override
	    public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    }
	    public void facebook() {
	        if (!checkNetwork()) {
	        Toast.makeText(getApplicationContext(),
	        "No active internet connection ...", Toast.LENGTH_SHORT)
	        .show();
	        return;
	        }
//	        if (!checkFbInstalled()) {
//	        Toast.makeText(getApplicationContext(),
//	        "Facebook app not installed!..", Toast.LENGTH_SHORT).show();
//	        return;
//	        }
	        Toast.makeText(getApplicationContext(), "Loading...",
	        Toast.LENGTH_SHORT).show();
	      
	        if (Session.getActiveSession() != null && Session.getActiveSession().isOpened()) {
	        	
	        	 if (FacebookDialog.canPresentShareDialog(this,
	        		        FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
	        		
	        		// showFeedDialog();	
	        		        	
	        		        FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
	        		        this).setName("FacebookSDKTuts")
	        		        .setLink(href)
	        		        .setDescription("Last SoldPrice:"+lastsoldprice+",30 Days Overall Change:"+sign+overallchange+"")
	        		        .setName(name)
	        		        .setCaption( "Property information from Zillow.com")
	        		        .setPicture(picture).build();
	        		       
	        		        uiHelper.trackPendingDialogCall(shareDialog.present());
	        		        } else {
	        		        	showFeedDialog();
	        		        }
	            }
	        else {

	            Session session = Session.getActiveSession();
	            if (!session.isOpened() && !session.isClosed()) {

	                //          List<String> permissions = new ArrayList<String>();
	                //            permissions.add("email");

	                session.openForRead(new Session.OpenRequest(this)
	                //                .setPermissions(permissions)
	                .setCallback(callback));
	            } else {
	                Session.openActiveSession(ResultActivity.this, true, callback);
	            }
	        }
	        }
	    
	    public static void callFacebookLogout(Context context) {
	        Session session = Session.getActiveSession();
	        if (session != null) {

	            if (!session.isClosed()) {
	                session.closeAndClearTokenInformation();
	                //clear your preferences if saved
	            }
	        } else {

	            session = new Session(context);
	            Session.setActiveSession(session);

	            session.closeAndClearTokenInformation();
	                //clear your preferences if saved

	        }

	    }
	        private boolean checkNetwork() {
	        boolean wifiAvailable = false;
	        boolean mobileAvailable = false;
	        ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
	        for (NetworkInfo netInfo : networkInfo) {
	        if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
	        if (netInfo.isConnected())
	        wifiAvailable = true;
	        if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
	        if (netInfo.isConnected())
	        mobileAvailable = true;
	        }
	        return wifiAvailable || mobileAvailable;
	        }
	        public Boolean checkFbInstalled() {
	        PackageManager pm = getPackageManager();
	        boolean flag = false;
	        try {
	        pm.getPackageInfo("com.facebook.katana",
	        PackageManager.GET_ACTIVITIES);
	        flag = true;
	        } catch (PackageManager.NameNotFoundException e) {
	        flag = false;
	        }
	        return flag;
	        }
	
	
	
	        private void showFeedDialog() {
	        	
	            Bundle postParams = new Bundle();	
	            postParams.putString("name",name);
	            postParams.putString("link", href);
	            postParams.putString("picture", picture);
	            postParams.putString("caption", "Property information from Zillow.com");
	            postParams.putString("description","Last SoldPrice:"+lastsoldprice+",30 Days Overall Change:"+sign+overallchange+"");
	            
	            
	            WebDialog feedDialog = new WebDialog.FeedDialogBuilder(this, Session.getActiveSession(),postParams)
	            .setOnCompleteListener(new OnCompleteListener() {
	                @Override
	                public void onComplete(Bundle values, FacebookException error) {
	                    if(error==null)
	                    {
	                        final String postId=values.getString("post_id");
	                        if(postId!=null)
	                            Toast.makeText(getApplicationContext(), "Posted Story,ID:"+postId+"", Toast.LENGTH_SHORT).show();
	                        else
	                            Toast.makeText(getApplicationContext(), "Post canceled", Toast.LENGTH_SHORT).show();
	                    }
	                    else
	                        if(error instanceof FacebookOperationCanceledException)
	                            Toast.makeText(getApplicationContext(), "Publish canceled",Toast.LENGTH_SHORT).show();
	                        else
	                            Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
	                }
	            }).build();
	            feedDialog.show();

	        }
	
	
	  @SuppressLint("NewApi")
	public static class PlaceholderFragment extends Fragment {

	        public PlaceholderFragment() { }
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

	
  
//
    public class DownloadBitmapTask extends AsyncTask<String, Void, Object[]> {
        @Override
        protected Object[] doInBackground(String... params) {
        	String[] strs = params;
        	 Object[] obj=new Object[6];        	 
        	 Bitmap bitmap[]=new Bitmap[3];
        	 Bitmap rebitmap[]=new Bitmap[3];
        	
        	 
        	 //ArrayList<E> li=new ArrayList<E>();
            try {
            	
            	URL[] urls = new URL[3];
            	for (int i = 0; i < 3; i++) {
            		urls[i] = new URL(new JSONObject(params[i]).getString("url"));
            	}
            	HttpURLConnection conn0 = (HttpURLConnection) urls[0].openConnection();
            	HttpURLConnection conn1 = (HttpURLConnection) urls[1].openConnection();
            	HttpURLConnection conn2 = (HttpURLConnection) urls[2].openConnection();
            	conn0.setDoInput(true);
            	conn1.setDoInput(true);
            	conn2.setDoInput(true);
            	
            	conn0.connect();
            	conn1.connect();
            	conn2.connect();
            	
            	
                bitmap[0] = BitmapFactory.decodeStream(conn0.getInputStream());
                bitmap[1] = BitmapFactory.decodeStream(conn1.getInputStream());
                bitmap[2] = BitmapFactory.decodeStream(conn2.getInputStream());
                
                float xScale = ((float) imageSwitcher.getWidth()) /  bitmap[0].getWidth();
                float yScale = ((float) imageSwitcher.getHeight()) /  bitmap[0].getHeight();
              //  float scale = (xScale <= yScale) ? xScale : yScale;

                // Create a matrix for the scaling and add the scaling data
                Matrix matrix = new Matrix();
                matrix.postScale(xScale*1.8f,  yScale*1.4f);
                
               rebitmap[0]=Bitmap.createBitmap(bitmap[0], 0, 0, bitmap[0].getWidth(), bitmap[0].getHeight(), matrix, false);
               rebitmap[1]=Bitmap.createBitmap(bitmap[1], 0, 0, bitmap[1].getWidth(), bitmap[0].getHeight(), matrix, false);
               rebitmap[2]=Bitmap.createBitmap(bitmap[2], 0, 0, bitmap[2].getWidth(), bitmap[0].getHeight(), matrix, false);
            } catch (Exception ex) {
            }
           
            obj[0]=new BitmapDrawable(rebitmap[0]);
            obj[1]=new BitmapDrawable(rebitmap[1]);
            obj[2]=new BitmapDrawable(rebitmap[2]);
            obj[3]=params[3];
            obj[4]=params[4];
            obj[5]=params[5];
            
            
         
            return obj;
        }
       
        protected void onPostExecute(Object[] result) {
        	imageSwitcher.setImageDrawable((Drawable) result[0]);
        	final Drawable imageIds[]={(Drawable) result[0],(Drawable) result[1],(Drawable) result[2]};
        	final String text[]={(String) result[3],(String) result[4],(String) result[5]};
            // to keep current Index of ImageID array
           
        	  btnNext.setOnClickListener(new View.OnClickListener() {
                  
                  public void onClick(View v) {
                	 
                      // TODO Auto-generated method stub
                       currentIndex++;
                         // If index reaches maximum reset it
                          if(currentIndex==messageCount)
                          currentIndex=0;
                          imageSwitcher.setImageDrawable(imageIds[currentIndex]);
                          textSwitcher.setText(Html.fromHtml(text[currentIndex]));
                          
                  }
              });
        	  
        	  btnPrevious.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					currentIndex--;
					if(currentIndex==-1)
					currentIndex=2;
                        imageSwitcher.setImageDrawable(imageIds[currentIndex]);
                        textSwitcher.setText(Html.fromHtml(text[currentIndex]));  
					
				}
			});
        }
        

		
    }
   

	
		
	 
	public String downloadUrl(String myurl) throws IOException {
	    InputStream is = null;
	    // Only display the first 500 characters of the retrieved
	    // web page content.
	    int len = 14000;
	        
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(10000 /* milliseconds */);
	        conn.setConnectTimeout(15000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = convertinputStreamToString(is);
	        return contentAsString;
	        
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ScrollView tablelayouttab=(ScrollView)findViewById(R.id.table);
		FrameLayout parseChartstab=(FrameLayout)findViewById(R.id.phasechart);
		// TODO Auto-generated method stub
		switch(tab.getPosition()){
			case 0:
				tablelayouttab.setVisibility(View.VISIBLE);
				parseChartstab.setVisibility(View.INVISIBLE);
				break;
			case 1:
				tablelayouttab.setVisibility(View.INVISIBLE);
				parseChartstab.setVisibility(View.VISIBLE);
				break;
			
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	

	
	 
}

