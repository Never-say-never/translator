package com.example.translateok;

import AppliacationManager.TranslateProcessManager;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	public static AssetManager assetManager;

	public static Context context;

	private ProgressDialog progress;

	public TranslateProcessManager translateProcess;

	//private ClipboardManager myClipboard;

	private EditText testText;

	private ListView lvMain;
	
	private Button search;

	private Button delete ;
	
	private static int searchCounter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.testText = (EditText) findViewById(R.id.editText1);
		this.lvMain   = (ListView) findViewById(R.id.listView1);
		this.search   = (Button) findViewById(R.id.button1);
		this.delete   = (Button) findViewById(R.id.button2);
		
		this.initListeners();
		
		assetManager = this.getAssets();
		context = this.getBaseContext();

		this.translateProcess = new TranslateProcessManager(this);
		this.translateProcess.startApplication();

	} 
	
	private void initListeners(){
        OnClickListener oclBtnOk = new OnClickListener() {
            public void onClick(View v) {
            	String exp = testText.getText().toString();
            	if(translateProcess.checkExpression(exp.trim())){
            		String test = translateProcess.search(exp.trim());
            		TranslateProcessManager.toastManager(exp + " - " + test);
            	}else{
            		TranslateProcessManager.toastManager("Incorrect input!");
            	}
            	
            	++searchCounter;
            }
        };
        search.setOnClickListener(oclBtnOk);
        
        OnClickListener onBtnDel = new OnClickListener() {
            public void onClick(View v) {
            	TranslateProcessManager.toastManager("delete");
            	translateProcess.deleteFromUserList();
            }
        };
        delete.setOnClickListener(onBtnDel);
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
		}else if(id == R.id.debugger){
			TranslateProcessManager.toastManager("go go debugger!");
			Intent intent = new Intent(MainActivity.this, DebugActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	/* process when activity is active again */
	@Override
	protected void onResume() {
		super.onResume();

		this.waitForSystemLoadEnd();

		String wordFromBuffer = translateProcess.getWordFromBuffer();
		
		if (translateProcess.checkExpression(wordFromBuffer)) {
			String test = translateProcess.search(wordFromBuffer); 
			testText.setText(test);
		} else {
			if(searchCounter != 0)
				TranslateProcessManager.toastManager("Incorrect input!");
		}
		
		++searchCounter;
	}

	public ProgressDialog getProgress() {
		this.progress = new ProgressDialog(this);
		progress.setMessage("Updating Dictionary...");
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.setCanceledOnTouchOutside(false);
		progress.setIndeterminate(false);
		progress.setMax(100);
		progress.show();

		return this.progress;
	}

	public ClipboardManager getContextManager() {
		return (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
	}

	public ListView getListViuw() {
		return this.lvMain;
	}

	private void waitForSystemLoadEnd() {
		translateProcess.createListView();
		final Thread loadThread = new Thread() {
			@Override
			public void run() {
				while (!translateProcess.systemLoaded) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loadThread.start();
	}
	
	public Button getDeleteButton(){
		return this.delete;
	}
}
