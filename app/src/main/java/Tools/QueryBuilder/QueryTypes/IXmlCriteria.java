package Tools.QueryBuilder.QueryTypes;

import Tools.QueryBuilder.ICriteria;

/**
 * Created by java_monkey on 3/6/2016.
 */
public interface IXmlCriteria extends ICriteria {

    public final String ATTR_VALUE = "value";

    public abstract XmlCriteria setQueryItem(String field, String value);

    public abstract XmlCriteria setQueryItem(String field);

    public abstract String getSearchingItem();

    public abstract XmlCriteria setSearchingItem(String item);

    public abstract XmlCriteria setSearchingItem(String item, String value);
}
