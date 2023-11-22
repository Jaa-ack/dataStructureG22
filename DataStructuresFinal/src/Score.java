import java.util.ArrayList;
import java.util.LinkedList;

public class Score {
	private ArrayList<Keyword> keywordList;
	private ArrayList<SearchingWeb> webList;
	private String theme;
	
	public Score() {
		keywordList = new ArrayList<Keyword>();
		webList = new ArrayList<SearchingWeb>();
	}
	
	public Score(String theme) {
		this.theme = theme;
		keywordList = new ArrayList<Keyword>();
		webList = new ArrayList<SearchingWeb>();
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public void addKeyword(Keyword keyword) {
		for (int i = 0; i < keywordList.size(); i++) {
			Keyword k = keywordList.get(i);
			if (keyword.getWeight() > k.getWeight()) {
				keywordList.add(i, keyword);
			}
		}
		keywordList.add(keyword);
	}
	
	public void addWeb(SearchingWeb url) {
		webList.add(url);
	}
	
	public void deleteKeyword(String name) {
		ArrayList<Keyword> found = new ArrayList<>();
		
		for (Keyword keyword : keywordList) {
			if (keyword.getName().equals(name)){
				found.add(keyword);
			}
		}
		
		if (found.isEmpty()){
			System.out.println("Keyword not found.");
		}else {
			keywordList.removeAll(found);
		}
	}
	
	public void deleteWeb(String name) {
		ArrayList<SearchingWeb> found = new ArrayList<>();
		
		for (SearchingWeb web : webList) {
			if (web.getName().equals(name)){
				found.add(web);
			}
		}
		
		if (found.isEmpty()) {
			System.out.println("Searching web not found.");
		}else{
			keywordList.removeAll(found);
		}
	}
	
	
	
}
