package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotification;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotifications;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.BaseView;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TBoolean;

public class TNotificationsUI extends AbstractParentTagSection {
	
	int [] childObjectIndexes;
	public TNotifications  notifications;   // Change the type of model object
	private int objectIndex;
	private int compositeIndex;
	int childCompositeIndex;
	protected Composite container;
	XMLEditor editor;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TNotificationsUI(XMLEditor editor,Composite parent,Composite container,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent,container,style,new String[] {"Notification"},"Notifications"); //change the list of items in drop down list
		this.notifications=(TNotifications)modelParent; // change the model object
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		this.container=container;
		this.editor=editor;
		childObjectIndexes = new int[1];
		setExpanded(true);
	} 

	@Override
	public void btnUpdateHandleLogic( XMLEditor textEditor)
			throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic( XMLEditor textEditor)
			throws JAXBException {
		BaseView baseView=(BaseView)container;
		baseView.refreshChildren(compositeIndex, objectIndex);
		
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo=this.getParent();
		this.dispose();
		tempCompo.layout(true,true);

	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException  {
		/////////////////////////////////////////// This is Item a //////////////////////////////////
	//	System.out.println("THis is TTAsks");
		ArrayList<TNotification> groups = new ArrayList<TNotification>();
		if (notifications != null) {
			groups = (ArrayList<TNotification>) notifications.getNotification();
			
		for (int i = 0; i < groups.size(); i++) {
			TNotificationUI tNot;
			if ((childComposites.size() == groups.size())) {
				try {
				tNot = (TNotificationUI) childComposites.get(i);
				tNot.initialize(editor);
				} catch (JAXBException e) {
				
					e.printStackTrace();
				}
			} else {
				try {
					tNot = new TNotificationUI(editor,compositeDetailArea,this,SWT.NONE,groups.get(childObjectIndexes[0]),childObjectIndexes[0],childCompositeIndex);
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
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
		
		System.out.println("refresh l value is " + childCompositeIndex);
		}

	}

	@Override
	public void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Notification")) {
			/*if (editor.getRootElement().getTasks() == null) {
				TTasks tTasks = new TTasks();
				editor.getRootElement().setTasks(
						tTasks);
			}*/
			System.out.println("Child Index new :"+childCompositeIndex);
			TNotification tNotification = new TNotification();
			tNotification.setName("");
			
			notifications.getNotification().add(childObjectIndexes[0], tNotification);
			TNotificationUI tNot = new TNotificationUI(editor,composite,this,SWT.NONE,tNotification,childObjectIndexes[0],childCompositeIndex);
			childComposites.add(childCompositeIndex, tNot);
			//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			centralUtils.addInstance(tNotification);
			childObjectIndexes[0]++;
			System.out.println("Array size last:"+(editor.getRootElement().getNotifications().equals(notifications)));
			childCompositeIndex++;
		}
		//sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		

	}

	@Override
	public void fillDetailArea(Composite composite) {
	
		//compositeDetailArea.setLayout(new GridLayout(1, false));
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		
	}

	@Override
	public void refreshChildren(String itemName,int childCompositeIndex, int childObjectIndex) {
		System.out.println("Child Index :"+childCompositeIndex);
		childComposites.remove(childCompositeIndex);
		notifications.getNotification().remove(objectIndex);
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		for (Composite c : childComposites) {
			TNotificationUI d = (TNotificationUI) c;  //children node type
			if (d.compositeIndex > childCompositeIndex) {
				d.compositeIndex--;
			}
			if (d.objectIndex >= childObjectIndex) {
				d.objectIndex--;
			}
		}
		
	}
	public void loadModel(Object model) throws JAXBException{
		notifications = (TNotifications) model;
		System.out.println(childComposites.size());
		for (Composite c : childComposites) {
			System.out.println("This is the testing");
			TNotificationUI d = (TNotificationUI) c;  //children node type
			d.refreshLogic(editor);
			d.loadModel(notifications.getNotification().get(d.objectIndex));
			
			this.layout();
		}
	}
	
	

}
