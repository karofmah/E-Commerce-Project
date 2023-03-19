package no.ntnu.ecomback.model.enumConverters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import no.ntnu.ecomback.model.Category;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.toString();
    }

    @Override
    public Category convertToEntityAttribute(String category) {
        if (category == null) {
            return null;
        }

        return Stream.of(Category.values())
                .filter(c -> c.toString().equals(category))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
