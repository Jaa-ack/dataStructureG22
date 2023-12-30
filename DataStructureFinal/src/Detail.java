import java.io.BufferedReader;
import java.io.FileReader;
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

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

public class Detail {
	private String author, catalogue;
	private ArrayList<String> recommender;
	
	public Detail() {
		recommender = new ArrayList<String>();
	}
	
	public KeywordList firstFinding(HashMap<String, String> searchingResult) { // 找第一階段關鍵字
		if (searchingResult.isEmpty()) { // 如果google搜尋下來沒有結果
			// 回傳使用者錯誤
			return null;
		}
		KeywordList firstList = new KeywordList();
		for(String url: searchingResult.values()) {
			if (url.contains("wikipedia")) {
				firstList = getKeyword(url);
				break;
			}else {
				firstList.addList(getKeyword(url));
			}
		}
		return firstList;
	}
	
	
	
	public KeywordList getKeyword(String url) { // 找網頁中出現最多次的10詞
		String content;
		KeywordList keywords = new KeywordList();
		try {
			content = fetchContent(url);
		
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
			    keywords.add(new Keyword(entry.getKey(), i));
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
	
//	public void getDetail(KeywordList keywords) {
//		for (String title : searchingResult.keySet()) {
//			String url = searchingResult.get(title);
//			try {
//				String content = fetchContent(url);
//				if (title.contains("博客來")) {
//					// 取得作者
//					String temp = content.substring(content.indexOf("作者"));
//					author = temp.substring(0, temp.indexOf("追蹤作者"));
//					
//					// 取得推薦人
//					temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
//					while (temp.contains("、")) {
//						recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
//						temp.substring(temp.indexOf("、"));
//					}
//					temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("作者介紹"));
//					while (temp.contains("——")) {
//						if (temp.contains("「")) {
//							recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
//							temp = temp.substring(temp.indexOf("「"));
//						}else {
//							recommender.add(temp.substring(temp.indexOf("——")));
//							break;
//						}
//					}
//					temp = content.substring(content.indexOf("好評推薦「"), content.indexOf("目錄列表"));
//					while (temp.contains("——")) {
//						if (temp.contains("「")) {
//							recommender.add(temp.substring(temp.indexOf("——"), temp.indexOf("「")));
//							temp = temp.substring(temp.indexOf("「"));
//						}else {
//							recommender.add(temp.substring(temp.indexOf("——")));
//							break;
//						}
//					}
//					
//					// 取得目錄
//					catalogue = content.substring(content.indexOf("目錄"), content.indexOf("序"));
//					
//					// 取得簡介並評分
//					temp = content.substring(content.indexOf("內容簡介"), content.indexOf("推薦"));
//					int score = new Score(temp).score(keywords);
//				}else if (title.contains("Readmoo")) {
//					// 取得作者
//					String temp = content.substring(content.indexOf("作者簡介"));
//					author = temp.substring(0, temp.indexOf("）"));
//					
//					// 取得推薦人
//					temp = content.substring(content.indexOf("名人推薦"), content.indexOf("好評推薦"));
//					while (temp.contains("、")) {
//						recommender.add(temp.substring(temp.indexOf(0, temp.indexOf("、"))));
//						temp.substring(temp.indexOf("、"));
//					}
//					
//					// 取得簡介並評分
//					temp = content.substring(content.indexOf("詳細資料"), content.indexOf("作者簡介"));
//					int score = new Score(temp).score(keywords);
//				}else if (title.contains("誠品線上")) {
//					// 取得作者
//					String temp = content.substring(content.indexOf("作者"));
//					if (temp.contains("譯者")) {
//						author = temp.substring(0, temp.indexOf("譯者"));
//					}else if (temp.contains("出版社")){
//						author = temp.substring(0, temp.indexOf("出版社"));
//					}
//					
//					// 取得簡介並評分
//					temp = content.substring(content.indexOf("內容簡介"), content.indexOf("作者簡介"));
//					int score = new Score(temp).score(keywords);
//				}else {
//					int i = new Score(content).score(keywords);
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
	private String fetchContent(String citeUrl) throws IOException{
		URL url = new URL(citeUrl);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String retVal = "";

		String line = null;
		while ((line = br.readLine()) != null)
		{
			retVal = retVal + line + "\n";
		}

		return retVal;
	}
}
