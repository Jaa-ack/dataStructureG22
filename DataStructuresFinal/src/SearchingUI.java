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
