package at.fhv.hotelmanagement.view.rest.serializer;

import at.fhv.hotelmanagement.domain.model.Price;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PriceSerializer extends StdSerializer<Price> {
    // default constructor required (for DI?), do not remove
    protected PriceSerializer() {
        this(null);
    }

    protected PriceSerializer(Class<Price> t) {
        super(t);
    }

    /*
        price: {
            amount:     123.45
            currency: {
                code:   "EUR",
                symbol: "€"
            }
        }
     */
    @Override
    public void serialize(Price value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("amount", value.getAmount());

        gen.writeObjectFieldStart("currency");
        gen.writeStringField("code", value.getCurrency().getCurrencyCode());
        gen.writeStringField("symbol", value.getCurrency().getSymbol());
        gen.writeEndObject();

        gen.writeEndObject();
    }
}
