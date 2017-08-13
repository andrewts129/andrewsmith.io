package io.andrewsmith.portfolio.controllers;

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
        ClassPathResource resumePdfFile = new ClassPathResource("files/AndrewSmithResume.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok().headers(headers)
                .contentLength(resumePdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(resumePdfFile.getInputStream()));
    }
}
