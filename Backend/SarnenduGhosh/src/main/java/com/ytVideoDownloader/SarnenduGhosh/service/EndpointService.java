package com.ytVideoDownloader.SarnenduGhosh.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.*;

public interface EndpointService {
    public List<String> getFormats(String url) throws IOException, InterruptedException;
    public ResponseEntity<byte[]> downloadVideo(String url,String formatId) throws IOException, InterruptedException;
}
