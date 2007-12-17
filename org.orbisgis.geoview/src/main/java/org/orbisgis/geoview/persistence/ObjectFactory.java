//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.12.17 at 10:39:28 AM CET 
//


package org.orbisgis.geoview.persistence;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.orbisgis.geoview.persistence package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LayerCollection_QNAME = new QName("", "layer-collection");
    private final static QName _Layer_QNAME = new QName("", "layer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.orbisgis.geoview.persistence
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ViewContext }
     * 
     */
    public ViewContext createViewContext() {
        return new ViewContext();
    }

    /**
     * Create an instance of {@link LayerType }
     * 
     */
    public LayerType createLayerType() {
        return new LayerType();
    }

    /**
     * Create an instance of {@link LayerCollectionType }
     * 
     */
    public LayerCollectionType createLayerCollectionType() {
        return new LayerCollectionType();
    }

    /**
     * Create an instance of {@link SelectedLayer }
     * 
     */
    public SelectedLayer createSelectedLayer() {
        return new SelectedLayer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LayerCollectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "layer-collection")
    public JAXBElement<LayerCollectionType> createLayerCollection(LayerCollectionType value) {
        return new JAXBElement<LayerCollectionType>(_LayerCollection_QNAME, LayerCollectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LayerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "layer")
    public JAXBElement<LayerType> createLayer(LayerType value) {
        return new JAXBElement<LayerType>(_Layer_QNAME, LayerType.class, null, value);
    }

}
