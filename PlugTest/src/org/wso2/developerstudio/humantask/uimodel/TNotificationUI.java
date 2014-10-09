package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TNotification;
import org.wso2.developerstudio.humantask.models.TNotificationInterface;
import org.wso2.developerstudio.humantask.models.TPeopleAssignments;
import org.wso2.developerstudio.humantask.models.TPresentationElements;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTaskInterface;

public class TNotificationUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TNotification notification;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();

	public TNotificationUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object objectModel,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.INTERFACE_TITLE,
						HTEditorConstants.PRIORITY_TITLE, "People Assignments",
						HTEditorConstants.PRESENTATION_ELEMENTS_TITLE,"Renderings" },
				HTEditorConstants.NOTIFICATION_TITLE);
		this.notification = (TNotification) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[6];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		notification.setName(((Text) textBoxesList.get(0)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TNotificationsUI tNotificationsUI = (TNotificationsUI) parentTagContainer;
		tNotificationsUI.refreshChildren("", compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Documentation")) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(
					new String(""));
			notification.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tNot = new TDocumentationUI(editor, composite,
					childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
					tDocumentation);
			childComposites.add(childCompositeIndex, tNot);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {  //min ocurs=1 max=1
			if (childObjectIndexes[1] < 1) {
				TNotificationInterface tNotificationInterface = new TNotificationInterface();
				tNotificationInterface.setPortType(new QName(""));
				tNotificationInterface.setOperation("");
				notification.setInterface(tNotificationInterface);
				TNotificationInterfaceUI tNot = new TNotificationInterfaceUI(editor, composite,
						this, SWT.NONE,tNotificationInterface,childObjectIndexes[1],childCompositeIndex);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[1]++;
				childCompositeIndex++;
				
				
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRIORITY_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0,"");
				notification.setPriority(tPriorityExpr);
				TPriorityExprUI tPriorityExprUI = new TPriorityExprUI(editor, composite,
						childCompositeIndex, childObjectIndexes[2], SWT.NONE,
						this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tPriorityExprUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		}else if (selection.equalsIgnoreCase("PeopleAssignments")) { //min ocurs=1 max=1
			if (childObjectIndexes[3] < 1) {
				/*TPeopleAssignments tPeopleAssignments = new TPeopleAssignments();
				notification.setPeopleAssignments(tPeopleAssignments);
				TPeopleAssignmentsInNotificationsUI tNot = new TPeopleAssignmentsInNotificationsUI(editor, composite,
						childCompositeIndex, childObjectIndexes[2], SWT.NONE,
						this, tPeopleAssignments);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			*/}
		}else if (selection.equalsIgnoreCase(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE)) {// //min ocurs=1 max=1
			if (childObjectIndexes[4] < 1) {
				TPresentationElements tPresentationElements = new TPresentationElements();
				notification.setPresentationElements(tPresentationElements);
				TPresentationElementsUI tPresentationElementsUI = new TPresentationElementsUI(
						editor, composite, this, SWT.NONE,
						tPresentationElements, childObjectIndexes[4],
						childCompositeIndex);
				childComposites.add(childCompositeIndex,
						tPresentationElementsUI);
				childObjectIndexes[4]++;
				childCompositeIndex++;
			}
		}else if (selection.equalsIgnoreCase("Renderings")) {
			/*if (childObjectIndexes[5] < 1) {
				TRenderings tRenderings = new TRenderings();
			notification.setRenderings(tRenderings);
				TRenderingsUI tNot = new TRenderingsUI(editor, composite,
						childCompositeIndex, childObjectIndexes[2], SWT.NONE,
						this, tRenderings);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[5]++;
				childCompositeIndex++;
			}*/
		}
		centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(4, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblName = formToolkit.createLabel(innerZComp, "Name",
				SWT.NONE);
		Text txtLang = formToolkit.createText(innerZComp, "", SWT.BORDER);
		textBoxesList.add(0, txtLang);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(notification.getName());
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException { /////////////////////////Start from this/////////////////////
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;
		
		if (childComposites.size() == 0) {
			ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) notification
					.getDocumentation();
			for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
					.size(); documentationGroupIndex++) {
				TDocumentationUI tDocumentationUI = new TDocumentationUI(
						editor, detailArea, childCompositeIndex,
						childObjectIndexes[0], SWT.NONE, this,
						documentationGroup.get(childObjectIndexes[0]));
				tDocumentationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tDocumentationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			if (notification.getInterface() != null) {
				TNotificationInterface interfaceObject = (TNotificationInterface) notification.getInterface();
				TNotificationInterfaceUI tNotificationInterfaceUI = new TNotificationInterfaceUI(editor, detailArea, this, SWT.NONE,
						interfaceObject, childObjectIndexes[1],
						childCompositeIndex);
				tNotificationInterfaceUI.initialize(editor);
				childComposites.add(childCompositeIndex, tNotificationInterfaceUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (notification.getPriority() != null) {
				TPriorityExpr priorityObject = (TPriorityExpr) notification
						.getPriority();
				TPriorityExprUI tPriorityUI = new TPriorityExprUI(editor,
						detailArea, childCompositeIndex, childObjectIndexes[2],
						SWT.NONE, this, priorityObject);
				tPriorityUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPriorityUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;

			}
			if (notification.getPeopleAssignments() != null) {
				/*TPresentationElements tPresentationElementsObject = (TPresentationElements) notification
						.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI = new TPresentationElementsUI(
						editor, detailArea, this, SWT.NONE,
						tPresentationElementsObject, childObjectIndexes[0],
						childCompositeIndex);
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;*/
			}
			if (notification.getPresentationElements() != null) {
				TPresentationElements tPresentationElementsObject = (TPresentationElements) notification
						.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI = new TPresentationElementsUI(
						editor, detailArea, this, SWT.NONE,
						tPresentationElementsObject, childObjectIndexes[4],
						childCompositeIndex);
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[4]++;
			}
			if (notification.getRenderings() != null) {
				/*TPresentationElements tPresentationElementsObject = (TPresentationElements) notification
						.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI = new TPresentationElementsUI(
						editor, detailArea, this, SWT.NONE,
						tPresentationElementsObject, childObjectIndexes[4],
						childCompositeIndex);
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[4]++;*/
			}
		}
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			notification.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.objectIndex > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI
								.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI = (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElements = (TPresentationElementsUI) compositeInstance;
					if (tPresentationElements.getCompositeIndex() > childCompositeIndex) {
						tPresentationElements
								.setCompositeIndex(tPresentationElements
										.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase("Interface")) {
			this.childObjectIndexes[1]--;
			notification.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				}  else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI = (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI = (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI
								.setCompositeIndex(tPresentationElementsUI
										.getCompositeIndex() - 1);
					}

				} else {
				}

			}
		} else if (itemName.equalsIgnoreCase("Priority")) {
			this.childObjectIndexes[2]--;
			notification.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI = (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI = (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI
								.setCompositeIndex(tPresentationElementsUI
										.getCompositeIndex() - 1);
					}

				} else {
				}

			}
		} else if (itemName.equalsIgnoreCase("Presentation Elements")) {
			this.childObjectIndexes[4]--;
			notification.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI = (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI = (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI
								.setCompositeIndex(tPresentationElementsUI
										.getCompositeIndex() - 1);
					}

				} else {
				}

			}
		}
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;
	}

	public void loadModel(Object model) throws JAXBException {
		notification = (TNotification) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance; 
				tDocumentationUI.loadModel(notification.getDocumentation().get(
						tDocumentationUI.objectIndex));
			} else if (compositeInstance instanceof TNotificationInterfaceUI) {
				TNotificationInterfaceUI tTaskInterfaceUI = (TNotificationInterfaceUI) compositeInstance; 
				tTaskInterfaceUI.refreshLogic(textEditor);
				tTaskInterfaceUI.loadModel(notification.getInterface());
			} else if (compositeInstance instanceof TPriorityExprUI) {
				TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance; 
				tPriorityUI.loadModel(notification.getPriority());
			} else if (compositeInstance instanceof TPresentationElementsUI) {
				TPresentationElementsUI tPresentationElementsUI = (TPresentationElementsUI) compositeInstance;
				tPresentationElementsUI.presentationElements = notification
						.getPresentationElements();
				tPresentationElementsUI.refreshLogic(textEditor);
				tPresentationElementsUI.loadModel(notification
						.getPresentationElements());

			}
		}
	}
	
	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}
}
