package SearchTranslatCore.source;

import SearchTranslatCore.source.PrimalSearch.PrimalSerch;

public class SearchWord extends PrimalSerch implements IWord{

	private String searchWard;
	
	public SearchWord(String ward){
		System.out.println("constructer of Word()");
		this.searchWard = ward;
	}

	@Override
	public void setEntity(String entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(String translateEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String translate() {
		return this.target(this.searchWard).primalSearch();
	}
	
	public String getSerchWard(){
		return this.searchWard;
	}
	
}
