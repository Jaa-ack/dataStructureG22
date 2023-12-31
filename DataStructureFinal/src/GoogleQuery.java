import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String keyword;
	public String url;
	public String content;
	
	public GoogleQuery(String keyword){
		this.keyword = keyword;
		try {
			String encodeKeyword=java.net.URLEncoder.encode(keyword,"utf-8");
			this.url = "https://www.google.com/search?q="+encodeKeyword+"&oe=utf8&num=20";
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	private String fetchContent() throws IOException{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null){
			retVal += line;
		}
		return retVal;
	}
	
	public HashMap<String, String> query() throws IOException{
		if(content == null){
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis){
			try {
				String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				
				if(title.equals("")) {
					continue;
				}
				
				//put title and pair into HashMap
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
			}
		}
		return retVal;
	}
	
	public HashMap<String, String> search() throws IOException {
        Document doc = Jsoup.connect(url).get();
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
	}
}