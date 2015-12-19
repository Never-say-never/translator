package SearchTranslatCore;

public interface ISearchProcess {

	public final String BUFFER_SEARCH_EMPTY = "byffer is empty!";
	
	public abstract void setSource(ISearchEntity source);
	
	public abstract String getTranslate();
	
	public abstract ISearchEntity getSource();
}
