package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class Task extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String title;

    @ManyToOne
    public User assignedTo;

    public boolean done = false;

    public static Finder<Long,Task> find = new Finder<>(
            Long.class, Task.class
    );

    public static List<Task> all() {
        return find.all();

        //find.join("project")
//        .where()
//                .eq("done", false)
//                .eq("project.members.email", user)
//                .findList()
    }

    public static void create(Task task) {
        task.save();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    /**
     * Check if a user is the owner of this task
     */
    public static boolean isOwner(Long task, String user) {
        return find.where()
                .eq("project.members.email", user)
                .eq("id", task)
                .findRowCount() > 0;
    }

}


