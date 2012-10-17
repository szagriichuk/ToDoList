package controllers;

import models.Task;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.tasks.index;

/**
 * Manage tasks related operations.
 */
@Security.Authenticated(Secured.class)
public class Tasks extends Controller {
    private static Form<Task> taskForm = form(Task.class);

    public static Result index() {
        return redirect(routes.Tasks.tasks());
    }

    public static Result tasks() {

        User activeUser = User.getActiveUser();

        return ok(
                index.render(Task.getAllTasksForUser(activeUser.email), activeUser, taskForm)
        );
    }

    public static Result newTask() {
        Form<Task> filledForm = taskForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    index.render(Task.all(), User.getActiveUser(), filledForm)
            );
        } else {
            Task.create(filledForm.get(), User.getActiveUser());
            return redirect(routes.Tasks.tasks());
        }
    }

    public static Result deleteTask(Long id) {
        if (Secured.isOwnerOf(id)) {
            Task.find.ref(id).delete();
            return redirect(routes.Tasks.tasks());
        } else {
            return forbidden();
        }
    }
}

