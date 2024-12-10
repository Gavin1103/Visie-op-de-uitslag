package dev.visie.elections.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/seeder/" + filename));
        return objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
