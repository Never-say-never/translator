package ApplicationModules.ApplicationBootstrap;

import com.example.translateok.DebugActivity;

import java.util.ArrayList;

import AsyncTaskManager.ITask;
import AsyncTaskManager.TaskManager;
import AsyncTaskManager.Tasks.AsyncTask;
import Models.IModel;
import ModelsReview.Entities.ApplicationConfigNew;
import ModelsReview.IModelNew  ;

/**
 * Maneged each start of application:
 * - check dictionary
 * - check updates
 * - boot first start (create default configs)
 * - maneged start of file dictionary parser
 * - check if all settings is good and exist
 *
 * Created by java_monkey on 2/12/2016.
 */
public class ApplicationLoader {

    private ApplicationConfigNew applicationConfig;

    private ITask dictioanaryInstaller;

    private ArrayList<? extends IModel> userDictionaryList;

    private int bootCounter;

    /**
     *
     * @param applicationConfig
     */
    public ApplicationLoader(IModelNew applicationConfig, ITask dictioanaryInstaller){
        this.applicationConfig = (ApplicationConfigNew) applicationConfig;
        this.applicationConfig.init();

        this.dictioanaryInstaller = dictioanaryInstaller;

        DebugActivity.logger.flushMessage("created: "
                + this.getClass().getName());
    }

    /**
     *
     * @throws Exception
     */
    public void bootApplication() throws Exception {
        // load application configuration file
        // or create it if its first start
        this.loadApplicationConfigurationFile();

        //if first boot load and pars vocabulary
        if(this.isFirstBoot()){
            DebugActivity.logger.flushMessage("FIRST BOOT");
            // load user private vocabularies, update application_config file:
            // increment app_run_counter and setup available user dictionary
            this.loadUserVocabularies();
        }else{
            DebugActivity.logger.flushMessage("NOT FIRST BOOT");
            // run loader anyway for testing
            this.loadUserVocabularies();
        }

        // update configuration
        this.updateConfig();

        /*
        // check if all configurations files load and process correctly
        if(!this.checkBootStatus()){
            throw new Exception("Boot fail");
        }*/
    }

    /**
     * Async task for pars and mapping main dictionary
     */
    private void loadUserVocabularies(){
        // Dictionary located into assets, AsyncFileWorker look for it
        // load, pars and write file name to application_config
        // if such file does not exist into assets it should download it from
        // repo or some place.
        TaskManager manager = new TaskManager()
                .setTask(this.dictioanaryInstaller)
                .processTasks();

        // waiting all threads finished
        manager.waitingVocabularyParserFinished();
        // messages for debug
        DebugActivity.logger.flushMessage("FINISH PROCESSING VOCABULARY");
    }

    /**
     *
     * @return
     */
    private boolean updateConfig(){
        DebugActivity.logger.flushMessage("UPDATE CONFIG");
        this.applicationConfig.setAppRunCounter(
                this.applicationConfig.getAppRunCounter() + 1);

        this.applicationConfig.commit();

        return true;
    }

    /**
     *
     * @return
     */
    private boolean isFirstBoot(){
        return this.applicationConfig.getAppRunCounter() <= 0;
    }

    /**
     *
     * @return
     */
    private boolean checkBootStatus(){

        return true;
    }

    private void loadApplicationConfigurationFile(){
        DebugActivity.logger.flushMessage("READ");
        this.applicationConfig.read();
    }

}
