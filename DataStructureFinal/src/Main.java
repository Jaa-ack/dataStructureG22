import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//import java.io.IOException;
//import java.util.HashMap;
//
//public class Main {
//	public static void main(String[] args) throws IOException {
//		KeywordList key = new KeywordList();
//		key.add(new Keyword("習慣", 3));
//		key.add(new Keyword("養成", 3));
//		key.add(new Keyword("推薦", 3));
//		System.out.println("Hello World!");
////		GoogleQuery googleSearch = new GoogleQuery("科幻");
////		 KeywordList keywords = new KeywordList();
////		 KeywordList.add(new Keyword(name, order);
////		 try {
////			HashMap<String, String> searchingResult = googleSearch.search();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	}
//}

// get user topic
// HashMap<String, String> topicResult = new GoogleQuery(keyword).search();
// KeywordList firstList = new Detail().topicFinding(topicResult);
// 

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class Main {

    public static void main(String[] args) {
//    	String url1 = "https://zh.wikipedia.org/wiki/" + "科學幻想";
		String url2 = "https://girlstyle.com/tw/article/264460/%E4%BD%A0%E6%98%AF%E5%A4%A9%E6%89%8D%E5%97%8E-%E6%97%A5%E5%AA%92%E8%A7%A3%E6%9E%90-%E5%A4%A9%E6%89%8D%E7%9A%8422%E5%80%8B%E7%89%B9%E5%BE%B5-%E7%AD%86%E8%A8%98%E5%BE%88%E9%AB%92%E4%BA%82-%E8%A6%BA%E5%BE%97%E5%8A%AA%E5%8A%9B%E4%B8%8D%E7%97%9B%E8%8B%A6-%E7%84%A6%E6%85%AE-%E5%96%9C%E6%AD%A1%E7%8D%A8%E8%99%95--%E5%BE%88%E6%9C%89%E5%8F%AF%E8%83%BD%E6%98%AF-%E7%94%9F%E6%B4%BB%E7%99%BD%E7%97%B4";

		Document doc1;
		try {
//			doc1 = Jsoup.connect(url1).get();
//			String article = doc1.select("div#mw-content-text").text();
			String content = fetchContent(url2);
	        String article = ZhConverterUtil.toTraditional(content);
			
			 // 使用jieba分詞工具進行中文分詞
	        JiebaSegmenter segmenter = new JiebaSegmenter();
	        List<SegToken> tokens = segmenter.process(article, JiebaSegmenter.SegMode.INDEX);

	        // 加載停用詞表
	        Set<String> stopWords = loadStopWords("src/main/resources/stopWords.txt");

	        // 建立詞彙與出現次數的映射表
	        Map<String, Integer> wordFrequency = new HashMap<>();
	        for (SegToken token : tokens) {
	            String word = token.word.trim();
	            if (!stopWords.contains(word)) {
	            	try{
	                    int number = Integer.parseInt(word);
	                }
	                catch (NumberFormatException ex){
	                	if (word.length() > 1) {
	                		wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
	                	}
	                }
	            }
	        }

	        // 排序詞彙出現次數（降序）
	        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordFrequency.entrySet());
	        sortedWords.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

	        // 取得出現頻率最高的前10個詞彙
	        int topN = 20;
	        for (int i = 0; i < Math.min(topN, sortedWords.size()); i++) {
	            Map.Entry<String, Integer> entry = sortedWords.get(i);
	            System.out.println("詞彙: " + entry.getKey() + ", 出現次數: " + entry.getValue());
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static Set<String> loadStopWords(String filename) {
        Set<String> stopWords = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopWords;
    }
    
    private static String fetchContent(String url) throws IOException{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null){
			retVal += line;
		}
		return retVal;
	}
}