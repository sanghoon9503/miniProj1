package minProj1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private String userid;
	private String userpassword;
	private String username;
	private String useremail;
	
	public User()
	{
		
	}
	
	public void print() {
		System.out.printf("%-10s%-20s%-15s%-50s\n", 
				"UserId", 
				"UserPassword", 
				"UserName", 
				"UserEmail");
	}
	
	public void printDetail() {
		System.out.println("회원 아이디 : " + userid );
		System.out.println("회원 비밀번호 : " + userpassword );
		System.out.println("회원 이름 : " + username );
		System.out.println("회원 이메일 : " + useremail);
	}
}
