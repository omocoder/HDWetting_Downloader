package com.omocoder.hdwettingdownloader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class VideoDownloader {

    private DownloadFileTask task;
    private ExecutorService executorService;
    private final PropertyHandler propertyHandler;

    VideoDownloader(final PropertyHandler propertyHandler) {
        this.propertyHandler = propertyHandler;
    }

    public void run() {
        init();
        downloadFiles();
        shutdown();
    }

    private void init() {
        task = new DownloadFileTask(buildAuthorizationValue(), propertyHandler.getDownloadPath(),
                propertyHandler.getUrl());
        executorService = Executors.newFixedThreadPool(propertyHandler.getMaxThreads());
    }

    private String buildAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString(
                (propertyHandler.getUsername() + ':' + propertyHandler.getPassword()).getBytes(StandardCharsets.UTF_8));
    }

    private void downloadFiles() {
        for (int i = propertyHandler.getStartIndex(); i < propertyHandler.getEndIndex(); i++) {
            addFileDownloadsToThreadPool(i);
        }
    }

    private void shutdown() {
        executorService.shutdown();
    }

    private void addFileDownloadsToThreadPool(final int id) {
        executorService.submit(() -> task.performDownloadFile(id));
    }
}