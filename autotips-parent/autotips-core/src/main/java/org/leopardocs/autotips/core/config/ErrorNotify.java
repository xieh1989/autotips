//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.04 at 05:33:53 PM CST 
//


package org.leopardocs.autotips.core.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorNotify complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorNotify">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="smtpConfig" type="{http://config.core.autotips.leopard.org}SmtpConfig"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fromMail" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subject" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="toMail" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorNotify", propOrder = {
    "smtpConfig"
})
public class ErrorNotify {

    @XmlElement(required = true)
    protected SmtpConfig smtpConfig;
    @XmlAttribute(required = true)
    protected String fromMail;
    @XmlAttribute(required = true)
    protected String subject;
    @XmlAttribute(required = true)
    protected String toMail;

    /**
     * Gets the value of the smtpConfig property.
     * 
     * @return
     *     possible object is
     *     {@link SmtpConfig }
     *     
     */
    public SmtpConfig getSmtpConfig() {
        return smtpConfig;
    }

    /**
     * Sets the value of the smtpConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link SmtpConfig }
     *     
     */
    public void setSmtpConfig(SmtpConfig value) {
        this.smtpConfig = value;
    }

    /**
     * Gets the value of the fromMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromMail() {
        return fromMail;
    }

    /**
     * Sets the value of the fromMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromMail(String value) {
        this.fromMail = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the toMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToMail() {
        return toMail;
    }

    /**
     * Sets the value of the toMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToMail(String value) {
        this.toMail = value;
    }

}