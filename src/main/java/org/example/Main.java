package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.downloader.ImageDownloader;
import org.example.downloader.WebScrapper;

import java.util.Set;


public class Main {
    public static final Logger infoLogger = LogManager.getLogger("queries");
    public static final Logger errorsLogger = LogManager.getLogger("errors"); // error logging

    public static void main(String[] args){
        String url = "https://www.imdb.com/";
        String dirPath = "./images";

        WebScrapper webScrapper = new WebScrapper(url);
        Set<String> links = webScrapper.getImageLinks();

        ImageDownloader imageDownloader = new ImageDownloader(dirPath);

        for (String link : links) {
            download(imageDownloader, link);
        }
    }

    private static void download(ImageDownloader imageDownloader, String link) {
        try {
            String downloadedFilePath = imageDownloader.download(link);
            infoLogger.info("Зображення за посиланням {} успішно завантажено в файл {}", link, downloadedFilePath);
        } catch (RuntimeException e) {
            errorsLogger.error("Помилка скачування файла {} в директорію {}", link, imageDownloader.getDirPath());
        }
    }
}