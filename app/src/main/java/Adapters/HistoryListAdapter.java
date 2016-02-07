package Adapters;

import java.util.ArrayList;
import java.util.List;

import AppliacationManager.TranslateProcessManager;
import Search.ProcessSearch.SearchEntetis.SearchEntity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.translateok.R;

public class HistoryListAdapter extends BaseAdapter {

	private Context ctx;
	
	private LayoutInflater lInflater;
	
	private List<SearchEntity> objects;

	public HistoryListAdapter(Context context, List<SearchEntity> list, boolean _empty) {
		ctx = context;
		objects   = list;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public List<SearchEntity> getList(){
		return (List<SearchEntity>) this.objects;
	}
	
	public void addObject(SearchEntity obj) {
		this.objects.add(obj);
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		position = (this.objects.size() - 1) - position;

		view = lInflater.inflate(R.layout.list_item, parent, false);

		final LinearLayout lrno_a = (LinearLayout) view
				.findViewById(R.id.linearLayout2);
		
		((TextView) view.findViewById(R.id.tvDescr)).setText(
				this.objects.get(position).getExp() + " - " + 
				this.objects.get(position).getTranslate());
		
		((TextView) view.findViewById(R.id.tvPrice))
				.setText("example1, example2, example3 ...");

		if(position == this.objects.size() - 1){
			((ImageView) view.findViewById(R.id.ivImage))
			.setImageResource(R.drawable.currentword1);
		}else{
			((ImageView) view.findViewById(R.id.ivImage))
					.setImageResource(R.drawable.use);
		}

		CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
		cbBuy.setOnCheckedChangeListener(myCheckChangList);
		cbBuy.setTag(position);
		cbBuy.setChecked(false);

		/*LinearLayout lrno = (LinearLayout) view
				.findViewById(R.id.linearLayout1);

		lrno_a.setBackgroundColor(Color.argb(250,105,105,105));
		
		lrno.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lrno_a.setBackgroundColor(Color.argb(156, 168, 201,100));
			}
		});*/

		return view;
	}

	public String getProduct(int position) {
		return (String) getItem(position);
	}

	public OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			final CompoundButton lrno_a = (CompoundButton) buttonView
					.findViewById(R.id.cbBox);
			
			int currentIttem = Integer.parseInt(lrno_a.getTag().toString());
			
			objects.get(currentIttem).checkChecked(lrno_a.isChecked());
			
			Button dButton = TranslateProcessManager.main.getDeleteButton();
			if(!isChecked()){
				dButton.setVisibility(Button.INVISIBLE);
			}else{
				dButton.setVisibility(Button.VISIBLE);
			}
			
		}
	};

	public ArrayList<SearchEntity> getBox() {
		ArrayList<SearchEntity> box = new ArrayList<SearchEntity>();
		for (int ix = 0; ix < objects.size(); ++ix) {
			SearchEntity w = (SearchEntity) objects.get(ix);
			if(w.checkChecked())
				box.add(objects.get(ix));
		}

		return box;
	}
	
	public boolean isChecked(){
		for(SearchEntity e : objects){
			if(e.checkChecked())
				return true;
		}
		
		return false;
	}
	
	public void deleteItem(){
		List<SearchEntity> objectsT = new ArrayList<SearchEntity>();
		
		for (int ix = 0; ix < objects.size() - 1; ++ix) {
			SearchEntity enttity = (SearchEntity) this.objects.get(ix);
			System.out.println(" is :" + ix + " " + enttity.getExp() + " " + enttity.checkChecked());
			
			if(enttity.checkChecked() == false){
				objectsT.add(enttity);
			}
			
		}
		
		this.objects.clear();
		this.objects.addAll(objectsT);
		
		objectsT = null;
	}


}