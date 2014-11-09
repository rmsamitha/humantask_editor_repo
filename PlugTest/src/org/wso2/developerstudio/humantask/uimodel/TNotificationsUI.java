package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TNotification;
import org.wso2.developerstudio.humantask.models.TNotifications;

/**
 * The UI class representing the "notifications" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TNotificationsUI extends AbstractParentTagSection {

	private int[] childObjectIndexes;
	public TNotifications notifications;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	/**
	 * Call the super abstract class to set the UI and initialize class's
	 * attribute variables
	 * 
	 * @param textEditor
	 * @param parentComposite
	 * @param parentTagContainer
	 * @param styleBit
	 * @param objectModel
	 * @param objectIndex
	 * @param compositeIndex
	 * @throws JAXBException
	 */
	public TNotificationsUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object modelParent,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.NOTIFICATION_TITLE },
				HTEditorConstants.NOTIFICATIONS_TITLE); // change the list of
														// items in drop down
														// list
		this.notifications = (TNotifications) modelParent; // change the model
															// object
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[1];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
	}

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		AbstractParentTagSection transition = (AbstractParentTagSection) parentTagContainer;
		transition.refreshChildren(HTEditorConstants.NOTIFICATIONS_TITLE,
				getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

	/**
	 * Whenever a tab change occur from text editor to UI editor, this method is
	 * invoked. It disposes all the child Sections in this section and recreate
	 * them and call initialize() of each of them to reinitialize their
	 * attribute values, according to the single model maintained by both the 
	 * UI editor and text .editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
		if (notifications != null) {
			for (Composite composite : childComposites) {
				composite.dispose();
			}
			for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
				childObjectIndexes[childObjectIndexesElementIndex] = 0;
			}
			childComposites.clear();
			childCompositeIndex = 0;

			ArrayList<TNotification> taskNotifications = (ArrayList<TNotification>) notifications
					.getNotification();
			for (int i = 0; i < taskNotifications.size(); i++) {
				TNotificationUI tNotificationUI = new TNotificationUI(editor,
						detailArea, this, SWT.NONE,
						taskNotifications.get(childObjectIndexes[0]),
						childObjectIndexes[0], childCompositeIndex);
				tNotificationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tNotificationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}
		}
	}

	@Override
	public void newButtonLogic(String selection, // //////////////Start from
													// this
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.NOTIFICATION_TITLE)) {
			TNotification tNotification = new TNotification();
			tNotification.setName("");
			notifications.getNotification().add(childObjectIndexes[0],
					tNotification);
			TNotificationUI tNotificationUI = new TNotificationUI(editor,
					composite, this, SWT.NONE, tNotification,
					childObjectIndexes[0], childCompositeIndex);
			childComposites.add(childCompositeIndex, tNotificationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		centralUtils.marshal(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		/*
		 * dispose update button as it is not required to this Section as there
		 * is no any attribute or xml content in this Section
		 */
		btnUpdate.dispose();
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {

	}

	/**
	 * Whenever a child Section of this section is removed by the user, this
	 * method is invoked to reorganize the order and indexes of the child
	 * Sections of this section
	 * 
	 * @param itemName
	 * @param childCompositeIndex
	 * @param childObjectIndex
	 */
	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		System.out.println("Child Index :" + childCompositeIndex);
		childComposites.remove(childCompositeIndex);
		notifications.getNotification().remove(getObjectIndex());
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		for (Composite compositeInstance : childComposites) {
			TNotificationUI tNotificationUI = (TNotificationUI) compositeInstance; // children
																					// node
																					// type
			if (tNotificationUI.getCompositeIndex() > childCompositeIndex) {
				tNotificationUI.setCompositeIndex(tNotificationUI
						.getCompositeIndex() - 1);
			}
			if (tNotificationUI.getObjectIndex() >= childObjectIndex) {
				tNotificationUI
						.setObjectIndex(tNotificationUI.getObjectIndex() - 1);
			}
		}

	}

	/**
	 * Load the JAXB model objects into the UI model from the top to bottom of
	 * the tree structure of the model whenever a tab change occurs from text
	 * editor to the UI editor.
	 * 
	 * @param model
	 * @throws JAXBException
	 */
	public void loadModel(Object model) throws JAXBException {
		notifications = (TNotifications) model;
		for (Composite compositeInstance : childComposites) {
			TNotificationUI tNotificationUI = (TNotificationUI) compositeInstance;
			tNotificationUI.refreshLogic(textEditor);
			tNotificationUI.loadModel(notifications.getNotification().get(
					tNotificationUI.getObjectIndex()));
			this.layout();
		}
	}

	/**
	 * Returns This section's(composite's) index (index of any type of child
	 * class objects created in the parent Section) as
	 * per the order created in this object's parent
	 * 
	 * @return This section's(composite's) index
	 */
	public int getCompositeIndex() {
		return compositeIndex;
	}

	/**
	 * Set this section's(composite's) index (index of any type of child class
	 * objects created in the parent Section)
	 * as per the order created in this object's parent
	 * 
	 * @param compositeIndex
	 */
	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

	/**
	 * Returns this Section's object index(index of only this type of class
	 * objects in the parent) as per the order created in its parent
	 * 
	 * @return objectIndex
	 */
	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

}
