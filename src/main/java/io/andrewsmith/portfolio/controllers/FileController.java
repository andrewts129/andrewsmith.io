package io.andrewsmith.portfolio.controllers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ini4j.Ini;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileController {

    @RequestMapping(value = "/resume", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> getResume() throws IOException {

        // These headers are sent with the PDF to the user
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        responseHeaders.add("Pragma", "no-cache");
        responseHeaders.add("Expires", "0");

        // Attempts to download the resume pdf from my private GitHub repo "Resume"
        HttpClient client = HttpClientBuilder.create().build();

        String urlToResume = "https://raw.githubusercontent.com/andrewts129/resume/master/AndrewSmithResume.pdf";
        HttpGet get = new HttpGet(urlToResume);

        // Loads the access token needed to access my private GitHub repo
        Ini keysIni = new Ini(new ClassPathResource("keys.ini").getInputStream());
        String gitHubAccessToken = keysIni.get("Github", "ACCESS_TOKEN");
        get.addHeader("Authorization", "token " + gitHubAccessToken);

        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();

        // If the GitHub pdf was successfully retrieved, deliver that. Otherwise, deliver a backup local copy
        if (entity != null) {
            return ResponseEntity
                    .ok().headers(responseHeaders)
                    .contentLength(entity.getContentLength())
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(new InputStreamResource(entity.getContent()));
        }
        else {
            ClassPathResource resumePdfFile = new ClassPathResource("files/AndrewSmithResume.pdf");
            return ResponseEntity
                    .ok().headers(responseHeaders)
                    .contentLength(resumePdfFile.contentLength())
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(new InputStreamResource(resumePdfFile.getInputStream()));
        }
    }
}
