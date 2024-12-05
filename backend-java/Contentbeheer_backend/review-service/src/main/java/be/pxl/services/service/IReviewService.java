package be.pxl.services.service;

import org.springframework.http.ResponseEntity;

public interface IReviewService {
    // Methode om de posts met status 'DRAFT' op te halen
    ResponseEntity<String> getDrafts();
}
