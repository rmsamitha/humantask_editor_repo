/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TFrom;
import org.wso2.developerstudio.humantask.models.TGenericHumanRoleAssignment;

/**
 * The UI class representing the "genericHumanRoleAssignment" xml element in the
 * .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TGenericHumanRoleAssignmentUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TGenericHumanRoleAssignment genericHumanRoleAssignment;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
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
	public TGenericHumanRoleAssignmentUI(XMLEditor textEditor, Composite parentComposite,
	                                     Composite parentTagContainer, int styleBit,
	                                     Object modelParent, int objectIndex, int compositeIndex,
	                                     String type, QName qname) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE, HTEditorConstants.FROM_TITLE },
		      type);
		this.genericHumanRoleAssignment = (TGenericHumanRoleAssignment) modelParent;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[2];
		setExpanded(true);
	}

	@Override
	public void onBtnUpdate(XMLEditor textEditor) throws JAXBException {
	}

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void onBtnRemove(XMLEditor textEditor) throws JAXBException {
		AbstractParentTagSection abstractParentTagSection =
		                                                    (AbstractParentTagSection) parentTagContainer;
		abstractParentTagSection.refreshChildren(HTEditorConstants.GENERIC_HUMAN_ROLE_TITLE,
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
	public void onPageRefresh(XMLEditor editor) throws JAXBException {
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;
		ArrayList<TDocumentation> documentationGroup =
		                                               (ArrayList<TDocumentation>) genericHumanRoleAssignment.getDocumentation();
		for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup.size(); documentationGroupIndex++) {
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(
			                                                         editor,
			                                                         detailArea,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE,
			                                                         this,
			                                                         documentationGroup.get(childObjectIndexes[0]));
			tDocumentationUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}
		if (genericHumanRoleAssignment.getFrom() != null) {
			TFrom fromObject = genericHumanRoleAssignment.getFrom();
			TFromUI tFromUI =
			                  new TFromUI(editor, detailArea, childCompositeIndex,
			                              childObjectIndexes[1], SWT.NONE, this, fromObject);
			tFromUI.initialize(editor);
			childComposites.add(childCompositeIndex, tFromUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		}
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
	}

	/**
	 * Create a new child Section for the selected xml child element
	 * 
	 * @param selection
	 * @param scrolledComposite
	 * @param editor
	 * @param composite
	 * @throws JAXBException
	 */
	@Override
	public void onCreateNewChild(String selection, ScrolledComposite sc3, XMLEditor editor,
	                             Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			genericHumanRoleAssignment.getDocumentation()
			                          .add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.FROM_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TFrom tFrom = new TFrom();
				tFrom.setExpressionLanguage("");
				tFrom.setLogicalPeopleGroup("");
				tFrom.getContent().add(0, "");
				genericHumanRoleAssignment.setFrom(tFrom);
				TFromUI tFromUI =
				                  new TFromUI(editor, detailArea, childCompositeIndex,
				                              childObjectIndexes[1], SWT.NONE, this, tFrom);
				tFromUI.initialize(editor);
				childComposites.add(childCompositeIndex, tFromUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}
		}

	}

	@Override
	public void fillDetailArea(Composite composite) {
		/*
		 * dispose update button as it is not required to this Section as there
		 * is no
		 * any attribute or xml content in this Section
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
	public void refreshChildren(String itemName, int childCompositeIndex, int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			genericHumanRoleAssignment.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationParameterUI) {

					TPresentationParameterUI tPresentationParameterUI =
					                                                    (TPresentationParameterUI) compositeInstance;
					if (tPresentationParameterUI.compositeIndex > childCompositeIndex) {
						tPresentationParameterUI.setCompositeIndex(tPresentationParameterUI.getCompositeIndex() - 1);
					}
				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.FROM_TITLE)) {
			this.childObjectIndexes[1]--;
			genericHumanRoleAssignment.setFrom(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TFromUI) {

					TFromUI tFromUI = (TFromUI) compositeInstance;
					if (tFromUI.getCompositeIndex() > childCompositeIndex) {
						tFromUI.setCompositeIndex(tFromUI.getCompositeIndex() - 1);
					}
					if (tFromUI.getObjectIndex() > childObjectIndex) {
						tFromUI.setObjectIndex(tFromUI.getObjectIndex() - 1);
					}
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
	public void loadModel(Object model) throws JAXBException {
		genericHumanRoleAssignment = (TGenericHumanRoleAssignment) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(genericHumanRoleAssignment.getDocumentation()
				                                                     .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TFromUI) {
				TFromUI tFromUI = (TFromUI) compositeInstance;
				tFromUI.loadModel(genericHumanRoleAssignment.getFrom());
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

	/**
	 * Set this Section's object index(index of only this type of class objects
	 * in the parent) as per the order created in This Section's parent.
	 * 
	 * @param objectIndex
	 */
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
