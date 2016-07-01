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
import org.wso2.developerstudio.humantask.models.TAggregate;
import org.wso2.developerstudio.humantask.models.TCopy;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TExpression;
import org.wso2.developerstudio.humantask.models.TExtensibleElements;
import org.wso2.developerstudio.humantask.models.TQuery;
import org.wso2.developerstudio.humantask.models.TResult;

/**
 * The UI class representing the "result" xml element in the .ht file All the
 * functionalities of that element are performed in this class, by implementing
 * and overriding the abstract super class methods.
 */
public class TResultUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TResult result;
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
	public TResultUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer, int styleBit,
			Object objectModel, int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite,
				parentTagContainer, styleBit, new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.AGGREGATE_TITLE, HTEditorConstants.COPY_TITLE },
				HTEditorConstants.RESULT_TITLE);
		this.result = (TResult) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[3];
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
		AbstractParentTagSection parentContainer = (AbstractParentTagSection) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.RESULT_TITLE, compositeIndex, objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {

	}

	@Override
	public void fillDetailArea(Composite composite) {
		/*
		 * dispose update button as it is not required to this Section as there
		 * is no any attribute or xml content in this Section
		 */
		btnUpdate.dispose();
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

			ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) result.getDocumentation();
			for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
					.size(); documentationGroupIndex++) {
				TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, detailArea, childCompositeIndex,
						childObjectIndexes[0], SWT.NONE, this, documentationGroup.get(childObjectIndexes[0]));
				tDocumentationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tDocumentationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}
			ArrayList<TExtensibleElements> aggregateOrCopyGroup = (ArrayList<TExtensibleElements>) result
					.getAggregateOrCopy();
			for (int i = 0; i < aggregateOrCopyGroup.size(); i++) {
				if (aggregateOrCopyGroup.get(i).getClass() == TAggregate.class) {
					TAggregateUI tAggregateUI = new TAggregateUI(editor, detailArea, this, SWT.NONE,
							aggregateOrCopyGroup.get(childObjectIndexes[1]), childObjectIndexes[1],
							childCompositeIndex);
					tAggregateUI.initialize(editor);
					childComposites.add(childCompositeIndex, tAggregateUI);
					childCompositeIndex++;
					childObjectIndexes[1]++;
				}
				if (aggregateOrCopyGroup.get(i).getClass() == TCopy.class) {
					TCopyUI tCopyUI = new TCopyUI(editor, detailArea, this, SWT.NONE,
							aggregateOrCopyGroup.get(childObjectIndexes[1]), childObjectIndexes[1],
							childCompositeIndex);
					tCopyUI.initialize(editor);
					childComposites.add(childCompositeIndex, tCopyUI);
					childCompositeIndex++;
					childObjectIndexes[1]++;
				}
			}

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
			result.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor, composite, childCompositeIndex,
					childObjectIndexes[0], SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.AGGREGATE_TITLE)) {
			TAggregate tAggregate = new TAggregate();
			tAggregate.setPart("");
			tAggregate.setLocation("");
			tAggregate.setCondition("");
			tAggregate.setFunction("");

			result.getAggregateOrCopy().add(childObjectIndexes[1], tAggregate);
			TAggregateUI tAggregateUI = new TAggregateUI(editor, composite, this, SWT.NONE, tAggregate,
					childObjectIndexes[1], childCompositeIndex);
			childComposites.add(childCompositeIndex, tAggregateUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.COPY_TITLE)) {

			TCopy tCopy = new TCopy();
			tCopy.setFrom(new TExpression());
			tCopy.setTo(new TQuery());
			result.getAggregateOrCopy().add(childObjectIndexes[1], tCopy);
			TCopyUI tCopyUI = new TCopyUI(editor, composite, this, SWT.NONE, tCopy, childObjectIndexes[1],
					childCompositeIndex);
			childComposites.add(childCompositeIndex, tCopyUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;

		}

		centralUtils.marshal(editor);
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
			result.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TAggregateUI) {
					TAggregateUI tAggregateUI = (TAggregateUI) compositeInstance;
					if (tAggregateUI.getCompositeIndex() > childCompositeIndex) {
						tAggregateUI.setCompositeIndex(tAggregateUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCopyUI) {
					TCopyUI tCopyUI = (TCopyUI) compositeInstance;
					if (tCopyUI.getCompositeIndex() > childCompositeIndex) {
						tCopyUI.setCompositeIndex(tCopyUI.getCompositeIndex() - 1);
					}

				}
			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.AGGREGATE_TITLE)) {
			this.childObjectIndexes[1]--;
			result.getAggregateOrCopy().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TAggregateUI) {
					TAggregateUI tAggregateUI = (TAggregateUI) compositeInstance;
					if (tAggregateUI.getCompositeIndex() > childCompositeIndex) {
						tAggregateUI.setCompositeIndex(tAggregateUI.getCompositeIndex() - 1);
					}
					if (tAggregateUI.getObjectIndex() > childObjectIndex) {
						tAggregateUI.setObjectIndex(tAggregateUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TCopyUI) {
					TCopyUI tCopyUI = (TCopyUI) compositeInstance;
					if (tCopyUI.getCompositeIndex() > childCompositeIndex) {
						tCopyUI.setCompositeIndex(tCopyUI.getCompositeIndex() - 1);
					}
					if (tCopyUI.getObjectIndex() > childObjectIndex) {
						tCopyUI.setObjectIndex(tCopyUI.getObjectIndex() - 1);
					}
				}
			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.COPY_TITLE)) {
			this.childObjectIndexes[1]--;
			result.getAggregateOrCopy().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TAggregateUI) {
					TAggregateUI tAggregateUI = (TAggregateUI) compositeInstance;
					if (tAggregateUI.getCompositeIndex() > childCompositeIndex) {
						tAggregateUI.setCompositeIndex(tAggregateUI.getCompositeIndex() - 1);
					}
					if (tAggregateUI.getObjectIndex() > childObjectIndex) {
						tAggregateUI.setObjectIndex(tAggregateUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TCopyUI) {
					TCopyUI tCopyUI = (TCopyUI) compositeInstance;
					if (tCopyUI.getCompositeIndex() > childCompositeIndex) {
						tCopyUI.setCompositeIndex(tCopyUI.getCompositeIndex() - 1);
					}

					if (tCopyUI.getObjectIndex() > childObjectIndex) {
						tCopyUI.setObjectIndex(tCopyUI.getObjectIndex() - 1);
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
		result = (TResult) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(result.getDocumentation().get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance.getClass() == TAggregateUI.class) {
				TAggregateUI tAggregateUI = (TAggregateUI) compositeInstance;
				tAggregateUI.aggregate = (TAggregate) result.getAggregateOrCopy().get(tAggregateUI.getObjectIndex());
				tAggregateUI.onPageRefresh(textEditor);
				tAggregateUI.loadModel(result.getAggregateOrCopy().get(tAggregateUI.getObjectIndex()));
			} else if (compositeInstance.getClass() == TCopyUI.class) {

				TCopyUI tCopyUI = (TCopyUI) compositeInstance;
				tCopyUI.tcopy = (TCopy) result.getAggregateOrCopy().get(tCopyUI.getObjectIndex());
				tCopyUI.onPageRefresh(textEditor);
				tCopyUI.loadModel(result.getAggregateOrCopy().get(tCopyUI.getObjectIndex()));
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
