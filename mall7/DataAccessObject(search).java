package search.services.mall.icia;

import java.util.ArrayList;

import beans.GoodsBean;

class DataAccessObject extends beans.DataAccessObject{
	
	DataAccessObject(){

	}

	//전체상품조회
	ArrayList<GoodsBean> searchGoods(){
		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();
		String query = "SELECT * FROM GOODSINFO";
		
		try {
			this.statement = this.connection.createStatement();
			this.rs = this.statement.executeQuery(query);
			while(rs.next()) {
				GoodsBean goods = new GoodsBean();
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("PRICE"));
				goods.setGoStock(rs.getInt("STOCK"));
				goods.setGoImage(rs.getNString("IMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				goods.setSeName(rs.getNString("SENAME"));
				gList.add(goods);
			}
		}catch(Exception e) {e.printStackTrace();}
		
		return gList;
	}

	//단어 검색 상품 조회
	ArrayList<GoodsBean> searchGoods(GoodsBean gb){
		ArrayList<GoodsBean> gList = null;
		
		
		return gList;
	}
}
