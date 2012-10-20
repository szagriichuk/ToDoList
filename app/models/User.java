package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "account")
public class User extends Model {

    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;

    public boolean isActive = false;

    // -- Queries

    public static Model.Finder<String, User> find = new Model.Finder(String.class, User.class);

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {

        return find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

    public static User getActiveUser(String email) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.email.equals(email))
                return user;
        }
        return new NullUser();
    }

    // --

    public String toString() {
        return "User(" + email + ")";
    }

    public static void logout(String email, String password) {
        User user = find.where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();

        user.isActive = false;
    }

}

