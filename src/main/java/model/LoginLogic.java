package model;

import org.mindrot.jbcrypt.BCrypt;
public class LoginLogic {
    public boolean execute(User user, String dbHashedPassword) {
        // インポートすることで、BCryptクラスがそのまま使えます
        return BCrypt.checkpw(user.getPass(), dbHashedPassword);
    }
}
