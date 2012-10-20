package controllers;

import models.Task;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.tasks.createTask;
import views.html.tasks.editTask;
import views.html.tasks.index;

/**
 * Manage tasks related operations.
 */
@Security.Authenticated(Secured.class)
public class Tasks extends Controller {
    private static Form<Task> taskForm = form(Task.class);

    public static Result HOME = redirect(
           routes.Tasks.list("title", "asc", "")
       );

    public static Result index() {
        return HOME;
    }

    public static Result list(String sortBy, String order, String filter) {

        User activeUser = User.getActiveUser(session("email"));

        return ok(
                index.render(
                        Task.getSortedList(activeUser.email, sortBy, order, filter),taskForm,
                        activeUser, sortBy, order, filter
                )
        );
    }

    public static Result create() {
        User activeUser = User.getActiveUser(session("email"));

        Form<Task> taskForm = form(Task.class);

        return ok(
                createTask.render(activeUser, taskForm)
        );
//    return TODO;
    }

    public static Result edit(Long id) {

        User activeUser = User.getActiveUser(session("email"));

        Form<Task> taskForm = form(Task.class).fill(
                Task.find.byId(id)
        );
        return ok(
                editTask.render(id, activeUser, taskForm)
        );
//        return TODO;
    }

    public static Result update(Long taskId) {
        User activeUser = User.getActiveUser(session("email"));

        Form<Task> filledForm = taskForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(
                    editTask.render(taskId, activeUser, filledForm));
//            return TODO;
        } else {

            filledForm.get().update(taskId);

            return HOME;
        }
    }

    public static Result newTask() {

        Form<Task> filledForm = taskForm.bindFromRequest();

        User activeUser = User.getActiveUser(session("email"));

        if (filledForm.hasErrors()) {
            return badRequest(
                    createTask.render(activeUser, filledForm)
            );
//            return TODO;
        } else {
            Task.create(filledForm.get(), User.getActiveUser(session("email")));
            return HOME;
        }
    }

    public static Result deleteTask(Long id) {
        if (Secured.isOwnerOf(id)) {
            Task.find.ref(id).delete();
            return HOME;
        } else {
            return forbidden();
        }
    }
}

