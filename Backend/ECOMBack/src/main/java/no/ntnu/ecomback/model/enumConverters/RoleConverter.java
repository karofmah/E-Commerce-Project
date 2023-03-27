/**

 This class is a JPA attribute converter used to convert a Role object to a String representation
 and vice versa. This is necessary because JPA cannot directly persist enums.
 */
package no.ntnu.ecomback.model.enumConverters;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import no.ntnu.ecomback.model.Role;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    /**
     * Converts a Role object to a String representation to be persisted in the database.
     *
     * @param role the Role object to be converted
     * @return the String representation of the Role object
     */
    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.toString();
    }

    /**
     * Converts a String representation of a Role to a Role object.
     *
     * @param role the String representation of the Role
     * @return the Role object
     */
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