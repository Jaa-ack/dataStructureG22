
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GoogleQuery {
	public String keyword;
	public String url;
	
	public GoogleQuery(String keyword) {
		this.keyword = keyword;
		try {
			String encodeKeyword=java.net.URLEncoder.encode(keyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GoogleQuery(WebNode node) {
		this.url = node.webPage.url;
	}
	
	public HashMap<String, String> query() throws IOException { // 取得子連結
		HashMap<String, String> retVal = new HashMap<String, String>();
		Document doc;
		ArrayList<String> denyList = readDenyList("/denyWebsite.txt");
		
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div.content a[href]");
	        
	        for (Element link : links) {
	        	// 排除自訂拒絕網站
	        	boolean isDenied = denyList.stream().anyMatch(link.attr("href")::contains);
	            if (!isDenied) {
	            	if (link.text() != null) {
	            		if (link.attr("abs:href") != null) {
	            			String title = link.text();
	    		            String subLink = link.attr("abs:href");
	    		            retVal.put(title, subLink);
	            		}
	            	}
		        }
	        }
			
			return retVal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<String, String>();
		}
	}
	
	public HashMap<String, String> search() throws IOException { // 取得搜尋結果中每個網站的標題和名稱
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			HashMap<String, String> retVal = new HashMap<String, String>();
	        String title = "";
	        String link = "";
	        ArrayList<String> denyList = readDenyList("/denyWebsite.txt");
	        
	        Elements searchResults = doc.select("div.g");
	        for (Element result : searchResults) {
	        	Element titleElement = result.selectFirst("h3");
	        	Element linkElement = result.selectFirst("a");
	        	
	        	// 排除自訂拒絕網站
	            boolean isDenied = denyList.stream().anyMatch(linkElement.attr("href")::contains);
	            if (!isDenied) {
	            	
		        	if (titleElement != null) {
		        		if (linkElement != null) {
		        			title = titleElement.text();
			        		link = linkElement.attr("href");
			        		retVal.put(title, link);
			        	}
		        	}
	            }
	        }
	        return retVal;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private ArrayList<String> readDenyList(String fileName) { // 讀取denyWebsite.txt中的內容並返回一個包含拒絕網站的列表
		ArrayList<String> denyList = new ArrayList<>();
		InputStream inputStream = getClass().getResourceAsStream(fileName);
	    
		if (inputStream != null) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = br.readLine()) != null) {
					denyList.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		    
		return denyList;
	}
}