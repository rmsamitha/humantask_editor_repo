package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TBooleanExpr;
import org.wso2.developerstudio.humantask.models.TCompletion;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TResult;

/**
 * The UI class representing the "completion" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TCompletionUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TCompletion completion;
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
	public TCompletionUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object objectModel,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.BOOLEAN_EXPR_TITLE,
						HTEditorConstants.RESULT_TITLE },
				HTEditorConstants.COMPLETION_TITLE);
		this.completion = (TCompletion) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[3];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {

	}

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TCompletionBehaviorUI tCompletionBehaviorUI = (TCompletionBehaviorUI) parentTagContainer;
		tCompletionBehaviorUI.refreshChildren(HTEditorConstants.COMPLETION_TITLE, getCompositeIndex(),
				getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			completion.getDocumentation().add(childObjectIndexes[0],
					tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					composite, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection
				.equalsIgnoreCase(HTEditorConstants.BOOLEAN_EXPR_TITLE)) {
			TBooleanExpr tBooleanExpr = new TBooleanExpr();
			tBooleanExpr.setExpressionLanguage("");
			completion.setCondition(tBooleanExpr);
			TBooleanExprUI tBooleanExprUI = new TBooleanExprUI(editor,
					composite, childCompositeIndex, childObjectIndexes[1],
					SWT.NONE, this, tBooleanExpr);
			childComposites.add(childCompositeIndex, tBooleanExprUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.RESULT_TITLE)) {
			TResult tResult = new TResult();
			completion.setResult(tResult);
			TResultUI tResultUI = new TResultUI(editor, composite, this,
					SWT.NONE, tResult, childObjectIndexes[2],
					childCompositeIndex);
			childComposites.add(childCompositeIndex, tResultUI);
			childObjectIndexes[2]++;
			childCompositeIndex++;
		}
		centralUtils.marshal(editor);
	}
	

	@Override
	public void fillDetailArea(Composite composite) {
		/*
		  * dispose update button as it is not required to this Section as there is no 
		  * any attribute or xml content in this Section
		  */
		btnUpdate.dispose(); 
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
	public void refreshLogic(XMLEditor editor) throws JAXBException {

		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		if (childComposites.size() == 0) {
			ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) completion
					.getDocumentation();
			for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
					.size(); documentationGroupIndex++) {
				TDocumentationUI tDocumentationUI = new TDocumentationUI(
						editor, detailArea, childCompositeIndex,
						childObjectIndexes[0], SWT.NONE, this,
						documentationGroup.get(childObjectIndexes[0]));
				tDocumentationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tDocumentationUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			if (completion.getCondition() != null) {
				TBooleanExpr booleanExprObject = (TBooleanExpr) completion
						.getCondition();
				TBooleanExprUI tBooleanExprUI =new TBooleanExprUI(editor,
						detailArea, childCompositeIndex, childObjectIndexes[1],
						SWT.NONE, this, booleanExprObject);
				tBooleanExprUI.initialize(editor);
				childComposites.add(childCompositeIndex, tBooleanExprUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (completion.getResult() != null) {
				TResult resultObject = (TResult) completion.getResult();
				TResultUI tResultUI = new TResultUI(editor, detailArea, this,
						SWT.NONE, resultObject, childObjectIndexes[2],
						childCompositeIndex);
				tResultUI.initialize(editor);
				childComposites.add(childCompositeIndex, tResultUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;
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
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			completion.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI
								.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TBooleanExprUI) {
					TBooleanExprUI tBooleanExprUI = (TBooleanExprUI) compositeInstance;
					if (tBooleanExprUI.getCompositeIndex() > childCompositeIndex) {
						tBooleanExprUI.setCompositeIndex(tBooleanExprUI
								.getCompositeIndex() - 1);
					}

				}  else if (compositeInstance instanceof TResultUI) {
					TResultUI tResultUI = (TResultUI) compositeInstance;
					if (tResultUI.getCompositeIndex() > childCompositeIndex) {
						tResultUI.setCompositeIndex(tResultUI
								.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.BOOLEAN_EXPR_TITLE)) {
			this.childObjectIndexes[1]--;
			completion.setCondition(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}

				}else if (compositeInstance instanceof TBooleanExprUI) {
					TBooleanExprUI tBooleanExprUI = (TBooleanExprUI) compositeInstance;
					if (tBooleanExprUI.getCompositeIndex() > childCompositeIndex) {
						tBooleanExprUI.setCompositeIndex(tBooleanExprUI
								.getCompositeIndex() - 1);
					}
					if (tBooleanExprUI.objectIndex > childObjectIndex) {
						tBooleanExprUI.setObjectIndex(tBooleanExprUI
								.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TResultUI ){
					TResultUI tResultUI = (TResultUI) compositeInstance;
					if (tResultUI.getCompositeIndex() > childCompositeIndex) {
						tResultUI.setCompositeIndex(tResultUI
								.getCompositeIndex() - 1);
					}

				}else {

				}
 
			}
		}else if (itemName.equalsIgnoreCase(HTEditorConstants.RESULT_TITLE)) {
			this.childObjectIndexes[2]--;
			completion.setResult(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}

				}else if (compositeInstance instanceof TBooleanExprUI) {
					TBooleanExprUI tBooleanExprUI = (TBooleanExprUI) compositeInstance;
					if (tBooleanExprUI.getCompositeIndex() > childCompositeIndex) {
						tBooleanExprUI.setCompositeIndex(tBooleanExprUI
								.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TResultUI) {
					TResultUI tResultUI = (TResultUI) compositeInstance;
					if (tResultUI.getCompositeIndex() > childCompositeIndex) {
						tResultUI.setCompositeIndex(tResultUI
								.getCompositeIndex() - 1);
					}
					if (tResultUI.getObjectIndex() > childObjectIndex) {
						tResultUI.setObjectIndex(tResultUI
								.getObjectIndex() - 1);
					}

				}else {

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
		completion = (TCompletion) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(completion.getDocumentation().get(
						tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TBooleanExprUI) {
				TBooleanExprUI tBooleanExprUI = (TBooleanExprUI) compositeInstance;
				tBooleanExprUI.loadModel(completion.getCondition());
			} else if (compositeInstance instanceof TResultUI) {
				TResultUI tResultUI = (TResultUI) compositeInstance;
				tResultUI.result=completion.getResult();
				tResultUI.refreshLogic(textEditor);
				tResultUI.loadModel(completion.getResult());
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
