package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;


public class Main {
    private static int number = 1;

    public static void main(String[] args) throws Exception {
        String url = "https://www.imdb.com/";
        Document doc = Jsoup.connect(url).get();
        Elements select = doc.select("img");
        HashSet<String> links = new HashSet<>();
        select.forEach(image -> links.add(image.attr("abs:src")));

        for (String link : links) {
            String extension = link
                    .replaceAll("^.+\\.", "")
                    .replace("?.+$", "");
            String filePath = "data/" + number++ + "." + extension;
            try {
                download(link, filePath);
            } catch (Exception e) {
                System.out.println("Couldn't download " + link);
            }
        }
    }

    public static void download(String link, String filePath) throws IOException {
        URLConnection connection = new URL(link).openConnection();
        InputStream inStream = connection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        int b;
        while ((b = inStream.read()) != -1) {
            fileOutputStream.write(b);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inStream.close();
    }
}