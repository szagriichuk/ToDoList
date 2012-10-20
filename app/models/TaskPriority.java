package models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sergiizagriichuk
 */
public enum TaskPriority {
    HIGH, NORMAL, LOW;

    public static Map<String, String> options() {
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();

        for (TaskPriority taskPriority : TaskPriority.values()) {
            options.put(taskPriority.name(), taskPriority.name());
        }

        return options;
    }
}
