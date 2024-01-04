import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WebTree{
	public WebNode root;

	public WebTree(WebPage rootPage) {
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(KeywordList keywords) throws IOException { // 設定tree內部所有網頁分數
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, KeywordList keywords) throws IOException{ // 設定指定node底下所有網頁分數
		startNode.setNodeScore(keywords);
	}

	public List<WebNode> rearrangeNodesByScore() { // 將tree內部網頁一分數重新排序
        List<WebNode> nodeList = new ArrayList<>();
        populateNodes(root, nodeList);

        nodeList.sort(new Comparator<WebNode>() {
            @Override
            public int compare(WebNode node1, WebNode node2) {
            	return Double.compare(node2.nodeScore, node1.nodeScore);
            }
        });

        nodeList.remove(root);
        return nodeList;
    }

    private void populateNodes(WebNode node, List<WebNode> nodeList) { // 將tree排序成List
        if (node == null) {
            return;
        }
        nodeList.add(node);
        for (WebNode child : node.children) {
            populateNodes(child, nodeList);
        }
    }
}