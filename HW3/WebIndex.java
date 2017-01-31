import java.util.*;

public class WebIndex {
		
	public static void main(String[] args) throws Exception{
		HashMap<String, String> mappedURLs = new HashMap<>(); 
		
		String seed = "http://www.ics.uci.edu";
		Spider spider = new Spider();
		mappedURLs = spider.search(seed);
		System.out.println("Crawled 50 links");
	}
}
