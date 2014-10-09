package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.Transition;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TNotification;
import org.wso2.developerstudio.humantask.models.TNotifications;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTasks;


public class TNotificationsUI extends AbstractParentTagSection {
	
	private int[] childObjectIndexes;
	public TNotifications notifications;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TNotificationsUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object modelParent,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.NOTIFICATION_TITLE },
				HTEditorConstants.NOTIFICATIONS_TITLE); //change the list of items in drop down list
		this.notifications=(TNotifications)modelParent; // change the model object
		this.objectIndex = objectIndex;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[1];
		setExpanded(true);
	} 

	@Override
	public void btnUpdateHandleLogic( XMLEditor textEditor)
			throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic( XMLEditor textEditor)
			throws JAXBException {
		Transition transition = (Transition) parentTagContainer;
		transition.refreshChildren(HTEditorConstants.NOTIFICATIONS_TITLE,compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException  {
		if (notifications != null) {
			for (Composite composite : childComposites) {
				composite.dispose();
			}
			for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
				childObjectIndexes[childObjectIndexesElementIndex] = 0;
			}
			childComposites.clear();
			childCompositeIndex = 0;

			ArrayList<TNotification> taskNotifications = (ArrayList<TNotification>) notifications.getNotification();
			for (int i = 0; i < taskNotifications.size(); i++) {
				TNotificationUI tNotificationUI = new TNotificationUI(editor, detailArea, this,
						SWT.NONE, taskNotifications.get(childObjectIndexes[0]),
						childObjectIndexes[0], childCompositeIndex);
				tNotificationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tNotificationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}
		}
	}

	@Override
	public void newButtonLogic(String selection, ////////////////Start from this
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.NOTIFICATION_TITLE)) {
			TNotification tNotification = new TNotification();
			tNotification.setName("");
			notifications.getNotification().add(childObjectIndexes[0], tNotification);
			TNotificationUI tNotificationUI = new TNotificationUI(editor,composite,this,SWT.NONE,tNotification,childObjectIndexes[0],childCompositeIndex);
			childComposites.add(childCompositeIndex, tNotificationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
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
		for (Composite compositeInstance : childComposites) {
			TNotificationUI tNotificationUI = (TNotificationUI) compositeInstance;  //children node type
			if (tNotificationUI.getCompositeIndex() > childCompositeIndex) {
				tNotificationUI.setCompositeIndex(tNotificationUI.getCompositeIndex() - 1);
			}
			if (tNotificationUI.getObjectIndex() >= childObjectIndex) {
				tNotificationUI.setObjectIndex(tNotificationUI.getObjectIndex() - 1);
			}
		}
		
	}
	public void loadModel(Object model) throws JAXBException{
		notifications = (TNotifications) model;
		for (Composite compositeInstance : childComposites) {
			TNotificationUI tNotificationUI = (TNotificationUI) compositeInstance;  //children node type
			tNotificationUI.refreshLogic(textEditor);
			tNotificationUI.loadModel(notifications.getNotification().get(tNotificationUI.getObjectIndex()));
			this.layout();
		}
	}
	
	

}

