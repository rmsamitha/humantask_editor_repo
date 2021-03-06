//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.22 at 06:17:49 PM IST 
//


package org.wso2.developerstudio.humantask.models;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tPeopleAssignments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tPeopleAssignments">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;group ref="{http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803}genericHumanRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPeopleAssignments", propOrder = {
    "genericHumanRole"
})
public class TPeopleAssignments
    extends TExtensibleElements
{

    @XmlElementRefs({
        @XmlElementRef(name = "taskInitiator", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class),
        @XmlElementRef(name = "taskStakeholders", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class),
        @XmlElementRef(name = "potentialOwners", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class),
        @XmlElementRef(name = "businessAdministrators", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class),
        @XmlElementRef(name = "recipients", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class),
        @XmlElementRef(name = "excludedOwners", namespace = "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803", type = JAXBElement.class)
    })
    protected List<JAXBElement<? extends TGenericHumanRoleAssignmentBase>> genericHumanRole;

    /**
     * Gets the value of the genericHumanRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericHumanRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenericHumanRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link TGenericHumanRoleAssignment }{@code >}
     * {@link JAXBElement }{@code <}{@link TGenericHumanRoleAssignment }{@code >}
     * {@link JAXBElement }{@code <}{@link TPotentialOwnerAssignment }{@code >}
     * {@link JAXBElement }{@code <}{@link TGenericHumanRoleAssignment }{@code >}
     * {@link JAXBElement }{@code <}{@link TGenericHumanRoleAssignment }{@code >}
     * {@link JAXBElement }{@code <}{@link TGenericHumanRoleAssignment }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends TGenericHumanRoleAssignmentBase>> getGenericHumanRole() {
        if (genericHumanRole == null) {
            genericHumanRole = new ArrayList<JAXBElement<? extends TGenericHumanRoleAssignmentBase>>();
        }
        return this.genericHumanRole;
    }

}
