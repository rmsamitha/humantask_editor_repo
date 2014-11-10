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
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TText;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "name" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TNameUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TText tName;
	protected int compositeIndex;
	protected int objectIndex;

	public TNameUI(XMLEditor textEditor, Composite parentComposite, int compositeIndex,
	               int objectIndex, int style, Composite container, Object modelParent)
	                                                                                   throws JAXBException {
		super(textEditor, parentComposite, container, style, HTEditorConstants.NAME_TAG_TITLE);
		this.objectIndex = objectIndex;
		this.tName = (TText) modelParent;
		this.parentTagContainer = container;
		this.compositeIndex = compositeIndex;
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
	public void onBtnUpdate(XMLEditor textEditor) throws JAXBException {
		// tName.setLang(((Text) textBoxesList.get(0)).getText());
		tName.setLang(((Combo) textBoxesList.get(0)).getText());

		if (tName.getContent().size() != 0) {
			tName.getContent().remove(0);
		}
		tName.getContent().add(0, ((Text) textBoxesList.get(1)).getText());
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
		TPresentationElementsUI parentContainer = (TPresentationElementsUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.NAME_TAG_TITLE, compositeIndex,
		                                objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
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
		if (tName.getLang() != null) {
			((Combo) textBoxesList.get(0)).setText(tName.getLang());
		}

		if (tName.getContent().size() != 0) {
			((Text) textBoxesList.get(1)).setText(tName.getContent().get(0).toString());
		}

	}

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element content (if available) in the
	 * section
	 */
	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(250);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		tblEditor = new TableEditor(table);
		Label lblXmlLang = new Label(table, SWT.NONE);
		lblXmlLang.setText("      " + HTEditorConstants.LANGUAGE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblXmlLang, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Combo cmbXmlLang = new Combo(table, SWT.NONE);
		cmbXmlLang.setItems(new String[] { "en-US", "en-GB", "de-DE", "fr-CA", "zh-Hant" });
		cmbXmlLang.select(0);
		textBoxesList.add(0, cmbXmlLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbXmlLang, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblReference = new Label(table, SWT.NONE);
		lblReference.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblReference.setText("     " + HTEditorConstants.VALUE_TAG_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblReference, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtReference = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtReference);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtReference, tblRow1, 3);
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) {
		tName = (TText) model;
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