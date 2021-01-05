package search.services.mall.icia;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.Action;
import beans.GoodsBean;

public class GoodsSearch {
	private DataAccessObject dao;

	public GoodsSearch() {

	}

	public Action entrance(HttpServletRequest req) {
		Action action = null;
		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);

		switch (reqValue) {
		case "Search":
			action = this.searchCtl(req);
			break;
		}
		return action;
	}

	private Action searchCtl(HttpServletRequest req) {
		Action action = new Action();
		String page = "goods.jsp";
		boolean actionType = false;
	

		GoodsBean gb = new GoodsBean();
		// req --> GoodsBean
		gb.setWord(req.getParameter("word"));

		// Connection 생성
		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		System.out.println(gb.getWord());
		
		// 상품 조회
		req.setAttribute("gList", this.makeGoodsList((gb.getWord().equals("")) ? this.searchGoods() : this.searchGoods(gb)));
		

		dao.setAutoCommit(true);
		dao.closeConnection();

		// Action 설정
		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private String makeGoodsList(ArrayList<GoodsBean> gList) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (GoodsBean g : gList) {
			index++;
			if (index % 3 == 1) {
				sb.append("<div class=\"line\">");
			}

			sb.append("<div class=\"item\">");
			sb.append("<div class=\"item__top\"><img src=\"image/" + g.getGoCode() + "_" + g.getSeCode()
					+ ".jpg\"/></div>");
			sb.append("<div class=\"item__bottom\">" + g.getGoName() + "<br/>" + g.getGoPrice() + "&nbsp;&nbsp;&nbsp;"
					+ g.getGoStock() + "</div>");
			sb.append("</div>");

			if (index % 3 == 0) {
				sb.append("</div>");
			}
		}
		if (index % 3 != 0) {
			sb.append("</div>");
		}return sb.toString();

	}

	private ArrayList<GoodsBean> searchGoods() {
		return dao.searchGoods();
	}

	private ArrayList<GoodsBean> searchGoods(GoodsBean gb) {
		return dao.searchGoods(gb);
	}
}
