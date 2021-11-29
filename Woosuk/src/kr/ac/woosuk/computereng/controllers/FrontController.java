package kr.ac.woosuk.computereng.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.ac.woosuk.computereng.models.AttachFileDTO;
import kr.ac.woosuk.computereng.models.BoardDTO;
import kr.ac.woosuk.computereng.models.UserDTO;
import kr.ac.woosuk.computereng.models.dao.BoardDao;
import kr.ac.woosuk.computereng.models.dao.UserDao;

/**
 * Servlet implementation class FrontController
 */
@MultipartConfig(location = "C:/upload/", maxFileSize = 1024 * 1024, maxRequestSize = 1024 * 1024
		* 20, fileSizeThreshold = 1024)
@WebServlet("/controller/*")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpSeravletResponse
	 *      response)
	 */

	private void checkURL(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		request.setCharacterEncoding("UTF-8");

		if (url.equals("/Woosuk/controller/LoginCheck")) {
			this.LoginCheck(request, response);
		} else if (url.equals("/Woosuk/controller/Write_board")) {
			this.Write_board(request, response);
		} else if (url.equals("/Woosuk/controller/Signup")) {
			this.Signup(request, response);
		} else if (url.equals("/Woosuk/controller/Board")) {
			this.Board(request, response);
		} else if (url.equals("/Woosuk/controller/signout")) {
			this.signout(request, response);
		} else if (url.equals("/Woosuk/controller/modifyUser")) {
			this.modifyUser(request, response);
		} else if (url.equals("/Woosuk/controller/modifyBoard")) {
			this.modifyBoard(request, response);
		} else if (url.equals("/Woosuk/controller/updateUser")) {
			this.updateUser(request, response);
		} else if (url.equals("/Woosuk/controller/imageLoad")) {
			this.imageLoad(request, response);
		} else if (url.equals("/Woosuk/controller/deleteBoard")) {
			this.deleteBoard(request, response);
		} else if (url.equals("/Woosuk/controller/updateBoard")) {
			this.updateBoard(request, response);
		}
	}

	private void LoginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		UserDao userDao = new UserDao();
		UserDTO user = userDao.selectUserByUserName(name);

		if (name.equals(user.getId()) && pass.equals(user.getPassword())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("/Woosuk/index.jsp");
		} else if (!name.equals(user.getId())) {
			response.sendRedirect("/Woosuk/signin.jsp?message=ID");
		} else if (name.equals(user.getId()) && !pass.equals(user.getPassword())) {
			response.sendRedirect("/Woosuk/signin.jsp?message=PASS");
		}

	}

	private void Signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pass1 = request.getParameter("pass1");
		String job = request.getParameter("job");
		String gender = request.getParameter("priority");
		String message = request.getParameter("message");

		UserDTO user = new UserDTO();
		user.setId(name);
		user.setEmail(email);
		user.setPassword(pass1);
		user.setJob(Integer.parseInt(job));
		user.setGender(gender);
		user.setIntroduction(message);
		user.setCreateDate(new Date());

		UserDao userDao = new UserDao();
		userDao.insertUser(user);

		response.sendRedirect("/Woosuk/signin.jsp");
	}

	private void signout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		response.sendRedirect("/Woosuk/signin.jsp");
	}

	private void modifyUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("Woosuk/signin.jsp");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("../signup.jsp");
			view.forward(request, response);
		}
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pass1 = request.getParameter("pass1");
		String job = request.getParameter("job");
		String gender = request.getParameter("priority");
		String message = request.getParameter("message");

		UserDTO user = new UserDTO();
		user.setId(name);
		user.setEmail(email);
		user.setPassword(pass1);
		user.setJob(Integer.parseInt(job));
		user.setGender(gender);
		user.setIntroduction(message);
		user.setCreateDate(new Date());

		UserDao userDao = new UserDao();
		userDao.updateUser(user);

		response.sendRedirect("/Woosuk/controller/LoginCheck?name=" + name + "&pass=" + pass1);
	}

	private void Write_board(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		Collection<Part> parts = request.getParts();
		BoardDTO board = new BoardDTO();
		BoardDao dao = new BoardDao();
		for (Part part : parts) {
			if (part.getName().equals("title")) {
				board.setTitle(request.getParameter("title"));
			} else if (part.getName().equals("subtitle")) {
				board.setSubTitle(request.getParameter("subtitle"));
			} else if (part.getName().equals("contents")) {
				board.setContents(request.getParameter("contents"));
			} else if (part.getName().equals("file")) {
				String header = part.getHeader("content-disposition");
				AttachFileDTO attachFile = new AttachFileDTO();
				String strs[] = header.split(";");
				for (String str : strs) {
					if (str.contains("filename")) {
						attachFile.setPath("C:/upload/");
						attachFile.setOriginalFileName(this.extractFileNameFromHeader(str, request));
						attachFile.setSaveFileName(UUID.randomUUID().toString());
						part.write(attachFile.getSaveFileName());
					}
				}
				board.setCreateDate(new Date());

				board.setWriter(user.getId());
				board.setAttachFile(attachFile);
			}
		}

		dao.insertBoard(board);
		response.sendRedirect("/Woosuk/controller/Board");
	}

	private void modifyBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		BoardDTO dto = new BoardDTO();
		BoardDao dao = new BoardDao();
		BoardDTO dto2 = new BoardDTO();

		dto.setId(Integer.parseInt(request.getParameter("id")));
		dto2 = dao.boardMain(dto);

		request.setAttribute("dto", dto2);
		RequestDispatcher view = request.getRequestDispatcher("../write_board.jsp");
		view.forward(request, response);

	}

	private void Board(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		BoardDao dao = new BoardDao();
		BoardDTO dto = new BoardDTO();
		if(user != null) {
		int idx = 0;
		if (request.getParameter("idx") != null) {
			idx = Integer.parseInt(request.getParameter("idx"));

		} else {
			ArrayList<BoardDTO> sublist = dao.indexList();
			if(sublist.size() > 0)	
				idx = sublist.get(0).getId();
		}
		dto.setId(idx);
		
		ArrayList<BoardDTO> list = dao.boardList();
		BoardDTO subdto = dao.boardMain(dto);
		
		request.setAttribute("list", list);
		request.setAttribute("dto", subdto);
		}
//		this.paging(request, response);
		RequestDispatcher view = request.getRequestDispatcher("../board.jsp");
		view.forward(request, response);
	}

	private String extractFileNameFromHeader(String header, HttpServletRequest request) {
		String fileName = header.split("=")[1].replace("\"", "");
		String seperator = "";
		if (request.getHeader("user-agent").contains("Trident")) {
			seperator = "\\";
		} else {
			seperator = "/";
		}
		int idx = fileName.lastIndexOf(seperator);
		fileName = fileName.substring(idx + 1);

		return fileName;
	}

	private void imageLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		BoardDTO dto = new BoardDTO();
		BoardDao dao = new BoardDao();
		dto.setId(id);
		BoardDTO dto2 = dao.boardMain(dto);
		String path = dto2.getAttachFile().getPath();
		String filename = dto2.getAttachFile().getSaveFileName();
		File file = new File(path + filename);

		filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\";");

		FileInputStream input = new FileInputStream(file);
		ServletOutputStream output = response.getOutputStream();

		byte[] buffer = new byte[1024];
		int data = 0;
		while ((data = input.read(buffer)) != -1) {
			output.write(buffer, 0, data);
		}
		input.close();
		output.flush();
		output.close();

	}

	private void updateBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		Collection<Part> parts = request.getParts();
		BoardDao boardDao = new BoardDao();
		BoardDTO board = new BoardDTO();
		for (Part part : parts) {
			if (part.getName().equals("title")) {
				board.setTitle(request.getParameter("title"));
			} else if (part.getName().equals("subtitle")) {
				board.setSubTitle(request.getParameter("subtitle"));
			} else if (part.getName().equals("contents")) {
				board.setContents(request.getParameter("contents"));
			} else if (part.getName().equals("file")) {
				String header = part.getHeader("content-disposition");
				AttachFileDTO attachFile = new AttachFileDTO();
				String strs[] = header.split(";");
				for (String str : strs) {
					if (str.contains("filename")) {
						attachFile.setPath("C:/upload/");
						attachFile.setOriginalFileName(this.extractFileNameFromHeader(str, request));
						attachFile.setSaveFileName(UUID.randomUUID().toString());
						part.write(attachFile.getSaveFileName());
					}
				}
				board.setAttachFile(attachFile);
				board.setCreateDate(new Date());
				board.setWriter(user.getId());
				board.setId(id);
			}
		}

		boardDao.updateBoard(board);
		response.sendRedirect("/Woosuk/controller/Board?idx=" + id);
	}

	private void deleteBoard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDTO dto = new BoardDTO();
		dto.setId(Integer.parseInt(request.getParameter("id")));
		BoardDao dao = new BoardDao();
		dao.deleteBoard(dto);
		this.Board(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.checkURL(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.checkURL(request, response);
	}
}
