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
import org.wso2.developerstudio.humantask.models.TBoolean;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTasks;

/**
 * The UI class representing the "tasks" xml element in the .ht file All the
 * functionalities of that element are performed in this class, by implementing
 * and overriding the abstract super class methods.
 */
public class TTasksUI extends AbstractParentTagSection {

	private int[] childObjectIndexes;
	public TTasks tasks;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;// the parent Section of this section
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
	public TTasksUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer, int styleBit,
			Object modelParent, int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit, new String[] { HTEditorConstants.TASK_TITLE },
				HTEditorConstants.TASKS_TITLE);
		this.tasks = (TTasks) modelParent;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[1];
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
		transition.refreshChildren(HTEditorConstants.TASKS_TITLE, getCompositeIndex(), getObjectIndex());
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
		if (tasks != null) {
			for (Composite composite : childComposites) {
				composite.dispose();
			}
			for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
				childObjectIndexes[childObjectIndexesElementIndex] = 0;
			}
			childComposites.clear();
			childCompositeIndex = 0;

			ArrayList<TTask> taskGroup = (ArrayList<TTask>) tasks.getTask();
			for (int i = 0; i < taskGroup.size(); i++) {
				TTaskUI tTaskUI = new TTaskUI(editor, detailArea, this, SWT.NONE, taskGroup.get(childObjectIndexes[0]),
						childObjectIndexes[0], childCompositeIndex);
				tTaskUI.initialize(editor);
				childComposites.add(childCompositeIndex, tTaskUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
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
		if (selection.equalsIgnoreCase(HTEditorConstants.TASK_TITLE)) {
			TTask tTask = new TTask();
			tTask.setName("");
			tTask.setActualOwnerRequired(TBoolean.YES);
			tasks.getTask().add(childObjectIndexes[0], tTask);
			TTaskUI tTaskUI = new TTaskUI(editor, composite, this, SWT.NONE, tTask, childObjectIndexes[0],
					childCompositeIndex);
			childComposites.add(childCompositeIndex, tTaskUI);
			childObjectIndexes[0]++;
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
	 * Initialize or set the values of attributes and xml content(if available)
	 * whenever a tab change occur from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
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
		childComposites.remove(childCompositeIndex);
		tasks.getTask().remove(getObjectIndex());
		this.childCompositeIndex--;
		this.childObjectIndexes[0]--;
		for (Composite compositeInstance : childComposites) {
			TTaskUI tTaskUI = (TTaskUI) compositeInstance;
			if (tTaskUI.getCompositeIndex() > childCompositeIndex) {
				tTaskUI.setCompositeIndex(tTaskUI.getCompositeIndex() - 1);
			}
			if (tTaskUI.getObjectIndex() >= childObjectIndex) {
				tTaskUI.setObjectIndex(tTaskUI.getObjectIndex() - 1);
			}
		}

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
		tasks = (TTasks) model;
		for (Composite compositeInstance : childComposites) {
			TTaskUI tTaskUI = (TTaskUI) compositeInstance;
			tTaskUI.task = tasks.getTask().get(tTaskUI.getObjectIndex());
			tTaskUI.onPageRefresh(textEditor);
			tTaskUI.loadModel(tasks.getTask().get(tTaskUI.getObjectIndex()));
			this.layout();
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
