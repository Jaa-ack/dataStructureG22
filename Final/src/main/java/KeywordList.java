import java.util.ArrayList;

public class KeywordList{
	private ArrayList<Keyword> list;

	public KeywordList() {
		this.list = new ArrayList<Keyword>();
	}
	
	public ArrayList<Keyword> getkList() { // 取得關鍵字ArrayList
		return list;
	}
	
	public void add(Keyword keyword) { // 新增關鍵字
		if (keyword.getTimes() < 2) { // 排除出現頻率<1的關鍵字
			return;
		}
		if (hasName(keyword.getName()) != null) { // 排除有一樣名字的，留下order, times較大的
			Keyword key = hasName(keyword.getName());
			if (key.getOrder() < keyword.getOrder()) {
				list.remove(key);
				add(keyword);
				return;
			}else if (key.getOrder() == keyword.getOrder()) {
				if (key.getTimes() <= keyword.getTimes()) {
					list.remove(key);
					add(keyword);
					return;
				}else {
					return;
				}
			}else {
				return;
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
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
	
	public void addList(KeywordList list) { // 新增關鍵字串
		for (Keyword key : list.getkList()) {
			add(key);
		}
	}

	public void addList(KeywordList list, int order) { // 新增關鍵字串同時設定Order
		for (Keyword key : list.getkList()) {
			add(new Keyword(key.getName(), order, key.getTimes()));
		}
	}
	
	public void deleteKeyword(Keyword keyword) { // 刪除指定關鍵字
		if (list.contains(keyword)){
			list.remove(keyword);
		}
	}
	
	public Keyword hasName(String name) { // 確認是否有名為name的關鍵字
		for (Keyword key : list) {
			if (key.getName().equals(name)) {
				return key;
			}
		}
		return null;
	}

	public ArrayList<String> outputOrder(int order) { // 回傳指定order的關鍵字
		ArrayList<String> retVal = new ArrayList<String>();
		for (Keyword key : list) {
			if (key.getOrder() == order) {
				retVal.add(key.getName());
			}
		}
		return retVal;
	}
}