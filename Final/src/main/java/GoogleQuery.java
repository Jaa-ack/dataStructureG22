
import java.io.IOException;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GoogleQuery {
	public String keyword;
	public String url;
	
	public GoogleQuery(String keyword){
		this.keyword = keyword;
		try {
			String encodeKeyword=java.net.URLEncoder.encode(keyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public GoogleQuery(WebNode node) {
		this.url = node.webPage.url;
	}
	
	public HashMap<String, String> query() throws IOException {
		HashMap<String, String> retVal = new HashMap<String, String>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div.content a[href]"); // 選擇所有帶有 href 屬性的 a 標籤
	        
	        for (Element link : links) {
	        	String title = link.text(); // 取得連結的文字內容（標題）
	            String subLink = link.attr("abs:href"); // 取得絕對連結
	            retVal.put(title, subLink);
	        }
			
			return retVal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<String, String>();
		}
        
	}
	
	public HashMap<String, String> search() throws IOException  {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			HashMap<String, String> retVal = new HashMap<String, String>();
	        String title = "";
	        String link = "";

	        // 找到搜尋結果中每個網站的標題和名稱
	        Elements searchResults = doc.select("div.g");
	        for (Element result : searchResults) {
	        	Element titleElement = result.selectFirst("h3");
	        	if (titleElement != null) {
	        		title = titleElement.text();
	        	}

	        	Element linkElement = result.selectFirst("a");
	        	if (linkElement != null) {
	        		link = linkElement.attr("href");
	        	}
	        	retVal.put(title, link);
	        }
	        return retVal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}