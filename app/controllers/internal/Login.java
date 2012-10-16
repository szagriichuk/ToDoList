package controllers.internal;

import models.User;

/**
 * @author Sergii.Zagriichuk
 */
public class Login {

    public String email;
    public String password;

    public String validate() {
        if (User.authenticate(email, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }


}
