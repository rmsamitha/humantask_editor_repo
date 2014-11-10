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

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDelegation;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TFrom;
import org.wso2.developerstudio.humantask.models.TPotentialDelegatees;

/**
 * The UI class representing the "delegation" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TDelegationUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TDelegation tDelegation;
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
	public TDelegationUI(XMLEditor textEditor, Composite parentComposite,
	                     Composite parentTagContainer, int style, Object modelParent,
	                     int objectIndex, int compositeIndex) throws JAXBException {
		super(
		      textEditor,
		      parentComposite,
		      parentTagContainer,
		      style,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE, HTEditorConstants.FROM_TITLE, },
		      HTEditorConstants.DELEGATION_TITLE);
		this.tDelegation = (TDelegation) modelParent;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[3];
		setExpanded(true);
	}

	/**
	 * Update the values of attributes of the section and marshal into the
	 * TextEditor when the update button of that section is clicked
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void onBtnUpdate(XMLEditor textEditor) throws JAXBException {
		tDelegation.setPotentialDelegatees(TPotentialDelegatees.fromValue(((Combo) textBoxesList.get(0)).getText()));
		centralUtils.marshal(textEditor);

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
		abstractParentTagSection.refreshChildren(HTEditorConstants.DELEGATION_TITLE,
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
		                                               (ArrayList<TDocumentation>) tDelegation.getDocumentation();
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
		if (tDelegation.getFrom() != null) {
			TFrom fromObject = tDelegation.getFrom();
			TFromUI tFromUI =
			                  new TFromUI(editor, detailArea, childCompositeIndex,
			                              childObjectIndexes[1], SWT.NONE, this, fromObject);
			tFromUI.initialize(editor);
			childComposites.add(childCompositeIndex, tFromUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		}

	}

	/**
	 * Initialize or set the values of attributes and xml content(if available)
	 * whenever a tab change occur
	 * from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if (tDelegation.getPotentialDelegatees() != null) {
			Combo combo = (Combo) textBoxesList.get(0);
			combo.select(combo.indexOf(tDelegation.getPotentialDelegatees().value()));
		}

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
			tDelegation.getDocumentation().add(childObjectIndexes[0], tDocumentation);
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
				tDelegation.setFrom(tFrom);
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

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element content (if available) in the
	 * section
	 * 
	 * @param composite
	 */
	@Override
	public void fillDetailArea(Composite composite) {
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);

		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(150);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor editorw = new TableEditor(table);

		Label lblType = new Label(table, SWT.NONE);
		lblType.setText("  Potential Delegetes");
		editorw.grabHorizontal = true;
		editorw.setEditor(lblType, tblRow1, 0);

		editorw = new TableEditor(table);

		Combo cmbPotentialDelegetees = new Combo(table, SWT.NONE);
		cmbPotentialDelegetees.setItems(new String[] {
		                                              TPotentialDelegatees.ANYBODY.value(),
		                                              TPotentialDelegatees.NOBODY.value(),
		                                              TPotentialDelegatees.POTENTIAL_OWNERS.value(),
		                                              TPotentialDelegatees.OTHER.value() });
		cmbPotentialDelegetees.select(0);
		textBoxesList.add(0, cmbPotentialDelegetees);
		editorw.grabHorizontal = true;
		editorw.setEditor(cmbPotentialDelegetees, tblRow1, 1);

	}

	/**
	 * Whenever a child Section of this section is removed by the user, this
	 * method is invoked to
	 * reorganize the order and indexes of the child Sections of this section
	 * 
	 * @param itemName
	 * @param childCompositeIndex
	 * @param childObjectIndex
	 */
	@Override
	public void refreshChildren(String itemName, int childCompositeIndex, int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			tDelegation.getDocumentation().remove(childObjectIndex);
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
			tDelegation.setFrom(null);
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
	 */
	public void loadModel(Object model) {
		tDelegation = (TDelegation) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(tDelegation.getDocumentation()
				                                      .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TFromUI) {
				TFromUI tFromUI = (TFromUI) compositeInstance;
				tFromUI.loadModel(tDelegation.getFrom());
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
