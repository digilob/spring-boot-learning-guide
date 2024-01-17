package org.learning.guide.component;

import org.junit.jupiter.api.Test;
import org.learning.guide.controller.schema.Author;
import org.learning.guide.controller.schema.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorsControllerTest extends BaseComponent {

    @Autowired
    private Environment springEnvironment;

    @Test
    public void testGetAllAuthors() {
        ResponseEntity<Authors> authorsResponseEntity =
                testRestTemplate.exchange(RequestEntity.get(getAuthorsUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .build(), Authors.class);

        assertEquals(HttpStatus.OK, authorsResponseEntity.getStatusCode());
        List<Author> authorList = authorsResponseEntity.getBody().getAuthors();
        assertFalse(authorList.isEmpty());
    }

    @Test
    public void testCreateAuthors() {
        Author newAuthor = makeDefaultAuthor();
        ResponseEntity<Author> authorsResponse =
                testRestTemplate.exchange(RequestEntity.post(getAuthorsUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .body(newAuthor), Author.class);
        assertEquals(HttpStatus.CREATED, authorsResponse.getStatusCode());

        assertNotNull(authorsResponse.getHeaders().getLocation());

        String authorHeaderId = authorsResponse.getHeaders().getLocation().toString();
        Author responseAuthor = authorsResponse.getBody();

        assertEquals(responseAuthor.getAuthorId(), Long.valueOf(authorHeaderId));
        assertEquals(newAuthor.getAuthorName(), responseAuthor.getAuthorName());
    }

    @Test
    public void testPutAuthors() {
        ResponseEntity<Author> authorsResponseEntity =
                testRestTemplate.exchange(RequestEntity.post(getAuthorsUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .body(makeDefaultAuthor()), Author.class);
        assertEquals(HttpStatus.CREATED, authorsResponseEntity.getStatusCode());
        URI location = authorsResponseEntity.getHeaders().getLocation();
        Long authorId = Long.valueOf(location.toString());

        Author requestedAuthor = getAuthor(authorId);
        requestedAuthor.setAuthorName("A New First Name");
        ResponseEntity<String> updateEntity =
                testRestTemplate.exchange(RequestEntity.put(getAuthorsUri(authorId))
                        .accept(MediaType.APPLICATION_JSON)
                        .body(requestedAuthor), String.class);
        assertEquals(HttpStatus.NO_CONTENT, updateEntity.getStatusCode());

        Author updatedAuthor = getAuthor(authorId);
        assertEquals("A New First Name", updatedAuthor.getAuthorName());
    }

    @Test
    public void testDeleteAuthors() {
        Author defaultAuthor = makeDefaultAuthor();
        ResponseEntity<String> authorsResponseEntity =
                testRestTemplate.exchange(RequestEntity.post(getAuthorsUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .body(defaultAuthor), String.class);
        assertEquals(HttpStatus.CREATED, authorsResponseEntity.getStatusCode());
        URI location = authorsResponseEntity.getHeaders().getLocation();
        Long authorId = Long.valueOf(location.toString());

        ResponseEntity<String> deleteEntity =
                testRestTemplate.exchange(RequestEntity.delete(getAuthorsUri(authorId))
                        .accept(MediaType.APPLICATION_JSON)
                        .build(), String.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteEntity.getStatusCode());

        ResponseEntity<String> authorResponse = getAuthorResponse(authorId);
        assertEquals(HttpStatus.NOT_FOUND, authorResponse.getStatusCode());
    }

    private Author getAuthor(Long authorId) {
        ResponseEntity<Author> exchange = testRestTemplate.exchange(RequestEntity.get(getAuthorsUri(authorId))
                .accept(MediaType.APPLICATION_JSON)
                .build(), Author.class);

        assertTrue(exchange.getStatusCode().is2xxSuccessful() || exchange.getStatusCode().value() == 404);
        return exchange.getBody();
    }

    private ResponseEntity<String> getAuthorResponse(Long authorId) {
        return testRestTemplate.exchange(RequestEntity.get(getAuthorsUri(authorId))
                        .accept(MediaType.APPLICATION_JSON)
                        .build(), String.class);
    }


    private Author makeDefaultAuthor() {
        Author authorEntity = new Author();
        authorEntity.setAuthorName("Ellen Weiss");
        return authorEntity;
    }

    private String getAuthorsUri(Long authorId) {
        return getAuthorsUri() + "/" + authorId;
    }

    private String getAuthorsUri() {
        String port = springEnvironment.getProperty("local.server.port", "5000");
        return "http://localhost:" + port + "/authors";
    }
}
