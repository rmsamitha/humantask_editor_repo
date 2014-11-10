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
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDefaultCompletion;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TResult;

/**
 * The UI class representing the "defaultCompletion" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TDefaultCompletionUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TDefaultCompletion defaultCompletion;
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
	public TDefaultCompletionUI(XMLEditor textEditor, Composite parentComposite,
	                            Composite parentTagContainer, int styleBit, Object objectModel,
	                            int objectIndex, int compositeIndex) throws JAXBException {
		super(
		      textEditor,
		      parentComposite,
		      parentTagContainer,
		      styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE, HTEditorConstants.RESULT_TITLE },
		      HTEditorConstants.DEFAULT_COMPLETION_TITLE);
		this.defaultCompletion = (TDefaultCompletion) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[2];
		setExpanded(true);
	}

	@Override
	public void onBtnUpdate(XMLEditor textEditor) throws JAXBException {
	}

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void onBtnRemove(XMLEditor textEditor) throws JAXBException {
		TCompletionBehaviorUI tCompletionBehaviorUI = (TCompletionBehaviorUI) parentTagContainer;
		tCompletionBehaviorUI.refreshChildren(HTEditorConstants.DEFAULT_COMPLETION_TITLE,
		                                      getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
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
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			defaultCompletion.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.RESULT_TITLE)) {
			TResult tResult = new TResult();

			defaultCompletion.setResult(tResult);
			TResultUI tResultUI =
			                      new TResultUI(editor, composite, this, SWT.NONE, tResult,
			                                    childObjectIndexes[1], childCompositeIndex);
			childComposites.add(childCompositeIndex, tResultUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		}

		centralUtils.marshal(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		btnUpdate.dispose();/*
							 * dispose update button as it is not required to
							 * this Section as there is no any
							 * attribute or xml content in this Section
							 */
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
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

		if (childComposites.size() == 0) {
			ArrayList<TDocumentation> documentationGroup =
			                                               (ArrayList<TDocumentation>) defaultCompletion.getDocumentation();
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

			if (defaultCompletion.getResult() != null) {
				TResult resultObject = defaultCompletion.getResult();
				TResultUI tResultUI =
				                      new TResultUI(editor, detailArea, this, SWT.NONE,
				                                    resultObject, childObjectIndexes[1],
				                                    childCompositeIndex);
				tResultUI.initialize(editor);
				childComposites.add(childCompositeIndex, tResultUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}
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
			defaultCompletion.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TResultUI) {
					TResultUI tResultUI = (TResultUI) compositeInstance;
					if (tResultUI.getCompositeIndex() > childCompositeIndex) {
						tResultUI.setCompositeIndex(tResultUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}

		} else if (itemName.equalsIgnoreCase(HTEditorConstants.RESULT_TITLE)) {
			this.childObjectIndexes[1]--;
			defaultCompletion.setResult(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TResultUI) {
					TResultUI tResultUI = (TResultUI) compositeInstance;
					if (tResultUI.getCompositeIndex() > childCompositeIndex) {
						tResultUI.setCompositeIndex(tResultUI.getCompositeIndex() - 1);
					}
					if (tResultUI.getObjectIndex() > childObjectIndex) {
						tResultUI.setObjectIndex(tResultUI.getObjectIndex() - 1);
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
		defaultCompletion = (TDefaultCompletion) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(defaultCompletion.getDocumentation()
				                                            .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TResultUI) {
				TResultUI tResultUI = (TResultUI) compositeInstance;
				tResultUI.result = defaultCompletion.getResult();
				tResultUI.onPageRefresh(textEditor);
				tResultUI.loadModel(defaultCompletion.getResult());
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
