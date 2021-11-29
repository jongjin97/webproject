package kr.ac.woosuk.computereng.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.ac.woosuk.computereng.models.UserDTO;

public class UserDao {

	private String driverName;
	private String url;
	private String username;
	private String password;

	public UserDao() {
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/woosuk?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
		this.username = "root";
		this.password = "123qwe";
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName(this.driverName);
		return DriverManager.getConnection(this.url, this.username, this.password);
	}

	public void insertUser(UserDTO user) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "INSERT INTO USER (ID, EMAIL, PASSWORD, JOB, GENDER, INTRODUCTION,CREATE_DATE) VALUES"
					+ "('"+user.getId()+"','"
					+user.getEmail()+"','"
					+user.getPassword()+"','"
					+user.getJob()+"','"
					+user.getGender()+"','"
					+user.getIntroduction()+"','"
					+user.getFormatDate()+"')";
			stmt.executeUpdate(sql);
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public UserDTO selectUserByUserName(String username) {
		UserDTO user = new UserDTO();

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT ID, EMAIL, PASSWORD, JOB, GENDER, INTRODUCTION,CREATE_DATE FROM USER WHERE ID='"+username+"'";
			ResultSet resultSet = stmt.executeQuery(sql);

			while(resultSet.next()) {
				user.setId(resultSet.getString("ID"));
				user.setEmail(resultSet.getString("EMAIL"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setJob(resultSet.getInt("JOB"));
				user.setGender(resultSet.getString("GENDER"));
				user.setIntroduction(resultSet.getString("INTRODUCTION"));
				user.setCreateDate(resultSet.getDate("CREATE_DATE"));
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public void updateUser(UserDTO user) {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "UPDATE USER SET "
					+ "EMAIL='"+user.getEmail()+"', "
					+ "PASSWORD='" + user.getPassword()+"', "
					+ "JOB="+ user.getJob()+", "
					+ "GENDER='"+ user.getGender()+"', "
					+ "INTRODUCTION='"+ user.getIntroduction()+"' "
					+ "WHERE ID='" + user.getId()+"'";
			stmt.executeUpdate(sql);
		}catch(SQLException |  ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
