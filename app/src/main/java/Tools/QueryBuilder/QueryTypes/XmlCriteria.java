package Tools.QueryBuilder.QueryTypes;

import com.example.translateok.DebugActivity;

import Tools.QueryBuilder.ICriteria;

/**
 * Created by java_monkey on 3/6/2016.
 */
public class XmlCriteria implements IXmlCriteria {

    /**
     *
     */
    private StringBuilder query;

    /**
     *
     */
    private String searchingItem;

    public XmlCriteria(){
        this.query = new StringBuilder();
    }

    @Override
    public XmlCriteria setQueryItem(String field, String value) {
        this.query.append('/').append(field).append("[@").append(value).append(']');
        return this;
    }

    @Override
    public XmlCriteria setQueryItem(String field) {
        this.query.append('/').append(field);
        return this;
    }

    @Override
    public XmlCriteria setSearchingItem(String item){
        this.searchingItem = item;
        this.setQueryItem("[@" + item + "]");

        return this;
    }

    public String getSearchingItem(){
        return this.searchingItem;
    }

    @Override
    public String getQuery() {
        DebugActivity.logger.flushMessage("XML query:" + query.toString());
        return query.toString();
    }

    @Override
    public XmlCriteria setSearchingItem(String item, String value) {
        this.searchingItem = item;
        this.setQueryItem(item, value);

        return this;
    }
}
