package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
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

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date dueDate;

    public TaskPriority priority = TaskPriority.NORMAL;

    public static Finder<Long, Task> find = new Finder<>(
            Long.class, Task.class
    );

    public static List<Task> all() {
        return find.all();
    }

    public static List<Task> getAllTasksForUser(String userEmail) {
        return find.where().eq("assignedTo.email", userEmail).order().asc("title").findList();
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

    public static List<Task> getSortedList(String email, String sortBy, String order, String filter) {
        return find.where().ilike("title", "%" + filter + "%").eq("assignedTo.email", email).orderBy(sortBy + " " + order).findList();
    }

}


