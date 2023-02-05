package com.omocoder.hdwettingdownloader;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

final class DownloadFileTask {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final String authorizationValue;
    private final String downloadPath;
    private final String baseUrl;

    DownloadFileTask(final String authorizationValue, final String downloadPath, final String baseUrl) {
        this.authorizationValue = authorizationValue;
        this.downloadPath = downloadPath;
        this.baseUrl = baseUrl;
    }

    public void performDownloadFile(final int id) {
        try {
            downloadFile(id);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    //TODO: exit when not authenticated
    public void downloadFile(final int id) throws IOException {
        final URL url = new URL(String.format(baseUrl, id));
        HttpURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty(AUTHORIZATION_HEADER, authorizationValue);

            if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
                throw new IOException(String.format("Unexpected HTTP status code: %s", connection.getResponseCode()));
            }

            connection.connect();
            try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
                final Path downloadFile = Paths.get(String.format(downloadPath, id));
                if (downloadFile.toFile().exists()) {
                    LOGGER.log(Level.INFO, "File {0} already exists, skipping download.", downloadFile);
                } else {
                    LOGGER.log(Level.INFO, "Downloading file from {0}", url);
                    Files.copy(inputStream, downloadFile);
                }
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}