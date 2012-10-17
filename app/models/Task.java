package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Task extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String title;

    @ManyToOne
    public User assignedTo;

    public boolean done = false;

    public static Finder<Long, Task> find = new Finder<>(
            Long.class, Task.class
    );

    public static List<Task> all() {
        return find.all();
    }

    public static List<Task> getAllTasksForUser(String userEmail) {
        return find.where().eq("assignedTo.email", userEmail).findList();
    }

    public static void create(Task task, User activeUser) {
        task.assignedTo = activeUser;
        task.save();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    /**
     * Check if a userEmail is the owner of this taskId
     */
    public static boolean isOwner(Long taskId, String userEmail) {
        return find.where().
                eq("id", taskId).
                eq("assignedTo.email", userEmail)
                .findRowCount() > 0;
    }

}


