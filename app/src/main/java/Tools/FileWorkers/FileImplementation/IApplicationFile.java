package Tools.FileWorkers.FileImplementation;

/**
 * Created by java_monkey on 2/23/2016.
 */
public interface IApplicationFile {

    /**
     *
     */
    public static enum Types {PERMANENT, TEMPORARY, CACHE};

    /**
     *
     * @param type
     */
    public abstract void setFileType(Types type);

}
