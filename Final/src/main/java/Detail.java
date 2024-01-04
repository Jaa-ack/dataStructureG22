import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

public class Detail {
	public Detail() {
	}
	
	public KeywordList firstFinding(HashMap<String, String> searchingResult) { // 搜尋各網站關鍵字
		if (searchingResult.isEmpty()) {
			return null;
		}
		
		KeywordList firstList = new KeywordList();
		ArrayList<String> urls = new ArrayList<>();
		for(String value: searchingResult.values()) {
			urls.add(value);
		}
		
		// 排除自訂拒絕網站
        ArrayList<String> denyList = readDenyList("/denyWebsite.txt");
        ArrayList<String> filteredUrls = filterDeniedSites(urls, denyList);
        
		for (int i = 0; i < Math.min(filteredUrls.size(), 10); i++) { // 取得第一階段關鍵字
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
	
	public KeywordList keywordChecking(HashMap<String, String> searchingResult) { // 對第一階段關鍵字再檢查作為第二階段關鍵字
		if (searchingResult.isEmpty()) { // 如果google搜尋下來沒有結果
			return null;
		}
		
		KeywordList list = new KeywordList();
		ArrayList<String> urls = new ArrayList<>();
		for(String value: searchingResult.values()) {
			urls.add(value);
		}
		
		// 排除自訂拒絕網站
        ArrayList<String> denyList = readDenyList("/denyWebsite.txt");
        ArrayList<String> filteredUrls = filterDeniedSites(urls, denyList);
		
        for (int i = 0; i < Math.min(filteredUrls.size(), 10); i++) { // 取得再搜尋結果中頻率前二的關鍵字
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
			
			// 內文翻譯成繁體中文並且排除英文內容
	        content = ZhConverterUtil.toTraditional(content);
	        String regex = "[a-zA-Z]";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(content);
	        content = matcher.replaceAll("");
			
	        // 使用jieba分詞工具進行中文分詞
			JiebaSegmenter segmenter = new JiebaSegmenter();
			List<SegToken> tokens = segmenter.process(content, JiebaSegmenter.SegMode.INDEX);
	
			// 排除自訂停用詞
			Set<String> stopWords = loadStopWords("/stopWords.txt");
	
			// 建立詞彙與出現次數的映射表，同時排除純數字內容
			Map<String, Integer> wordFrequency = new HashMap<>();
			for (SegToken token : tokens) {
			    String word = token.word.trim();
			    if (!stopWords.contains(word)) {
			    	if (!stopWords.equals(word)) {
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
	
	private Set<String> loadStopWords(String fileName) { // 讀取stopWord.txt中的內容並返回一個包含停用詞的列表
	    Set<String> stopWords = new HashSet<>();
	    InputStream inputStream = getClass().getResourceAsStream(fileName);

	    if (inputStream != null) {
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                stopWords.add(line.trim());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (inputStream != null) {
	                    inputStream.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return stopWords;
	}

	private String fetchParagraph(String citeUrl) throws IOException { // 取得內文文章
		try {
			Document doc = Jsoup.connect(citeUrl).get();
            Elements paragraphs = doc.select("p, h1, h2, h3, h4, h5, h6");
            String content = paragraphs.text();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
	}

	private ArrayList<String> readDenyList(String fileName) { // 讀取denyWebsite.txt中的內容並返回一個包含拒絕網站的列表
		ArrayList<String> denyList = new ArrayList<>();
	    InputStream inputStream = getClass().getResourceAsStream(fileName);
	    
	    if (inputStream != null) {
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                denyList.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                if (inputStream != null) {
	                    inputStream.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    return denyList;
	}

    private ArrayList<String> filterDeniedSites(List<String> urls, List<String> denyList) { // 過濾掉包含在拒絕清單中的URL
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