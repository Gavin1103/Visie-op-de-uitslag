package dev.visie.elections.config;

import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Topic.class, TopicResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUsername(), TopicResponseDto::setUsername);
        });

        return modelMapper;
    }
}
