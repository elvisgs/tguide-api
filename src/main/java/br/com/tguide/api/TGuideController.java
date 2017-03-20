package br.com.tguide.api;

import br.com.tguide.api.rdf.vocabulary.GEO;
import br.com.tguide.api.rdf.vocabulary.REV;
import br.com.tguide.api.rdf.vocabulary.SchemaOrg;
import br.com.tguide.api.rdf.vocabulary.TGUIDE;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@RestController("/ratings")
public class TGuideController {

    private static final String FUSEKI_URI = "http://eudesdionatas.tk:8080/TGuide";
    private static final String GRAPH_URI = "/ratings";
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

    @PostMapping
    public ResponseEntity save(@RequestBody @Validated PlaceRating placeRating) {
        Date collectedAt = placeRating.getCollectedAt();
        String comment = placeRating.getComment();
        comment = hasText(comment) ? comment : "";

        Model model = createModel();

        String about = "http://tguide.com.br/" + UUID.randomUUID();
        
        Resource resource = model.createResource(about, SchemaOrg.Place)
                .addProperty(SchemaOrg.name, placeRating.getPlaceName())
                .addProperty(GEO.latitude, String.valueOf(placeRating.getLatitude()))
                .addProperty(GEO.longitude, String.valueOf(placeRating.getLongitude()))
                .addProperty(REV.rating, String.valueOf(placeRating.getValue()))
                .addProperty(REV.comment, comment)
                .addProperty(TGUIDE.collectedAt, dateFormat.format(collectedAt), XSDDatatype.XSDdateTime);

        DatasetAccessor datasetAccessor = DatasetAccessorFactory.createHTTP(FUSEKI_URI);
        datasetAccessor.add(GRAPH_URI, model);

        model.write(System.out);

        return ResponseEntity.ok().build();
    }

    private Model createModel() {
        Model model = ModelFactory.createDefaultModel();
        model.setNsPrefix("schema", SchemaOrg.getURI());
        model.setNsPrefix("geo", GEO.getURI());
        model.setNsPrefix("rev", REV.getURI());
        model.setNsPrefix("tguide", TGUIDE.getURI());

        return model;
    }
}
