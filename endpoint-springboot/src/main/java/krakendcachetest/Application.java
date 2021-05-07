package krakendcachetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class Application {

	@GetMapping("/test")
	public ResponseEntity<Status> endpoint() {
		//Spring will add to the response "Transfer-Encoding: chunked" header and krakend never caches the result
		System.out.println("/test hit at " + new Date());
		return ResponseEntity.ok()
				.header("Cache-Control", "public, max-age=60")
				.body(new Status());
	}

	@GetMapping(value = "/test2", produces = "application/json")
	public ResponseEntity<String> test2() {
		//as the response is a String, Spring returns it with Content-Length header and no "Transfer-Encoding: chunked"
		//this is cached by krakend
		System.out.println("/test2 hit at " + new Date());
		return ResponseEntity.ok()
				.header("Cache-Control", "public, max-age=60")
				.body("{ \"message\": \"This will be cached: " + new Date() + "\" }");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	static class Status {
		public String message;

		public Status() {
			this.message = "This should be cached: " + new Date();
		}
	}
}
