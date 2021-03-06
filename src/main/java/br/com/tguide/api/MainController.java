package br.com.tguide.api;

import br.com.tguide.api.rdf.vocabulary.GEO;
import br.com.tguide.api.rdf.vocabulary.REV;
import br.com.tguide.api.rdf.vocabulary.SchemaOrg;
import br.com.tguide.api.rdf.vocabulary.TGUIDE;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class MainController {

    private static final String FUSEKI_URI = "http://eudesdionatas.tk:8080/TGuide";
    private static final String GRAPH_URI = "/ratings";
    private final DatasetAccessor datasetAccessor;

    public MainController() {
        datasetAccessor = DatasetAccessorFactory.createHTTP(FUSEKI_URI);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Validated PlaceRating placeRating) {
        Model model = createModel();
        placeRating.addAsResourceTo(model);

        datasetAccessor.add(GRAPH_URI, model);

        //model.write(System.out);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PlaceRating>> findAll() {
        Model model = datasetAccessor.getModel(GRAPH_URI);
        List<PlaceRating> items = new ArrayList<>();

        if (model != null && !model.isEmpty())
            model.listSubjects().forEachRemaining(res -> items.add(PlaceRating.fromResource(res)));

        return ResponseEntity.ok(items);
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
