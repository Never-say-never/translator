package Debugger;

import android.widget.TextView;

import com.example.translateok.DebugActivity;
import com.example.translateok.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by java_monkey on 2/12/2016.
 */
public class ApplicationDebugger{

    private DebugActivity debug;

    /**
     * time format for debugging tools
     */
    private final String TIME_FORMAT = "HH:mm:ss";

    public ApplicationDebugger(){

    }

    /**
     * set activity
     * @param debug
     */
    public void setSource(DebugActivity debug){
        this.debug = debug;
    }

    /**
     * flush message with a type
     * @param msg
     * @param type
     */
    public void flushMessage(String msg, int type){
        TextView textView = (TextView) this.debug.findViewById(R.id.debugTextView);

        StringBuilder debugMessage = new StringBuilder();
        debugMessage.append("\n")
                .append(this.getLogTime())
                .append(" [")
                .append(type)
                .append("] >>> ")
                .append(msg);

        textView.append(debugMessage);
    }

    /**
     * flush message to debug window
     * @param msg
     */
    public void flushMessage(String msg){
        TextView textView = (TextView) this.debug.findViewById(R.id.debugTextView);

        StringBuilder debugMessage = new StringBuilder();
        debugMessage.append("\n")
                .append(" [")
                .append(this.getLogTime())
                .append("] >>> ")
                .append(msg);

        textView.append(debugMessage);
    }

    /**
     * get current time
     * @return
     */
    private String getLogTime(){
        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        return df.format(new Date());
    }

}
