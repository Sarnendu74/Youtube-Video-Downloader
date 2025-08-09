package com.ytVideoDownloader.SarnenduGhosh.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EndpointServiceImpl implements EndpointService{

    @Override
    public List<String> getFormats(String url) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\ariji\\AppData\\Local\\Packages\\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\\LocalCache\\local-packages\\Python313\\Scripts\\yt-dlp.exe", "-F", url);

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        List<String> formats = new ArrayList<>();
        while((line = reader.readLine()) != null){
            if (line.matches("^[0-9]+\\s+.*")){
                formats.add(line.trim());
            }
        }
        process.waitFor();
        return formats;
    }

    @Override
    public ResponseEntity<byte[]> downloadVideo(String url, String formatId) throws IOException, InterruptedException {
        // Create a temporary file for the downloaded video
        File temp = File.createTempFile("video", ".mp4");

        // Correct ProcessBuilder command (with -f flag and full yt-dlp path)
        ProcessBuilder pb = new ProcessBuilder(
                "C:\\Users\\ariji\\AppData\\Local\\Packages\\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\\LocalCache\\local-packages\\Python313\\Scripts\\yt-dlp.exe",
                "-f", formatId,
                "-o", temp.getAbsolutePath(),
                url
        );
        pb.redirectErrorStream(true);

        Process process = pb.start();
        process.waitFor();

        // Read the file into a byte array
        byte[] videoBytes = new FileInputStream(temp).readAllBytes();
        temp.delete();

        // Return as downloadable response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=video.mp4")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(videoBytes);
    }

}
