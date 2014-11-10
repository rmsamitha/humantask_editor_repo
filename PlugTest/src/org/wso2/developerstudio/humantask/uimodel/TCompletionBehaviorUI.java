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
import org.wso2.developerstudio.humantask.models.TBooleanExpr;
import org.wso2.developerstudio.humantask.models.TCompletion;
import org.wso2.developerstudio.humantask.models.TCompletionBehavior;
import org.wso2.developerstudio.humantask.models.TDefaultCompletion;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TPattern;
import org.wso2.developerstudio.humantask.models.TResult;

/**
 * The UI class representing the "completionBehavior" xml element in the .ht
 * file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TCompletionBehaviorUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TCompletionBehavior completionBehavior;
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
	public TCompletionBehaviorUI(XMLEditor textEditor, Composite parentComposite,
	                             Composite parentTagContainer, int styleBit, Object objectModel,
	                             int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
		                    HTEditorConstants.COMPLETION_TITLE,
		                    HTEditorConstants.DEFAULT_COMPLETION_TITLE },
		      HTEditorConstants.COMPLETION_BEHAVIOR_TITLE);
		this.completionBehavior = (TCompletionBehavior) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
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
		completionBehavior.setCompletionAction(TPattern.fromValue(((Combo) textBoxesList.get(0)).getText()));
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
		TTaskUI parentContainer = (TTaskUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.COMPLETION_BEHAVIOR_TITLE,
		                                compositeIndex, objectIndex);
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
		if (completionBehavior.getCompletionAction() != null) {
			Combo comboBox = (Combo) textBoxesList.get(0);
			comboBox.select(comboBox.indexOf(completionBehavior.getCompletionAction().value()));
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

		Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(1, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Table table = new Table(innerZComp, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(125);

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblcompletionAction = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblcompletionAction.setText("  " + HTEditorConstants.COMPLETION_ACTION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblcompletionAction, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Combo cmbcompletionAction = new Combo(table, SWT.NONE);
		cmbcompletionAction.setItems(new String[] { TPattern.AUTOMATIC.toString().toLowerCase(),
		                                           TPattern.MANUAL.toString().toLowerCase() });
		cmbcompletionAction.select(0);
		textBoxesList.add(0, cmbcompletionAction);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbcompletionAction, tblRow1, 1);

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
		                                               (ArrayList<TDocumentation>) completionBehavior.getDocumentation();
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
		ArrayList<TCompletion> completionGroup =
		                                         (ArrayList<TCompletion>) completionBehavior.getCompletion();
		for (int i = 0; i < completionGroup.size(); i++) {
			TCompletionUI tCompletionUI =
			                              new TCompletionUI(
			                                                editor,
			                                                detailArea,
			                                                this,
			                                                SWT.NONE,
			                                                completionGroup.get(childObjectIndexes[1]),
			                                                childObjectIndexes[1],
			                                                childCompositeIndex);
			tCompletionUI.initialize(editor);
			childComposites.add(childCompositeIndex, tCompletionUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		}
		if (completionBehavior.getDefaultCompletion() != null) {
			TDefaultCompletion tDefaultCompletion = completionBehavior.getDefaultCompletion();
			TDefaultCompletionUI tDefaultCompletionUI =
			                                            new TDefaultCompletionUI(
			                                                                     editor,
			                                                                     detailArea,
			                                                                     this,
			                                                                     SWT.NONE,
			                                                                     tDefaultCompletion,
			                                                                     childObjectIndexes[2],
			                                                                     childCompositeIndex);
			tDefaultCompletionUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDefaultCompletionUI);
			childCompositeIndex++;
			childObjectIndexes[2]++;
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
			completionBehavior.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.COMPLETION_TITLE)) {
			TCompletion tCompletion = new TCompletion();
			tCompletion.setCondition(new TBooleanExpr());
			tCompletion.setResult(new TResult());
			completionBehavior.getCompletion().add(childObjectIndexes[1], tCompletion);
			TCompletionUI tCompletionUI =
			                              new TCompletionUI(editor, composite, this, SWT.NONE,
			                                                tCompletion, childObjectIndexes[1],
			                                                childCompositeIndex);
			childComposites.add(childCompositeIndex, tCompletionUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.DEFAULT_COMPLETION_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TDefaultCompletion tDefaultCompletion = new TDefaultCompletion();
				tDefaultCompletion.setResult(new TResult());
				completionBehavior.setDefaultCompletion(tDefaultCompletion);
				TDefaultCompletionUI tDefaultCompletionUI =
				                                            new TDefaultCompletionUI(
				                                                                     editor,
				                                                                     composite,
				                                                                     this,
				                                                                     SWT.NONE,
				                                                                     tDefaultCompletion,
				                                                                     childObjectIndexes[2],
				                                                                     childCompositeIndex);
				childComposites.add(childCompositeIndex, tDefaultCompletionUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		}

		centralUtils.marshal(editor);
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
			completionBehavior.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TCompletionUI) {
					TCompletionUI tCompletionUI = (TCompletionUI) compositeInstance;
					if (tCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionUI.setCompositeIndex(tCompletionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDefaultCompletionUI) {
					TDefaultCompletionUI tDefaultCompletionUI =
					                                            (TDefaultCompletionUI) compositeInstance;
					if (tDefaultCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tDefaultCompletionUI.setCompositeIndex(tDefaultCompletionUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.COMPLETION_TITLE)) {
			this.childObjectIndexes[1]--;
			completionBehavior.getCompletion().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TCompletionUI) {
					TCompletionUI tCompletionUI = (TCompletionUI) compositeInstance;
					if (tCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionUI.setCompositeIndex(tCompletionUI.getCompositeIndex() - 1);
					}
					if (tCompletionUI.getObjectIndex() > childObjectIndex) {
						tCompletionUI.setObjectIndex(tCompletionUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TDefaultCompletionUI) {
					TDefaultCompletionUI tDefaultCompletionUI =
					                                            (TDefaultCompletionUI) compositeInstance;
					if (tDefaultCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tDefaultCompletionUI.setCompositeIndex(tDefaultCompletionUI.getCompositeIndex() - 1);
					}

				} else {
				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.DEFAULT_COMPLETION_TITLE)) {
			this.childObjectIndexes[2]--;
			completionBehavior.setDefaultCompletion(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TCompletionUI) {
					TCompletionUI tCompletionUI = (TCompletionUI) compositeInstance;
					if (tCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionUI.setCompositeIndex(tCompletionUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TDefaultCompletionUI) {
					TDefaultCompletionUI tDefaultCompletionUI =
					                                            (TDefaultCompletionUI) compositeInstance;
					if (tDefaultCompletionUI.getCompositeIndex() > childCompositeIndex) {
						tDefaultCompletionUI.setCompositeIndex(tDefaultCompletionUI.getCompositeIndex() - 1);
					}
					if (tDefaultCompletionUI.getObjectIndex() > childObjectIndex) {
						tDefaultCompletionUI.setObjectIndex(tDefaultCompletionUI.getObjectIndex() - 1);
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
		completionBehavior = (TCompletionBehavior) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(completionBehavior.getDocumentation()
				                                             .get(tDocumentationUI.getObjectIndex()));
			}
			if (compositeInstance instanceof TCompletionUI) {
				TCompletionUI tCompletionUI = (TCompletionUI) compositeInstance;
				tCompletionUI.completion =
				                           completionBehavior.getCompletion()
				                                             .get(tCompletionUI.getObjectIndex());
				tCompletionUI.onPageRefresh(textEditor);
				tCompletionUI.loadModel(completionBehavior.getCompletion()
				                                          .get(tCompletionUI.getObjectIndex()));
			}
			if (compositeInstance instanceof TDefaultCompletionUI) {
				TDefaultCompletionUI tDefaultCompletionUI =
				                                            (TDefaultCompletionUI) compositeInstance;
				tDefaultCompletionUI.defaultCompletion = completionBehavior.getDefaultCompletion();
				tDefaultCompletionUI.onPageRefresh(textEditor);
				tDefaultCompletionUI.loadModel(completionBehavior.getDefaultCompletion());
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
