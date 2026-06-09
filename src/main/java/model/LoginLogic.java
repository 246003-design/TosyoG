package model;

import org.mindrot.jbcrypt.BCrypt;

import entity.User;
public class LoginLogic {
	//ID,パスワード、ハッシュ化したパスワード
    public boolean execute(User user, String dbHashedPassword) {
        
    	String inputPassword = user.getPassword();
    	
        if(BCrypt.checkpw(inputPassword, dbHashedPassword)) {
        	return true;
        }
        return false;
    }
}
