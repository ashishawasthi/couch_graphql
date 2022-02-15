package dt.graphql.datafetchers;

import graphql.schema.DataFetcher;
import dt.graphql.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigDataFetcher {
    private final ConfigRepository configRepository;

    @Autowired
    public ConfigDataFetcher(final ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public DataFetcher getById() {
        return dataFetchingEnvironment -> {
            return configRepository.getConfigById(dataFetchingEnvironment.getArgument("id"));
        };
    }
}
