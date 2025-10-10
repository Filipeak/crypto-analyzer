package com.newsscraper.sources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HTTPDownloadUtilsTest {

    @Test
    void testFetchExampleAPI() {
        String result = HTTPDownloadUtils.fetch("https://jsonplaceholder.typicode.com/posts/1");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("{  \"userId\": 1,  \"id\": 1,  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"}", result);
    }
}