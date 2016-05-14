package Models.Entities;

import com.example.translateok.DebugActivity;
import com.example.translateok.MainActivity;

import javax.xml.parsers.ParserConfigurationException;

import Models.IModel;
import Tools.FileWorkers.FileImplementation.FileWorkerXml;
import Tools.FileWorkers.FileImplementation.IFile;
import Tools.FileWorkers.FileManager;
import Tools.FileWorkers.IDataSourceDriver;
import Tools.SpeedParsers.IParser;
import Tools.SpeedParsers.Parsers.ParserXmlWrapper;

/**
 * Created by java_monkey on 2/17/2016.
 *
 * Application config entity, should loading before start application.
 * To check conditions and setups.
 *
 * One instance to rule them all
 * One instance to find them
 * One instance to bring them all
 * And in the heap bind them
 */
public class ApplicationConfig implements IModel{

    /**
     * Default constant file with app configuration
     */
    private final static String CONFIG_FILE_PATH = "application_config.xml";

    /**
     * Document manager
     */
    private FileWorkerXml document;

    /**
     * Parser for easy getting xml data
     */
    private IParser xmlParser;

    /**
     * Driver for managing file system of devise
     * under application
     */
    private FileManager sourceDriver;

    /**
     *
     */
    private ApplicationConfig(){
        DebugActivity.logger.flushMessage(this.getClass().getName() + " loaded!");
    }

    /**
     *
     * @return
     */
    @Override
    public ApplicationConfig init(){
        try {
            // create and setup source manager
            FileWorkerXml fileWorker = new FileWorkerXml(this.getFullPath());
            fileWorker.setContext(MainActivity.context);

            // setup source manager
            this.sourceDriver.setSource(fileWorker);
            // setup content access manager
            this.xmlParser = new ParserXmlWrapper();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public IDataSourceDriver getDriver() {
        return this.sourceDriver;
    }

    /**
     * get full path to config file
     *
     * @return
     */
    private String getFullPath(){
        return IFile.HOME_PATH + CONFIG_FILE_PATH;
    }

    /**
     * Set driver for managing resources
     *
     * @param sourceDriver
     */
    @Override
    public void setSourceDriver(IDataSourceDriver sourceDriver) {
        this.sourceDriver = (FileManager) sourceDriver;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean write(Object obj) {
        DebugActivity.logger.flushMessage("WRITING");
        boolean result = this.sourceDriver.save(obj);
        DebugActivity.logger.flushMessage("WROTE!!!");

        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean create() {
        DebugActivity.logger.flushMessage("CREATE");
        return this.sourceDriver.create();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean delete() {
        DebugActivity.logger.flushMessage("DELETED");
        return this.sourceDriver.removeFile();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean read() {
        DebugActivity.logger.flushMessage(
                this.getClass().getName() + " get resource.");

        this.document = (FileWorkerXml) this.sourceDriver.read();
        this.setupContentForParser();

        DebugActivity.logger.flushMessage("HAS BEEN READ!");

        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public IParser find() {
        return this.xmlParser;
    }

    /**
     *
     */
    private void setupContentForParser(){
        this.xmlParser.setContent(this.document.getContent());
    }

    /**
     * Singleton
     * Create instance of model
     */
    private static class ApplicationConfigHolder{
        private final static ApplicationConfig instance = new ApplicationConfig();
    }

    /**
     * get instance of ApplicationConfig
     *
     * @return
     */
    public static ApplicationConfig getInstance(){
        return ApplicationConfigHolder.instance;
    }

}
