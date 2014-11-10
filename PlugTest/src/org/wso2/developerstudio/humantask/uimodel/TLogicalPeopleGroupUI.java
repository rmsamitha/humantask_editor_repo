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
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TParameter;
import org.wso2.developerstudio.humantask.models.TLogicalPeopleGroup;

/**
 * The UI class representing the "logicalPeopleGroup" xml element in the .ht
 * file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the super class methods.
 */
public class TLogicalPeopleGroupUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;/*
									  * Array to keep the indexes of various
									  * type of child object indexes
									  */
	public TLogicalPeopleGroup logicalPeopleGroup;/*
												   * model class related to this
												   * UI Section
												   */
	private int objectIndex;/*
							 * this Section's object index(index of only this
							 * type of class objects in the parent) as per the
							 * order created in This Section's parent.
							 */
	private int compositeIndex; /*
								 * this section's(composite's) index (index of
								 * any type of child class objects created in
								 * the parent Section) as per the order created
								 * in this object's parent
								 */
	private int childCompositeIndex; /*
									  * Index to keep this
									  * section's(Composite's) child Sections
									  */
	private Composite parentTagContainer; // parent Section(xml element)
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
	public TLogicalPeopleGroupUI(XMLEditor textEditor, Composite parentComposite,
	                             Composite parentTagContainer, int styleBit, Object objectModel,
	                             int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
		                    HTEditorConstants.PARAMETER_TITLE },
		      HTEditorConstants.LOGICAL_PEOPLE_GROUP_TITLE);
		this.logicalPeopleGroup = (TLogicalPeopleGroup) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[2];
		setExpanded(true); // set the Section expanded just when created.
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
		/* Set the name attribute value of this xml element */
		logicalPeopleGroup.setName(((Text) textBoxesList.get(0)).getText());

		/* Set the reference attribute value of this xml element */
		logicalPeopleGroup.setReference(((Text) textBoxesList.get(1)).getText());

		// write onto the text editor
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
		TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI = (TLogicalPeopleGroupsUI) parentTagContainer;

		// calls the refreshChildren() method of this Section's parent Section
		tLogicalPeopleGroupsUI.refreshChildren(HTEditorConstants.LOGICAL_PEOPLE_GROUP_TITLE,
		                                       getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();// dispose this UI Section
		parentComposite.layout(true, true);

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
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) { // check
			// the
			// selection
			TDocumentation tDocumentation = new TDocumentation();

			// initialize attribute values to prevent being null
			tDocumentation.setLang("");

			// iInitially add an empty string to the content of this xml element
			tDocumentation.getContent().add(new String(""));

			/*
			 * add the created tDocumentation model object to the list of
			 * Documentation child elements of this Section
			 */
			logicalPeopleGroup.getDocumentation().add(childObjectIndexes[0], tDocumentation);

			// create and set a UI object to the created model object
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);

			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PARAMETER_TITLE)) {// check
			// the selection
			TParameter tParameter = new TParameter();

			// initialize attribute values to prevent being null
			tParameter.setName("");
			tParameter.setType(new QName(""));

			// iInitially add an empty string to the content of this xml element
			tParameter.getContent().add(new String(""));

			/*
			 * add the created tDocumentation model object to the list of
			 * Documentation child elements of this Section
			 */
			logicalPeopleGroup.getParameter().add(childObjectIndexes[1], tParameter);

			// create and set a UI object to the created model object
			TParameterUI tParameterUI =
			                            new TParameterUI(editor, composite, this, SWT.NONE,
			                                             tParameter, childObjectIndexes[1],
			                                             childCompositeIndex);
			childComposites.add(childCompositeIndex, tParameterUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;

		}

		centralUtils.marshal(editor);
	}

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the
	 * section
	 * 
	 * @param composite
	 */
	@Override
	public void fillDetailArea(Composite composite) {
		Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(1, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		// create table
		final Table table = new Table(innerZComp, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);

		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(150);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblName = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblName.setText("  " + HTEditorConstants.NAME_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblName, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtName = new Text(table, SWT.BORDER | SWT.MULTI);
		textBoxesList.add(0, txtName);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtName, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblReference = new Label(table, SWT.NONE);
		lblReference.setText("     " + HTEditorConstants.REFERENCE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblReference, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtReference = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtReference);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtReference, tblRow1, 3);

	}

	/**
	 * Initialize or set the values of attributes whenever a tab change occur
	 * from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if (logicalPeopleGroup.getName() != null) {
			// get the "Name" attribute value from the model and fill it into
			// the corresponding text box
			((Text) textBoxesList.get(0)).setText(logicalPeopleGroup.getName());
		}
		if (logicalPeopleGroup.getReference() != null) {
			// get the "Reference" attribute value from the model and fill it
			// into the corresponding text box
			((Text) textBoxesList.get(1)).setText(logicalPeopleGroup.getReference());
		}
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
		// dispose all child Sections
		for (Composite composite : childComposites) {
			composite.dispose();
		}

		// reset all the child object indexes to 0
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		if (childComposites.size() == 0) {
			// get the child Documentation model object list of this model
			// object
			ArrayList<TDocumentation> documentationGroup =
			                                               (ArrayList<TDocumentation>) logicalPeopleGroup.getDocumentation();

			// create and set a UI object to each Documentation model object
			// from the above list
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
				tDocumentationUI.initialize(editor);// Initialize or set the
				// values of attributes of
				// that Documentation
				// element

				// add above UI object to the childComposite list of this
				// Section.
				childComposites.add(childCompositeIndex, tDocumentationUI);

				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			// get the child Parameter model object list of this model object
			ArrayList<TParameter> parameterGroup =
			                                       (ArrayList<TParameter>) logicalPeopleGroup.getParameter();

			// create and set a UI object to each Parameter model object from
			// the above list
			for (int parameterGroupIndex = 0; parameterGroupIndex < parameterGroup.size(); parameterGroupIndex++) {
				TParameterUI tTParameterUI =
				                             new TParameterUI(
				                                              editor,
				                                              detailArea,
				                                              this,
				                                              SWT.NONE,
				                                              parameterGroup.get(childObjectIndexes[1]),
				                                              childObjectIndexes[1],
				                                              childCompositeIndex);
				tTParameterUI.initialize(editor);// Initialize or set the values
				// of attributes of that
				// Parameter element

				// add above UI object to the childComposite list of this
				// Section.
				childComposites.add(childCompositeIndex, tTParameterUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}
		}
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
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) { /*
																				 * select
																				 * the
																				 * child
																				 * element
																				 * type
																				 * that
																				 * is
																				 * removed
																				 */
			this.childObjectIndexes[0]--;
			// remove that user removed element from the model
			logicalPeopleGroup.getDocumentation().remove(childObjectIndex);

			/*
			 * consider to reorganize the order and indexes of all the other
			 * child Sections of
			 * this section
			 */
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) { // if the
					// child is
					// a
					// Documentation
					// element
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					// reorganizing is required only if the removed composites's
					// index is less than the now considering other child
					// composite
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					// as the type of both the removed and currently considering
					// composite is same (both are tDocumentationUI) object
					// index of the considering object too should be changed
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TParameterUI) { // if
					// the
					// child
					// is a
					// Parameter
					// element
					TParameterUI tParameterUI = (TParameterUI) compositeInstance;
					if (tParameterUI.getCompositeIndex() > childCompositeIndex) {
						tParameterUI.setCompositeIndex(tParameterUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.PARAMETER_TITLE)) {
			this.childObjectIndexes[1]--;
			logicalPeopleGroup.getParameter().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TParameterUI) {
					TParameterUI tParameterUI = (TParameterUI) compositeInstance;
					if (tParameterUI.getCompositeIndex() > childCompositeIndex) {
						tParameterUI.setCompositeIndex(tParameterUI.getCompositeIndex() - 1);
					}
					if (tParameterUI.objectIndex > childObjectIndex) {
						tParameterUI.setObjectIndex(tParameterUI.getObjectIndex() - 1);
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
	public void loadModel(Object model) throws JAXBException {
		// assign the model TDocumentation object to this UI Section's
		// logicalPeopleGroup variable
		logicalPeopleGroup = (TLogicalPeopleGroup) model;

		// iterate all the child composites of this Composite
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(logicalPeopleGroup.getDocumentation()
				                                             .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TParameterUI) {
				TParameterUI tParameterUI = (TParameterUI) compositeInstance;
				tParameterUI.onPageRefresh(textEditor);
				tParameterUI.loadModel(logicalPeopleGroup.getParameter()
				                                         .get(tParameterUI.objectIndex));
			}
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

	/**
	 * Set this Section's object index(index of only this type of class objects
	 * in the parent) as per the order created in This Section's parent.
	 * 
	 * @param objectIndex
	 */
	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}
}
