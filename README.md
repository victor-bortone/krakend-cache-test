# Krakend cache test-case

Test-Case for an issue with krakend cache mechanism, [described on github](https://github.com/devopsfaith/krakend/issues/335).

This is a simple application with two endpoints: http://localhost:8081/test and http://localhost:8081/test2, created to 
serve as a test-case for the [krakend](https://www.krakend.io/) API gateway cache mechanism. Apparently, krakend cache 
mechanism fails to kick in when the response uses [chunked transfer-encoding](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Transfer-Encoding#directives).

- /test is returned with "Transfer-Encoding: chunked" header and krakend doesn't cache the result

- /test2 is returned with "Content-Lenght" header, without "Transfer-Encoding" and krakend is able to cache the result 

Both endpoints use "Cache-Control: public, max-age=60" header to indicate krakend that the response could be cached and
return a response with "Content-Type: application/json".

# Run instructions

You will need docker and docker-compose installed. Just execute:

```
docker-compose up
```

After a few minutes (Maven dependencies take a while to download), krakend will run at http://localhost:8080, so to 
test both endpoints, you should hit http://localhost:8080/test and http://localhost:8080/test2. The first endpoint will
always hit the Java application. On the other hand, the second one will be cached by krakend as expected and only the
first request will be passed to the Java application.
