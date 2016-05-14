package Tools.SpeedParsers;

import java.util.ArrayList;

/**
 * Created by java_monkey on 2/22/2016.
 */
public abstract class ParserAdapter implements IParser {

    protected String attributeSearch;

    protected ArrayList<String> nodeListSearch;

    /**
     * Clear search parameters
     */
    protected void clearQuery(){
        this.nodeListSearch.clear();
        this.nodeListSearch.trimToSize();

        this.attributeSearch = null;
    }

    public abstract void init();

}
