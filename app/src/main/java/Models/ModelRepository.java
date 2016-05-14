package Models;

import com.example.translateok.DebugActivity;

import Models.Entities.ApplicationConfig;
import ModelsReview.Entities.ApplicationConfigNew;
import ModelsReview.IModelNew;
import Tools.FileWorkers.FileManager;
import Tools.SpeedParsers.Parsers.ParserXmlWrapper;

/**
 * Created by java_monkey on 2/17/2016.
 */
public class ModelRepository {

    private final Class<IModelNew> TYPE = IModelNew.class;

    private final static String PATH_TO_MODELS = "ModelsReview.Entities.";

    public ModelRepository(){

    }

    public IModelNew getModel(final String className){
        DebugActivity.logger.flushMessage(this.getClass().getName()
                + " :load Class " + className);
        IModelNew model = null;
        try {
            model = this.TYPE.cast(Class.forName(PATH_TO_MODELS + className).newInstance());
        } catch (InstantiationException e) {
            DebugActivity.logger.flushMessage(e.getMessage());
        } catch (IllegalAccessException e) {
            DebugActivity.logger.flushMessage( e.getMessage());
        } catch (ClassNotFoundException e) {
            DebugActivity.logger.flushMessage(e.getMessage());
        }

        return model;
    }

    public ApplicationConfigNew getApplicationConfig(){
        return ApplicationConfigNew.getInstance();
    }
}
