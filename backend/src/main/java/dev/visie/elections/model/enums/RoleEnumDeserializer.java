package dev.visie.elections.model.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RoleEnumDeserializer extends JsonDeserializer<RoleEnum> {
    @Override
    public RoleEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();
        return RoleEnum.valueOf(value);
    }
}
