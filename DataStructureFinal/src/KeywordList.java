import java.util.*;

public class KeywordList{
	private ArrayList<Keyword> lst;

	public KeywordList(){
		this.lst = new ArrayList<Keyword>();
	}

	public ArrayList<Keyword> getkList(){
		return lst;
	}
	
	public void add(Keyword keyword){
		for (int i = 0; i < lst.size(); i++) {
			if (keyword.getName().equals(lst.get(i).getName())) {
				if (keyword.getOrder() == (lst.get(i).getOrder())) {
					lst.set(i, new Keyword(keyword.getName(), keyword.getOrder(), keyword.getTimes() + lst.get(i).getTimes()));
				}
			}
			if (keyword.getOrder() <= lst.get(i).getOrder()) {
				if (keyword.getTimes() <= lst.get(i).getTimes()) {
					lst.add(i, keyword);
					return;
				}
			}
		}
		lst.add(keyword);
	}
	
	public void addList(KeywordList list) {
		for (Keyword keyword : list.getkList()) {
			lst.add(keyword);
		}
	}

	public LinkedList<Keyword> has(String name){
		LinkedList<Keyword> results = new LinkedList<>();
		for (int i = 0; i < lst.size(); i++){
			Keyword k = lst.get(i);
			if (k.getName().equals(name)){
				results.add(k);
			}
		}if (results.isEmpty()){
			return null;
		}else{
			return results;
		}
	}

	public void deleteKeyword(Keyword keyword){
		ArrayList<Keyword> found = new ArrayList<>();
		for (Keyword key : lst) {
			if (key == keyword){
				found.add(keyword);
			}
		}
		
		if (!found.isEmpty()){
			lst.removeAll(found);
		}
	}
	
	public KeywordList outputName(String name){
		KeywordList result = new KeywordList();
		for (int i = 0; i < lst.size(); i++){
			Keyword k = lst.get(i);
			if (k.getName().equals(name)){
				result.add(k);
			}
		}
		return result;
	}

	
	
	
	
//	public void outputFirstN(int n){
//		if (n > lst.size()){
//			System.out.println("InvalidOperation");
//			return;
//		}
//		LinkedList<Keyword> found = new LinkedList<>();
//
//		for (int i = 0; i < n; i++){
//			Keyword k = lst.get(i);
//			found.add(k);
//		}
//		printKeywordList(found);
//	}
//
//	public void deleteIndex(int i){
//		if (i >= lst.size()){
//			return;
//		}
//		lst.remove(i);
//	}

//	public void deleteHas(String pattern){
//		
//		LinkedList<Keyword> found = new LinkedList<>();
//		
//		for (Keyword keyword : lst) {
//			if (keyword.name.contains(pattern)){
//				found.add(keyword);
//			}
//		}
//		
//		if (!found.isEmpty()){
//			lst.removeAll(found);
//		}
//	}
//
//	public void deleteName(String name){
//		
//		LinkedList<Keyword> found = new LinkedList<>();
//		
//		for (Keyword keyword : lst) {
//			if (keyword.name.equals(name)){
//				found.add(keyword);
//			}
//		}
//		
//		if (!found.isEmpty()){
//			lst.removeAll(found);
//		}
//	}

	public void deleteAll(){
		lst = new ArrayList<Keyword>();
	}

	private void printKeywordList(LinkedList<Keyword> kLst){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < kLst.size(); i++){
			Keyword k = kLst.get(i);
			if (i > 0)
				sb.append(" ");
			sb.append(k.toString());
		}
		System.out.println(sb.toString());
	}
}