package no.ntnu.ecomback.model.enumConverters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import no.ntnu.ecomback.model.Role;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.toString();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        if (role == null) {
            return null;
        }

        return Stream.of(Role.values())
                .filter(c -> c.toString().equals(role))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
