package org.example.downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static org.example.Main.errorsLogger;


public class ImageDownloader {
    private final String dirPath;
    private int fileNumber = 0;

    public ImageDownloader(String dirPath) {
        this.dirPath = dirPath;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                String message = "Не вдалося створити директорію " + dirPath;
                errorsLogger.error(message);
                throw new RuntimeException(message);
            }
        }
    }

    public String download(String link) {
        InputStream inputStream;
        try {
            URLConnection urlConnection = new URL(link).openConnection();
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String filePath = createDirPath(link, dirPath);
        try {
            writePath(inputStream, filePath);
        } catch (IOException e) {
            errorsLogger.error("Помилка запису файла, файл пропущений:{}", filePath);
            throw new RuntimeException(e);
        }
        return filePath;
    }

    private static void writePath(InputStream inputStream, String filePath) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            int b;
            while ((b = inputStream.read()) != -1) {
                fileOutputStream.write(b);
            }
            inputStream.close();
        }
    }

    private String createDirPath(String link, String dirPath) {
        String fileExtension = link.replaceAll("^.+\\.", "").replace("?.+$", "");
        return dirPath + "/" + fileNumber++ + "." + fileExtension;
    }

    public String getDirPath() {
        return dirPath;
    }
}
