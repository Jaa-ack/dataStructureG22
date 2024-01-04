import java.io.IOException;
import java.util.ArrayList;

public class WebNode{
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;

	public WebNode(WebPage webPage){
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	public void setNodeScore(KeywordList keywords) throws IOException { // 設定node分數
		if (children.size() != 0) { // 如果有子網頁，node分數 = （所有子網頁 / 子網頁數量）1/4 + 原網頁3/4
			for (WebNode child : children) {
				child.setNodeScore(keywords);
				nodeScore += child.nodeScore;
			}
			nodeScore = nodeScore / children.size() / 4;
			webPage.setScore(keywords);
			nodeScore += webPage.score * 0.75;
		}else { // 如果沒有子網頁，node分數 = 原網頁
			webPage.setScore(keywords);
			nodeScore += webPage.score;
		}
	}

	public void addChild(WebNode child) { // 新增子網頁
		this.children.add(child);
		child.parent = this;
	}

	public int getDepth() { // 取得node深度
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null) {
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}
