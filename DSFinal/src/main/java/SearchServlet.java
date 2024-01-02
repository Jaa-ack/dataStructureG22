import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/DSFinal/SearchServlet")
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
		String requestUri = request.getRequestURI();
		request.setAttribute("requestUri", requestUri);
		request.getRequestDispatcher("Index.jsp")
		 .forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 取得表單中的搜尋內容	    
        String topic = request.getParameter("searchTerm");
        System.out.println(topic);
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

	    // 處理後的數據存儲在 request 屬性中以便在 JSP 中訪問
	    String[] segments = new String[]{"https://github.com/Jaa-ack/dataStructureG22", "名字", "github.detail, www.example.com", "test", "測試網站"};
	    request.setAttribute("segments", segments);

	    // 導向到結果的 JSP 頁面
	    request.getRequestDispatcher("ResultPage.jsp").forward(request, response);
	}

}
