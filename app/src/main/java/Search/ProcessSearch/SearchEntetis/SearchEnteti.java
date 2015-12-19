package Search.ProcessSearch.SearchEntetis;

public interface SearchEnteti {

	public abstract void setExp(String entety);
	
	public abstract String getExp();
	
	public abstract String[] getAtomOfExp();

	public abstract void setTranslate(String string);
	
	public abstract String getTranslate();
	
	public abstract boolean checkChecked();
	
	public abstract void checkChecked(boolean check);
}
