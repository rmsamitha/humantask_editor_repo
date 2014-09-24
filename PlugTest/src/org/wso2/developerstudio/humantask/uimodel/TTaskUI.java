package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TBoolean;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TTaskUI extends AbstractParentTagSection {
	int [] childObjectIndexes;
	private TTask task;   // Change the type of model object
	int objectIndex; //this is this elements object index
	int compositeIndex; //this is this elements composite index
	int childCompositeIndex;
	Composite container;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	
	
	public TTaskUI(XMLEditor editor,Composite parent,Composite container,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent, style,new String[] {"Documentation", "Interface", "Priority", "People Assignments", "Delegation","Presentation Elements"});
		//TTasks tasks=(TTasks)modelParent;
		System.out.println(objectIndex);
		this.task=(TTask)modelParent;
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		this.container=container;
		childObjectIndexes = new int[6];
	}

	
	
	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor)
			throws JAXBException {
		task.setName(((Text)textBoxes.get(0)).getText());
		task.setActualOwnerRequired(TBoolean.fromValue(((Combo)textBoxes.get(1)).getText()));
		try {
			centralUtils.marshalMe(textEditor);
			} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException {
		TTasksUI tTasksUI=(TTasksUI)container;
		tTasksUI.refreshChildren(compositeIndex, objectIndex);
		
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo=this.getParent();
		this.dispose();
		//tempCompo.layout(true,true);

	}


	@Override
	public void newButtonLogic(String selection,ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Documentation")) {
			if (editor.getRootElement().getTasks().getTask().get(objectIndex) == null) {
				TTask tTask = new TTask();
				editor.getRootElement().getTasks().getTask().set(objectIndex, tTask);
			}
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String("This is the value"));
			editor.getRootElement().getTasks().getTask().get(objectIndex).getDocumentation().add(childObjectIndexes[0],tDocumentation);
			TDocumentationUI tNot = new TDocumentationUI(editor,composite,childObjectIndexes[0],childCompositeIndex,SWT.NONE,this,tDocumentation);
			childComposites.add(i, tNot);
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			//centralUtils.addInstance(tTask);
			childObjectIndexes[0]++;
			
		}
		
		
		
		//sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		childCompositeIndex++;

	}

	@Override
	public void fillDetailArea(Composite composite) {
		Label lblName = toolkit.createLabel(composite, "Lang", SWT.NONE);
		lblName.setBounds(20, 23, 100, 15);
		Text txtName = toolkit.createText(composite, "", SWT.NONE);
		txtName.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtName);
		Label lblReference = toolkit.createLabel(composite, "Attributesomething",
				SWT.NONE);
		lblReference.setBounds(252, 23, 100, 15);
		 Combo combo = new Combo(composite, SWT.NONE);
	     combo.setItems(new String [] {"yes","no"});
	     combo.select(0);
	     combo.setBounds(384, 23, 100, 21);
		textBoxes.add(1, combo);
	}



	@Override
	public void initialize(XMLEditor textEditor)
			throws JAXBException {
		((Text)textBoxes.get(0)).setText(task.getName());
		
	}



	@Override
	public void refreshLogic(XMLEditor editor, Composite composite,
			ScrolledComposite sc3) throws JAXBException {
		
		
		ArrayList<TDocumentation> groups = new ArrayList<TDocumentation>();
		if (editor.getRootElement().getTasks().getTask() != null) {
			groups = (ArrayList<TDocumentation>) editor.getRootElement().getTasks().getTask().get(objectIndex).getDocumentation();
			
		for (int i = 0; i < groups.size(); i++) {
			TDocumentationUI tNot;
			if ((childComposites.size() == groups.size())) {
				try {
				tNot = (TDocumentationUI) childComposites.get(i);
				tNot.initialize(editor);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			} else {
				try {
					tNot = new TDocumentationUI(editor,composite,childObjectIndexes[0],childCompositeIndex,SWT.NONE,this,groups.get(childObjectIndexes[0]));
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					compositeIndex++;
					childObjectIndexes[0]++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				
			}
			//tNot.updated = true;
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			//sc3.layout(true, true);
			//innerSection.layout(true, true);
			
		}
	}
	}


	@Override
	public void refreshChildren(int childCompositeIndex, int childObjectIndex) {
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		task.getDocumentation().remove(childObjectIndex);
		for (Composite c : childComposites) {
			TDocumentationUI d = (TDocumentationUI) c;  //children node type
			if (d.compositeIndex > childCompositeIndex) {
				d.compositeIndex--;
			}
			if (d.objectIndex >= childObjectIndex) {
				d.objectIndex--;
			}

		}
		
	}

}
