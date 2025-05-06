package org.example.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WebScrapper {
    private final String url;

    public WebScrapper(String url) {
        this.url = url;
    }

    public Set<String> getImageLinks() {
        HashSet<String> links = new HashSet<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements images = doc.select("img");
            for (Element image : images) {
                links.add(image.attr("abs:src"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return links;
    }
}
