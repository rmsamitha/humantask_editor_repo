package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroups;

public class TNotificationParentComposite extends AbstractParentTagComposite {
	
	int [] objectIndexes;
	TLogicalPeopleGroups logicalPeopleGroups;
	public TNotificationParentComposite(XMLEditor editor, Composite parent,
			int style,Object modelParent) throws JAXBException {
		super(editor, parent, style,new String[] {"a", "b", "c", "d", "e"});
		this.logicalPeopleGroups=(TLogicalPeopleGroups)modelParent;
		objectIndexes = new int[5];
		
	}

	@Override
	public void refreshLogic(XMLEditor editor, ArrayList<Composite> composites,
			Composite composite, ScrolledComposite sc3) {
		int j = 0;
		try {
			centralUtils.unmarshalMe(editor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		/////////////////////////////////////////// This is Item a //////////////////////////////////
		ArrayList<TLogicalPeopleGroup> groups = new ArrayList<TLogicalPeopleGroup>();
		if (editor.getRootElement().getLogicalPeopleGroups() != null) {
			groups = (ArrayList<TLogicalPeopleGroup>) editor.getRootElement()
					.getLogicalPeopleGroups().getLogicalPeopleGroup();
		
		
		for (int i = 0; i < groups.size(); i++) {
			TNotificationComposite tNot;
			if ((composites.size() == groups.size())) {
				tNot = (TNotificationComposite) composites.get(j);
				tNot.initialize(j, editor);
			} else {
				tNot = new TNotificationComposite(sc3,editor, composite,
						composites, j,objectIndexes[0], SWT.NONE, this,groups.get(i));
				tNot.initialize(j, editor);
				composites.add(j, tNot);
				
			}
			tNot.updated = true;
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			sc3.layout(true, true);
			innerSection.layout(true, true);
			j++;
			System.out.println("j  value" + j);
			objectIndexes[0]++;
		}
		////////////////////////////////////////////////////////////////////////////////////////////
		compositeIndex = j;
		System.out.println("refresh l value is " + compositeIndex);
		}
	}

	@Override
	public void newButtonLogic(String selection,int i, ArrayList<Composite> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException {
	
		if (selection.equalsIgnoreCase("a")) {
			System.out.println("new button l value is " + i);
			if (editor.getRootElement().getLogicalPeopleGroups() == null) {
				TLogicalPeopleGroups tLogicalPeopleGroups = new TLogicalPeopleGroups();
				editor.getRootElement().setLogicalPeopleGroups(
						tLogicalPeopleGroups);
				
			}
			
			System.out.println("objectIndexes[0] :"+objectIndexes[0]);
			
			
			TLogicalPeopleGroup tLogicalPeopleGroup = new TLogicalPeopleGroup();
			tLogicalPeopleGroup.setName("");
			tLogicalPeopleGroup.setReference("");
			editor.getRootElement().getLogicalPeopleGroups()
					.getLogicalPeopleGroup().add(i, tLogicalPeopleGroup);
			TNotificationComposite tNot = new TNotificationComposite(sc3,editor,
					composite, composites, i,objectIndexes[0], SWT.NONE, this,tLogicalPeopleGroup);
			composites.add(i, tNot);
			sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			centralUtils.addInstance(tLogicalPeopleGroup);
			objectIndexes[0]++;
			
		}else if(selection.equalsIgnoreCase("b")){
			System.out.println("new button l value is " + i);
			if (editor.getRootElement().getLogicalPeopleGroups() == null) {
				TLogicalPeopleGroups tLogicalPeopleGroups = new TLogicalPeopleGroups();
				editor.getRootElement().setLogicalPeopleGroups(
						tLogicalPeopleGroups);
			}
			
			
			TLogicalPeopleGroup tLogicalPeopleGroup = new TLogicalPeopleGroup();
			tLogicalPeopleGroup.setName("");
			tLogicalPeopleGroup.setReference("");
			editor.getRootElement().getLogicalPeopleGroups()
					.getLogicalPeopleGroup().add(i, tLogicalPeopleGroup);
			TNotificationComposite tNot = new TNotificationComposite(sc3,editor,
					composite, composites, i,objectIndexes[1], SWT.NONE, this,tLogicalPeopleGroup);
			composites.add(i, tNot);
			sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			centralUtils.addInstance(tLogicalPeopleGroup);
			objectIndexes[1]++;
		}
		sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fillDetailArea(Composite composite) {
		Label lblNewLabel = toolkit.createLabel(composite, "New Label",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);
		txtNewText.setBounds(102, 23, 61, 21);
		textBoxes.add(txtNewText);
	}

}
