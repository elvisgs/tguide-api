package br.com.tguide.api;

import br.com.tguide.api.rdf.vocabulary.GEO;
import br.com.tguide.api.rdf.vocabulary.REV;
import br.com.tguide.api.rdf.vocabulary.SchemaOrg;
import br.com.tguide.api.rdf.vocabulary.TGUIDE;
import lombok.Data;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PlaceRating {

    private double latitude, longitude;

    private float value;

    @NotBlank
    private String placeName;

    private String comment;

    @NotNull
    private Date collectedAt;

}
