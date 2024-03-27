package minProj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (userid, username, userpassword, userage, useremail) VALUES (?,?,?,?,?)");
			Scanner scanner = new Scanner(System.in);
			while(true) {
				System.out.print("아이디 입력 : ");
				String userid = scanner.nextLine();
				if (userid.equals("q")) break;

				//입력 값을 설정 한다
				pstmt.setString(1, userid);
				pstmt.setString(2, "홍길동");
				pstmt.setString(3, "1004");
				pstmt.setInt(4, 20);
				pstmt.setString(5, "hong1@naver.com");

				System.out.println("입력된 아이디 : " + userid);

				int updated = pstmt.executeUpdate();
				//변경된 건 수 
				System.out.println("변경 건수  : " + updated);
			}


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
