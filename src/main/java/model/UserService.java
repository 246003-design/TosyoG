//サイトの最初に今の権限でアクセスできるか確認するクラス
//sessionが保持してる権限情報を渡してください
package model;

import java.util.List;

public class UserService {
		
	public boolean checkPermission(List<Integer> roles, int currentUser) {//いったんstring型で受け取る
		if(roles.isEmpty() || currentUser == -1)return false;//未入力の場合-1
		
		for(int role : roles) {
			if(role == currentUser) {
				return true;
			}
		}
		return false;
	}
}
