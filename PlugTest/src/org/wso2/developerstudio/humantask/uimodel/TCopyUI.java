package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TCopy;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TExpression;
import org.wso2.developerstudio.humantask.models.TQuery;

/**
 * The UI class representing the "copy" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TCopyUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TCopy tcopy;
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
	public TCopyUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer,
	               int style, Object modelParent, int objectIndex, int compositeIndex)
	                                                                                  throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, style,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE, HTEditorConstants.FROM_TITLE,
		                    HTEditorConstants.TO_TITLE, }, HTEditorConstants.COPY_TITLE);
		this.tcopy = (TCopy) modelParent;
		this.setObjectIndex(objectIndex);
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
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
		TResultUI resultUI = (TResultUI) parentTagContainer;
		resultUI.refreshChildren(HTEditorConstants.COPY_TITLE, compositeIndex, objectIndex);
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
			ArrayList<TDocumentation> documentationGroup =
			                                               (ArrayList<TDocumentation>) tcopy.getDocumentation();
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

			if (tcopy.getFrom() != null) {
				TExpression fromObject = tcopy.getFrom();
				TExpressionUI tExpressionUI =
				                              new TExpressionUI(editor, detailArea, this, SWT.NONE,
				                                                fromObject, childObjectIndexes[1],
				                                                childCompositeIndex,
				                                                HTEditorConstants.FROM_TITLE);
				tExpressionUI.initialize(editor);
				childComposites.add(childCompositeIndex, tExpressionUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}
			if (tcopy.getTo() != null) {
				TQuery toObject = tcopy.getTo();
				TQueryUI tQueryUI =
				                    new TQueryUI(editor, detailArea, this, SWT.NONE, toObject,
				                                 childObjectIndexes[2], childCompositeIndex,
				                                 HTEditorConstants.TO_TITLE);
				tQueryUI.initialize(editor);
				childComposites.add(childCompositeIndex, tQueryUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;
			}
		}
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3, XMLEditor editor,
	                           Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			tcopy.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.FROM_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TExpression tExpression = new TExpression();
				tExpression.setExpressionLanguage("");
				tExpression.getContent().add(0, "");
				tcopy.setFrom(tExpression);

				TExpressionUI tExpressionUI =
				                              new TExpressionUI(editor, composite, this, SWT.NONE,
				                                                tExpression, childObjectIndexes[1],
				                                                childCompositeIndex,
				                                                HTEditorConstants.FROM_TITLE);
				childComposites.add(childCompositeIndex, tExpressionUI);
				childComposites.add(childCompositeIndex, tExpressionUI);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.TO_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TQuery tQuery = new TQuery();
				tQuery.setPart("");
				tQuery.setQueryLanguage("");
				tQuery.getContent().add(0, "");
				tcopy.setTo(tQuery);
				TQueryUI tQueryUI =
				                    new TQueryUI(editor, detailArea, this, SWT.NONE, tQuery,
				                                 childObjectIndexes[2], childCompositeIndex,
				                                 HTEditorConstants.TO_TITLE);
				childComposites.add(childCompositeIndex, tQueryUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		}
		centralUtils.marshal(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		/*
		 * dispose update button as it is not required to this Section as there
		 * is no
		 * any attribute or xml content in this Section
		 */
		btnUpdate.dispose();
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
			tcopy.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		}
		if (itemName.equalsIgnoreCase(HTEditorConstants.FROM_TITLE)) {
			this.childObjectIndexes[1]--;
			tcopy.setFrom(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}
					if (tExpressionUI.objectIndex > childObjectIndex) {
						tExpressionUI.setObjectIndex(tExpressionUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		}
		if (itemName.equalsIgnoreCase(HTEditorConstants.TO_TITLE)) {
			this.childObjectIndexes[2]--;
			tcopy.setTo(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}
					if (tQueryUI.getObjectIndex() > childObjectIndex) {
						tQueryUI.setObjectIndex(tQueryUI.getObjectIndex() - 1);
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
		tcopy = (TCopy) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(tcopy.getDocumentation()
				                                .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TExpressionUI) {
				TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
				tExpressionUI.expression = tcopy.getFrom();
				tExpressionUI.refreshLogic(textEditor);
				tExpressionUI.loadModel(tcopy.getFrom());
			} else if (compositeInstance instanceof TQueryUI) {
				TQueryUI tQueryUI = (TQueryUI) compositeInstance;
				tQueryUI.query = tcopy.getTo();
				tQueryUI.refreshLogic(textEditor);
				tQueryUI.loadModel(tcopy.getTo());
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
