//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.02 at 07:13:07 PM CEST 
//


package org.orbisgis.core.renderer.persistance.se;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AxisChartType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AxisChartType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/se}GraphicType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/se}UnitOfMeasure" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}Transform" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}Normalize" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}PolarChart" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}CategoryWidth"/>
 *         &lt;element ref="{http://www.opengis.net/se}CategoryGap" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}Fill" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}Stroke" minOccurs="0"/>
 *         &lt;element ref="{http://www.opengis.net/se}AxisScale"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AxisChartType", propOrder = {
    "unitOfMeasure",
    "transform",
    "normalize",
    "polarChart",
    "categoryWidth",
    "categoryGap",
    "fill",
    "stroke",
    "axisScale"
})
public class AxisChartType
    extends GraphicType
{

    @XmlElement(name = "UnitOfMeasure")
    @XmlSchemaType(name = "anyURI")
    protected String unitOfMeasure;
    @XmlElement(name = "Transform")
    protected TransformType transform;
    @XmlElement(name = "Normalize")
    protected NormalizeType normalize;
    @XmlElement(name = "PolarChart")
    protected PolarChartType polarChart;
    @XmlElement(name = "CategoryWidth", required = true)
    protected ParameterValueType categoryWidth;
    @XmlElement(name = "CategoryGap")
    protected ParameterValueType categoryGap;
    @XmlElementRef(name = "Fill", namespace = "http://www.opengis.net/se", type = JAXBElement.class)
    protected JAXBElement<? extends FillType> fill;
    @XmlElementRef(name = "Stroke", namespace = "http://www.opengis.net/se", type = JAXBElement.class)
    protected JAXBElement<? extends StrokeType> stroke;
    @XmlElement(name = "AxisScale", required = true)
    protected AxisScaleType axisScale;

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the transform property.
     * 
     * @return
     *     possible object is
     *     {@link TransformType }
     *     
     */
    public TransformType getTransform() {
        return transform;
    }

    /**
     * Sets the value of the transform property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformType }
     *     
     */
    public void setTransform(TransformType value) {
        this.transform = value;
    }

    /**
     * if defined, data will be normalized (i.e. convert to percentage)
     *                 
     * 
     * @return
     *     possible object is
     *     {@link NormalizeType }
     *     
     */
    public NormalizeType getNormalize() {
        return normalize;
    }

    /**
     * Sets the value of the normalize property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormalizeType }
     *     
     */
    public void setNormalize(NormalizeType value) {
        this.normalize = value;
    }

    /**
     * Gets the value of the polarChart property.
     * 
     * @return
     *     possible object is
     *     {@link PolarChartType }
     *     
     */
    public PolarChartType getPolarChart() {
        return polarChart;
    }

    /**
     * Sets the value of the polarChart property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolarChartType }
     *     
     */
    public void setPolarChart(PolarChartType value) {
        this.polarChart = value;
    }

    /**
     * Gets the value of the categoryWidth property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterValueType }
     *     
     */
    public ParameterValueType getCategoryWidth() {
        return categoryWidth;
    }

    /**
     * Sets the value of the categoryWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterValueType }
     *     
     */
    public void setCategoryWidth(ParameterValueType value) {
        this.categoryWidth = value;
    }

    /**
     * Gets the value of the categoryGap property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterValueType }
     *     
     */
    public ParameterValueType getCategoryGap() {
        return categoryGap;
    }

    /**
     * Sets the value of the categoryGap property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterValueType }
     *     
     */
    public void setCategoryGap(ParameterValueType value) {
        this.categoryGap = value;
    }

    /**
     * Gets the value of the fill property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link FillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SolidFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GraphicFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DensityFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DotMapFillType }{@code >}
     *     
     */
    public JAXBElement<? extends FillType> getFill() {
        return fill;
    }

    /**
     * Sets the value of the fill property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link FillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SolidFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GraphicFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DensityFillType }{@code >}
     *     {@link JAXBElement }{@code <}{@link DotMapFillType }{@code >}
     *     
     */
    public void setFill(JAXBElement<? extends FillType> value) {
        this.fill = ((JAXBElement<? extends FillType> ) value);
    }

    /**
     * Gets the value of the stroke property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PenStrokeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link StrokeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GraphicStrokeType }{@code >}
     *     
     */
    public JAXBElement<? extends StrokeType> getStroke() {
        return stroke;
    }

    /**
     * Sets the value of the stroke property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PenStrokeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link StrokeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GraphicStrokeType }{@code >}
     *     
     */
    public void setStroke(JAXBElement<? extends StrokeType> value) {
        this.stroke = ((JAXBElement<? extends StrokeType> ) value);
    }

    /**
     * Gets the value of the axisScale property.
     * 
     * @return
     *     possible object is
     *     {@link AxisScaleType }
     *     
     */
    public AxisScaleType getAxisScale() {
        return axisScale;
    }

    /**
     * Sets the value of the axisScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link AxisScaleType }
     *     
     */
    public void setAxisScale(AxisScaleType value) {
        this.axisScale = value;
    }

}