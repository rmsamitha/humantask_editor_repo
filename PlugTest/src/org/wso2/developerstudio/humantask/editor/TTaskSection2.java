package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.uimodel.TTaskUI;

public class TTaskSection2 extends AbstractParentTagSection {
	int [] childObjectIndexes;
	private TTasks tasks;   // Change the type of model object
	private int objectIndex;
	private int compositeIndex;
	int childCompositeIndex;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	int depth;
	private XMLEditor editor;

	public TTaskSection2(XMLEditor editor,Composite parent,
			int style,Object modelParent,int objectIndex,int compositeIndex,int depth) throws JAXBException {
		super(editor, parent, style, new String [] {""},depth);
		setText("Task");
		setExpanded(true);
		this.tasks=(TTasks)modelParent; // change the model object
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		childObjectIndexes = new int[1]; 
		this.editor=editor;
		this.depth=depth;
	}

	@Override
	public void refreshLogic(XMLEditor editor,
			Composite composite, ScrolledComposite sc3) throws JAXBException {
		int j = 0;
		try {
			centralUtils.unmarshalMe(editor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		ArrayList<TTask> groups = new ArrayList<TTask>();
		if (editor.getRootElement().getLogicalPeopleGroups() != null) {
			groups = (ArrayList<TTask>) editor.getRootElement()
					.getTasks().getTask();
		}

		for (int i = 0; i < groups.size(); i++) {
			TTaskInterfaceSection tNot;
			if ((childComposites.size() == groups.size())) {
				tNot = (TTaskInterfaceSection) childComposites.get(j);
				tNot.initialize( editor);
			} else {
				tNot = new TTaskInterfaceSection(editor, composite, j,SWT.NONE,1);
				tNot.initialize(editor);
				childComposites.add(j, tNot);

			}
			tNot.updated = true;
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			//sc3.layout(true, true);
			//innerSection.layout(true, true);
			j++;
			System.out.println("j  value" + j);
		}
		compositeIndex = j;
		System.out.println("refresh l value is " + compositeIndex);
	}


	@Override
	public void fillDetailArea(final Composite composite) {
		Label lblNewLabel = toolkit.createLabel(composite,
				"Label in TTask Section", SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);

		Button btnAddLPG = new Button(composite, SWT.NONE);
		btnAddLPG.setText("plus");
		//btnNewgroup
		//btnNewgroup.setText("yyyy");
		//System.out.println(btnNewgroup.isDisposed());
		btnAddLPG.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Section scc2;
				try {
					scc2 = new TTaskSection2(editor, composite, Section.TWISTIE
							| Section.TITLE_BAR, tasks,objectIndex, compositeIndex,depth);
					scc2.setExpanded(false);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	/*	

		TTaskInterfaceSection TISx = new TTaskInterfaceSection(editor,
				composite, 2, Section.TWISTIE | Section.TITLE_BAR);
		TISx.setExpanded(false);
		//System.out.println("created a Task Interface section");

		Section sc3 = new TTaskInterfaceSection(editor, composite, 1,
				Section.TWISTIE | Section.TITLE_BAR);*/
		//sc3.setExpanded(false);
		// sc3.setText("hooyee");
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			final XMLEditor editor, final Composite parent) throws JAXBException {
	
				System.out.println("in the Task class newButtonLogic class");
				Section scc2;
//				try {
//					scc2 = new TTaskSection(editor, parent, Section.TWISTIE
//							| Section.TITLE_BAR,tasks,objectIndex,compositeIndex,depth);
//					//scc2=new TTaskUI(editor, parent,parent, objectIndex, tasks,childCompositeIndex,9);
//
//					scc2.setExpanded(false);
//					System.out.println("Created Task section in a Task. depth now is;"+depth);
//				} catch (JAXBException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				depth++;

	
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshChildren(int childCompositeIndex, int childObjectIndex) {
		// TODO Auto-generated method stub
		
	}
}
