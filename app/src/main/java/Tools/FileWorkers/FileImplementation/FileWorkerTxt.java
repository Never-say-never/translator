package Tools.FileWorkers.FileImplementation;

import com.example.translateok.DebugActivity;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by java_monkey on 2/21/2016.
 */
public class FileWorkerTxt extends FileAbstract {

    private String pathToFile;

    private StringBuilder content;

    @Override
    public Document getContent() {
        return null;
    }

    public FileWorkerTxt(String pathToFile){
        this.pathToFile = pathToFile;
        this.content = new StringBuilder();
    }

    /**
     *
     * @param pathToFile
     */
    public void setPathToFile(String pathToFile){
        this.pathToFile = pathToFile;
    }

    /**
     * Update all file content
     * @param obj
     * @return
     */
    @Override
    public boolean updateFile(Object obj) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(this.getOutStream());

        if(obj != null) {
            writer.write(((String) obj).toCharArray());
        }

        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean createFile() throws IOException{
        return this.updateFile(null);
    }

    @Override
    public boolean readFile() throws IOException{
        BufferedReader reader = this.getBufferedReader();
        String lineFromFile = null;
        while ((lineFromFile = reader.readLine()) != null) {
            this.content.append(lineFromFile);
        }

        DebugActivity.logger.flushMessage(
                this.pathToFile + " :" + this.content);

        reader.close();

        return true;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public String getPathToFile() {
        return this.pathToFile;
    }
}
