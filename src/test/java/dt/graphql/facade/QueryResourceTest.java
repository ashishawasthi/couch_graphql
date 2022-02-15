package dt.graphql.facade;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dt.graphql.domain.StirtConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QueryResourceTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private Bucket couchbaseBucket;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String graphQLQuery;
    private ResponseEntity<String> response;

    @Before
    public void setUp() {
        couchbaseBucket.bucketManager().flush();
    }

    @Test
    public void given_data_populated_valid_query_should_return_results() throws Exception {
        givenThereAreProductsInTheDatabase();
        andIHaveASimpleGraphQLQuery();
        whenIMakeTheRequest();
        thenItShouldReturnTheExpectedResponse();
    }

    private void givenThereAreProductsInTheDatabase() throws Exception {
        final StirtConfig stirtConfig = new StirtConfig(1001, "20", "100", "12");
        final JsonDocument document = JsonDocument.create(String.valueOf(stirtConfig.getId()), JsonObject.fromJson(OBJECT_MAPPER.writeValueAsString(stirtConfig)));
        couchbaseBucket.insert(document);
    }

    private void andIHaveASimpleGraphQLQuery() {
        graphQLQuery = "{\"query\":\"{product (id:1001){ ndfJumpsAndTurns ndfCurveWeights } }\"}";
    }

    private void whenIMakeTheRequest() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        final HttpEntity<String> entity = new HttpEntity<>(graphQLQuery, headers);

        response = testRestTemplate.exchange("/query", HttpMethod.POST, entity, String.class);
    }

    private void thenItShouldReturnTheExpectedResponse() throws Exception {
        final JsonNode json = OBJECT_MAPPER.readTree(response.getBody());
        assertTrue(json.get("dataPresent").asBoolean());
        assertTrue(json.get("extensions").isNull());

        final JsonNode product = json.get("data").get("stirtConfig");
        assertEquals("20", product.get("ndfCurveWeights").asText());
        assertEquals("100", product.get("ndfCurveWeights").asInt());
    }
}
