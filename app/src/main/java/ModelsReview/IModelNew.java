package ModelsReview;

import Tools.QueryBuilder.ICriteria;
import Tools.QueryBuilder.QueryTypes.IXmlCriteria;

/**
 * Created by java_monkey on 3/2/2016.
 */
public interface IModelNew {

    public abstract void save();

    public abstract void read();

    public abstract void update();

    public abstract void delete();

    /**
     * initialisation model components
     */
    abstract public void init();

    abstract public Object find(IXmlCriteria criteria) ;
}
