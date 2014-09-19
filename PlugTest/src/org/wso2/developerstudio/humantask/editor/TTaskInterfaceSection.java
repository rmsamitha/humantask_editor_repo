package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotification;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotificationInterface;

public class TTaskInterfaceSection extends AbstractNonReptitiveSection {

	public TTaskInterfaceSection(XMLEditor editor, Composite parent, int index,
			int style) {

		super(editor, parent, index, style, "Task Interface");
		setText("SectionInterface");
		//setSize(600, 80);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void btnUpdateHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {

		/*
		 * TNotificationInterface tNotificationInterface ; //new
		 * TNotificationInterface().set
		 * if((textEditor.getRootElement().getNotifications
		 * ().getNotification().size()<(compositeIndex+1))){
		 * tNotificationInterface=new TNotificationInterface();
		 * tNotificationInterface.setOperation(textBoxes.get(0).getText());
		 * //tNotificationInterface.setReference(textBoxes.get(1).getText());
		 * textEditor
		 * .getRootElement().getNotifications().getNotification().get(compositeIndex
		 * ,) textEditor.getRootElement().getLogicalPeopleGroups().
		 * getLogicalPeopleGroup().add(compositeIndex,tLogicalPeopleGroup);
		 * }else{
		 * tLogicalPeopleGroup=textEditor.getRootElement().getLogicalPeopleGroups
		 * ().getLogicalPeopleGroup().get(compositeIndex);
		 * tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
		 * tLogicalPeopleGroup.setReference(textBoxes.get(1).getText()); //int
		 * index
		 * =ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup
		 * ().indexOf(tl); } try { //centralUtils.marshalMe(textEditor);
		 * centralUtils.testMarshalMe(textEditor); } catch (JAXBException e) {
		 * e.printStackTrace(); }
		 */

	}

	@Override
	public void initialize(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		// TODO Auto-generated method stub
		/*
		 * ArrayList<TLogicalPeopleGroup> logicalPeopleGroups=new
		 * ArrayList<TLogicalPeopleGroup>();
		 * if(textEditor.getRootElement().getLogicalPeopleGroups()!= null){
		 * logicalPeopleGroups=(ArrayList<TLogicalPeopleGroup>)
		 * textEditor.getRootElement
		 * ().getLogicalPeopleGroups().getLogicalPeopleGroup(); ////Check
		 * 
		 * if(logicalPeopleGroups.size() > compositeIndex &&
		 * !logicalPeopleGroups.get(compositeIndex).getName().isEmpty()){
		 * System.
		 * out.println("i value is "+logicalPeopleGroups.get(compositeIndex
		 * ).getName());
		 * textBoxes.get(0).setText(logicalPeopleGroups.get(compositeIndex
		 * ).getName());
		 * textBoxes.get(1).setText(logicalPeopleGroups.get(compositeIndex
		 * ).getReference()); } }
		 */

	}

	@Override
	public void fillDetailArea() {
	/*	Label lblPortType = toolkit.createLabel(composite_SecTASK_SC , "PortType",
				SWT.NONE);
		lblPortType.setBounds(20, 23, 100, 15);
		Text txtPortType = toolkit.createText(composite_SecTASK_SC, "", SWT.NONE);
		try {
			txtPortType.setBounds(152, 23, 100, 21);

			//textBoxes.add(0, txtPortType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Label lblOperation = toolkit.createLabel(composite_SecTASK_SC, "Operation",
				SWT.NONE);
		lblOperation.setBounds(252, 23, 100, 15);
		Text txtOperation = toolkit.createText(composite_SecTASK_SC, "", SWT.NONE);
		txtOperation.setBounds(384, 23, 100, 21);*/
		//textBoxes.add(1, txtOperation);
	}

	@Override
	public void btnRemoveHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		// TODO Auto-generated method stub
		
	}

}
