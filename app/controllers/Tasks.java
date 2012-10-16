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
        return ok(
                index.render(Task.all(), User.getActiveUser(), taskForm)
        );
    }

    public static Result newTask() {
        return TODO;
//        Form<Task> filledForm = taskForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//            return badRequest(
//                    index.render(Task.all(), filledForm)
//            );
//        } else {
//            Task.create(filledForm.get());
//            return redirect(routes.Tasks.tasks());
//        }
    }

    public static Result deleteTask(Long id) {
        if (Secured.isOwnerOf(id)) {
            Task.find.ref(id).delete();
            return ok();
        } else {
            return forbidden();
        }
    }
}

