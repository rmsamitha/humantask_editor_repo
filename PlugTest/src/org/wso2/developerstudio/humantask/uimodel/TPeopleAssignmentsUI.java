package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TGenericHumanRoleAssignment;
import org.wso2.developerstudio.humantask.models.TGenericHumanRoleAssignmentBase;
import org.wso2.developerstudio.humantask.models.TPeopleAssignments;
import org.wso2.developerstudio.humantask.models.TPotentialOwnerAssignment;

/**
 * The UI class representing the "peopleAssignments" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TPeopleAssignmentsUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TPeopleAssignments tPeopleAssignments;
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
	public TPeopleAssignmentsUI(XMLEditor textEditor,
			Composite parentComposite, Composite parentTagContainer, int style,
			Object modelParent, int objectIndex, int compositeIndex)
			throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, style,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.POTENTIAL_OWNERS_TITLE,
						HTEditorConstants.EXCLUDED_OWNERS_TITLE,
						HTEditorConstants.TASK_INITIATOR_TITLE,
						HTEditorConstants.TASK_STAKE_HOLDERS_TITLE,
						HTEditorConstants.BUSINESS_ADMINISTRATORS_TITLE,
						HTEditorConstants.RECIPIENTS_TITLE },
				HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE);
		this.tPeopleAssignments = (TPeopleAssignments) modelParent;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[3];
		setExpanded(true);
	}
	
	/**
	 * Update the values of attributes(xml attributes) of the section and xml
	 * element content value and marshal into the TextEditor when the update
	 * button of that section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
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
		AbstractParentTagSection taskUI = (AbstractParentTagSection) parentTagContainer;
		taskUI.refreshChildren(HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE,
				compositeIndex, objectIndex);
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
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) tPeopleAssignments
				.getDocumentation();
		for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
				.size(); documentationGroupIndex++) {
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					detailArea, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this,
					documentationGroup.get(childObjectIndexes[0]));
			tDocumentationUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}

		ArrayList<JAXBElement<? extends TGenericHumanRoleAssignmentBase>> genericPeopleGroup = (ArrayList<JAXBElement<? extends TGenericHumanRoleAssignmentBase>>) tPeopleAssignments
				.getGenericHumanRole();

		for (int genericPeopleIndex = 0; genericPeopleIndex < genericPeopleGroup
				.size(); genericPeopleIndex++) {
			if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.PotentialOwners_QNAME)) {
				TPotentialOwnerAssignmentsUI tPotentialOwnerAssignmentUI = new TPotentialOwnerAssignmentsUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.POTENTIAL_OWNERS_TITLE,
						HTEditorConstants.PotentialOwners_QNAME);
				tPotentialOwnerAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tPotentialOwnerAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.ExcludedOwners_QNAME)) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.EXCLUDED_OWNERS_TITLE,
						HTEditorConstants.ExcludedOwners_QNAME);
				tGenericHumanRoleAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tGenericHumanRoleAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.TaskInitiator_QNAME)) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.TASK_INITIATOR_TITLE,
						HTEditorConstants.TaskInitiator_QNAME);
				tGenericHumanRoleAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tGenericHumanRoleAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.TaskStakeholders_QNAME)) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.TASK_STAKEHOLDERS_TITLE,
						HTEditorConstants.TaskStakeholders_QNAME);
				tGenericHumanRoleAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tGenericHumanRoleAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.BusinessAdministrators_QNAME)) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.BUSINESS_ADMINISTRATORS_TITLE,
						HTEditorConstants.BusinessAdministrators_QNAME);
				tGenericHumanRoleAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tGenericHumanRoleAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else if (genericPeopleGroup.get(genericPeopleIndex).getName()
					.equals(HTEditorConstants.Recipients_QNAME)) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
						editor, detailArea, this, SWT.NONE, genericPeopleGroup
								.get(childObjectIndexes[1]).getValue(),
						childObjectIndexes[1], childCompositeIndex,
						HTEditorConstants.RECIPIENTS_TITLE,
						HTEditorConstants.Recipients_QNAME);
				tGenericHumanRoleAssignmentUI.initialize(editor);
				childComposites.add(childCompositeIndex,
						tGenericHumanRoleAssignmentUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			} else {

			}
		}
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			tPeopleAssignments.getDocumentation().add(childObjectIndexes[0],
					tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					composite, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.POTENTIAL_OWNERS_TITLE)) {
			TPotentialOwnerAssignment tPotentialOwnerAssignment = new TPotentialOwnerAssignment();
			JAXBElement<TPotentialOwnerAssignment> tp = new JAXBElement<TPotentialOwnerAssignment>(
					HTEditorConstants.PotentialOwners_QNAME,
					TPotentialOwnerAssignment.class, tPotentialOwnerAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TPotentialOwnerAssignmentsUI tGenericHumanRoleAssignmentUI = new TPotentialOwnerAssignmentsUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex,
					HTEditorConstants.POTENTIAL_OWNERS_TITLE,
					HTEditorConstants.PotentialOwners_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.EXCLUDED_OWNERS_TITLE)) {
			TGenericHumanRoleAssignment tGenericHumanRoleAssignment = new TGenericHumanRoleAssignment();
			JAXBElement<TGenericHumanRoleAssignment> tp = new JAXBElement<TGenericHumanRoleAssignment>(
					HTEditorConstants.ExcludedOwners_QNAME,
					TGenericHumanRoleAssignment.class,
					tGenericHumanRoleAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex,
					HTEditorConstants.EXCLUDED_OWNERS_TITLE,
					HTEditorConstants.ExcludedOwners_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.TASK_INITIATOR_TITLE)) {
			TGenericHumanRoleAssignment tGenericHumanRoleAssignment = new TGenericHumanRoleAssignment();
			JAXBElement<TGenericHumanRoleAssignment> tp = new JAXBElement<TGenericHumanRoleAssignment>(
					HTEditorConstants.TaskInitiator_QNAME,
					TGenericHumanRoleAssignment.class,
					tGenericHumanRoleAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex,
					HTEditorConstants.TASK_INITIATOR_TITLE,
					HTEditorConstants.TaskInitiator_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.TASK_STAKE_HOLDERS_TITLE)) {
			TGenericHumanRoleAssignment tGenericHumanRoleAssignment = new TGenericHumanRoleAssignment();
			JAXBElement<TGenericHumanRoleAssignment> tp = new JAXBElement<TGenericHumanRoleAssignment>(
					HTEditorConstants.TaskStakeholders_QNAME,
					TGenericHumanRoleAssignment.class,
					tGenericHumanRoleAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex,
					HTEditorConstants.TASK_STAKE_HOLDERS_TITLE,
					HTEditorConstants.TaskStakeholders_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.BUSINESS_ADMINISTRATORS_TITLE)) {
			TGenericHumanRoleAssignment tGenericHumanRoleAssignment = new TGenericHumanRoleAssignment();
			JAXBElement<TGenericHumanRoleAssignment> tp = new JAXBElement<TGenericHumanRoleAssignment>(
					HTEditorConstants.TaskInitiator_QNAME,
					TGenericHumanRoleAssignment.class,
					tGenericHumanRoleAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex,
					HTEditorConstants.TASK_STAKE_HOLDERS_TITLE,
					HTEditorConstants.TaskInitiator_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.RECIPIENTS_TITLE)) {
			TGenericHumanRoleAssignment tGenericHumanRoleAssignment = new TGenericHumanRoleAssignment();
			JAXBElement<TGenericHumanRoleAssignment> tp = new JAXBElement<TGenericHumanRoleAssignment>(
					HTEditorConstants.Recipients_QNAME,
					TGenericHumanRoleAssignment.class,
					tGenericHumanRoleAssignment);
			tPeopleAssignments.getGenericHumanRole().add(childObjectIndexes[1],
					tp);
			TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = new TGenericHumanRoleAssignmentUI(
					editor, detailArea, this, SWT.NONE, tPeopleAssignments
							.getGenericHumanRole().get(childObjectIndexes[1])
							.getValue(), childObjectIndexes[1],
					childCompositeIndex, HTEditorConstants.RECIPIENTS_TITLE,
					HTEditorConstants.Recipients_QNAME);
			tGenericHumanRoleAssignmentUI.initialize(editor);
			childComposites.add(childCompositeIndex,
					tGenericHumanRoleAssignmentUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
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
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			tPeopleAssignments.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI
								.getObjectIndex() - 1);
					}
				} else if (compositeInstance.getClass() == TGenericHumanRoleAssignmentUI.class) {

					TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = (TGenericHumanRoleAssignmentUI) compositeInstance;
					if (tGenericHumanRoleAssignmentUI.getCompositeIndex() > childCompositeIndex) {
						tGenericHumanRoleAssignmentUI
								.setCompositeIndex(tGenericHumanRoleAssignmentUI
										.getCompositeIndex() - 1);
					}
				} else {

				}

			}
		} else if (itemName
				.equalsIgnoreCase(HTEditorConstants.GENERIC_HUMAN_ROLE_TITLE)) {
			this.childObjectIndexes[1]--;
			tPeopleAssignments.getGenericHumanRole().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance.getClass() == TPotentialOwnerAssignmentsUI.class) {

					TPotentialOwnerAssignmentsUI tPotentialUserAssignmentsUI = (TPotentialOwnerAssignmentsUI) compositeInstance;
					if (tPotentialUserAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPotentialUserAssignmentsUI
								.setCompositeIndex(tPotentialUserAssignmentsUI
										.getCompositeIndex() - 1);
					}
					if (tPotentialUserAssignmentsUI.getObjectIndex() > childObjectIndex) {
						tPotentialUserAssignmentsUI
								.setObjectIndex(tPotentialUserAssignmentsUI
										.getObjectIndex() - 1);
					}
				} else if (compositeInstance.getClass() == TGenericHumanRoleAssignmentUI.class) {

					TGenericHumanRoleAssignmentUI tgenericHumanRoleAssignementUI = (TGenericHumanRoleAssignmentUI) compositeInstance;
					if (tgenericHumanRoleAssignementUI.getCompositeIndex() > childCompositeIndex) {
						tgenericHumanRoleAssignementUI
								.setCompositeIndex(tgenericHumanRoleAssignementUI
										.getCompositeIndex() - 1);
					}
					if (tgenericHumanRoleAssignementUI.getObjectIndex() > childObjectIndex) {
						tgenericHumanRoleAssignementUI
								.setObjectIndex(tgenericHumanRoleAssignementUI
										.getObjectIndex() - 1);
					}
				} else {

				}

			}
		}
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;
	}

	/**
	 * Load the JAXB model objects into the UI model from the top to bottom of
	 * the tree structure of the model whenever a tab change occurs from text
	 * editor to the UI editor.
	 * 
	 * @param model
	 * @throws JAXBException
	 */
	public void loadModel(Object objectModel) throws JAXBException {
		tPeopleAssignments = (TPeopleAssignments) objectModel;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(tPeopleAssignments
						.getDocumentation().get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance.getClass() == TPotentialOwnerAssignmentsUI.class) {
				TPotentialOwnerAssignmentsUI tPotentialOwnerAssignmentsUI = (TPotentialOwnerAssignmentsUI) compositeInstance;
				tPotentialOwnerAssignmentsUI.refreshLogic(textEditor);
				tPotentialOwnerAssignmentsUI.loadModel(tPeopleAssignments
						.getGenericHumanRole()
						.get(tPotentialOwnerAssignmentsUI.getObjectIndex())
						.getValue());
			} else if (compositeInstance.getClass() == TGenericHumanRoleAssignmentUI.class) {
				TGenericHumanRoleAssignmentUI tGenericHumanRoleAssignmentUI = (TGenericHumanRoleAssignmentUI) compositeInstance;
				tGenericHumanRoleAssignmentUI.refreshLogic(textEditor);
				tGenericHumanRoleAssignmentUI.loadModel(tPeopleAssignments
						.getGenericHumanRole()
						.get(tGenericHumanRoleAssignmentUI.getObjectIndex())
						.getValue());
			}
		}
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

}
