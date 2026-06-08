import java.util.Arrays;
import java.util.List;

import model.UserService;

public class Maintest {

	public static void main(String[] args) {
		UserService userservice = new UserService();
		
		List<Integer> roles = Arrays.asList(1, 2);//司書、管理者しか入れない
		
		int currentRole = 0;
		
		if(userservice.checkPermission(roles, currentRole)) {
			System.out.println("アクセスに成功しました。権限は:" + currentRole + "です。");
		}else {
			System.out.println("アクセスに失敗しました。権限は:" + currentRole + "はアクセス不可です。");

		}
	}
}
