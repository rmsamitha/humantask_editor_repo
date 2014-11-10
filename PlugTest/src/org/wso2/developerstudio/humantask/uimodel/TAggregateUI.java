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
import org.wso2.developerstudio.humantask.models.TAggregate;
import org.wso2.developerstudio.humantask.models.TDocumentation;

/**
 * The UI class representing the "aggregate" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TAggregateUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TAggregate aggregate;
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
	public TAggregateUI(XMLEditor textEditor, Composite parentComposite,
	                    Composite parentTagContainer, int styleBit, Object objectModel,
	                    int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE },
		      HTEditorConstants.AGGREGATE_TITLE);
		this.aggregate = (TAggregate) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
		setExpanded(false);
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
		aggregate.setPart(((Text) textBoxesList.get(0)).getText());
		aggregate.setLocation(((Text) textBoxesList.get(1)).getText());
		aggregate.setCondition(((Text) textBoxesList.get(2)).getText());
		aggregate.setFunction(((Text) textBoxesList.get(3)).getText());
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
		TResultUI tResultUI = (TResultUI) parentTagContainer;
		tResultUI.refreshChildren(HTEditorConstants.AGGREGATE_TITLE, compositeIndex, objectIndex);
		centralUtils.marshal(textEditor);
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);

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
			aggregate.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
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

		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Table table = new Table(detailAreaInnerComposite, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		for (int i = 0; i < 4; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(100);
		}

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblPart = new Label(table, SWT.NONE);
		lblPart.setText("  " + HTEditorConstants.PART_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblPart, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtPart = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtPart);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtPart, tblRow1, 1);

		tblEditor = new TableEditor(table);

		Label lblLocation = new Label(table, SWT.NONE);
		lblLocation.setText("  " + HTEditorConstants.LOCATION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblLocation, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtLocation = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtLocation);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtLocation, tblRow1, 3);

		TableItem tblRow2 = new TableItem(table, SWT.NONE);
		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tblEditor = new TableEditor(table);

		Label lblCondition = new Label(table, SWT.NONE);
		lblCondition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCondition.setText("  " + HTEditorConstants.CONDITION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblCondition, tblRow2, 0);

		tblEditor = new TableEditor(table);
		Text txtCondition = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtCondition);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtCondition, tblRow2, 1);

		tblEditor = new TableEditor(table);

		Label lblFunction = new Label(table, SWT.NONE);
		lblFunction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFunction.setText("  " + HTEditorConstants.FUNCTION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblFunction, tblRow2, 2);

		tblEditor = new TableEditor(table);
		Text txtFunction = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtFunction);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtFunction, tblRow2, 3);

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

		if (aggregate.getPart() != null) {
			((Text) textBoxesList.get(0)).setText(aggregate.getPart());
		}
		if (aggregate.getLocation() != null) {
			((Text) textBoxesList.get(1)).setText(aggregate.getLocation());
		}
		if (aggregate.getCondition() != null) {
			((Text) textBoxesList.get(2)).setText(aggregate.getCondition());
		}
		if (aggregate.getFunction() != null) {
			((Text) textBoxesList.get(3)).setText(aggregate.getFunction());
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
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		ArrayList<TDocumentation> documentationGroup =
		                                               (ArrayList<TDocumentation>) aggregate.getDocumentation();
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
			aggregate.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
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
		aggregate = (TAggregate) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(aggregate.getDocumentation()
				                                    .get(tDocumentationUI.getObjectIndex()));
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
