package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;

public class TNotificationComposite extends AbstractNonReptitiveComposite {

	public TNotificationComposite(XMLEditor editor, Composite parent,
			int index, int style) {
		super(editor, parent, index, style,"notification group");
		HTCompositeUtil htCompositeUtil=new HTCompositeUtil();
		this.setBounds(htCompositeUtil.getRectangle(index, 20,110, parent.getParent().getSize().x, 100, 10));    //remove
	}
	
	@Override
	public void initialize(int compositeIndex,XMLEditor textEditor){
		
		ArrayList<TLogicalPeopleGroup> logicalPeopleGroups=new ArrayList<TLogicalPeopleGroup>();
		if(textEditor.getRootElement().getLogicalPeopleGroups()!= null){
			logicalPeopleGroups=(ArrayList<TLogicalPeopleGroup>) textEditor.getRootElement().getLogicalPeopleGroups().getLogicalPeopleGroup();      ////Check
			
			if(logicalPeopleGroups.size() > compositeIndex && !logicalPeopleGroups.get(compositeIndex).getName().isEmpty()){
				System.out.println("i value is "+logicalPeopleGroups.get(compositeIndex).getName());
				textBoxes.get(0).setText(logicalPeopleGroups.get(compositeIndex).getName());
				textBoxes.get(1).setText(logicalPeopleGroups.get(compositeIndex).getReference());
			}
		}
		
	}

	@Override
	public void btnUpdateHandleLogic(int compositeIndex,XMLEditor textEditor) {
		TLogicalPeopleGroup tLogicalPeopleGroup ;
			
		if((textEditor.getRootElement().getLogicalPeopleGroups().getLogicalPeopleGroup().size()<(compositeIndex+1))){
			tLogicalPeopleGroup=new TLogicalPeopleGroup();
			tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
			tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());	
			textEditor.getRootElement().getLogicalPeopleGroups().getLogicalPeopleGroup().add(compositeIndex,tLogicalPeopleGroup);
		}else{
			tLogicalPeopleGroup=textEditor.getRootElement().getLogicalPeopleGroups().getLogicalPeopleGroup().get(compositeIndex);
			tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
			tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());
			//int index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
		}
		try {
			//centralUtils.marshalMe(textEditor);
			centralUtils.testMarshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void fillDetailArea() {
		Label lblName = toolkit.createLabel(detailArea, "Name",
				SWT.NONE);
		lblName.setBounds(20, 23, 100, 15);
		Text txtName = toolkit.createText(detailArea, "", SWT.NONE);
		txtName.setBounds(152, 23, 100, 21);
		textBoxes.add(0,txtName);
		Label lblReference = toolkit.createLabel(detailArea, "Reference",
				SWT.NONE);
		lblReference.setBounds(252, 23, 100, 15);
		Text txtReference = toolkit.createText(detailArea, "", SWT.NONE);
		txtReference.setBounds(384, 23, 100, 21);
		textBoxes.add(1,txtReference);
	}
}
