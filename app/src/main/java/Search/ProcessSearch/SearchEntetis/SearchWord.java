package Search.ProcessSearch.SearchEntetis;

import java.util.ArrayList;
import java.util.List;

public class SearchWord implements SearchEntity {

	private String expression;
	
	private boolean isChecked;
	
	private List<String> translatePool;
	
	public SearchWord(String entety) {
		this.translatePool = new ArrayList<String>();
		this.setExp(entety);
		
		this.isChecked = false;
	}
	
	public void chekOn(){
		this.isChecked = true;
	}
	
	public void checkOff(){
		this.isChecked = false;
	}
	
	/* :D */
	public boolean checkChecked(){
		return this.isChecked;
	}

	@Override
	public void setExp(String entety) {
		this.expression = entety;
	}

	@Override
	public String getExp() {
		return this.expression;
	}

	@Override
	public String[] getAtomOfExp() {
		// TODO Auto-generated method stub
		return this.expression.split(" ");
	}

	@Override
	public void setTranslate(String string) {
		this.translatePool.add(string);
	}

	@Override
	public String getTranslate() {
		if(translatePool.isEmpty()){
			return "";
		}
		
		return translatePool.get(0);
	}

	@Override
	public void checkChecked(boolean check) {
		this.isChecked = check;
	}

}
