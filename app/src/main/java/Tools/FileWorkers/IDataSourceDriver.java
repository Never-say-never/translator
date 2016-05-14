package Tools.FileWorkers;

import Tools.FileWorkers.FileImplementation.IFile;

/**
 * Created by java_monkey on 2/24/2016.
 */
public interface IDataSourceDriver{

    /**
     *
     * @return
     */
    public abstract IFile read();

    /**
     *
     * @param obj
     * @return
     */
    public abstract boolean save(Object obj);

    /**
     *
     * @return
     */
    public abstract boolean create();

    /**
     *
     * @return
     */
    public abstract boolean removeFile();

    /**
     *
     * @param file
     * @return
     */
    public abstract boolean removeFile(IFile file);

    /**
     *
     * @param source
     */
    public abstract void setSource(IFile source);
}
