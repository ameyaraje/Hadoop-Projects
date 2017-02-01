import java.util.ArrayList;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawl {

	static List<String> links = new LinkedList<>();
	List<String> result = new ArrayList<String>();

	public void extractLinks(String url) throws Exception { 

		try {
			Connection conn = Jsoup.connect(url);
			Document doc = conn.get(); 
			Elements links = doc.select("a[href]"); 
			for (Element link : links) {
				if (hasWeirdStuff(link))
					continue;
				result.add(link.attr("abs:href")); 
			} 
			for (String link : result) { 
				System.out.println(link); 
			}
		}
		catch(Exception e) {
			System.out.println("ENCOUNTERED 404!!!!");
			e.printStackTrace();
		}
	}

	public List<String> getLinks () {
		return this.result;
	}

	public boolean hasWeirdStuff(Element link) {
		String[] ignore = {"mailto", 
				"drive", 
				"dropbox", 
				"login", 
				"onedrive",
				"signup"
		};

		for (String s : ignore) {
			if (link.attr("abs:href").toLowerCase().contains(s)) 
				return true;
		}

		return false;
	}

}
