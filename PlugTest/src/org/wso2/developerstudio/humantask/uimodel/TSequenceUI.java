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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TExtensibleElements;
import org.wso2.developerstudio.humantask.models.TParallel;
import org.wso2.developerstudio.humantask.models.TRoutingPatternType;
import org.wso2.developerstudio.humantask.models.TSequence;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "Sequence" xml element in the .ht file All the
 * functionalities of that element are performed in this class, by implementing
 * and overriding the abstract super class methods.
 */
public class TSequenceUI extends AbstractParentTagSection {

	private int[] childObjectIndexes;
	public TSequence tSequence;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
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
	public TSequenceUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer, int styleBit,
			Object objectModel, int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer,
				styleBit, new String[] { "Completion behaviour", HTEditorConstants.FROM_TITLE,
						HTEditorConstants.SEQUENCE_TITLE, HTEditorConstants.PARALLEL_TITLE },
				HTEditorConstants.SEQUENCE_TITLE);
		this.tSequence = (TSequence) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[4];
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
		if (tSequence.getType() != null) {
			Combo combo = (Combo) textBoxesList.get(0);
			combo.select(combo.indexOf(tSequence.getType().value()));

		}

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
		AbstractParentTagSection parentTagSection = (AbstractParentTagSection) parentTagContainer;
		parentTagSection.refreshChildren(HTEditorConstants.SEQUENCE_TITLE, getCompositeIndex(), getObjectIndex());
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

		if (childComposites.size() == 0) {
			ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) tSequence.getDocumentation();
			for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
					.size(); documentationGroupIndex++) {
				TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, detailArea, childCompositeIndex,
						childObjectIndexes[0], SWT.NONE, this, documentationGroup.get(childObjectIndexes[0]));
				tDocumentationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tDocumentationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			/*
			 * if (task.getInterface() != null) { TTaskInterface interfaceObject
			 * = (TTaskInterface) task .getInterface(); TTaskInterfaceUI
			 * tTaskInterfaceUI = new TTaskInterfaceUI(editor, detailArea, this,
			 * SWT.NONE, interfaceObject, childObjectIndexes[1],
			 * childCompositeIndex); tTaskInterfaceUI.initialize(editor);
			 * childComposites.add(childCompositeIndex, tTaskInterfaceUI);
			 * childCompositeIndex++; childObjectIndexes[1]++; }
			 */

			ArrayList<TExtensibleElements> sequenceOrParallelGroup = (ArrayList<TExtensibleElements>) tSequence
					.getParallelOrSequence();
			for (int sequenceOrParallelIndex = 0; sequenceOrParallelIndex < sequenceOrParallelGroup
					.size(); sequenceOrParallelIndex++) {
				if (sequenceOrParallelGroup.get(sequenceOrParallelIndex).getClass() == TParallel.class) {
					TParallelUI tGenericHumanRoleAssignmentUI = new TParallelUI(editor, detailArea, this, SWT.NONE,
							sequenceOrParallelGroup.get(childObjectIndexes[2]), childObjectIndexes[2],
							childCompositeIndex);
					tGenericHumanRoleAssignmentUI.initialize(editor);
					childComposites.add(childCompositeIndex, tGenericHumanRoleAssignmentUI);
					childCompositeIndex++;
					childObjectIndexes[2]++;
				}
				if (sequenceOrParallelGroup.get(sequenceOrParallelIndex).getClass() == TSequence.class) {
					TSequenceUI tSequenceUI = new TSequenceUI(editor, detailArea, this, SWT.NONE,
							sequenceOrParallelGroup.get(childObjectIndexes[2]), childObjectIndexes[2],
							childCompositeIndex);
					tSequenceUI.initialize(editor);
					childComposites.add(childCompositeIndex, tSequenceUI);
					childCompositeIndex++;
					childObjectIndexes[2]++;
				}

			}
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
		if (tSequence.getType() != null) {
			((Text) textBoxesList.get(0)).setText(tSequence.getType().toString());
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
			tSequence.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, composite, childCompositeIndex,
					childObjectIndexes[0], SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			/*
			 * if (childObjectIndexes[1] < 1) { TTaskInterface tTaskInterface =
			 * new TTaskInterface(); tTaskInterface.setPortType(new QName(""));
			 * tTaskInterface.setOperation("");
			 * tParallel.setInterface(tTaskInterface); TTaskInterfaceUI
			 * tTaskInterfaceUI = new TTaskInterfaceUI(editor, composite, this,
			 * SWT.NONE, tTaskInterface, childObjectIndexes[1],
			 * childCompositeIndex); childComposites.add(childCompositeIndex,
			 * tTaskInterfaceUI); childObjectIndexes[1]++;
			 * childCompositeIndex++; }
			 */
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PARALLEL_TITLE)) {

			TParallel tParallel = new TParallel();
			tParallel.setType(TRoutingPatternType.fromValue("all"));
			this.tSequence.getParallelOrSequence().add(childObjectIndexes[2], tParallel);
			TParallelUI tPriorityExprUI = new TParallelUI(editor, detailArea, this, SWT.NONE, tParallel,
					childObjectIndexes[2], childCompositeIndex);
			childComposites.add(childCompositeIndex, tPriorityExprUI);
			childObjectIndexes[2]++;
			childCompositeIndex++;

		} else if (selection.equalsIgnoreCase(HTEditorConstants.SEQUENCE_TITLE)) {
			TSequence tSequence = new TSequence();
			tSequence.setType(TRoutingPatternType.fromValue("all"));
			this.tSequence.getParallelOrSequence().add(childObjectIndexes[2], tSequence);
			TSequenceUI tPresentationElementsUI = new TSequenceUI(editor, composite, this, SWT.NONE, tSequence,
					childObjectIndexes[2], childCompositeIndex);
			childComposites.add(childCompositeIndex, tPresentationElementsUI);
			childObjectIndexes[2]++;
			childCompositeIndex++;

		}
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
		lblType.setText("Language");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblType, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Combo cmbOwnerRequired = new Combo(table, SWT.NONE);
		cmbOwnerRequired.setItems(new String[] { "all", "single" });
		cmbOwnerRequired.select(0);
		textBoxesList.add(0, cmbOwnerRequired);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbOwnerRequired, tblRow1, 1);

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
			tSequence.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} /*
					 * else if (compositeInstance instanceof TTaskInterfaceUI) {
					 * TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI)
					 * compositeInstance; if
					 * (tTaskInterfaceUI.getCompositeIndex() >
					 * childCompositeIndex) {
					 * tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI
					 * .getCompositeIndex() - 1); }
					 * 
					 * }
					 */else if (compositeInstance instanceof TParallelUI) {
					TParallelUI tParallelUI = (TParallelUI) compositeInstance;
					if (tParallelUI.getCompositeIndex() > childCompositeIndex) {
						tParallelUI.setCompositeIndex(tParallelUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TSequenceUI) {
					TSequenceUI tSequenceUI = (TSequenceUI) compositeInstance;
					if (tSequenceUI.getCompositeIndex() > childCompositeIndex) {
						tSequenceUI.setCompositeIndex(tSequenceUI.getCompositeIndex() - 1);
					}

				}
			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.PARALLEL_TITLE)) {
			this.childObjectIndexes[2]--;
			tSequence.getParallelOrSequence().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TParallelUI) {
					TParallelUI tParallelUI = (TParallelUI) compositeInstance;
					if (tParallelUI.getCompositeIndex() > childCompositeIndex) {
						tParallelUI.setCompositeIndex(tParallelUI.getCompositeIndex() - 1);
					}
					if (tParallelUI.getObjectIndex() > childObjectIndex) {
						tParallelUI.setObjectIndex(tParallelUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TSequenceUI) {
					TSequenceUI tSequenceUI = (TSequenceUI) compositeInstance;
					if (tSequenceUI.getCompositeIndex() > childCompositeIndex) {
						tSequenceUI.setCompositeIndex(tSequenceUI.getCompositeIndex() - 1);
					}
					if (tSequenceUI.getObjectIndex() > childObjectIndex) {
						tSequenceUI.setObjectIndex(tSequenceUI.getObjectIndex() - 1);
					}

				}
			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.SEQUENCE_TITLE)) {
			this.childObjectIndexes[2]--;
			tSequence.getParallelOrSequence().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TParallelUI) {
					TParallelUI tParallelUI = (TParallelUI) compositeInstance;
					if (tParallelUI.getCompositeIndex() > childCompositeIndex) {
						tParallelUI.setCompositeIndex(tParallelUI.getCompositeIndex() - 1);
					}
					if (tParallelUI.getObjectIndex() > childObjectIndex) {
						tParallelUI.setObjectIndex(tParallelUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TSequenceUI) {
					TSequenceUI tSequenceUI = (TSequenceUI) compositeInstance;
					if (tSequenceUI.getCompositeIndex() > childCompositeIndex) {
						tSequenceUI.setCompositeIndex(tSequenceUI.getCompositeIndex() - 1);
					}
					if (tSequenceUI.getObjectIndex() > childObjectIndex) {
						tSequenceUI.setObjectIndex(tSequenceUI.getObjectIndex() - 1);
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
		tSequence = (TSequence) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(tSequence.getDocumentation().get(tDocumentationUI.getObjectIndex()));
			} /*
				 * else if (compositeInstance instanceof TTaskInterfaceUI) {
				 * TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI)
				 * compositeInstance; tTaskInterfaceUI.refreshLogic(textEditor);
				 * tTaskInterfaceUI.loadModel(task.getInterface()); }
				 */else if (compositeInstance instanceof TParallelUI) {
				TParallelUI tParallelUI = (TParallelUI) compositeInstance;
				tParallelUI.tParallel = (TParallel) tSequence.getParallelOrSequence().get(tParallelUI.getObjectIndex());
				tParallelUI.onPageRefresh(textEditor);
				tParallelUI.loadModel(tSequence.getParallelOrSequence().get(tParallelUI.getObjectIndex()));
			} else if (compositeInstance instanceof TSequenceUI) {
				TSequenceUI tSequenceUI = (TSequenceUI) compositeInstance;
				tSequenceUI.tSequence = (TSequence) tSequence.getParallelOrSequence().get(tSequenceUI.getObjectIndex());
				tSequenceUI.onPageRefresh(textEditor);
				tSequenceUI.loadModel(tSequence.getParallelOrSequence().get(tSequenceUI.getObjectIndex()));

			}
		}
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

}
