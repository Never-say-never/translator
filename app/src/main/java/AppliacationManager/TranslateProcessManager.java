package AppliacationManager;

import Adapters.HistoryListAdapter;
import AndroidFilesType.FilesType.SDCardFile;
import Search.Search;
import Search.ProcessSearch.SearchEntetis.SearchEntity;
import Session.SessionProcessManager;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.translateok.MainActivity;

public class TranslateProcessManager {

	private SessionProcessManager sessionManager;

	private HistoryListAdapter boxAdapter;

	private Search search;

	public static MainActivity main;

	public static ProgressDialog progress;

	public static Handler mHandler;

	public volatile boolean systemLoaded = false;

	/* general count of progress lines */
	public volatile static int gProgress = 0;

	/* current count of progress lines */
	public volatile static int cProgress = 0;

	/* current count of progress lines */
	public volatile static boolean firstBoot = false;
	
	/* constant for debug messages */
	public static final String APP_MSG = "app_call :: ";

	public TranslateProcessManager(MainActivity main) {
		this.sessionManager = new SessionProcessManager();
		this.search 		= new Search();
		progress  			= main.getProgress();
		TranslateProcessManager.main = main;
	}

	public void startApplication() {
		this.startApplicationSession();
		//this.createListView();
	}

	/*
	 * Start translateOk application Load configure file, load dictionary, load
	 * user dictionary, update unknown words Google translate serves
	 */
	@SuppressLint("HandlerLeak")
	private void startApplicationSession() {
		progress.incrementProgressBy(7);
		final Thread loadThread = new Thread() {
			@Override
			public void run() {
				progress.incrementProgressBy(11);
				sessionManager.startSession();
				//createListView();
				systemLoaded = true;
				progress.incrementProgressBy(1);
			};
		};
		loadThread.start();

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				progress.setIndeterminate(false);
				if (calculateProgress() < progress.getMax()) {
					progress.incrementProgressBy(calculateProgress());
					mHandler.sendEmptyMessageDelayed(0, 500);

				} else {
					progress.dismiss();
				}

				System.out.println("Still work");
			}
		};

		mHandler.sendEmptyMessageDelayed(0, 2000);

		while (true) {
			if(firstBoot){
				break;
			}
			
			if(systemLoaded){
				this.createListView();
				break;
			}
		}
	}

	@SuppressLint("NewApi")
	public String getWordFromBuffer() {
		
		ClipboardManager myClipboard = main.getContextManager();

		if (myClipboard.getPrimaryClip() == null) {
			return null;
		}

		ClipData abc = myClipboard.getPrimaryClip();
		ClipData.Item item = abc.getItemAt(0);

		return item.getText().toString();
	}

	public void createListView() {
		appMessage("createListView()");
		for (SearchEntity str : sessionManager.getSource().getList()) {
			System.out.println("before ::" + str.getExp());
		}

		this.boxAdapter = new HistoryListAdapter(MainActivity.context,
				sessionManager.getSource().getList(), false);

		main.getListViuw().setAdapter(boxAdapter);
		main.getListViuw().setVisibility(View.VISIBLE);
	}

	public String search(String wordFromBuffer) {
		SearchEntity resultSearch = this.search.searchProcess(wordFromBuffer);
		
		if(resultSearch.getTranslate().length() <= 0){
			return Search.EMPTY_REZULT;
		}
		
		int position = this.ifWardAlreadyInUserLiust(wordFromBuffer);
		
		if(position >= 0){
			this.reBuildUserList(position);
		}else{
			this.saveNewWord(wordFromBuffer + " " + resultSearch.getTranslate());
		}
		
		this.boxAdapter.addObject(resultSearch);
		this.boxAdapter.notifyDataSetChanged();
		
		return resultSearch.getTranslate();
	}

	void reBuildUserList(int position){
		this.boxAdapter.getList().remove(position);
	}
	
	private int ifWardAlreadyInUserLiust(String searchWord){
		for(int ix = 0; ix < this.boxAdapter.getCount(); ++ix){
			SearchEntity temp = (SearchEntity) this.boxAdapter.getItem(ix);
			if(temp.getExp().equals(searchWord)){
				return ix;
			}
		}
		
		return -1;
	}
	
	private void saveNewWord(String searchWard){
		SDCardFile defaultFile = new SDCardFile();
		
		defaultFile.setFileName(this.sessionManager.getSource()
				.defaultCurrentBook());

		this.sessionManager.getSource().writeFile(defaultFile,
				searchWard + "\n");
		
		defaultFile = null;
	}
	
	private int calculateProgress() {
		int currentProgress = 0;

		if (TranslateProcessManager.cProgress == 0) {
			return currentProgress;
		}

		currentProgress = (TranslateProcessManager.gProgress * 100)
				/ TranslateProcessManager.cProgress;

		return currentProgress;
	}

	public static void appMessage(String msg) {
		System.out.println(APP_MSG + msg);
	}
	
	public boolean checkExpression(String exp){
		if(exp == null || exp.length() == 0){
			return false;
		}
		
		for(char c : exp.toCharArray()){ 
			if(c < 97 || c > 122){
				return false;
			}
		}
		
		return true;
	}
	
    public static void toastManager(String msg){
    	Toast.makeText(MainActivity.context, msg, Toast.LENGTH_LONG).show();
    }

	public void deleteFromUserList() {
		if(!this.boxAdapter.isChecked()){
			return;
		}
		
		this.boxAdapter.deleteItem();
		this.boxAdapter.notifyDataSetChanged();
	}

}
