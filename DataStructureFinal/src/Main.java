import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException {
		 String topic = "時間管理";
		 HashMap<String, String> topicResult = new GoogleQuery(topic,"google").search();
		 KeywordList keyList = new Detail().firstFinding(topicResult);
		 for (Keyword keyword : keyList.getkList()){
			 HashMap<String, String> searchingResult = new GoogleQuery(keyword.getName(), "google").search();
			 KeywordList kList = new Detail().keywordChecking(searchingResult);
			 if (kList.has(topic) != null) {
				 keyList.deleteKeyword(keyword);
				 int times = 0;
				 for (Keyword k : kList.outputName(topic).getkList()) {
					 times += k.getTimes();
				 }
				 keyList.add(new Keyword(keyword.getName(), 2, times));
			 }
		 }
		 keyList.add(new Keyword(topic, 5, 0));
		 for (Keyword keyword : keyList.getkList()) {
			 System.out.println(keyword.getName() + "\t" + keyword.getOrder() + "\t" + keyword.getTimes());
		 }
		 
		 HashMap<String, String> bookList = new GoogleQuery(topic,"book").query();
		 ArrayList<Integer> scores = new ArrayList<Integer>();
		 ArrayList<String> books = new ArrayList<String>();
		 for (String bookName : bookList.keySet()) {
			 scores.add(new Score(keyList).score(bookName));
			 books.add(bookName);
			 System.out.println(new Score(keyList).score(bookName));
		 }
		 
		 while (scores.size() > 0) {
			 int maxNumber = Collections.max(scores);
			 int index = scores.indexOf(maxNumber);
			 scores.remove(index);	 
			 System.out.println("Score: " + maxNumber + "\t" + "Title :" + books.get(index) + "\nurl :" + bookList.get(books.get(index)));
			 books.remove(index);
		 }
		 
		 // 要把每個字都弄成繁體中文或大寫英文
		 // 排除英文同字問題
	}
}
    