import java.io.IOException;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException {
		 String topic = "科幻";
//		 HashMap<String, String> topicResult = new GoogleQuery(topic,"google").search();
//		 KeywordList keyList = new Detail().firstFinding(topicResult);
//		 for (Keyword keyword : keyList.getkList()){
//			 HashMap<String, String> searchingResult = new GoogleQuery(keyword.getName(), "google").search();
//			 KeywordList kList = new Detail().keywordChecking(searchingResult);
//			 if (kList.has(topic) != null) {
//				 keyList.deleteKeyword(keyword);
//				 int times = 0;
//				 for (Keyword k : kList.outputName(topic).getkList()) {
//					 times += k.getTimes();
//				 }
//				 keyList.add(new Keyword(keyword.getName(), 2, times));
//			 }
//		 }
//		 for (Keyword keyword : keyList.getkList()) {
//			 System.out.println(keyword.getName() + "\t" + keyword.getOrder() + "\t" + keyword.getTimes());
//		 }
		 
		 HashMap<String, String> bookList = new GoogleQuery(topic,"book").query();
		 for (String bookName : bookList.keySet()) {
			 
		 }
		//"https://www.google.com.tw/books/edition/" + title + "/3Dv1273wE6kC?hl=zh-TW&gbpv=0"
	}
}
    