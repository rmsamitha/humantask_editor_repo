//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 06:17:49 PM IST 
//


package org.wso2.developerstudio.humantask.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tLeanTask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tLeanTask">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tTaskBase">
 *       &lt;sequence>
 *         &lt;element name="documentation" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tDocumentation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="interface" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tTaskInterface" maxOccurs="0" minOccurs="0"/>
 *         &lt;element name="messageSchema" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tMessageSchema"/>
 *         &lt;element name="priority" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tPriority-expr" minOccurs="0"/>
 *         &lt;element name="peopleAssignments" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tPeopleAssignments" minOccurs="0"/>
 *         &lt;element name="delegation" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tDelegation" minOccurs="0"/>
 *         &lt;element name="presentationElements" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tPresentationElements" minOccurs="0"/>
 *         &lt;element name="outcome" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tQuery" minOccurs="0"/>
 *         &lt;element name="searchBy" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tExpression" minOccurs="0"/>
 *         &lt;element name="renderings" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tRenderings" minOccurs="0"/>
 *         &lt;element name="deadlines" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tDeadlines" minOccurs="0"/>
 *         &lt;element name="composition" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tComposition" maxOccurs="0" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="actualOwnerRequired" type="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tBoolean" default="yes" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tLeanTask")
public class TLeanTask
    extends TTaskBase
{


}
