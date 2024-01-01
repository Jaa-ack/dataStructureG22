import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Score {
	public ArrayList<Keyword> keywords;

	public Score(KeywordList keywords) throws IOException {
		this.keywords = keywords.getkList();
	}
	
	public int score(String bookName) throws IOException { // not yet
		String content = fetchContent("https://www.google.com.tw/books/edition/" + bookName + "/3Dv1273wE6kC?hl=zh-TW&gbpv=0");
		int score = 0;
		for (int i = 0; i < keywords.size(); i++) {
			int count = BoyerMoore(content, keywords.get(i).getName());
			score += (keywords.size() - i) * count * keywords.get(i).getOrder();
		}
		return score;
	}
	
	private String fetchContent(String citeUrl) throws IOException{
		try {
            Document document = Jsoup.connect(citeUrl).get();
            // 獲取網頁內容
            Element body = document.body();
            String content = body.text();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(citeUrl);
            return "";
        }
	}
	
	public int BoyerMoore(String T, String P){
    	int i = T.length();
    	int j = P.length();
    	int times = 0;
        
        // Bonus: Implement Boyer-Moore Algorithm     
    	
    	for (int a = j; a < i; a++) {
    		Boolean hasP = true;
    		for (int x = j - 1; x >= 0; x--) {
    			String badCharacter = Character.toString(T.charAt(a + x - j));
    			String character = Character.toString(P.charAt(x));
        		
    			if (!(badCharacter.equals(character))) {
        			hasP = false;
        			String Preverse = new StringBuffer(P).reverse().toString();
        			
        			// bad-character shift rule
        			int bcShift = 0;
        			if (Preverse.indexOf(badCharacter, j - x - 1) == -1) {
        				bcShift = x;
        			}else {
        				bcShift = Preverse.indexOf(badCharacter, j - x - 1) - (j - x - 1) - 1;
        			}
        			
        			// good-suffix shift rule
        			String goodSuffix = P.substring(x+1);
        			int gsShift = 0;
        			if (goodSuffix.length() > 0) {
        				String test = new StringBuffer(goodSuffix).reverse().toString();
            			
        				for (int n = 0; n < goodSuffix.length(); n++) {
            				test = new StringBuffer(goodSuffix.substring(n)).reverse().toString();
            				if (Preverse.indexOf(test, j - x - 1) != -1) {
            					gsShift = Preverse.indexOf(test, j - x - 1);
            				}
            			}
            				
            			if (gsShift == 0)
            				gsShift = j - 1;
            			
        			}
        			
        			if (bcShift > gsShift) {
        				a += bcShift;
        			}else {
        				a += gsShift;
        			}
        			break;
        		}
        	}
    		
    		if (hasP) {
    			times++;
    		}
    	}

        return times;
    }

	
	
//	public void getDetail(KeywordList keywords) {
//	for (String title : searchingResult.keySet()) {
//		String url = searchingResult.get(title);
//		try {
//			String content = fetchContent(url);
//			if (title.contains("博客來")) {
//				// 取得作者
//				String temp = content.substring(content.indexOf("作者"));
//				author = temp.substring(0, temp.indexOf("追蹤作者"));
//				
//				// 取得推薦人
//				temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
//				while (temp.contains("、")) {
//					recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
//					temp.substring(temp.indexOf("、"));
//				}
//				temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("作者介紹"));
//				while (temp.contains("——")) {
//					if (temp.contains("「")) {
//						recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
//						temp = temp.substring(temp.indexOf("「"));
//					}else {
//						recommender.add(temp.substring(temp.indexOf("——")));
//						break;
//					}
//				}
//				temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("目錄列表"));
//				while (temp.contains("——")) {
//					if (temp.contains("「")) {
//						recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
//						temp = temp.substring(temp.indexOf("「"));
//					}else {
//						recommender.add(temp.substring(temp.indexOf("——")));
//						break;
//					}
//				}
//				
//				// 取得目錄
//				catalogue = content.substring(content.indexOf("目錄"), content.indexOf("序"));
//				
//				// 取得簡介並評分
//				temp = content.substring(content.indexOf("內容簡介"), content.indexOf("推薦"));
//				int score = new Score(temp).score(keywords);
//			}else if (title.contains("Readmoo")) {
//				// 取得作者
//				String temp = content.substring(content.indexOf("作者簡介"));
//				author = temp.substring(0, temp.indexOf("）"));
//				
//				// 取得推薦人
//				temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
//				while (temp.contains("、")) {
//					recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
//					temp.substring(temp.indexOf("、"));
//				}
//				
//				// 取得簡介並評分
//				temp = content.substring(content.indexOf("詳細資料"), content.indexOf("作者簡介"));
//				int score = new Score(temp).score(keywords);
//			}else if (title.contains("誠品線上")) {
//				// 取得作者
//				String temp = content.substring(content.indexOf("作者"));
//				if (temp.contains("譯者")) {
//					author = temp.substring(0, temp.indexOf("譯者"));
//				}else if (temp.contains("出版社")){
//					author = temp.substring(0, temp.indexOf("出版社"));
//				}
//				
//				// 取得簡介並評分
//				temp = content.substring(content.indexOf("內容簡介"), content.indexOf("作者簡介"));
//				int score = new Score(temp).score(keywords);
//			}else {
//				int i = new Score(content).score(keywords);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
//
}
