package model;

import play.data.validation.Constraints.Required;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergiizagriichuk
 */
public class Task {

    public Long id;

    @Required
    public String label;

    public static List<Task> all() {
        return new ArrayList<Task>();
    }

    public static void create(Task task) {
    }

    public static void delete(Long id) {
    }

}
