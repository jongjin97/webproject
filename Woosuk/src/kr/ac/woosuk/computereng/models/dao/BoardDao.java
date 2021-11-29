package kr.ac.woosuk.computereng.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import kr.ac.woosuk.computereng.models.BoardDTO;

public class BoardDao {
	private String driverName;
	private String url;
	private String username;
	private String password;

	public BoardDao() {
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/woosuk?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
		this.username = "root";
		this.password = "123qwe";
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(this.driverName);
		return DriverManager.getConnection(this.url, this.username, this.password);
	}

	public void insertBoard(BoardDTO board) {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			System.out.println("ss");
			String sql = "INSERT INTO BOARD (TITLE, SUB_TITLE, CONTENTS, CREATE_DATE, WRITER) VALUES " 
			+ "('" + board.getTitle() 
			+ "', '" + board.getSubTitle() 
			+ "', '" + board.getContents() 
			+ "', '" + board.getFormatDate()
			+ "', '" + board.getWriter() + "')";
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = stmt.getGeneratedKeys();
			if(resultSet.next())
				board.setId(resultSet.getInt(1));

			sql = "INSERT INTO ATTACH_FILE (BOARD_ID, PATH, ORIGINAL_FILE_NAME, SAVE_FILE_NAME)  VALUES " 
			+ "('" + board.getId() 
			+ "', '" + board.getAttachFile().getPath() 
			+ "', '" + board.getAttachFile().getOriginalFileName() 
			+ "', '" + board.getAttachFile().getSaveFileName() + "')";
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public ArrayList<BoardDTO> boardList() {
		ArrayList<BoardDTO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "select * from board";
			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setId(result.getInt(1));
				dto.setTitle(result.getString(2));
				dto.setSubTitle(result.getString(3));
				dto.setContents(result.getString(4));
				dto.setCreateDate(result.getTimestamp(5));
				dto.setWriter(result.getString(6));
				list.add(dto);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public BoardDTO boardMain(BoardDTO dto) {
		BoardDTO dto2 = new BoardDTO();

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();

			String sql = "select * from board where id = " + dto.getId();
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {
				dto2.setId(result.getInt(1));
				dto2.setTitle(result.getString(2));
				dto2.setSubTitle(result.getString(3));
				dto2.setContents(result.getString(4));
				dto2.setCreateDate(result.getTimestamp(5));
				dto2.setWriter(result.getString(6));
				dto2.setAttachFile(new AttachFileDao().getFile(dto));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto2;
	}

	public ArrayList<BoardDTO> indexList() {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			String sql;

			sql = "SELECT * FROM board order by create_date desc";
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setId(result.getInt(1));
				dto.setTitle(result.getString(2));
				dto.setSubTitle(result.getString(3));
				dto.setContents(result.getString(4));
				dto.setCreateDate(result.getTimestamp(5));
				dto.setWriter(result.getString(6));
				dto.setAttachFile(new AttachFileDao().getFile(dto));
				list.add(dto);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public void deleteBoard(BoardDTO dto) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			String sql;

			sql = "delete from board where id = " + dto.getId();
			stmt.executeUpdate(sql);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateBoard(BoardDTO dto) {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			String sql = "update board set "
					+ "title='" + dto.getTitle() + "', "
					+ "sub_title='" + dto.getSubTitle() + "', "
					+ "contents='" + dto.getContents() + "', "
					+ "create_date='" + dto.getFormatDate() + "', "
					+ "writer='" + dto.getWriter() + "' "
					+ "where id='" + dto.getId() + "'";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			sql = "update attach_file set "
					+ "path='" + dto.getAttachFile().getPath() + "', "
					+ "original_file_name='" + dto.getAttachFile().getOriginalFileName() + "', "
					+ "save_file_name='" + dto.getAttachFile().getSaveFileName() + "' "
					+ "where board_id='" + dto.getId() + "'";
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
