package SearchTranslatCore;

import android.annotation.SuppressLint;

public class SearchProcess implements ISearchProcess {
	
	private ISearchEntity sourse;
	
	public SearchProcess() {

	}
	
	@Override
	public void setSource(ISearchEntity source) {
		this.sourse = source;
	}

	public String getTranslate() {
		String translite = this.sourse.translate();
		
		if(translite == null){
			return "Nothing to find, pleas load expression to buffer or use siple search";
		}
		
		if(translite.length() == 0){
			return "Nothing to find, pleas load expression to buffer or use siple search";
		}
		
		return translite;
	}

	@Override
	public ISearchEntity getSource() {
		// TODO Auto-generated method stub
		return null;
	}

}
