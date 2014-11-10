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
import org.wso2.developerstudio.humantask.models.TBooleanExpr;

/**
 * The UI class representing the "condition" xml element of type tBoolean-expr
 * in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TBooleanExprUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	public TBooleanExpr tBooleanExpr;
	protected int compositeIndex;
	protected int objectIndex;

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
	public TBooleanExprUI(XMLEditor textEditor, Composite parentComposite, int compositeIndex,
	                      int objectIndex, int styleBit, Composite parentTagContainer,
	                      Object modelParent) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      HTEditorConstants.BOOLEAN_EXPR_TITLE);
		this.objectIndex = objectIndex;
		this.tBooleanExpr = (TBooleanExpr) modelParent;
		this.parentTagContainer = parentTagContainer;
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
		tBooleanExpr.setExpressionLanguage(((Text) textBoxesList.get(0)).getText());
		if (tBooleanExpr.getContent().size() != 0) {
			tBooleanExpr.getContent().remove(0);
		}
		tBooleanExpr.getContent().add(0, ((Text) textBoxesList.get(1)).getText());
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
		parentContainer.refreshChildren(HTEditorConstants.BOOLEAN_EXPR_TITLE, compositeIndex,
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
		if (tBooleanExpr.getExpressionLanguage() != null) {
			((Text) textBoxesList.get(0)).setText(tBooleanExpr.getExpressionLanguage());
		}
		if (tBooleanExpr.getContent().size() != 0) {
			((Text) textBoxesList.get(1)).setText((String) tBooleanExpr.getContent().get(0));
		}
	}

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the
	 * section
	 */
	@Override
	public void fillDetailArea() {

		Composite innerZComp = formToolkit.createComposite(detailArea);
		innerZComp.setLayout(new GridLayout(1, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Table table = new Table(innerZComp, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		for (int i = 0; i < 4; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(100);

		}

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblExprLang = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblExprLang.setText("  " + HTEditorConstants.EXPRESSION_LANGUAGE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblExprLang, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtExprLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtExprLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtExprLang, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblValue = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblValue.setText("  " + HTEditorConstants.VALUE_TAG_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtValue);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtValue, tblRow1, 3);
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) {
		tBooleanExpr = (TBooleanExpr) model;
	}

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
