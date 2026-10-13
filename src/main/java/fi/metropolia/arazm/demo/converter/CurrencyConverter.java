package fi.metropolia.arazm.demo.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CurrencyConverter implements AttributeConverter<Double, BigDecimal>{

    @Override
    public BigDecimal convertToDatabaseColumn(Double attribute){
        if(attribute == null){
            return null;
        }
        return BigDecimal.valueOf(attribute).setScale(2, RoundingMode.HALF_UP);
    }

    @Override 
    public Double convertToEntityAttribute(BigDecimal dbData){
        if(dbData == null){
            return null;
        }
        return dbData.doubleValue();

    }
    
}
