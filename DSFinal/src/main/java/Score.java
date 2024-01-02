import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import jakarta.servlet.http.HttpServlet;

public class Score {
	public ArrayList<Keyword> keywords;
	public String relativeWord;

	public Score(KeywordList keywords) throws IOException {
		this.keywords = keywords.getkList();
		relativeWord = "";
	}
	
	public int score(String url) throws IOException {
		String content = fetchContent(url);
		int score = 0;
		for (Keyword key : keywords) {
			int count = BoyerMoore(content, key.getName());
			if (count > 0) {
				relativeWord += key + "、";
			}
			score += key.getOrder() * count;
		}
		
		return score;
	}
	
	public String getRelativeWord() {
		return relativeWord;
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
}
