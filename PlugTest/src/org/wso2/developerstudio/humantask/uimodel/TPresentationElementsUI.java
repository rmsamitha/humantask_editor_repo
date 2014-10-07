package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TPresentationElements;
import org.wso2.developerstudio.humantask.models.TPresentationParameters;
import org.wso2.developerstudio.humantask.models.TText;

public class TPresentationElementsUI extends AbstractParentTagSection {
	private int [] childObjectIndexes;
	public TPresentationElements presentationElements;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TPresentationElementsUI(XMLEditor textEditor,Composite parentComposite,Composite parentTagContainer,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite,parentTagContainer,style,new String[] {HTEditorConstants.NAME_TAG_TITLE,HTEditorConstants.PRESENTATION_ELEMENTS_TITLE,HTEditorConstants.SUBJECT_TITLE,HTEditorConstants.DESCRIPTION_TITLE},HTEditorConstants.PRESENTATION_ELEMENTS_TITLE);
		this.presentationElements=(TPresentationElements)modelParent;
		this.compositeIndex=compositeIndex;
		this.parentTagContainer=parentTagContainer;
		this.textEditor=textEditor;
		this.childObjectIndexes = new int[4];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI taskUI=(TTaskUI)parentTagContainer;
		taskUI.refreshChildren(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE, compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
		for(Composite composite:childComposites){
				composite.dispose();
			}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex]=0;
		}
			childComposites.clear();
			childCompositeIndex=0;
			
			ArrayList<TText> tNameGroup = new ArrayList<TText>();
			tNameGroup = (ArrayList<TText>) presentationElements.getName();
			for (int tNameGroupIndex = 0; tNameGroupIndex < tNameGroup.size(); tNameGroupIndex++) {
				 TNameUI tNameUI;
				 tNameUI = new TNameUI(editor, detailArea,
								childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
								tNameGroup.get(childObjectIndexes[0]));
						tNameUI.initialize(editor);
						childComposites.add(childCompositeIndex, tNameUI);
						childCompositeIndex++;
						childObjectIndexes[0]++;
			}
			
			if (presentationElements.getPresentationParameters() != null) {
				TPresentationParametersUI tPresentationParametersUI;
				TPresentationParameters presentationParametersObject = (TPresentationParameters) presentationElements.getPresentationParameters();
				tPresentationParametersUI = new TPresentationParametersUI(editor,detailArea,this,SWT.NONE,presentationParametersObject,childObjectIndexes[1],childCompositeIndex);
				tPresentationParametersUI.initialize(editor);
					childComposites.add(childCompositeIndex, tPresentationParametersUI);
					childCompositeIndex++;
					childObjectIndexes[1]++;
			}
		}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.NAME_TAG_TITLE)) {
			TText tName = new TText();
			tName.setLang("");
			tName.getContent().add(new String(""));
			presentationElements.getName().add(childObjectIndexes[0], tName);
			TNameUI tNameUI = new TNameUI(editor, composite,
					childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
					tName);
			childComposites.add(childCompositeIndex, tNameUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETERS_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TPresentationParameters tPresentationParameters = new TPresentationParameters();
				tPresentationParameters.setExpressionLanguage("");
				presentationElements.setPresentationParameters(tPresentationParameters);
				TPresentationParametersUI tPresentationParametersUI = new TPresentationParametersUI(editor,detailArea,this,SWT.NONE,tPresentationParameters,childObjectIndexes[1],childCompositeIndex);
				childComposites.add(childCompositeIndex, tPresentationParametersUI);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		}/*
		} else if (selection.equalsIgnoreCase("Priority")) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0,"");
				task.setPriority(tPriorityExpr);
				TPriorityExprUI tNot = new TPriorityExprUI(editor, composite,
						childCompositeIndex, childObjectIndexes[2], SWT.NONE,
						this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		}*/

		centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.NAME_TAG_TITLE)) {
			this.childObjectIndexes[0]--;
			presentationElements.getName().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TNameUI) {
					
					TNameUI tNameUI = (TNameUI) compositeInstance;
					if (tNameUI.compositeIndex > childCompositeIndex) {
						tNameUI.setCompositeIndex(tNameUI.getCompositeIndex() - 1);
					}
					if (tNameUI.objectIndex > childObjectIndex) {
						tNameUI.setObjectIndex(tNameUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationParametersUI) {
					TPresentationParametersUI tPresentationParametersUI = (TPresentationParametersUI) compositeInstance;
					if (tPresentationParametersUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationParametersUI.setCompositeIndex(tPresentationParametersUI.getCompositeIndex() - 1);
					}

				} /*else if (c instanceof TPriorityExprUI) {
					TPriorityExprUI d = (TPriorityExprUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}else if (c instanceof TPresentationElementsUI) {
					TPresentationElementsUI d = (TPresentationElementsUI) c;
					if (d.getCompositeIndex() > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}*/ else {
					
				}

			}
		}else if (itemName.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETERS_TITLE)) {
			this.childObjectIndexes[1]--;
			presentationElements.getName().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TNameUI) {
					
					TNameUI tNameUI = (TNameUI) compositeInstance;
					if (tNameUI.compositeIndex > childCompositeIndex) {
						tNameUI.setCompositeIndex(tNameUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationParametersUI) {
					TPresentationParametersUI tPresentationParametersUI = (TPresentationParametersUI) compositeInstance;
					if (tPresentationParametersUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationParametersUI.setCompositeIndex(tPresentationParametersUI.getCompositeIndex() - 1);
					}

					if (tPresentationParametersUI.getObjectIndex() > childObjectIndex) {
						tPresentationParametersUI.setObjectIndex(tPresentationParametersUI.getObjectIndex() - 1);
					}

				} /*else if (c instanceof TPriorityExprUI) {
					TPriorityExprUI d = (TPriorityExprUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}else if (c instanceof TPresentationElementsUI) {
					TPresentationElementsUI d = (TPresentationElementsUI) c;
					if (d.getCompositeIndex() > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}*/ else {
					
				}

			}
		}

	}
	
	public void loadModel(Object objectModel) throws JAXBException {
		presentationElements = (TPresentationElements) objectModel;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TNameUI) {
				TNameUI tNameUI = (TNameUI) compositeInstance; // children node type
				tNameUI.loadModel(presentationElements.getName().get(tNameUI.objectIndex));
			} else if (compositeInstance instanceof TPresentationParametersUI) {
				TPresentationParametersUI tPresentationParametersUI = (TPresentationParametersUI) compositeInstance;
				tPresentationParametersUI.presentationParameters=presentationElements.getPresentationParameters();// children node type
				tPresentationParametersUI.refreshLogic(textEditor);
				tPresentationParametersUI.loadModel(presentationElements.getPresentationParameters());
			} /*else if (c instanceof TPriorityExprUI) {
				TPriorityExprUI d = (TPriorityExprUI) c; // children node type
				d.loadModel(task.getPriority());
			}*/
		}
	}

	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

}