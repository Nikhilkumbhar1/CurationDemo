package com.accion.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class MavenSourceService {

    @Value("${savePath}")
    private String savePath;

    public String downloadMavenSource(String packageName, String version) {
        String[] groupIdArtifactId = packageName.split(":");
        if (groupIdArtifactId.length != 2) {
            return "Invalid format for groupId:artifactId. Use the format: <groupId:artifactId>";
        }

        String groupId = groupIdArtifactId[0];
        String artifactId = groupIdArtifactId[1];
        String baseUrl = "https://repo1.maven.org/maven2";
        String groupPath = groupId.replace('.', '/');
        String artifactPath = String.format("%s/%s/%s/%s/%s-%s-sources.jar", baseUrl, groupPath, artifactId, version, artifactId, version);

        String finalSavePath = savePath + "/" + artifactId + "-" + version + "-sources.jar";

        if (downloadFromUrl(artifactPath, finalSavePath)) {
            try {
                unzip(finalSavePath, finalSavePath.replace(".jar", ""));
            } catch (IOException e) {
                return "An error occurred while unzipping: " + e.getMessage();
            }
            return "Source code downloaded and saved to " + finalSavePath;
        } else {
            return "Failed to download source code. Response code: 404";
        }
    }

    private boolean downloadFromUrl(String urlStr, String savePath) {
        System.out.println("Constructed URL :"+ urlStr);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedInputStream in = new BufferedInputStream(httpConn.getInputStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                }
                httpConn.disconnect();
                return true;
            } else {
                httpConn.disconnect();
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void unzip(String jarFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(Paths.get(jarFilePath)))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[1024];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
