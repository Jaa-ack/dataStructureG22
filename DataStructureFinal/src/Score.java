import java.util.ArrayList;

public class Score {
	private String summary;

	public Score(String summary) {
		this.summary = summary;
	}
	
	public int score(KeywordList keywords) { // not yet
		return 1;
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
