package minProj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class UserExample {
	
	private Scanner scanner = new Scanner(System.in);
	
	public void UsersList() {
		System.out.println();
		System.out.println("[회원들 목록]");
		System.out.println("------------------------------------------------------------------------");
		System.out.printf("%-10s%-20s%-15s%-50s\n", "UserId", "UserPassword", "UserName", "UserEmail");
		System.out.println("------------------------------------------------------------------------");
		System.out.println();
		
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
			
			PreparedStatement pstmt = conn.prepareStatement("select * from tb_users");
			ResultSet rs = pstmt.executeQuery();
			
			//등록된 회원이 없을때를 위한 변수
			boolean uExistData = false;
			
			while(rs.next()) {
				uExistData = true;
				
				String userid = rs.getString("userid");
				String userpassword = rs.getString("userpassword");
				String username = rs.getString("username");
				String useremail = rs.getString("useremail");
				
				System.out.println("userid : " + userid);
				System.out.println("userpassword : " + userpassword);
				System.out.println("username : " + username);
				System.out.println("useremail : " + useremail);
				System.out.println("=========================\n");
			}
			
			//회원이 없을경우 출력
			if(!uExistData) {
				System.out.println("등록된 회원이 없습니다.");
			}
			
			rs.close();
			pstmt.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		//여기서 연결을 끊어버리면 안된다
			finally {
			if(conn != null) {
				try {
					//연결 끊기
					conn.close();
					System.out.println("연결 끊기");
				} catch (SQLException e) {}
			}
		}
		MainMenu();
	}
	
	public void MainMenu() {
		System.out.println();
		System.out.println("메인 메뉴 : 1.Insert || 2.Read || 3. Update || 4. Delete");
		System.out.println("--------");
		System.out.println("메뉴 선택 : ");
		String menuNo = scanner.nextLine();
		System.out.println();
		
		switch(menuNo) {
			case "1" -> Insert();
			case "2" -> Read();
			case "3" -> Exit();
		}
	}
	
	public void Insert() {
		System.out.println("새로운 회원 등록");
		System.out.println("아이디 : ");
		String userid = scanner.nextLine();
		System.out.println("비밀번호 : ");
		String userpassword = scanner.nextLine();
		System.out.println("이름 : ");
		String username = scanner.nextLine();
		System.out.println("이메일 : ");
		String useremail = scanner.nextLine();
		
		System.out.println("------------------------------");
		System.out.println("보조 메뉴 : 1. 새로운 회원을 정말로 등록 하시겠습니까? || 2. 취소하시겠습니까?");
		System.out.println("메뉴 선택 : ");
		String menuNO = scanner.nextLine();
		if(menuNO.equals("1")) {
		
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
	
				PreparedStatement pstmt = conn.prepareStatement("insert into tb_users(userid, userpassword, username, useremail) values(?,?,?,?)");
				
						pstmt.setString(1, userid);
						pstmt.setString(2, userpassword);
						pstmt.setString(3, username);
						pstmt.setString(4, useremail);
						
						int updated = pstmt.executeUpdate();
						System.out.println("입력 건수 : " + updated);
						
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
		UsersList();
	}
	
	public void Read() {
		System.out.println("[회원상세정보 읽기]");
		System.out.println("userid : ");
		
		String userid = scanner.nextLine();
		
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

			PreparedStatement pstmt = conn.prepareStatement("select * from tb_users where userid=?");
			
			//입력 값 설정
			pstmt.setString(1, userid);
			
			ResultSet rs = pstmt.executeQuery();
			//그 입력한 id가 있을 때
			if(rs.next()) {
				userid = rs.getString(1);
				String userpassword = rs.getString(2);
				String username = rs.getString(3);
				String useremail = rs.getString(4);
	
				System.out.println("userid : " + userid);
				System.out.println("userpassword : " + userpassword);
				System.out.println("username : " + username);
				System.out.println("useremail : " + useremail);
			
				//한명의 회원 정보 읽고 보조 메뉴 출력
				System.out.println("---------------------------");
				System.out.println("보조 메뉴 : 1.Update | 2. Delete | 3. List");
				System.out.println("메뉴 선택 : ");
				String menuNo = scanner.nextLine();
				System.out.println();
				
				switch(menuNo) {
				case "1" -> {
					System.out.println("수정할 회원의 userid를 입력하세요 : ");
					String updateUserId = scanner.nextLine();
					Update(updateUserId);
				}
				case "2" -> {
					System.out.println("삭제할 회원의 userid를 입력하세요 : ");
					String deleteUserId = scanner.nextLine();
					Update(deleteUserId);
				}
				case "3" -> UsersList();
				}
			
			
			
			}else {
				//변경하려고 할 id가 없을때
				System.out.println("[" + userid + "]에 대한 자료가 존재하지 않습니다.");
			}
			rs.close();
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

	public void Update(String userid) {
		
		System.out.println("수정할 정보를 입력하세요");
		System.out.println("비밀번호");
		String userpassword = scanner.nextLine();
		System.out.println("이름");
		String username = scanner.nextLine();
		System.out.println("이메일");
		String useremail = scanner.nextLine();
		
		System.out.println("---------------------------");
		System.out.println("보조 메뉴 : 1.Ok | 2.Cancel ");
		System.out.println("메뉴 선택 : ");
		String menuNo = scanner.nextLine();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from tb_users where userid=?");
			pstmt.setString(1, userid);
			
			int updated = pstmt.executeUpdate();
			pstmt.close();
			System.out.println("삭제된 건 수 : " + updated);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		UsersList();
		
	}
	
	public void Delete(String userid) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from tb_users where userid=?");
			pstmt.setString(1, userid);
			
			int updated = pstmt.executeUpdate();
			pstmt.close();
			System.out.println("삭제된 건 수 : " + updated);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		UsersList();
	}
	
	public void exit() {
		System.exit(0);
	}
	
	
	
}

