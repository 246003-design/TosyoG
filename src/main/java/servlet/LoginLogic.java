package servlet;

public class LoginLogic {
	public boolean execute(User user) {
		//passwordを参照して判定
		if(user.getPass().equals(password)) {return ture;}
		return false;
	}
}
