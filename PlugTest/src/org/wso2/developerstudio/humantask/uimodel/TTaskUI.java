package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

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
import org.wso2.developerstudio.humantask.models.TBoolean;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TPresentationElements;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTaskInterface;
import org.wso2.developerstudio.humantask.models.TTasks;

public class TTaskUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TTask task;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();

	public TTaskUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object objectModel,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.INTERFACE_TITLE,
						HTEditorConstants.PRIORITY_TITLE, "People Assignments",
						"Delegation",
						HTEditorConstants.PRESENTATION_ELEMENTS_TITLE },
				HTEditorConstants.TASK_TITLE);
		this.task = (TTask) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[6];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		task.setName(((Text) textBoxesList.get(0)).getText());
		task.setActualOwnerRequired(TBoolean.fromValue(((Combo) textBoxesList
				.get(1)).getText()));
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTasksUI tTasksUI = (TTasksUI) parentTagContainer;
		tTasksUI.refreshChildren("", getCompositeIndex(), getObjectIndex());
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			task.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					composite, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TTaskInterface tTaskInterface = new TTaskInterface();
				tTaskInterface.setPortType(new QName(""));
				tTaskInterface.setOperation("");
				task.setInterface(tTaskInterface);
				TTaskInterfaceUI tTaskInterfaceUI = new TTaskInterfaceUI(
						editor, composite, childCompositeIndex,
						childObjectIndexes[1], SWT.NONE, this, tTaskInterface);
				childComposites.add(childCompositeIndex, tTaskInterfaceUI);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRIORITY_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0, "");
				task.setPriority(tPriorityExpr);
				TPriorityExprUI tPriorityExprUI = new TPriorityExprUI(editor,
						composite, childCompositeIndex, childObjectIndexes[2],
						SWT.NONE, this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tPriorityExprUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE)) {
			if (childObjectIndexes[3] < 1) {
				TPresentationElements tPresentationElements = new TPresentationElements();
				task.setPresentationElements(tPresentationElements);
				;
				TPresentationElementsUI tPresentationElementsUI = new TPresentationElementsUI(
						editor, composite, this, SWT.NONE,
						tPresentationElements, childObjectIndexes[0],
						childCompositeIndex);
				;
				childComposites.add(childCompositeIndex,
						tPresentationElementsUI);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			}
		}
		centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(4, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblLang = formToolkit.createLabel(innerZComp, "Language",
				SWT.NONE);
		Text txtLang = formToolkit.createText(innerZComp, "", SWT.BORDER);
		textBoxesList.add(0, txtLang);
		Label lblOwnerRequired = formToolkit.createLabel(innerZComp,
				"Actual Owner Required", SWT.NONE);
		Combo cmbOwnerRequired = new Combo(innerZComp, SWT.NONE);
		cmbOwnerRequired.setItems(new String[] { "yes", "no" });
		cmbOwnerRequired.select(0);
		textBoxesList.add(1, cmbOwnerRequired);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(task.getName());
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {

		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		if (childComposites.size() == 0) {
			ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) task
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

			if (task.getInterface() != null) {
				TTaskInterface interfaceObject = (TTaskInterface) task
						.getInterface();
				TTaskInterfaceUI tTaskInterfaceUI = new TTaskInterfaceUI(
						editor, detailArea, childCompositeIndex,
						childObjectIndexes[1], SWT.NONE, this, interfaceObject);
				tTaskInterfaceUI.initialize(editor);
				childComposites.add(childCompositeIndex, tTaskInterfaceUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (task.getPriority() != null) {
				TPriorityExpr priorityObject = (TPriorityExpr) task
						.getPriority();
				TPriorityExprUI tPriorityUI = new TPriorityExprUI(editor,
						detailArea, childCompositeIndex, childObjectIndexes[2],
						SWT.NONE, this, priorityObject);
				tPriorityUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPriorityUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;

			}
			if (task.getPresentationElements() != null) {
				TPresentationElements tPresentationElementsObject = (TPresentationElements) task
						.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI = new TPresentationElementsUI(
						editor, detailArea, this, SWT.NONE,
						tPresentationElementsObject, childObjectIndexes[0],
						childCompositeIndex);
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;
			}
		}
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			task.getDocumentation().remove(childObjectIndex);
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
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.compositeIndex > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI
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
			task.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.compositeIndex > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI
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
			task.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.compositeIndex > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI
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
			this.childObjectIndexes[3]--;
			task.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.compositeIndex > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI
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
		task = (TTask) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance; 
				tDocumentationUI.loadModel(task.getDocumentation().get(
						tDocumentationUI.objectIndex));
			} else if (compositeInstance instanceof TTaskInterfaceUI) {
				TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance; 
				tTaskInterfaceUI.loadModel(task.getInterface());
			} else if (compositeInstance instanceof TPriorityExprUI) {
				TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance; 
				tPriorityUI.loadModel(task.getPriority());
			} else if (compositeInstance instanceof TPresentationElementsUI) {
				TPresentationElementsUI tPresentationElementsUI = (TPresentationElementsUI) compositeInstance;
				tPresentationElementsUI.presentationElements = task
						.getPresentationElements();
				tPresentationElementsUI.refreshLogic(textEditor);
				tPresentationElementsUI.loadModel(task
						.getPresentationElements());

			}
		}
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}
}
