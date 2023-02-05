package com.omocoder.hdwettingdownloader;

import java.io.File;

final class PropertyHandler {

    private static final String MAX_THREADS_PROPERTY = "maxThreads";
    private static final String URL_PROPERTY = "url";
    private static final String DOWNLOAD_PATH_PROPERTY = "downloadPath";
    private static final String DOWNLOAD_FILENAME_PROPERTY = "filenamePattern";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String START_INDEX_PROPERTY = "startIndex";
    private static final String END_INDEX_PROPERTY = "endIndex";

    private static final int DEFAULT_MAX_THREADS = 16;
    private static final String DEFAULT_URL = "https://hdwetting.com/members/hd_videos/vid_%d.mp4";
    private static final String DEFAULT_FILENAME = "hdwetting_%s.mp4";

    private final String url;
    private final int maxThreads;
    private final String downloadPath;
    private final String username;
    private final String password;
    private final int startIndex;
    private final int endIndex;

    PropertyHandler() {
        this.maxThreads = Integer.parseInt(
                System.getProperty(MAX_THREADS_PROPERTY, String.valueOf(DEFAULT_MAX_THREADS)));
        this.url = System.getProperty(URL_PROPERTY, DEFAULT_URL);
        this.startIndex = Integer.parseInt(System.getProperty(START_INDEX_PROPERTY, "0"));
        this.endIndex = Integer.parseInt(System.getProperty(END_INDEX_PROPERTY, "10"));
        final String downloadFileName = System.getProperty(DOWNLOAD_FILENAME_PROPERTY, DEFAULT_FILENAME);

        this.username = readRequiredProperty(USERNAME_PROPERTY);
        this.password = readRequiredProperty(PASSWORD_PROPERTY);
        this.downloadPath = readRequiredProperty(DOWNLOAD_PATH_PROPERTY) + File.separator + downloadFileName;
    }

    private String readRequiredProperty(final String propertyName) {
        final String value = System.getProperty(propertyName);
        if (value == null) {
            throw new IllegalArgumentException(String.format("The property '%s' is required", propertyName));
        }
        return value;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}