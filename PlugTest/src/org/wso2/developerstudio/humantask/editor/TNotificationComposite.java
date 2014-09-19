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
	ArrayList<Composite> composites;
	Composite parent,container;
	boolean updated;
	ScrolledComposite sc3;
	public int objectIndex;
	TLogicalPeopleGroup logicalPeopleGroup;

	public TNotificationComposite(ScrolledComposite sc3,XMLEditor editor, Composite parent,ArrayList<Composite> composites,
			int compositeIndex,int objectIndex, int style,Composite container,Object modelParent) {
		super(editor, parent,compositeIndex, style,"notification group");
		this.objectIndex = objectIndex;
		this.composites=composites;
		this.parent= parent;
		
		logicalPeopleGroup=(TLogicalPeopleGroup)modelParent;;
		for(int p=0;p<composites.size();p++){
			System.out.println("This is is :"+((TNotificationComposite)composites.get(p)).objectIndex);
		}
		System.out.println("Obj index :"+objectIndex);
		System.out.println("Comp index :"+compositeIndex);
		this.container=container;
		this.sc3=sc3;
		//HTCompositeUtil htCompositeUtil=new HTCompositeUtil();
		//this.setBounds(htCompositeUtil.getRectangle(objectIndex, 20,110, parent.getParent().getSize().x, 100, 10));    //remove
	}
	
	@Override
	public void initialize(int objectIndex,XMLEditor textEditor){
		
			//if(logicalPeopleGroup.size()+1 >= (objectIndex) && !logicalPeopleGroup.getName().isEmpty()){
				System.out.println("i value is "+logicalPeopleGroup.getName());
				textBoxes.get(0).setText(logicalPeopleGroup.getName());
				textBoxes.get(1).setText(logicalPeopleGroup.getReference());
			//}
		
	}

	@Override
	public void btnUpdateHandleLogic(int objectIndex,XMLEditor textEditor) {
		TLogicalPeopleGroup tLogicalPeopleGroup ;
		//System.out.println("a value :"+ logicalPeopleGroup.size());	
	/*	if((logicalPeopleGroups.size()<(compositeIndex+1))){
			
			tLogicalPeopleGroup=new TLogicalPeopleGroup();
			tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
			tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());	
			logicalPeopleGroups.add(compositeIndex,tLogicalPeopleGroup);
		}else{*/
			System.out.println("a value for :"+ objectIndex+1);
			tLogicalPeopleGroup=logicalPeopleGroup;
			tLogicalPeopleGroup.setName(textBoxes.get(0).getText());
			tLogicalPeopleGroup.setReference(textBoxes.get(1).getText());
			//int index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
		//}
		try {
			centralUtils.marshalMe(textEditor);
			updated=true;
			//centralUtils.testMarshalMe(textEditor);
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

	@Override
	public void btnRemoveHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		//if(updated){
		System.out.print("Composite : object :"+compositeIndex+" - "+objectIndex);
		composites.remove(compositeIndex);
		TNotificationParentComposite parentContainer = (TNotificationParentComposite) container; 
		System.out.print("[parent composite index :"+parentContainer.compositeIndex);
		parentContainer.compositeIndex--;
		parentContainer.objectIndexes[0]--;
		System.out.println(parentContainer.objectIndexes[0]+" : "+objectIndex);
		System.out.print("[parent composite index :"+parentContainer.compositeIndex);
		System.out.print("Composite : object :"+compositeIndex+" - "+objectIndex);
		textEditor.getRootElement().getLogicalPeopleGroups().getLogicalPeopleGroup().remove(objectIndex);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		for(Composite c:composites){
			TNotificationComposite d = (TNotificationComposite) c;
			if(d.compositeIndex>compositeIndex){
				d.compositeIndex--;
			}
			System.out.println(d.objectIndex);
			if(d.objectIndex>=objectIndex){
				d.objectIndex--;
				System.out.println(d.objectIndex);
			}
			
		}
		//}
		//index--;
		
		Composite tempCompo=this.getParent();
		this.dispose();
		sc3.layout(true,true);
		tempCompo.layout(true,true);
	}
}
