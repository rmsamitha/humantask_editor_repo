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
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TPresentationParameter;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "presentationParameter" xml element in the .ht
 * file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TPresentationParameterUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TPresentationParameter presentationParameter;
	protected int compositeIndex;
	protected int objectIndex;

	public TPresentationParameterUI(XMLEditor textEditor, Composite parentComposite,
	                                int compositeIndex, int objectIndex, int styleBit,
	                                Composite parentTagContainer, Object modelParent)
	                                                                                 throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      HTEditorConstants.PRESENTATION_PARAMETER_TITLE);
		this.objectIndex = objectIndex;
		this.presentationParameter = (TPresentationParameter) modelParent;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex = compositeIndex;
		setExpanded(false);
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
		if (presentationParameter.getName() != null) {
			presentationParameter.setName(((Text) textBoxesList.get(0)).getText());
		}
		presentationParameter.setType(new QName(((Text) textBoxesList.get(1)).getText()));
		if (presentationParameter.getContent().size() != 0) {
			presentationParameter.getContent().remove(0);
		}
		presentationParameter.getContent().add(0, ((Text) textBoxesList.get(2)).getText());
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
		TPresentationParametersUI parentContainer = (TPresentationParametersUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.PRESENTATION_PARAMETER_TITLE,
		                                compositeIndex, objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentTagComposite = this.getParent();
		this.dispose();
		parentTagComposite.layout(true, true);
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
		if (presentationParameter.getName() != null) {
			((Text) textBoxesList.get(0)).setText(presentationParameter.getName());
		}
		if (presentationParameter.getType() != null) {
			((Text) textBoxesList.get(1)).setText(presentationParameter.getType().getLocalPart());
		}
		if (presentationParameter.getContent().size() != 0) {
			String content = (String) presentationParameter.getContent().get(0);
			((Text) textBoxesList.get(2)).setText(content.trim());
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
		new TableColumn(table, SWT.NONE).setWidth(250);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		TableEditor tblEditor = new TableEditor(table);

		Label lblName = new Label(table, SWT.NONE);
		lblName.setText("  " + HTEditorConstants.NAME_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblName, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtName = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtName);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtName, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblType = new Label(table, SWT.NONE);
		lblType.setText("     " + HTEditorConstants.TYPE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblType, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtType = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtType);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtType, tblRow1, 3);

		TableItem tblRow2 = new TableItem(table, SWT.NONE);

		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblEditor = new TableEditor(table);

		Label lblValue = new Label(table, SWT.NONE);
		lblValue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue.setText("  " + HTEditorConstants.VALUE_ATTRIBUTE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue, tblRow2, 0);

		tblEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(2, txtValue);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtValue, tblRow2, 1);

		tblEditor = new TableEditor(table);
		Label lblValue2 = new Label(table, SWT.NONE);
		lblValue2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue2.setText("");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue2, tblRow2, 2);

		tblEditor = new TableEditor(table);
		Label lblValue3 = new Label(table, SWT.NONE);
		lblValue3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue3.setText("");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue3, tblRow2, 3);
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) {
		presentationParameter = (TPresentationParameter) model;
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
