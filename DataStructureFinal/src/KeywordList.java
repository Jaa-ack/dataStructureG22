import java.util.*;

public class KeywordList{
	private ArrayList<Keyword> list;

	public KeywordList(){
		this.list = new ArrayList<Keyword>();
	}
	
	public ArrayList<Keyword> getkList(){
		return list;
	}
	
	public void add(Keyword keyword){
		if (keyword.getTimes() < 2) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (hasName(keyword.getName()) != null) { // 如果有一樣名字的留order, times最大的 
				if (hasName(keyword.getName()).getOrder() < keyword.getOrder()) {
					list.remove(hasName(keyword.getName()));
					add(keyword);
					return;
				}else if (hasName(keyword.getName()).getOrder() == keyword.getOrder()) {
					if (hasName(keyword.getName()).getTimes() < keyword.getTimes()) {
						list.remove(hasName(keyword.getName()));
						add(keyword);
						return;
					}
				}
			}
			if (keyword.getOrder() == list.get(i).getOrder()) { // order, times越小越前面
				if (keyword.getTimes() <= list.get(i).getTimes()) {
					list.add(i,keyword);
					return;
				}
			}
		}
		list.add(keyword);
		return;
	}
	
	public void addList(KeywordList list) {
		for (Keyword key : list.getkList()) {
			add(key);
		}
	}

	public void addList(KeywordList list, int order) {
		for (Keyword key : list.getkList()) {
			add(new Keyword(key.getName(), order, key.getTimes()));
		}
	}
	
	public void deleteKeyword(Keyword keyword){
		if (list.contains(keyword)){
			list.remove(keyword);
		}
	}
	
	public Keyword hasName(String name) {
		for (Keyword key : list) {
			if (key.getName().equals(name)) {
				return key;
			}
		}
		return null;
	}
}