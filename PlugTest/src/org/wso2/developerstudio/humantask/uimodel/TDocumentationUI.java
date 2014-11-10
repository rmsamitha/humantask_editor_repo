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

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;

/**
 * The UI class representing the "documentation" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TDocumentationUI extends AbstractEndTagSection {
	private Composite parentTagContainer; // parent Section(xml element)
	private TDocumentation documentation; // model class related to this UI
	// Section
	private int compositeIndex;/*
							    * this section's(composite's) index (index of
							    * any type of child class objects created in
							    * the parent Section) as per the order created
							    * in this object's parent
							    */
	private int objectIndex;/*
							 * this Section's object index(index of only this
							 * type of class objects in the parent) as per the
							 * order created in This Section's parent.
							 */

	/**
	 * Call the super abstract class to set the UI and initialize class's
	 * attribute variables
	 * 
	 * @param textEditor
	 * @param parentComposite
	 * @param compositeIndex
	 * @param objectIndex
	 * @param styleBit
	 * @param parentTagContainer
	 * @param objectModel
	 * @throws JAXBException
	 */
	public TDocumentationUI(XMLEditor textEditor, Composite parentComposite, int compositeIndex,
	                        int objectIndex, int styleBit, Composite parentTagContainer,
	                        Object objectModel) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      HTEditorConstants.DOCUMENTATION_TITLE);
		this.objectIndex = objectIndex;
		documentation = (TDocumentation) objectModel;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex = compositeIndex;
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
	public void onBtnUpdate(XMLEditor textEditor) throws JAXBException {
		/* Set the Lang attribute value of this xml element */
		documentation.setLang(((Combo) textBoxesList.get(0)).getText());

		/* Set the content value of this xml element */
		if (documentation.getContent().size() > 0) {
			documentation.getContent().remove(0);
		}
		documentation.getContent().add(0, ((Text) textBoxesList.get(1)).getText());

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
		AbstractParentTagSection parentContainer = (AbstractParentTagSection) parentTagContainer;

		// calls the refreshChildren() method of this Section's parent Section
		parentContainer.refreshChildren(HTEditorConstants.DOCUMENTATION_TITLE, compositeIndex,
		                                objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();// dispose this UI Section
		parentComposite.layout(true, true);
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
		if (documentation.getLang() != null) {
			// get the "lang" attribute value from the model and fill it into
			// the corresponding combo box
			((Combo) textBoxesList.get(0)).setText(documentation.getLang());
		}
		if (documentation.getContent().size() != 0) {
			// get the xml element content value from the model and fill it
			// into the corresponding text box
			((Text) textBoxesList.get(1)).setText((String) documentation.getContent().get(0));
		}
	}

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes in the section
	 */
	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		// create table
		Table table = new Table(detailAreaInnerComposite, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);

		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(200);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblName = new Label(table, SWT.NONE);
		lblName.setText("  " + HTEditorConstants.LANGUAGE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblName, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Combo cmbXmlLang = new Combo(table, SWT.NONE);
		cmbXmlLang.setItems(new String[] { "en-US", "en-GB", "de-DE", "fr-CA", "zh-Hant" });
		cmbXmlLang.select(0);
		textBoxesList.add(0, cmbXmlLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbXmlLang, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblReference = new Label(table, SWT.NONE);
		lblReference.setText("     " + HTEditorConstants.VALUE_TAG_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblReference, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtValue);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtValue, tblRow1, 3);

		table.setLinesVisible(true);

	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) {
		// assign the model TDocumentation object to this Section's
		// documentation variable
		documentation = (TDocumentation) model;
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
