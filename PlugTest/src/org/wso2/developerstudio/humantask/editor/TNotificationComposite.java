package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;

public class TNotificationComposite extends AbstractChildTagComposite {
	Composite container;
	public int objectIndex;
	protected int compositeIndex;
	TLogicalPeopleGroup logicalPeopleGroup;
	

	public TNotificationComposite(XMLEditor editor, Composite parent,
			int compositeIndex, int objectIndex, int style,
			Composite container, Object modelParent) {
		super(editor, parent, style, "notification group");
		this.objectIndex = objectIndex;
		logicalPeopleGroup = (TLogicalPeopleGroup) modelParent;
		this.container = container;
		this.compositeIndex =compositeIndex;

		// HTCompositeUtil htCompositeUtil=new HTCompositeUtil();
		// this.setBounds(htCompositeUtil.getRectangle(objectIndex, 20,110,
		// parent.getParent().getSize().x, 100, 10)); //remove
	}

	@Override
	public void initialize(XMLEditor textEditor) {

		// if(logicalPeopleGroup.size()+1 >= (objectIndex) &&
		// !logicalPeopleGroup.getName().isEmpty()){
		System.out.println("i value is " + logicalPeopleGroup.getName());
		textBoxes.get(0).setText(logicalPeopleGroup.getName());
		textBoxes.get(1).setText(logicalPeopleGroup.getReference());
		// }

	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) {
		TLogicalPeopleGroup tLogicalPeopleGroup;
		// System.out.println("a value :"+ logicalPeopleGroup.size());
		/*
		 * if((logicalPeopleGroups.size()<(compositeIndex+1))){
		 * 
		 * tLogicalPeopleGroup=new TLogicalPeopleGroup();
		 * tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
		 * tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());
		 * logicalPeopleGroups.add(compositeIndex,tLogicalPeopleGroup); }else{
		 */
		
		tLogicalPeopleGroup = logicalPeopleGroup;
		tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
		tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());
		// int
		// index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
		// }
		try {
			centralUtils.marshalMe(textEditor);
			
			// centralUtils.testMarshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fillDetailArea() {
		Label lblName = toolkit.createLabel(detailArea, "Name", SWT.NONE);
		lblName.setBounds(20, 23, 100, 15);
		Text txtName = toolkit.createText(detailArea, "", SWT.NONE);
		txtName.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtName);
		Label lblReference = toolkit.createLabel(detailArea, "Reference",
				SWT.NONE);
		lblReference.setBounds(252, 23, 100, 15);
		Text txtReference = toolkit.createText(detailArea, "", SWT.NONE);
		txtReference.setBounds(384, 23, 100, 21);
		textBoxes.add(1, txtReference);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException {
		TNotificationParentComposite parentContainer = (TNotificationParentComposite) container;
		parentContainer.refreshChildren(compositeIndex, objectIndex);
		System.out.println("Object Index"+objectIndex);
		textEditor.getRootElement().getLogicalPeopleGroups()
				.getLogicalPeopleGroup().remove(objectIndex);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);
	}
}
