package kr.ac.woosuk.computereng.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.ac.woosuk.computereng.models.AttachFileDTO;
import kr.ac.woosuk.computereng.models.BoardDTO;

public class AttachFileDao {
	private String driverName;
	private String url;
	private String username;
	private String password;

	public AttachFileDao() {
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/woosuk?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
		this.username = "root";
		this.password = "123qwe";
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName(this.driverName);
		return DriverManager.getConnection(this.url, this.username, this.password);
	}
	
	public AttachFileDTO getFile(BoardDTO dto2) {
		AttachFileDTO dto = new AttachFileDTO();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "select * from attach_file where BOARD_ID = " + dto2.getId();
			ResultSet rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				dto.setIdx(rset.getInt(2));
				dto.setPath(rset.getString(3));
				dto.setOriginalFileName(rset.getString(4));
				dto.setSaveFileName(rset.getString(5));
			}
				
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}
