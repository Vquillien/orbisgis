//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-646 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.09.08 at 04:10:19 PM CEST 
//


package org.orbisgis.renderer.symbol.collection.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for simple-symbol-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="simple-symbol-type">
 *   &lt;complexContent>
 *     &lt;extension base="{org.orbisgis.symbol}symbol-type">
 *       &lt;sequence>
 *         &lt;element ref="{org.orbisgis.symbol}property" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="symbol-type-id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "simple-symbol-type", propOrder = {
    "property"
})
public class SimpleSymbolType
    extends SymbolType
{

    @XmlElement(namespace = "org.orbisgis.symbol")
    protected List<Property> property;
    @XmlAttribute(name = "symbol-type-id", required = true)
    protected String symbolTypeId;

    /**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        return this.property;
    }

    /**
     * Gets the value of the symbolTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSymbolTypeId() {
        return symbolTypeId;
    }

    /**
     * Sets the value of the symbolTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSymbolTypeId(String value) {
        this.symbolTypeId = value;
    }

}
