package com.example.translateok;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import ApplicationModules.ApplicationBootstrap.ApplicationLoader;
import AsyncTaskManager.Tasks.DictionaryInstaller;
import Debugger.ApplicationDebugger;
import Models.IModel;
import Models.ModelRepository;
import ModelsReview.IModelNew;
import Tools.FileWorkers.AsyncFileManager;
import Tools.FileWorkers.FileManager;
import Tools.FileWorkers.FileImplementation.FileWorkerXml;
import Tools.FileWorkers.FileImplementation.IFile;
import Tools.SpeedParsers.Parsers.ParserVocabulary;

/**
 * Created by java_monkey on 2/12/2016.
 */
public class DebugActivity extends Activity {

    public static ApplicationDebugger logger = new ApplicationDebugger();

    private String[] data = {
        "ApplicationLoader",
        "ApplicationConfig",
        "LoadModel"
    };

    public DebugActivity(){
        logger.setSource(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        TextView debuggerLog = (TextView) findViewById(R.id.debugTextView);
        debuggerLog.setMovementMethod(new ScrollingMovementMethod());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        // create component and test it
                        //new ModelRepository().getApplicationConfig();
                        ModelRepository modelRepo = new ModelRepository();
                        ApplicationLoader appLoader = new ApplicationLoader(
                                modelRepo.getApplicationConfig(),
                                new DictionaryInstaller(
                                        new ParserVocabulary(),
                                        new AsyncFileManager())
                        );

                        try {
                            appLoader.bootApplication();
                        } catch (Exception e) {
                            DebugActivity.logger.flushMessage(e.getMessage());
                        }

                        break;
                    case 1:
                        // test for txt
                        /*FileManager fileManager = new FileManager();
                        fileManager.setSourceDriver(
                                new FileWorkerTxt(IFile.HOME_PATH + "test\\test_txt100.txt")
                        );*/
                        // rewrite file
                        //fileManager.save("update 2");

                        //read current file
                        //fileManager.read();

                        //create empty file
                        //fileManager.create();

                        /*FileManager fileManager = new FileManager();
                        try {
                            fileManager.setSource(
                                    new FileWorkerXml(IFile.HOME_PATH + "test\\test1_xml.xml")
                                            .setContext(MainActivity.context)
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fileManager.create();

                        Map testMap = new HashMap<String, String>();
                        testMap.put("app_run_count", "236");
                        fileManager.save(testMap);

                        fileManager.read();*/

                        break;
                    case 2:
                        // create another component and test it
                        IModelNew model = new ModelRepository().getModel("UserDictionary");
                        model.read();

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(getBaseContext(), "Nothing was chosen", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
