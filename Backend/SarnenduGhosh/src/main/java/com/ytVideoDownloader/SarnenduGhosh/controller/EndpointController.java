package com.ytVideoDownloader.SarnenduGhosh.controller;

import com.ytVideoDownloader.SarnenduGhosh.service.EndpointService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EndpointController {

    @Autowired
    private EndpointService service;

    @GetMapping("/formats")
    public List<String> getFormats (@RequestParam String url) throws IOException,InterruptedException {
        return service.getFormats(url);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadVideo(@RequestParam String url, @RequestParam String formatId) throws IOException, InterruptedException{
        return service.downloadVideo(url,formatId);
    }
}
