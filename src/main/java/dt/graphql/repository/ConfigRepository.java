package dt.graphql.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.Document;
import com.fasterxml.jackson.databind.ObjectMapper;
import dt.graphql.domain.StirtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigRepository {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Bucket bucket;

    @Autowired
    public ConfigRepository(final Bucket couchbaseBucket) {
        this.bucket = couchbaseBucket;
    }

    public StirtConfig getConfigById(final String configId) throws Exception {
        final Document document = bucket.get(configId);
        return convertDocumentContents(document);
    }

    private StirtConfig convertDocumentContents(final Document document) throws Exception {
        return OBJECT_MAPPER.readValue(document.content().toString(), StirtConfig.class);
    }
}
