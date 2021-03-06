package controllers;


import controllers.internal.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

public class Application extends Controller {

    public static Result login() {
        return ok(
                login.render(form(Login.class))
        );
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                    routes.Tasks.index()
            );
        }

    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();

        flash("success", "You've been logged out");

        return redirect(
                routes.Application.login()
        );
    }


}