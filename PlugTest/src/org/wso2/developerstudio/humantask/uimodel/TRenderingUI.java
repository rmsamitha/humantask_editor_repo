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
import org.wso2.developerstudio.humantask.models.TRendering;

/**
 * The UI class representing the "rendering" xml element in the .ht file All the
 * functionalities of that element are performed in this class, by implementing
 * and overriding the abstract super class methods.
 */
public class TRenderingUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TRendering rendering;
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
	public TRenderingUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer, int styleBit,
			Object objectModel, int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE }, HTEditorConstants.RENDERING_TITLE);
		this.rendering = (TRendering) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
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
		rendering.setType(new QName(((Text) textBoxesList.get(0)).getText()));
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
		AbstractParentTagSection transition = (AbstractParentTagSection) parentTagContainer;
		transition.refreshChildren(HTEditorConstants.RENDERING_TITLE, getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	/**
	 * Whenever a tab change occur from text editor to UI editor, this method is
	 * invoked. It disposes all the child Sections in this section and recreate
	 * them and call initialize() of each of them to reinitialize their
	 * attribute values, according to the single model maintained by both the UI
	 * editor and text .editor
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

		ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) rendering.getDocumentation();
		for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
				.size(); documentationGroupIndex++) {
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, detailArea, childCompositeIndex,
					childObjectIndexes[0], SWT.NONE, this, documentationGroup.get(childObjectIndexes[0]));
			tDocumentationUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}
	}

	/**
	 * Initialize or set the values of attributes and xml content(if available)
	 * whenever a tab change occur from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if (rendering.getType() != null) {
			((Text) textBoxesList.get(0)).setText(rendering.getType().getLocalPart().toString());
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
	public void onCreateNewChild(String selection, ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			rendering.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, composite, childCompositeIndex,
					childObjectIndexes[0], SWT.NONE, this, tDocumentation);
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

		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		for (int i = 0; i < 2; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(100);
		}

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblType = new Label(table, SWT.NONE);
		lblType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblType.setText("Type");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblType, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtType = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtType);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtType, tblRow1, 1);

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
			rendering.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
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
		rendering = (TRendering) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(rendering.getDocumentation().get(tDocumentationUI.getObjectIndex()));
			}
		}
	}

	/**
	 * Set this Section's object index(index of only this type of class objects
	 * in the parent) as per the order created in This Section's parent.
	 * 
	 * @param objectIndex
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
	 * class objects created in the parent Section) as per the order created in
	 * this object's parent
	 * 
	 * @return This section's(composite's) index
	 */
	public int getCompositeIndex() {
		return compositeIndex;
	}

	/**
	 * Set this section's(composite's) index (index of any type of child class
	 * objects created in the parent Section) as per the order created in this
	 * object's parent
	 * 
	 * @param compositeIndex
	 */
	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

}
