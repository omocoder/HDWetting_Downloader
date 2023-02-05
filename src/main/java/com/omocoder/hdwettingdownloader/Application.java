package com.omocoder.hdwettingdownloader;

public final class Application {

    //private static final String URL = "https://hdwetting.com/members/hd_videos/vid_%d.mp4";
    //private static final String URL = "https://hd-diapers.com/members/hd_videos/hdd_%d.mp4";
    //private static final String DOWNLOAD_FILENAME = "hdwetting_%s.mp4";
    //private static final String DOWNLOAD_FILENAME = "hd-diapers_%s.mp4";

    private Application() {
    }

    public static void main(final String... args) {
        final PropertyHandler propertyHandler = new PropertyHandler();
        final VideoDownloader videoDownloader = new VideoDownloader(propertyHandler);
        videoDownloader.run();
    }
}