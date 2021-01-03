package auth.services.mall.icia;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.AuthBean;

public class Authentication {
	private DataAccessObject dao;

	public Authentication() {
	}

	public Action entrance(HttpServletRequest req) {
		Action action = null;
		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);
		switch (reqValue) {
		case "Join":
			action = this.joinCtl(req);
			break;

		case "LogIn":
			action = this.logInCtl(req);
			break;
		}
		return action;
	}

	private Action logInCtl(HttpServletRequest req) {
		Action action = new Action();
		AuthBean auth = new AuthBean();
		boolean actionType = false;
		String page = "login.jsp";
		String message = "아이디나 패스워드가 올바르지 않습니다.";

		this.dao = new DataAccessObject();
		this.dao.getConnection();
		this.dao.setAutoCommit(false);

		auth.setmId(req.getParameterValues("accessInfo")[0]);
		auth.setmPassword(req.getParameterValues("accessInfo")[1]);
		auth.setAccessType(1);

		// 로그인 = db(AccessLog insert) + middle(Session)
		// userId check = 아이디 유무
		if (this.isUserId(auth)) {
			// 현재 활성화 여부 확인
			if (this.isActive(auth)) {
				// userId + userPassword 일치여부
				if (this.isAccess(auth)) {
					// ACCESSLOG INS
					if (this.insAccessLog(auth)) {
						// COMMIT;

						// http session
						HttpSession session = req.getSession();
						session.setAttribute("accessInfo", searchMemberInfo(auth).get(0).getmId());

						dao.setTransaction(true); // commit

						// 회원이름, 로그인 시간 추출
						ArrayList<AuthBean> ab = this.searchMemberInfo(auth);
						req.setAttribute("mId", ab.get(0).getmId());
						req.setAttribute("mName", ab.get(0).getmName());
						req.setAttribute("accessTime", ab.get(0).getAccessTime());

						message = "";
						page = "index.jsp"; //response.jsp

					} else {
						dao.setTransaction(false); // rollback
						message = "네트워크가 불안정합니다. 다시 접속해주세요.";
					}
				}

			} else {
				message = "휴면 계정입니다. 활성화를 해주세요.";
			}

		} // Action 설정
		action.setRedirect(false);
		action.setPage(page);

		req.setAttribute("message", message);
		this.dao.setAutoCommit(true);
		this.dao.closeConnection();
		return action;
	}

	private ArrayList<AuthBean> searchMemberInfo(AuthBean auth) {
		ArrayList<AuthBean> memberList = null;
		return dao.searchMemberInfo(auth);
	}

	private boolean insAccessLog(AuthBean auth) {
		return this.convertToBoolean(dao.insAccessLog(auth));
	}

	private boolean isAccess(AuthBean auth) {
		return convertToBoolean(dao.isAccess(auth));
	}

	private boolean isActive(AuthBean auth) {
		return this.convertToBoolean(dao.isActive(auth));
	}

	private boolean isUserId(AuthBean auth) {
		return convertToBoolean(dao.isMember(auth));

	}

	private Action joinCtl(HttpServletRequest req) {
		Action action = new Action();

		AuthBean auth = new AuthBean();
		auth.setmId(req.getParameterValues("joinInfo")[0]);
		auth.setmPassword(req.getParameterValues("joinInfo")[1]);
		auth.setmName(req.getParameterValues("joinInfo")[2]);
		auth.setPhone(req.getParameterValues("joinInfo")[3]);
		auth.setState("A");

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		if (!this.isMember(auth)) {
			if (this.insMember(auth)) {
				dao.setTransaction(true);

				action.setActionType(true);// 기본값 false?
				action.setPage("login.jsp");
			}
		}

		dao.setAutoCommit(true);
		dao.closeConnection();

		return action;
	}

	private boolean isMember(AuthBean auth) {
		return this.convertToBoolean(dao.isMember(auth));
	}

	private boolean insMember(AuthBean auth) {
		return this.convertToBoolean(dao.joinMember(auth));
	}

	private boolean convertToBoolean(int value) {
		return (value == 1) ? true : false;
	}
}
