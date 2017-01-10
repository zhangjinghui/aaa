package com.zjh.control;

import java.io.IOException;
import com.zjh.service.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zjh.vo.User;

@WebServlet(name = "UserControl", urlPatterns = { "/user" })
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService service = new UserServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		User user = null;
		User user2 = null;
		RequestDispatcher dispatcher = null;
		switch (action) {
		case "login":
			user = new User();
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			if ((user2 = service.login(user)) != null) {
				request.getSession().setAttribute("user",user2);
				dispatcher = request.getRequestDispatcher("success.jsp");
			} else {
				dispatcher = request.getRequestDispatcher("index.html");
			}
			break;
		case "register":
			if(service.register(request)){
				dispatcher=request.getRequestDispatcher("index.html");
			}else{
				dispatcher=request.getRequestDispatcher("false.html");
			}
			break;
		}
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

	public void init() throws ServletException {

	}

	public void destroy() {
		super.destroy();
	}

}
