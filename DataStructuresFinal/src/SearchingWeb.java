import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SearchingWeb {
	private String name;
	private String url;
	private String content;
	
	public SearchingWeb(String name, String url) throws IOException {
		this.name = name;
		this.url = url;
		content = fetchContent();
	}
	
	public String fetchContent() throws IOException{
		URL url = new URL(this.url);
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
	
	public String getContent() {
		return content;
	}
	
	public String getName() {
		return name;
	}
}
