package ModelsReview.Entities;

import com.example.translateok.DebugActivity;
import com.example.translateok.MainActivity;

import javax.xml.parsers.ParserConfigurationException;

import ModelsReview.ModelAbsNew;
import Tools.FileWorkers.FileImplementation.FileWorkerXml;
import Tools.FileWorkers.FileImplementation.IFile;
import Tools.FileWorkers.FileManager;
import Tools.FileWorkers.IDataSourceDriver;
import Tools.QueryBuilder.ICriteria;
import Tools.QueryBuilder.QueryTypes.IXmlCriteria;
import Tools.QueryBuilder.QueryTypes.XmlCriteria;
import Tools.SpeedParsers.IParser;
import Tools.SpeedParsers.IParserQuery;
import Tools.SpeedParsers.Parsers.ParserXmlWrapper;

/**
 * Created by java_monkey on 3/2/2016.
 */
public class ApplicationConfigNew extends ModelAbsNew{

    /**
     * Default constant file with app configuration
     */
    private final static String CONFIG_FILE_PATH = "application_config";

    private String vocabularyVersion;

    private String appKey;

    private String version;

    private Integer UserVocabularies;

    private Integer appRunCounter;

    private Integer userWordsRate;

    private static ApplicationConfigNew instance = new ApplicationConfigNew(
            new ParserXmlWrapper(), new FileManager());

    public static ApplicationConfigNew getInstance(){
        if(instance == null){
            instance = new ApplicationConfigNew(
                    new ParserXmlWrapper(),
                    new FileManager());

            return instance;
        }

        return instance;
    }

    private ApplicationConfigNew(IParser parser, IDataSourceDriver driver) {
        super(parser, driver);
    }

    private ApplicationConfigNew(){

    }

    public String getVocabularyVersion() {
        if (this.vocabularyVersion == null){
            this.vocabularyVersion = this.find(new XmlCriteria()
                    .setQueryItem("app_config")
                    .setSearchingItem("vocabulary_v",IXmlCriteria.ATTR_VALUE)).toString();

            return this.vocabularyVersion;
        }

        return vocabularyVersion;
    }

    public void setVocabularyVersion(String vocabularyVersion) {
        this.changesMap.put("vocabulary_v", appKey);
        this.vocabularyVersion = vocabularyVersion;
    }

    public String getAppKey() {
        if (this.appKey == null){
            this.appKey = this.find(new XmlCriteria()
                    .setQueryItem("app_config")
                    .setSearchingItem("app_key", IXmlCriteria.ATTR_VALUE)).toString();

            return this.appKey;
        }

        return appKey;
    }

    public void setAppKey(String appKey) {
        this.changesMap.put("app_key", appKey);
        this.appKey = appKey;
    }

    public String getVersion() {
        if (this.version == null){
            this.version = this.find(new XmlCriteria()
                    .setQueryItem("app_config")
                    .setSearchingItem("version", IXmlCriteria.ATTR_VALUE)).toString();

            return this.version;
        }

        return version;
    }

    public void setVersion(String version) {
        this.changesMap.put("version", version);
        this.version = version;
    }

    public int getUserVocabularies() {
        if (this.UserVocabularies == null){
            this.UserVocabularies = Integer.valueOf(
                    this.find(new XmlCriteria()
                            .setQueryItem("app_config")
                            .setQueryItem("statistic")
                            .setSearchingItem("user_vocabularies", IXmlCriteria.ATTR_VALUE)).toString());

            return this.UserVocabularies;
        }

        return UserVocabularies;
    }

    public void setUserVocabularies(int userVocabularies) {
        this.changesMap.put("user_vocabularies", String.valueOf(userVocabularies));
        UserVocabularies = userVocabularies;
    }

    public int getAppRunCounter() {
        if (this.appRunCounter == null){
            this.appRunCounter = Integer.valueOf(
                    this.find(new XmlCriteria()
                            .setQueryItem("app_config")
                            .setQueryItem("statistic")
                            .setSearchingItem("app_run_count", IXmlCriteria.ATTR_VALUE)).toString());

            return this.appRunCounter;
        }

        return appRunCounter;
    }

    public void setAppRunCounter(int appRunCounter) {
        this.changesMap.put("app_run_count", String.valueOf(appRunCounter));
        this.appRunCounter = appRunCounter;
    }

    public int getUserWordsRate() {
        if (this.userWordsRate == null){
            this.userWordsRate = Integer.valueOf(
                    this.find(new XmlCriteria()
                            .setQueryItem("app_config")
                            .setQueryItem("statistic")
                            .setSearchingItem("user_words", IXmlCriteria.ATTR_VALUE)).toString());

            return this.userWordsRate;
        }

        return userWordsRate;
    }

    public void setUserWordsRate(int userWordsRate) {
        this.changesMap.put("user_words", String.valueOf(userWordsRate));
        this.userWordsRate = userWordsRate;
    }

    @Override
    public Object find(IXmlCriteria criteria) {
        Object[][] tmp =  ((IParserQuery) this.parser).runQuery(criteria);
        if(tmp.length == 1){
            return tmp[0][1];
        }

        return tmp[tmp.length - 1][1];
    }

    /**
     * Define type of source
     */
    @Override
    public void init() {
        try {
            FileWorkerXml fileWorker = new FileWorkerXml(IFile.HOME_PATH + CONFIG_FILE_PATH);
            fileWorker.setContext(MainActivity.context);
            this.driver.setSource(fileWorker);
        } catch (Exception e) {
            e.printStackTrace();
            DebugActivity.logger.flushMessage(
                    this.getClass().getName()+ " :msg " + e.getMessage());
        }
    }
}
