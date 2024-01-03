import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.RequestDispatcher;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String topic = request.getParameter("searchTerm");
	      
		// 取得關鍵字
		HashMap<String, String> topicResult = new GoogleQuery(topic).search();
		KeywordList keyList = new Detail().firstFinding(topicResult);
						
		ArrayList<Keyword> temp = new ArrayList<Keyword>(keyList.getkList());
		for (Keyword keyword : temp){
			// 對關鍵字搜尋並取得其常出現的字
			HashMap<String, String> searchingResult = new GoogleQuery(keyword.getName()).search();
			KeywordList kList = new Detail().keywordChecking(searchingResult);
							
			if (kList.hasName(topic) != null) { // 如果關鍵字中常出現的字有提到主題
				keyList.addList(kList, 2);
				keyList.add(new Keyword(keyword.getName(), 3, keyword.getTimes()));
			}
		}
						
		keyList.add(new Keyword(topic, 4, 2));
				
		// 取得搜尋結果並評分
		String encodeKeyword=java.net.URLEncoder.encode(topic,"utf-8");
		WebPage rootPage = new WebPage("https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20", topic);
		WebTree tree = new WebTree(rootPage);
			
		ArrayList<String> searchingName = keyList.outputOrder(3);
		if (searchingName.size() < 3) {
			for (int i = keyList.outputOrder(2).size() - 1; i > 0; i--) {
				searchingName.add(keyList.outputOrder(2).get(i));
					if (searchingName.size() >= 3) {
					break;
				}
			}
		}
		if (searchingName.size() < 3) {
			for (int i = keyList.outputOrder(1).size() - 1; i > 0; i--) {
				searchingName.add(keyList.outputOrder(1).get(i));
				if (searchingName.size() >= 3) {
					break;
				}
			}
		}
				
		for (String name : searchingName) {
			String searchQuery = name + "書";
			HashMap<String, String> searchResults = new GoogleQuery(searchQuery).search();

			for (String title : searchResults.keySet()) {
				tree.root.addChild(new WebNode(new WebPage(searchResults.get(title), title)));
			}
		}
		      
		for (WebNode node : tree.root.children) {
			HashMap<String, String> searchResults = new GoogleQuery(node).query();
			if (searchResults != null) {
				for (String title : searchResults.keySet()) {
					node.addChild(new WebNode(new WebPage(searchResults.get(title), title)));
				}
			}
		}

		tree.setPostOrderScore(keyList);
		List<WebNode> node = tree.rearrangeNodesByScore();
		String result = "";
		for (int i = 1; i < 30; i++) {
			result += node.get(i).webPage.url + "~";
			result += node.get(i).webPage.name + "~";
			result += node.get(i).webPage.relativeWord + "分數：" + node.get(i).nodeScore  + "~";
		}
		
	    // 處理後的數據存儲在 request 屬性中以便在 JSP 中訪問
	    String[] segments = result.split("~");
	    request.setAttribute("segments", segments);

	    // 導向到結果的 JSP 頁面
	    request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
	}
}