package controller.mall.icia;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.services.mall.icia.Authentication;
import beans.Action;
import search.services.mall.icia.GoodsSearch;

@WebServlet({ "/Main", "/LogInForm", "/LogIn", "/JoinForm", "/Join", "/LogOut", "/Search" })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.adaptiveRouting(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.adaptiveRouting(request, response);
	}

	private void adaptiveRouting(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println(req.getParameter("member"));
		Action action = null;
		GoodsSearch goods = null;

		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);
		// 클라이언트 요청
		// Get Type
		if (req.getMethod().equals("GET")) {
			action = new Action();
			action.setActionType(true); // 기본값 false?
			action.setPage("index.jsp");
			action.setMessage("잘못된 접근입니다. 메인페이지로 이동합니다.");
		} else {
			
			// Post Type
			if (reqValue.equals("LogInForm")) {
				action = new Action();
				action.setActionType(false);
				action.setPage("login.jsp");
			} else if (reqValue.equals("JoinForm")) {
				action = new Action();
				action.setActionType(false);
				action.setPage("join.jsp");
			} else if (reqValue.equals("Main")) {
				action = new Action();
				action.setActionType(false);
				action.setPage("index.jsp");
			} else if (reqValue.equals("LogIn")) {
				Authentication auth = new Authentication();
				action = auth.entrance(req);
			} else if (reqValue.equals("Join")) {
				Authentication auth = new Authentication();
				action = auth.entrance(req);
			} else if (reqValue.equals("LogOut")) {
				Authentication auth = new Authentication();
				action = auth.entrance(req);
			} else if (reqValue.equals("Search")) {
				goods = new GoodsSearch();
				action = goods.entrance(req);

			}
			req.setAttribute("prev", req.getParameter("page"));
		}

		// 서버 응답
		if (action.isActionType()) {
			if (action.getMessage() != null) {
				res.sendRedirect(action.getPage() + "?message=" + URLEncoder.encode(action.getMessage(), "UTF-8"));

			} else {
				res.sendRedirect(action.getPage());
				System.out.println(action.getMessage());
			}
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			System.out.println(action.getPage());
			dispatcher.forward(req, res);
		}
	}

}
