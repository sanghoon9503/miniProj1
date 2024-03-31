package minProj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
	
	private static Connection conn = null;
	private static PreparedStatement UserListPstmt = null;
	//다른 객체도 정적화 하여 한곳에 모은다.
	private static PreparedStatement UserInsertPstmt = null;
	private static PreparedStatement UserReadPstmt = null;
	private static PreparedStatement UserDeletePstmt = null;

	static {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mariadb://localhost/miniproj1db",
					"bituser", //계정이름 
					"1004" //계정비밀번호
					);
			System.out.println("연결 성공");
			
			UserListPstmt = conn.prepareStatement("select * from tb_users");
			UserInsertPstmt = conn.prepareStatement("insert into tb_users(userid, userpassword, username, useremail) values(?,?,?,?)");
			UserReadPstmt = conn.prepareStatement("select * from tb_users where userid=?");
			UserDeletePstmt = conn.prepareStatement("delete from tb_users where userid=?");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	//SQL동작 구문 Method
	public List<User> list(){
		List<User> list = new ArrayList<>();
		try {
			ResultSet rs = UserListPstmt.executeQuery();
			while(rs.next()) {
				User user = new User(
						rs.getString("userid")
						,rs.getString("userpassword")
						,rs.getString("username")
						,rs.getString("useremail"));
				list.add(user);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int insert(User user) {
		int updated = 0;
		try{
			UserInsertPstmt.setString(1, user.getUserid());
			UserInsertPstmt.setString(2, user.getUserpassword());
			UserInsertPstmt.setString(3, user.getUsername());
			UserInsertPstmt.setString(4, user.getUseremail());
			
			updated = UserInsertPstmt.executeUpdate();
			conn.commit();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	
	public User read(String userid) {
		User user = null;
		try {
			UserReadPstmt.setString(1, userid);
			ResultSet rs = UserReadPstmt.executeQuery();
			//그 입력한 id가 있을 때
			if(rs.next()) {
				user = new User(rs.getString("userid")
								,rs.getString("userpassword")
								,rs.getString("username")
								,rs.getString("useremail"));
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public int update(User user) {
		return 0;
	}
	
	public int delete(int user) {
		int updated = 0;
		try {
				UserDeletePstmt.setString(1, userid);
				updated = UserDeletePstmt.executeUpdate();
				conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
}
