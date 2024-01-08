import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
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
		request.setCharacterEncoding("UTF-8");
		String topic = request.getParameter("searchTerm");
		Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyBMt4eeTCcVXYBwu9kZ7bl2uJSJ6myYCZ8").build().getService();
        Translation translation = translate.translate(topic, Translate.TranslateOption.targetLanguage("zh-TW"));
        topic = translation.getTranslatedText();
        topic = ZhConverterUtil.toTraditional(topic);
		
		// 取得第一階段關鍵字
		HashMap<String, String> topicResult = new GoogleQuery(topic).search();
		KeywordList keyList = new Detail().firstFinding(topicResult);
						
		// 對第一階段關鍵字再搜尋
		ArrayList<Keyword> temp = new ArrayList<Keyword>(keyList.getkList());
		for (Keyword keyword : temp){
			HashMap<String, String> searchingResult = new GoogleQuery(keyword.getName()).search();
			KeywordList kList = new Detail().keywordChecking(searchingResult);
							
			if (kList.hasName(topic) != null) { // 如果關鍵字中常出現的字有提到主題將其權重加重
				keyList.addList(kList, 2);
				keyList.add(new Keyword(keyword.getName(), 3, keyword.getTimes()));
			}
		}
						
		keyList.add(new Keyword(topic, 4, 2)); // 主題設定權重加入關鍵字列表
						
		// 取得搜尋結果並評分
		String encodeKeyword=java.net.URLEncoder.encode(topic + "書","utf-8");
		WebPage rootPage = new WebPage("https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20", topic);
		WebTree tree = new WebTree(rootPage);
		
		// 取關鍵字列表最重要的三個關鍵字
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
		
		// 對三個關鍵字搜尋並取得其搜尋結果
		for (String name : searchingName) {
			String searchQuery = name + "書";
			HashMap<String, String> searchResults = new GoogleQuery(searchQuery).search();

			for (String title : searchResults.keySet()) {
				tree.root.addChild(new WebNode(new WebPage(searchResults.get(title), title)));
			}
		}

		// 取得搜尋結果的子網頁
		for (WebNode node : tree.root.children) {
			HashMap<String, String> searchResults = new GoogleQuery(node).query();
			if (searchResults != null) {
				for (String title : searchResults.keySet()) {
					node.addChild(new WebNode(new WebPage(searchResults.get(title), title)));
				}
			}
		}

		// 將tree所有網頁評分並且排序
		tree.setPostOrderScore(keyList);
		List<WebNode> node = tree.rearrangeNodesByScore();
		String result = topic + "~";
		for (int i = 1; i < 30; i++) {
			result += node.get(i).webPage.url + "~";
			result += node.get(i).webPage.name + "~";
			DecimalFormat df = new DecimalFormat("#.##");
	        String score = df.format(node.get(i).nodeScore);
			result += node.get(i).webPage.relativeWord + "分數：" + score  + "~";
		}
		
	    String[] segments = result.split("~");
	    request.setAttribute("segments", segments);

	    // 導向到結果的 JSP 頁面
	    request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
	}
}