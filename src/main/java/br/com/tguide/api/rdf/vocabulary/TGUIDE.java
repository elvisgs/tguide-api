package br.com.tguide.api.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

public class TGUIDE {

    public static final String uri = "http://tguide.com.br/props#";

    public static final Property collectedAt = ResourceFactory.createProperty(uri, "collectedAt");

    public static String getURI() {
        return uri;
    }
}
