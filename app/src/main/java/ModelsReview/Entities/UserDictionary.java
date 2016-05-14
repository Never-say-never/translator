package ModelsReview.Entities;

import com.example.translateok.DebugActivity;

import ModelsReview.ModelAbsNew;
import Tools.QueryBuilder.QueryTypes.IXmlCriteria;

/**
 * Created by java_monkey on 2/19/2016.
 */
public class UserDictionary extends ModelAbsNew {

    public UserDictionary(){
        DebugActivity.logger.flushMessage(
                this.getClass().getName() + " loaded!");
    }

    @Override
    public void init() {

    }

    @Override
    public Object find(IXmlCriteria criteria) {
        return null;
    }
}
