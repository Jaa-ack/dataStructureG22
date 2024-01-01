import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException {
		String topic = "選舉";
		// 對主題搜尋第一次並取得關鍵字
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
		
		for (Keyword keyword : keyList.getkList()) {
			System.out.println(keyword.getName() + "\t" + keyword.getOrder() + "\t" + keyword.getTimes());
		}
		 
//		HashMap<String, String> bookList = new GoogleQuery(topic,"book").query();
//		ArrayList<Integer> scores = new ArrayList<Integer>();
//		ArrayList<String> books = new ArrayList<String>();
//		for (String bookName : bookList.keySet()) {
//			scores.add(new Score(keyList).score(bookName));
//			books.add(bookName);
//			System.out.println(new Score(keyList).score(bookName));
//		}
//		 
//		while (scores.size() > 0) {
//			int maxNumber = Collections.max(scores);
//			int index = scores.indexOf(maxNumber);
//			scores.remove(index);	 
//			System.out.println("Score: " + maxNumber + "\t" + "Title :" + books.get(index) + "\nurl :" + bookList.get(books.get(index)));
//			books.remove(index);
//		}
	}
}