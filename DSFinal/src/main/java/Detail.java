import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.AbstractDocument.Content;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import jakarta.servlet.http.HttpServlet;

public class Detail {
	public Detail() {
	}
	
	public KeywordList firstFinding(HashMap<String, String> searchingResult) { // 搜尋各網站關鍵字
		if (searchingResult.isEmpty()) { // 如果google搜尋下來沒有結果
			// 回傳使用者錯誤
			return null;
		}
		KeywordList firstList = new KeywordList();
		
		ArrayList<String> urls = new ArrayList<>();
		for(String value: searchingResult.values()) {
			urls.add(value);
		}
        ArrayList<String> denyList = readDenyList("src/main/java/resources/denyWebsite.txt");
        ArrayList<String> filteredUrls = filterDeniedSites(urls, denyList);
        
		for (int i = 0; i < Math.min(filteredUrls.size(), 10); i++) {
			if (filteredUrls.get(i).contains("wikipedia")) {
				firstList = getKeyword(filteredUrls.get(i), 10);
				break;
			}else {
				KeywordList list = getKeyword(filteredUrls.get(i), 2);
				firstList.addList(list);
			}
		}
		return firstList;
	}
	
	public KeywordList keywordChecking(HashMap<String, String> searchingResult) {
		if (searchingResult.isEmpty()) { // 如果google搜尋下來沒有結果
			// 回傳使用者錯誤
			return null;
		}
		KeywordList list = new KeywordList();
		
		ArrayList<String> urls = new ArrayList<>();
		for(String value: searchingResult.values()) {
			urls.add(value);
		}
        ArrayList<String> denyList = readDenyList("src/main/java/resources/denyWebsite.txt");
        ArrayList<String> filteredUrls = filterDeniedSites(urls, denyList);
		
        for (int i = 0; i < Math.min(filteredUrls.size(), 10); i++) {
			KeywordList keywords = getKeyword(filteredUrls.get(i), 2);
			list.addList(keywords);
		}
		
		return list;
	}
	
	public KeywordList getKeyword(String url, int num) { // 找網頁中出現最多次的詞
		String content;
		KeywordList keywords = new KeywordList();
		try {
			content = fetchParagraph(url);
			
	        String article = ZhConverterUtil.toTraditional(content);
			
	        // 使用jieba分詞工具進行中文分詞
			JiebaSegmenter segmenter = new JiebaSegmenter();
			List<SegToken> tokens = segmenter.process(article, JiebaSegmenter.SegMode.INDEX);
	
			// 加載停用詞表
			Set<String> stopWords = loadStopWords("src/main/java/resources/stopWords.txt");
	
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
			int topN = num;
			for (int i = 0; i < Math.min(topN, sortedWords.size()); i++) {
				Map.Entry<String, Integer> entry = sortedWords.get(i);
				keywords.add(new Keyword(entry.getKey(), 1, entry.getValue()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keywords;
	}
	
	private Set<String> loadStopWords(String filename) {
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
	
	private String fetchParagraph(String citeUrl) throws IOException{
		try {
			Document doc = Jsoup.connect(citeUrl).get();
            Elements paragraphs = doc.select("p, h1, h2, h3, h4, h5, h6"); // 選擇所有的段落 <p>
            String content = paragraphs.text();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            FileWriter fileWriter = new FileWriter("src/main/java/resources/denyWebsite.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // 寫入內容到文件
            bufferedWriter.write(citeUrl);
            bufferedWriter.newLine(); // 加上換行符號

            // 關閉寫入器
            bufferedWriter.close();
            return "";
        }
	}

	// 讀取 denyWebsite.txt 中的內容並返回一個包含拒絕網站的列表
    private ArrayList<String> readDenyList(String fileName) {
        ArrayList<String> denyList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                denyList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return denyList;
    }

    // 過濾掉包含在拒絕清單中的 URL
    private ArrayList<String> filterDeniedSites(List<String> urls, List<String> denyList) {
    	ArrayList<String> filteredUrls = new ArrayList<>();
    	for (String urlString : urls) {
            boolean shouldSkip = denyList.stream().anyMatch(urlString::contains);
            if (!shouldSkip) {
                filteredUrls.add(urlString);
            }
        }
        return filteredUrls;
    }
}