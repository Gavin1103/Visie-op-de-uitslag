package dev.visie.elections.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class JsonSeeder {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public JsonSeeder(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public <T> List<T> loadData(String filename, Class<T> clazz) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:seeder/" + filename);

        if (!resource.exists()) {
            throw new IllegalArgumentException("File not found: seeder/" + filename);
        }

        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(
                    inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        }
    }
}
