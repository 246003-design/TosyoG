package servlet;

import org.mindrot.jbcrypt.BCrypt;

public class HashGenerator {
	public static void main(String[] args) {
		
		// 💡 ここに、ハッシュ化したいパスワード（平文）を入力します
		String rawPassword = "pass123"; 
		
		// BCryptでハッシュ化！
		String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
		
		System.out.println("元のパスワード: " + rawPassword);
		System.out.println("ハッシュ化されたパスワード: ");
		System.out.println(hashedPassword); // ← これをコンソールからコピーする！
	}
}