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
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TRendering;
import org.wso2.developerstudio.humantask.models.TRenderings;

/**
 * The UI class representing the "renderings" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TRenderingsUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TRenderings renderings;
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
	public TRenderingsUI(XMLEditor textEditor, Composite parentComposite,
	                     Composite parentTagContainer, int styleBit, Object modelParent,
	                     int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
		                    HTEditorConstants.RENDERING_TITLE }, HTEditorConstants.RENDERINGS_TITLE);
		this.renderings = (TRenderings) modelParent;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
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
		AbstractParentTagSection transition = (AbstractParentTagSection) parentTagContainer;
		transition.refreshChildren(HTEditorConstants.RENDERINGS_TITLE, getCompositeIndex(),
		                           getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
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
		if (renderings != null) {
			for (Composite composite : childComposites) {
				composite.dispose();
			}
			for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
				childObjectIndexes[childObjectIndexesElementIndex] = 0;
			}
			childComposites.clear();
			childCompositeIndex = 0;

			ArrayList<TDocumentation> documentationGroup =
			                                               (ArrayList<TDocumentation>) renderings.getDocumentation();
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

			ArrayList<TRendering> renderingGroup =
			                                       (ArrayList<TRendering>) renderings.getRendering();

			for (int i = 0; i < renderingGroup.size(); i++) {
				TRenderingUI tRenderingUI =
				                            new TRenderingUI(
				                                             editor,
				                                             detailArea,
				                                             this,
				                                             SWT.NONE,
				                                             renderingGroup.get(childObjectIndexes[1]),
				                                             childObjectIndexes[1],
				                                             childCompositeIndex);
				tRenderingUI.initialize(editor);
				childComposites.add(childCompositeIndex, tRenderingUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}
		}

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {

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
			renderings.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.RENDERING_TITLE)) {
			TRendering tRendering = new TRendering();
			tRendering.setType(new QName(""));
			renderings.getRendering().add(childObjectIndexes[1], tRendering);
			TRenderingUI tRenderingUI =
			                            new TRenderingUI(editor, composite, this, SWT.NONE,
			                                             tRendering, childObjectIndexes[1],
			                                             childCompositeIndex);
			childComposites.add(childCompositeIndex, tRenderingUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		}
		centralUtils.marshal(editor);

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
			renderings.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TRenderingUI) {
					TRenderingUI tRenderingUI = (TRenderingUI) compositeInstance;
					if (tRenderingUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingUI.setCompositeIndex(tRenderingUI.getCompositeIndex() - 1);
					}
				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.RENDERING_TITLE)) {
			this.childObjectIndexes[1]--;
			renderings.getRendering().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingUI) {
					TRenderingUI tRenderingUI = (TRenderingUI) compositeInstance;
					if (tRenderingUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingUI.setCompositeIndex(tRenderingUI.getCompositeIndex() - 1);
					}
					if (tRenderingUI.getObjectIndex() >= childObjectIndex) {
						tRenderingUI.setObjectIndex(tRenderingUI.getObjectIndex() - 1);
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
		renderings = (TRenderings) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(renderings.getDocumentation()
				                                     .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TRenderingUI) {
				TRenderingUI tRenderingUI = (TRenderingUI) compositeInstance;
				tRenderingUI.rendering =
				                         renderings.getRendering()
				                                   .get(tRenderingUI.getObjectIndex());
				tRenderingUI.onPageRefresh(textEditor);
				tRenderingUI.loadModel(renderings.getRendering().get(tRenderingUI.getObjectIndex()));
				this.layout();
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
	 * Set this section's(composite's) index (index of any type of child class
	 * objects created in the parent Section)
	 * as per the order created in this object's parent
	 * 
	 * @param compositeIndex
	 */
	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

}
