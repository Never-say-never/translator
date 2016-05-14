package Tools.SpeedParsers;

import Tools.QueryBuilder.QueryTypes.IXmlCriteria;

/**
 * Created by java_monkey on 3/12/2016.
 */
public interface IParserQuery {

    public abstract Object[][] runQuery(IXmlCriteria criteria);

}
