package shorturl.server.server.application.server;

import org.springframework.http.ResponseEntity;
import shorturl.server.server.application.dto.UrlRequest;

public interface UrlMapServer {
     public ResponseEntity getLongUrl(UrlRequest shortUrlReq);

     public ResponseEntity getShortUrl(UrlRequest longUrlReq);
}
