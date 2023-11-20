import java.util.ArrayList;

public class Score {
	private ArrayList<Keyword> keywordList;
	private ArrayList<SearchingWeb> webList;
	private String theme;
	
	public Score() {
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
	
	
	
}
