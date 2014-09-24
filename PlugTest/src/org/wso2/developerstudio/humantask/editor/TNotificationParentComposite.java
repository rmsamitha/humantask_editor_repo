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
	int [] childObjectIndexes;
	private TLogicalPeopleGroups logicalPeopleGroups;
	private int objectIndex;
	private int compositeIndex;
	int childCompositeIndex;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TNotificationParentComposite(XMLEditor editor,Composite parent,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent, style,new String[] {"a", "b", "c", "d", "e"});
		this.logicalPeopleGroups=(TLogicalPeopleGroups)modelParent;
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		childObjectIndexes = new int[5];
	}

	@Override
	public void refreshLogic(XMLEditor editor,
			Composite composite, ScrolledComposite sc3) {
		
		
		/////////////////////////////////////////// This is Item a //////////////////////////////////
		ArrayList<TLogicalPeopleGroup> groups = new ArrayList<TLogicalPeopleGroup>();
		if (editor.getRootElement().getLogicalPeopleGroups() != null) {
			groups = (ArrayList<TLogicalPeopleGroup>) editor.getRootElement()
					.getLogicalPeopleGroups().getLogicalPeopleGroup();
		
		
		for (int i = 0; i < groups.size(); i++) {
			TNotificationComposite tNot;
			if ((childComposites.size() == groups.size())) {
				tNot = (TNotificationComposite) childComposites.get(i);
				tNot.initialize(editor);
			} else {
				tNot = new TNotificationComposite(editor, composite, childCompositeIndex,childObjectIndexes[0], SWT.NONE, this,groups.get(i));
				tNot.initialize(editor);
				childComposites.add(childCompositeIndex, tNot);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}
		//	tNot.updated = true;
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			sc3.layout(true, true);
			//innerSection.layout(true, true);
			
			System.out.println("j  value" + childCompositeIndex);
			
		}
		////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Agin value" + childObjectIndexes[0]);
		}
	}

	@Override
	public void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException {
	
		if (selection.equalsIgnoreCase("a")) {
			System.out.println("new button l value is " + i);
			if (editor.getRootElement().getLogicalPeopleGroups() == null) {
				TLogicalPeopleGroups tLogicalPeopleGroups = new TLogicalPeopleGroups();
				editor.getRootElement().setLogicalPeopleGroups(
						tLogicalPeopleGroups);
				
			}
			
			System.out.println("objectIndexes[0] :"+childObjectIndexes[0]);
			
			
			TLogicalPeopleGroup tLogicalPeopleGroup = new TLogicalPeopleGroup();
			tLogicalPeopleGroup.setName("");
			tLogicalPeopleGroup.setReference("");
			editor.getRootElement().getLogicalPeopleGroups()
					.getLogicalPeopleGroup().add(childObjectIndexes[0], tLogicalPeopleGroup);
			TNotificationComposite tNot = new TNotificationComposite(editor,
					composite, childCompositeIndex,childObjectIndexes[0], SWT.NONE, this,tLogicalPeopleGroup);
			childComposites.add(childCompositeIndex, tNot);
			sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			centralUtils.addInstance(tLogicalPeopleGroup);
			childObjectIndexes[0]++;
			
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
			TNotificationComposite tNot = new TNotificationComposite(editor,
					composite, i,childObjectIndexes[1], SWT.NONE, this,tLogicalPeopleGroup);
			childComposites.add(i, tNot);
			sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			centralUtils.addInstance(tLogicalPeopleGroup);
			childObjectIndexes[1]++;
		}
		sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		childCompositeIndex++;
	}

	@Override
	public void fillDetailArea(Composite composite) {
		/*Label lblNewLabel = toolkit.createLabel(composite, "New Label",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);
		txtNewText.setBounds(102, 23, 61, 21);
		textBoxes.add(txtNewText);*/
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor)
			throws JAXBException {
		
		
		//int index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
	//}
	try {
		centralUtils.marshalMe(textEditor);
		
		//centralUtils.testMarshalMe(textEditor);
	} catch (JAXBException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException {
		//composites.remove(compositeIndex);
	
		refreshChildren(compositeIndex, objectIndex);
		
		textEditor.getRootElement().setLogicalPeopleGroups(null);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		//}
		//index--;
		
		Composite tempCompo=this.getParent();
		this.dispose();
		//parentSc.layout(true,true);
		tempCompo.layout(true,true);
		
	}

	@Override
	public void initialize(XMLEditor textEditor)
			throws JAXBException {
		
		
	}
	
	@Override
	public void refreshChildren(int childCompositeIndex,int childObjectIndex){
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		for (Composite c : childComposites) {
			TNotificationComposite d = (TNotificationComposite) c;  //children node type
			if (d.compositeIndex > childCompositeIndex) {
				d.compositeIndex--;
			}
			if (d.objectIndex >= childObjectIndex) {
				d.objectIndex--;
			}

		}
		
	}



}
