import java.util.*;

public class Spider {
	static int noOfPages = 50;
	static List<String> pagesToVisit = new LinkedList<>();
	static Set<String> pagesVisited = new HashSet<>();

	public HashMap<String, String> search(String url) throws Exception{
		HashMap<String, String> map = new HashMap<>();

		while (this.pagesVisited.size() < noOfPages) {
			Crawl crawler = new Crawl();
			System.out.println("New Crawler created");
			
			String currentURL;
			if (this.pagesVisited.isEmpty()) {
				System.out.println("Empty");
				currentURL = url;
				this.pagesVisited.add(url);
			}
			else {
				System.out.println("Empty, next URL picked");
				currentURL = this.nextURL();
			}
			System.out.println("Calling extractLinks");
			crawler.extractLinks(currentURL);
			this.pagesToVisit.addAll(crawler.getLinks());
			
			for (String s: pagesVisited) {
				System.out.println(s);
			}
		}
		return map;
	}
	
	private String nextURL() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while(this.pagesVisited.contains(nextUrl));
		
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
}
