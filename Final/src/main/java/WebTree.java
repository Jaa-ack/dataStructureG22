import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WebTree{
	public WebNode root;

	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(KeywordList keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, KeywordList keywords) throws IOException{
		startNode.setNodeScore(keywords);
	}

	public List<WebNode> rearrangeNodesByScore() {
        List<WebNode> nodeList = new ArrayList<>();
        populateNodes(root, nodeList);

        // Sort the nodeList using the custom comparator
        nodeList.sort(new Comparator<WebNode>() {
            @Override
            public int compare(WebNode node1, WebNode node2) {
            	return Double.compare(node2.nodeScore, node1.nodeScore);
            }
        });

        return nodeList;
    }

    // Helper method to populate nodes from tree to a list
    private void populateNodes(WebNode node, List<WebNode> nodeList) {
        if (node == null) {
            return;
        }
        nodeList.add(node);
        for (WebNode child : node.children) {
            populateNodes(child, nodeList);
        }
    }
}