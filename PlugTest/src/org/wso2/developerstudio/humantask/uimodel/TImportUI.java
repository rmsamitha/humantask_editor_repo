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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.THumanInteractionsUI;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TImport;

/**
 * The UI class representing the "import" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TImportUI extends AbstractEndTagSection {

	private Composite parentTagContainer;
	public TImport tImport;
	protected int compositeIndex;
	protected int objectIndex;

	public TImportUI(XMLEditor textEditor, Composite parentComposite, int compositeIndex,
	                 int objectIndex, int styleBit, Composite parentTagContainer, Object modelParent)
	                                                                                                 throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      HTEditorConstants.IMPORT_TITLE);
		this.objectIndex = objectIndex;
		this.tImport = (TImport) modelParent;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex = compositeIndex;
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
		tImport.setImportType(((Combo) textBoxesList.get(0)).getItem(((Combo) textBoxesList.get(0)).getSelectionIndex()));
		tImport.setLocation(((Text) textBoxesList.get(1)).getText());
		tImport.setNamespace(((Text) textBoxesList.get(2)).getText());
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
		THumanInteractionsUI parentContainer = (THumanInteractionsUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.IMPORT_TITLE, compositeIndex, objectIndex);
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
		if (tImport.getImportType() != null) {
			Combo comboBox = (Combo) textBoxesList.get(0);
			comboBox.select(comboBox.indexOf(tImport.getImportType().toString()));
		}
		if (tImport.getNamespace() != null) {
			((Text) textBoxesList.get(2)).setText(tImport.getNamespace() + "");
		}
		if (tImport.getLocation() != null) {
			((Text) textBoxesList.get(1)).setText(tImport.getLocation() + "");
		}

	}

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element content (if available) in the
	 * section
	 */
	@Override
	public void fillDetailArea() {
		final Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		// for (int i = 0; i < 4; i++) {
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(250);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(350);

		// }

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblImportType = new Label(table, SWT.NONE);
		lblImportType.setText("  " + HTEditorConstants.IMPORT_TYPE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblImportType, tblRow1, 0);

		tblEditor = new TableEditor(table);
		final Combo cmbImportType = new Combo(table, SWT.NONE);
		cmbImportType.setItems(new String[] { HTEditorConstants.HTTP_SCHEMAS_XMLSOAP_ORG_WSDL, "" });
		cmbImportType.select(0);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbImportType, tblRow1, 1);
		textBoxesList.add(0, cmbImportType);

		tblEditor = new TableEditor(table);
		Label lblLocation = new Label(table, SWT.NONE);
		lblLocation.setText("     " + HTEditorConstants.LOCATION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblLocation, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Composite comps = formToolkit.createComposite(table);
		comps.setLayout(new GridLayout(2, true));
		final Text txtLocation = new Text(comps, SWT.BORDER);
		//
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 250;
		txtLocation.setLayoutData(gd_text_1);
		//

		org.eclipse.swt.widgets.Button btnLocation =
		                                             formToolkit.createButton(comps, "Browse",
		                                                                      SWT.NONE);
		GridData gd_btnLocation = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnLocation.widthHint = 60;
		btnLocation.setLayoutData(gd_btnLocation);

		textBoxesList.add(1, txtLocation);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(comps, tblRow1, 3);

		TableItem tblRow2 = new TableItem(table, SWT.NONE);
		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		tblEditor = new TableEditor(table);
		Label lblNamespace = new Label(table, SWT.NONE);
		lblNamespace.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNamespace.setText("  " + HTEditorConstants.NAMESPACE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblNamespace, tblRow2, 0);

		tblEditor = new TableEditor(table);
		Text txtNamespace = new Text(table, SWT.BORDER);
		textBoxesList.add(2, txtNamespace);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtNamespace, tblRow2, 1);

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

		table.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.height = 30;

			}
		});

		btnLocation.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
				dialog.setFilterPath("c:\\temp");
				if (cmbImportType.getText().equals(HTEditorConstants.HTTP_SCHEMAS_XMLSOAP_ORG_WSDL)) {
					dialog.setFilterExtensions(new String[] { "*.wsdl" });
					dialog.setText("Select WSDL");
				} else {
					dialog.setFilterExtensions(new String[] { "*" });
					dialog.setText("Select File");
				}

				String result = dialog.open();
				if (result != null) {
					txtLocation.setText(result);
				}
			}
		});
		btnUpdate.setToolTipText("Update XML : You may need to go back to text \neditor and come back once to \naffect this updation on Notification Interface \ntag and Task Interface tag correctly.  ");
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) {
		tImport = (TImport) model;
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
