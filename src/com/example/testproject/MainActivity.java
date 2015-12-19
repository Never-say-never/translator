package com.example.testproject;

import SearchTranslatCore.SearchProcess;
import SearchTranslatCore.source.SearchWord;
import WorkSession.Session;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
@SuppressLint("NewApi") 
public class MainActivity extends ActionBarActivity {

	public static SearchProcess finder;
	
	public static AssetManager assetManager;
	
	public static Context context;
	
	public static Activity activity;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        assetManager = this.getAssets();
        context = getApplicationContext();
        finder  = new SearchProcess();   
        
        Session session = new Session();
        
        session.checkDictionary();
        //session.loadPreviousSession();
        
    }

    /* process when activity is active again */
    @Override
    protected void onResume() {
        super.onResume();
        
        TextView textVeiw  = (TextView)findViewById(R.id.textView1);
        TextView textVeiw1 = (TextView)findViewById(R.id.textView2);
        
        String wardToFinde = this.getWordFromBuffer();
        System.out.println("Search is:" + wardToFinde);
        if(wardToFinde != null){
        	finder.setSource(new SearchWord(wardToFinde));
        	textVeiw.setText(finder.getTranslate());
        	textVeiw1.setText("");
        }else{
        	textVeiw.setText(finder.BUFFER_SEARCH_EMPTY);
        	textVeiw1.setText("");
        }  
        
    }
     
    private String getWordFromBuffer(){
    	ClipData.Item item = null;
    	ClipboardManager myClipboard = null;
        
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        
        if(myClipboard.getPrimaryClip() == null){
        	System.out.print("**************not trith");
        	return null;
        }
        
        ClipData abc = myClipboard.getPrimaryClip();
        
        item = abc.getItemAt(0);
        
        return item.getText().toString();
    }
    
    private void simpleAlertExemple(){
    	 new AlertDialog.Builder(this)
        .setTitle("Delete entry")
        .setMessage("Are you sure you want to delete this entry?")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // continue with delete
            }
         })
        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                // do nothing
            }
         })
        .setIcon(android.R.drawable.ic_dialog_alert) 
        .show();
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
    
    public static void toastManager(String msg){
    	System.out.println("%%%%-------------->");
    	Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
