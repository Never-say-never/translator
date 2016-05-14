package ModelsReview;

import java.util.HashMap;
import java.util.Map;

import Tools.FileWorkers.FileImplementation.IFile;
import Tools.FileWorkers.IDataSourceDriver;
import Tools.QueryBuilder.ICriteria;
import Tools.SpeedParsers.IParser;

/**
 * Created by java_monkey on 3/2/2016.
 */
public abstract class ModelAbsNew implements IModelNew{

    protected IParser parser;

    protected IDataSourceDriver driver;

    /**
     * primal data format
     */
    protected Object dataObject;

    /**
     * map with uncommitted/unsaved changes
     */
    protected Map<String, String> changesMap;

    public ModelAbsNew() {
        this.changesMap = new HashMap();
    }

    public ModelAbsNew(IParser parser, IDataSourceDriver driver){
        this();
        this.parser = parser;
        this.driver = driver;
    }

    protected void load(){
        this.read();
    }

    @Override
    public void save() {
        this.driver.save(this.dataObject);
    }

    @Override
    public void read() {
        this.dataObject = this.driver.read();
        this.parser.setContent(((IFile) this.dataObject).getContent());
    }

    @Override
    public void update() {
        this.driver.save(this.dataObject);
    }

    @Override
    public void delete() {
        this.driver.removeFile();
    }

    public ModelAbsNew setParser(IParser parser) {
        this.parser = parser;
        return this;
    }

    public ModelAbsNew setDriver(IDataSourceDriver driver) {
        this.driver = driver;
        return this;
    }

    /**
     * fix all changes
     */
    public void commit(){
        this.driver.save(this.changesMap);
        this.changesMap = new HashMap<String, String>();
    }

}
