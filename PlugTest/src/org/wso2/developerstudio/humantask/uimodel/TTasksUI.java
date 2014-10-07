package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.Transition;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TBoolean;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTasks;

public class TTasksUI extends AbstractParentTagSection {

	private int[] childObjectIndexes;
	public TTasks tasks;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();

	public TTasksUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object modelParent,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.TASK_TITLE },
				HTEditorConstants.TASKS_TITLE);
		this.tasks = (TTasks) modelParent;
		this.objectIndex = objectIndex;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[1];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		Transition transition = (Transition) parentTagContainer;
		transition.refreshChildren(compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
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
				TTaskUI tTaskUI = new TTaskUI(editor, detailArea, this,
						SWT.NONE, taskGroup.get(childObjectIndexes[0]),
						childObjectIndexes[0], childCompositeIndex);
				tTaskUI.initialize(editor);
				childComposites.add(childCompositeIndex, tTaskUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}
		}
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.TASK_TITLE)) {
			TTask tTask = new TTask();
			tTask.setName("");
			tTask.setActualOwnerRequired(TBoolean.YES);
			tasks.getTask().add(childObjectIndexes[0], tTask);
			TTaskUI tTaskUI = new TTaskUI(editor, composite, this, SWT.NONE,
					tTask, childObjectIndexes[0], childCompositeIndex);
			childComposites.add(childCompositeIndex, tTaskUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		childComposites.remove(childCompositeIndex);
		tasks.getTask().remove(objectIndex);
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

	public void loadModel(Object model) throws JAXBException {
		tasks = (TTasks) model;
		System.out.println(childComposites.size());
		for (Composite compositeInstance : childComposites) {
			TTaskUI tTaskUI = (TTaskUI) compositeInstance;
			tTaskUI.task = tasks.getTask().get(tTaskUI.getObjectIndex());
			tTaskUI.refreshLogic(textEditor);
			tTaskUI.loadModel(tasks.getTask().get(tTaskUI.getObjectIndex()));
			this.layout();
		}
	}
	
}
