import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WebPage{
	public String url;
	public String name;
	public double score;
	public String relativeWord;

	public WebPage(String url, String name){
		this.url = url;
		this.name = name;
	}

	public void setScore(KeywordList keywords) {
		Score temp;
		try {
			temp = new Score(keywords);
			score = 0;
			score += temp.score(url);
			relativeWord = temp.getRelativeWord();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			score = 0;
			relativeWord = "";
		}
	}
}