package controllers.internal;

import models.User;

/**
 * @author Sergii.Zagriichuk
 */
public class Logout {

    public String email;
    public String password;

    public void validate() {
        User.logout(email, password);
    }

}
