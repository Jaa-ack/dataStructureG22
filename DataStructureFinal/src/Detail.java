import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Detail {
	private HashMap<String, String> searchingResult;
	private String author, catalogue;
	private ArrayList<String> recommender;
	
	public Detail(HashMap<String, String> searchingResult) {
		this.searchingResult = searchingResult;
		recommender = new ArrayList<String>();
	}
	
	public void getDetail(KeywordList keywords) {
		for (String title : searchingResult.keySet()) {
			String url = searchingResult.get(title);
			try {
				String content = fetchContent(url);
				if (title.contains("博客來")) {
					// 取得作者
					String temp = content.substring(content.indexOf("作者"));
					author = temp.substring(0, temp.indexOf("追蹤作者"));
					
					// 取得推薦人
					temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
					while (temp.contains("、")) {
						recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
						temp.substring(temp.indexOf("、"));
					}
					temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("作者介紹"));
					while (temp.contains("——")) {
						if (temp.contains("「")) {
							recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
							temp = temp.substring(temp.indexOf("「"));
						}else {
							recommender.add(temp.substring(temp.indexOf("——")));
							break;
						}
					}
					temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("目錄列表"));
					while (temp.contains("——")) {
						if (temp.contains("「")) {
							recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
							temp = temp.substring(temp.indexOf("「"));
						}else {
							recommender.add(temp.substring(temp.indexOf("——")));
							break;
						}
					}
					
					// 取得目錄
					catalogue = content.substring(content.indexOf("目錄"), content.indexOf("序"));
					
					// 取得簡介並評分
					temp = content.substring(content.indexOf("內容簡介"), content.indexOf("推薦"));
					int score = new Score(temp).score(keywords);
				}else if (title.contains("Readmoo")) {
					// 取得作者
					String temp = content.substring(content.indexOf("作者簡介"));
					author = temp.substring(0, temp.indexOf("）"));
					
					// 取得推薦人
					temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
					while (temp.contains("、")) {
						recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
						temp.substring(temp.indexOf("、"));
					}
					
					// 取得簡介並評分
					temp = content.substring(content.indexOf("詳細資料"), content.indexOf("作者簡介"));
					int score = new Score(temp).score(keywords);
				}else if (title.contains("誠品線上")) {
					// 取得作者
					String temp = content.substring(content.indexOf("作者"));
					if (temp.contains("譯者")) {
						author = temp.substring(0, temp.indexOf("譯者"));
					}else if (temp.contains("出版社")){
						author = temp.substring(0, temp.indexOf("出版社"));
					}
					
					// 取得簡介並評分
					temp = content.substring(content.indexOf("內容簡介"), content.indexOf("作者簡介"));
					int score = new Score(temp).score(keywords);
				}else {
					int i = new Score(content).score(keywords);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String fetchContent(String citeUrl) throws IOException{
		URL url = new URL(citeUrl);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";

		String line = null;
		while ((line = br.readLine()) != null)
		{
			retVal = retVal + line + "\n";
		}

		return retVal;
	}
}
