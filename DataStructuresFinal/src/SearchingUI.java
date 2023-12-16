//// 找書名有關鍵字的書並獲得連結
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.IOException;
//
//public class SearchingUI{
//
//    public static void main(String[] args) {
//        String keyword = "history"; // 替換成你想要查詢的關鍵字
//        String url = "https://www.goodreads.com/search?q=" + keyword;
////    	String url = "https://www.eslite.com/category/3/44&sa=U&ved=2ahUKEwjFnYKPlY-DAxUGha8BHataDo4QFnoECAYQAg&usg=AOvVaw3CffRu4mcWuspm2BK-FnOr";
//
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements bookLinks = doc.select(".bookTitle");
//
//            for (Element link : bookLinks) {
//                String bookTitle = link.text();
//                String bookUrl = "https://www.goodreads.com" + link.attr("href");
//
//                System.out.println("書名: " + bookTitle);
//                System.out.println("連結: " + bookUrl);
//                System.out.println();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.IOException;
//
//public class SearchingUI {
//    public static void main(String[] args) {
//        String url = "https://www.momoshop.com.tw/category/MgrpCategory.jsp?m_code=4000900309&cateLevel=2";
//        
//        try {
//            // 使用 Jsoup 連接到網頁並取得 Document 物件
//            Document doc = Jsoup.connect(url).get();
//            
//            // 從 Document 中選擇所有超連結元素
//            Elements links = doc.select("a[href]");
//            
//            // 迭代並顯示所有超連結
//            for (Element link : links) {
//                String href = link.attr("href");
//                System.out.println(href);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

// 顯示google搜尋關鍵字後的連結
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.IOException;
//
//public class SearchingUI {
//    public static void main(String[] args) {
//        String url = "https://www.google.com/search?q=%E7%A7%91%E5%B9%BB%E3%80%81%E6%9B%B8&oq=%E7%A7%91%E5%B9%BB%E3%80%81%E6%9B%B8&gs_lcrp=EgZjaHJvbWUqBggAEEUYOzIGCAAQRRg7MgoIARAAGIAEGKIEMgoIAhAAGIAEGKIEMgoIAxAAGIAEGKIEMgoIBBAAGIAEGKIEMgYIBRBFGDzSAQg0NjM0ajBqNKgCALACAA&sourceid=chrome&ie=UTF-8";
//
//        try {
//            Document doc = Jsoup.connect(url).get();
//
//            // 選擇所有搜索結果的元素
//            Elements searchResults = doc.select("div.g");
//
//            // 迭代並輸出每個搜索結果的連結
//            for (Element result : searchResults) {
//                Element link = result.selectFirst("a[href]");
//                String href = link.attr("href");
//                System.out.println("連結: " + href);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

public class SearchingUI{
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}

// 從書本連結中取得該連結內的內容以取得簡介，但以下方法會取得簡介、作者介紹、評論等等內容，還要再想辦法調整
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.IOException;
//
//public class SearchingUI {
//
//    public static void main(String[] args) {
//        String bookUrl = "https://www.goodreads.com/book/show/36505403-boy-s-life?ref=rae_0"; // 替換成你要查詢書籍的URL
//
//        try {
//            Document doc = Jsoup.connect(bookUrl).get();
//            Elements allElements = doc.getAllElements();
//
//            StringBuilder description = new StringBuilder();
//
//            for (Element element : allElements) {
//                description.append(element.text()).append("\n");
//            }
//
//            String extractedDescription = description.toString().trim();
//            if (!extractedDescription.isEmpty()) {
//                System.out.println("書籍簡介:");
//                System.out.println(extractedDescription);
//            } else {
//                System.out.println("找不到書籍簡介");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//

