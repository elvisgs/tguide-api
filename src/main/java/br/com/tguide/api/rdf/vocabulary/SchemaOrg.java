package br.com.tguide.api.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class SchemaOrg {

    public static final String uri = "http://schema.org/";

    public static final Resource Place = ResourceFactory.createResource(uri + "Place");

    public static final Property name = ResourceFactory.createProperty(uri, "name");

    public static String getURI() {
        return uri;
    }
}
