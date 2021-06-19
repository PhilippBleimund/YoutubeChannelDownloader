package WebScrapper;

import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ogermirror {
	
	Document doc;
	
	public ogermirror() {
		try {
			initDocWith();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(doc);
		String[] links = getSiteURL();
		for(String s : links) {
			getVideoURL(s);
		}
		
	}
	
	public void initDocWith() throws IOException{
		String baseURL = "https://ogermirror.tk/";
		
		doc = Jsoup.connect(baseURL).timeout(60000).maxBodySize(0) .get();
	}
	
	public String[] getSiteURL() {
		Elements contend = doc.select("main.index");
		
		int i=0;
		String[] links = new String[contend.select("a").size()];
		System.out.println(contend.select("a").size());
		for(Element e : contend.select("a")) {
			
			String s = e.attr("href").toString();
			s = s.substring(3, s.length());
			
			links[i] = s;
			System.out.println(e.ownText());
			System.out.println(s);
			i++;
		}
		
		return links;
	}
	
	public String getVideoURL(String preLink) {
		Document docVideo = null;
		try {
			docVideo = Jsoup.connect("https://ogermirror.tk/" + preLink).timeout(60000).maxBodySize(0) .get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Elements video = docVideo.getElementsByClass("video");
		Elements video2 = video.select("video");
		Elements source = video2.select("source");
		
		System.out.println(source.attr("src"));
		return source.attr("src").toString();
	}
}
