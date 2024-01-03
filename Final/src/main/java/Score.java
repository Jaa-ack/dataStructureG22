
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class Score {
	public ArrayList<Keyword> keywords;
	public String relativeWord;

	public Score(KeywordList keywords) throws IOException {
		this.keywords = keywords.getkList();
		relativeWord = "";
	}
	
	public double score(String url) {
		String content;
		try {
			content = fetchContent(url);
			content = ZhConverterUtil.toTraditional(content);
			String regex = "[a-zA-Z]";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(content);

	        // 使用 Matcher 尋找匹配的字母並替換為空字串
	        content = matcher.replaceAll("");
			
			double score = 0;
			
			for (int i = keywords.size(); i > 0; i--) {
				int count = BoyerMoore(content, keywords.get(i-1).getName());
				if (count > 0) {
					relativeWord += keywords.get(i-1).getName() + "、";
				}
				if (keywords.get(i-1).getOrder() == 4) {
					score += keywords.get(i-1).getOrder() * count * 50;
				}else if (keywords.get(i-1).getOrder() == 3) {
					score += keywords.get(i-1).getOrder() * count * 30;
				}else if (keywords.get(i-1).getOrder() == 2) {
					score += keywords.get(i-1).getOrder() * count * 5;
				}else{
					score += keywords.get(i-1).getOrder() * count * 2;
				}
				
			}
			
			return score;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
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
        	return "";
        }
	}
	
	public int BoyerMoore(String T, String P){
    	int i = T.length();
    	int j = P.length();
    	int times = 0;   
    	
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
