package minProj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ConnectionManager {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//JDBC Driver 등록
			Class.forName("org.mariadb.jdbc.Driver");

			//연결하기
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost/miniproj1db",
					"bituser", //계정이름 
					"1004" //계정비밀번호
					);

			System.out.println("연결 성공");

			//삽입 쿼리문
			//PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tb_users (UserId, UserPassword, UserName, UserEmail) VALUES (?,?,?,?)");
			
			//수정(update) 쿼리문
			//PreparedStatement pstmt = conn.prepareStatement("update tb_users set UserPassword=?, UserName=?, UserEmail=? where UserId=? ");
			
			//삭제(delete) 쿼리문
			//PreparedStatement pstmt = conn.prepareStatement("delete from tb_users where UserId=? ");
			
			//읽기(read) 쿼리문
			PreparedStatement pstmt = conn.prepareStatement("select userid, userpassword, username, useremail from tb_users where userid=?");
			
			Scanner scanner = new Scanner(System.in);
			while(true) {
				/*
				System.out.print("아이디 입력 : ");
				String UserId = scanner.nextLine();
				if (UserId.equals("q")) break;
				//입력 값을 설정 한다
				pstmt.setString(1, UserId);
				
				System.out.println("비밀번호 입력 : ");
				String UserPassword = scanner.nextLine();
				pstmt.setString(2, UserPassword);
				
				System.out.println("사용자 이름 입력 : ");
				String UserName = scanner.nextLine();
				pstmt.setString(3, UserName);
				
				System.out.println("이메일 입력 : ");
				String UserEmail = scanner.nextLine();
				pstmt.setString(4, UserEmail);
				
				
				System.out.println("입력된 아이디 : " + UserId);
				System.out.println("입력된 비밀번호 : " + UserPassword);
				System.out.println("입력된 이름 : " + UserName);
				System.out.println("입력된 이메일 : " + UserEmail);
				*/
				
				//-------------------------------------
				
				/*System.out.print("변경할 자료를 찾기 위한 아이디 입력 : ");
				String userid = scanner.nextLine();
				if (userid.equals("q")) break;

				
				System.out.println("비밀번호 : ");
				String userpassword = scanner.nextLine();
				System.out.println("이름 : ");
				String username = scanner.nextLine();
				System.out.println("이메일 : ");
				String useremail = scanner.nextLine();
				
				//입력 값 세팅
				pstmt.setString(1, userpassword);
				pstmt.setString(2, username);
				pstmt.setString(3, useremail);
				pstmt.setString(4, userid);
				*/
				
				//---------------------------------------
				
				/*
				System.out.println("정보를 삭제할 아이디 입력 : ");
				String userid = scanner.nextLine();
				if(userid.equals("q"))break;
				
				pstmt.setString(1, userid);
				*/
				
				
				//-------------------------------------
				System.out.println("상제 정보를 출력할 아이디 입력 : ");
				String userid = scanner.nextLine();
				if(userid.equals("q"))break;
				
				pstmt.setString(1, userid);
				
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					//찾고자 하는 자료가 있는 경우
					userid = rs.getString("UserId");
					String userpassword = rs.getString("UserPassword");
					String username = rs.getString("UserName");
					String useremail = rs.getString("UserEmail");
					
					System.out.println("userid : " + userid);
					System.out.println("userpassword : " + userpassword);
					System.out.println("username : " + username);
					System.out.println("useremail : " + useremail);
					
				}else {
					//찾고자 하는 자료가 없는 경우
					System.out.println("userid : [" + userid + "]에 대한 자료가 존재하지 않습니다.");
				}
				rs.close();
				
				int updated = pstmt.executeUpdate();
				//변경된 건 수 
				System.out.println("변경 건수  : " + updated);
				
				scanner.nextLine();
			}
			pstmt.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					//연결 끊기
					conn.close();
					System.out.println("연결 끊기");
				} catch (SQLException e) {}
			}
		}
	}
}
