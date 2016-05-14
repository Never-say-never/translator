package Models.Structures;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by java_monkey on 2/20/2016.
 */
public final class ConfigStructure {

    /**
     * Map with configuration
     */
    private final Map<String, String> configMap = new HashMap();

    /**
     * Map with user statistics
     */
    private final Map<String, Integer> statisticsMap = new HashMap();

    public ConfigStructure(){
        configMap.put("version", "Translate Ok 1.0");
        configMap.put("app_key", "TOK-0000FFX00001");
        configMap.put("vocabulary_v", "v0.1_dictionary");

        statisticsMap.put("user_vocabularies", 0);
        statisticsMap.put("app_run_count", 0);
        statisticsMap.put("user_words", 0);
    }

    /**
     * Get default setup for statistics
     * @return
     */
    public Map<String, Integer> getStatistic(){
        return statisticsMap;
    }

    /**
     * Get default setups for config
     * @return
     */
    public Map<String, String> getConfigs(){
        return configMap;
    }

}
