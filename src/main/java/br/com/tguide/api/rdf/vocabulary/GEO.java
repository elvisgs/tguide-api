package br.com.tguide.api.rdf.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

public class GEO {

    public static final String uri = "http://www.w3.org/2003/01/geo/wgs84_pos#";

    public static final Property longitude = ResourceFactory.createProperty(uri, "long");
    public static final Property latitude = ResourceFactory.createProperty(uri, "lat");

    public static String getURI() {
        return uri;
    }
}
