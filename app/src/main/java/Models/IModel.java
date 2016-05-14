package Models;

import Tools.FileWorkers.IDataSourceDriver;
import Tools.SpeedParsers.IParser;

/**
 * Created by java_monkey on 2/17/2016.
 */
public interface IModel {

    /**
     *
     * @return
     */
    public abstract IModel init();

    /**
     *
     * @param obj
     * @return
     */
    public abstract boolean write(Object obj);

    /**
     *
     * @return
     */
    public abstract boolean create();

    /**
     *
     * @return
     */
    public abstract boolean delete();

    /**
     *
     */
    public abstract boolean read();

    /**
     *
     * @return
     */
    public abstract IParser find();

     /**
     *
     * @param source
     */
    public abstract void setSourceDriver(IDataSourceDriver source);

    /**
     *
     * @return
     */
    public abstract IDataSourceDriver getDriver();
}
