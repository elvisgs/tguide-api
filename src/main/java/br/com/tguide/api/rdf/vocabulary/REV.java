package br.com.tguide.api.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

public class REV {

    public static final String uri = "http://purl.org/stuff/rev#";

    public static final Property comment = ResourceFactory.createProperty(uri, "comment");
    public static final Property rating = ResourceFactory.createProperty(uri, "rating");

    public static String getURI() {
        return uri;
    }
}
