//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.04 at 05:33:53 PM CST 
//


package org.leopardocs.autotips.core.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Autotip complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Autotip">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sources" type="{http://config.core.autotips.leopard.org}Sources"/>
 *         &lt;element name="indexPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fields">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="field" type="{http://config.core.autotips.leopard.org}FieldItem" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="defaultFieldName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="defaultHitsPer" type="{http://www.w3.org/2001/XMLSchema}int" default="20" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Autotip", propOrder = {
    "sources",
    "indexPath",
    "fields"
})
public class Autotip {

    @XmlElement(required = true)
    protected Sources sources;
    @XmlElement(required = true)
    protected String indexPath;
    @XmlElement(required = true)
    protected Autotip.Fields fields;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(required = true)
    protected String defaultFieldName;
    @XmlAttribute
    protected Integer defaultHitsPer;

    /**
     * Gets the value of the sources property.
     * 
     * @return
     *     possible object is
     *     {@link Sources }
     *     
     */
    public Sources getSources() {
        return sources;
    }

    /**
     * Sets the value of the sources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sources }
     *     
     */
    public void setSources(Sources value) {
        this.sources = value;
    }

    /**
     * Gets the value of the indexPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndexPath() {
        return indexPath;
    }

    /**
     * Sets the value of the indexPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndexPath(String value) {
        this.indexPath = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * @return
     *     possible object is
     *     {@link Autotip.Fields }
     *     
     */
    public Autotip.Fields getFields() {
        return fields;
    }

    /**
     * Sets the value of the fields property.
     * 
     * @param value
     *     allowed object is
     *     {@link Autotip.Fields }
     *     
     */
    public void setFields(Autotip.Fields value) {
        this.fields = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the defaultFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultFieldName() {
        return defaultFieldName;
    }

    /**
     * Sets the value of the defaultFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultFieldName(String value) {
        this.defaultFieldName = value;
    }

    /**
     * Gets the value of the defaultHitsPer property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDefaultHitsPer() {
        if (defaultHitsPer == null) {
            return  20;
        } else {
            return defaultHitsPer;
        }
    }

    /**
     * Sets the value of the defaultHitsPer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultHitsPer(Integer value) {
        this.defaultHitsPer = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="field" type="{http://config.core.autotips.leopard.org}FieldItem" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "field"
    })
    public static class Fields {

        @XmlElement(required = true)
        protected List<FieldItem> field;

        /**
         * Gets the value of the field property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the field property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getField().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FieldItem }
         * 
         * 
         */
        public List<FieldItem> getField() {
            if (field == null) {
                field = new ArrayList<FieldItem>();
            }
            return this.field;
        }

    }

}
