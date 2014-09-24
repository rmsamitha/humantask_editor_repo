package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TBoolean;

public class TTasksUI extends AbstractParentTagSection {
	
	int [] childObjectIndexes;
	public TTasks tasks;   // Change the type of model object
	private int objectIndex;
	private int compositeIndex;
	int childCompositeIndex;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TTasksUI(XMLEditor editor,Composite parent,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent, style,new String[] {"Task"}); //change the list of items in drop down list
		this.tasks=(TTasks)modelParent; // change the model object
		System.out.println("Array size :"+(editor.getRootElement().getTasks().equals(tasks)));
		System.out.println("Array size p:"+editor.getRootElement().getTasks().equals(modelParent));
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		childObjectIndexes = new int[1]; //change the number of items in dropdown menu
	} 

	@Override
	public void btnUpdateHandleLogic( XMLEditor textEditor)
			throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic( XMLEditor textEditor)
			throws JAXBException {
		textEditor.getRootElement().setTasks(null);
		refreshChildren(compositeIndex, objectIndex);
		
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
	public void refreshLogic(XMLEditor editor,
			Composite composite, ScrolledComposite sc3) throws JAXBException  {
		System.out.println("Array size last:"+(editor.getRootElement().getTasks().equals(this.tasks)));
		
		/////////////////////////////////////////// This is Item a //////////////////////////////////
		ArrayList<TTask> groups = new ArrayList<TTask>();
		if (tasks != null) {
			groups = (ArrayList<TTask>) tasks.getTask();
			
		for (int i = 0; i < groups.size(); i++) {
			TTaskUI tNot;
			if ((childComposites.size() == groups.size())) {
				try {
				tNot = (TTaskUI) childComposites.get(i);
				tNot.initialize(editor);
				} catch (JAXBException e) {
				
					e.printStackTrace();
				}
			} else {
				try {
					tNot = new TTaskUI(editor,composite,this,SWT.NONE,groups.get(childObjectIndexes[0]),childObjectIndexes[0],childCompositeIndex);
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
		////////////////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("refresh l value is " + compositeIndex);
		}

	}

	@Override
	public void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Task")) {
			/*if (editor.getRootElement().getTasks() == null) {
				TTasks tTasks = new TTasks();
				editor.getRootElement().setTasks(
						tTasks);
			}*/
			TTask tTask = new TTask();
			tTask.setName("");
			tTask.setActualOwnerRequired(TBoolean.YES);
			tasks.getTask().add(childObjectIndexes[0], tTask);
			TTaskUI tNot = new TTaskUI(editor,composite,this,SWT.NONE,tTask,childObjectIndexes[0],childCompositeIndex);
			childComposites.add(childCompositeIndex, tNot);
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			centralUtils.addInstance(tTask);
			childObjectIndexes[0]++;
			System.out.println("Array size last:"+(editor.getRootElement().getTasks().equals(tasks)));
			
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
	

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		
	}

	@Override
	public void refreshChildren(int childCompositeIndex, int childObjectIndex) {
		childComposites.remove(childCompositeIndex);
		tasks.getTask().remove(objectIndex);
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		for (Composite c : childComposites) {
			TTaskUI d = (TTaskUI) c;  //children node type
			if (d.compositeIndex > childCompositeIndex) {
				d.compositeIndex--;
			}
			if (d.objectIndex >= childObjectIndex) {
				d.objectIndex--;
			}

		}
		
	}

	

}
