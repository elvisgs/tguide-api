package br.com.tguide.api;

import br.com.tguide.api.rdf.vocabulary.GEO;
import br.com.tguide.api.rdf.vocabulary.REV;
import br.com.tguide.api.rdf.vocabulary.SchemaOrg;
import br.com.tguide.api.rdf.vocabulary.TGUIDE;
import lombok.Data;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Data
public class PlaceRating {

    public static final String URI = "http://tguide.com.br/";

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    private double latitude, longitude;

    private float value;

    @NotBlank
    private String placeName;

    private String comment;

    @NotNull
    private Date collectedAt;

    public Resource addAsResourceTo(Model model) {
        String comment = hasText(this.comment) ? this.comment : "";

        return model.createResource(getResourceUri(), SchemaOrg.Place)
                .addProperty(SchemaOrg.name, placeName)
                .addProperty(GEO.latitude, String.valueOf(latitude))
                .addProperty(GEO.longitude, String.valueOf(longitude))
                .addProperty(REV.rating, String.valueOf(value))
                .addProperty(REV.comment, comment)
                .addProperty(TGUIDE.collectedAt, dateFormat.format(this.collectedAt), XSDDatatype.XSDdateTime);
    }

    private String getResourceUri() {
        return URI + UUID.randomUUID();
    }

    public static PlaceRating fromResource(Resource res) {
        PlaceRating rating = new PlaceRating();
        rating.latitude = res.getProperty(GEO.latitude).getDouble();
        rating.longitude = res.getProperty(GEO.longitude).getDouble();
        rating.placeName = res.getProperty(SchemaOrg.name).getString();
        rating.value = res.getProperty(REV.rating).getFloat();
        rating.comment = res.getProperty(REV.comment).getString();
        try {
            rating.collectedAt = dateFormat.parse(res.getProperty(TGUIDE.collectedAt).getString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return rating;
    }

}
