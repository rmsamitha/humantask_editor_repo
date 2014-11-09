package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

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
import org.wso2.developerstudio.humantask.models.TBoolean;
import org.wso2.developerstudio.humantask.models.TCompletionBehavior;
import org.wso2.developerstudio.humantask.models.TDelegation;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TExpression;
import org.wso2.developerstudio.humantask.models.TPeopleAssignments;
import org.wso2.developerstudio.humantask.models.TPresentationElements;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;
import org.wso2.developerstudio.humantask.models.TQuery;
import org.wso2.developerstudio.humantask.models.TRenderings;
import org.wso2.developerstudio.humantask.models.TTask;
import org.wso2.developerstudio.humantask.models.TTaskInterface;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "task" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TTaskUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;//one array element for each child object type
	public TTask task;
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
	public TTaskUI(XMLEditor textEditor, Composite parentComposite, Composite parentTagContainer,
	               int styleBit, Object objectModel, int objectIndex, int compositeIndex)
	                                                                                     throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
		                    HTEditorConstants.INTERFACE_TITLE, HTEditorConstants.PRIORITY_TITLE,
		                    HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE,
		                    HTEditorConstants.COMPLETION_BEHAVIOR_TITLE,
		                    HTEditorConstants.DELEGATION_TITLE,
		                    HTEditorConstants.PRESENTATION_ELEMENTS_TITLE,
		                    HTEditorConstants.OUTCOME_TITLE, HTEditorConstants.SEARCHBY_TITLE,
		                    HTEditorConstants.RENDERINGS_TITLE, }, HTEditorConstants.TASK_TITLE);
		this.task = (TTask) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[10];
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
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		task.setName(((Text) textBoxesList.get(0)).getText());
		task.setActualOwnerRequired(TBoolean.fromValue(((Combo) textBoxesList.get(1)).getText()
		                                                                             .toLowerCase()));
		centralUtils.marshal(textEditor);
	}
	
	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTasksUI tTasksUI = (TTasksUI) parentTagContainer;
		tTasksUI.refreshChildren("", getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3, XMLEditor editor,
	                           Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			task.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TTaskInterface tTaskInterface = new TTaskInterface();
				tTaskInterface.setPortType(new QName(""));
				tTaskInterface.setOperation("");
				task.setInterface(tTaskInterface);
				TTaskInterfaceUI tTaskInterfaceUI =
				                                    new TTaskInterfaceUI(editor, composite, this,
				                                                         SWT.NONE, tTaskInterface,
				                                                         childObjectIndexes[1],
				                                                         childCompositeIndex);
				childComposites.add(childCompositeIndex, tTaskInterfaceUI);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRIORITY_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0, "");
				task.setPriority(tPriorityExpr);
				TPriorityExprUI tPriorityExprUI =
				                                  new TPriorityExprUI(editor, composite,
				                                                      childCompositeIndex,
				                                                      childObjectIndexes[2],
				                                                      SWT.NONE, this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tPriorityExprUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE)) {
			if (childObjectIndexes[3] < 1) {
				TPeopleAssignments tPeopleAssignments = new TPeopleAssignments();
				task.setPeopleAssignments(tPeopleAssignments);
				TPeopleAssignmentsUI tPeopleAssignmentsUI =
				                                            new TPeopleAssignmentsUI(
				                                                                     editor,
				                                                                     composite,
				                                                                     this,
				                                                                     SWT.NONE,
				                                                                     tPeopleAssignments,
				                                                                     childObjectIndexes[3],
				                                                                     childCompositeIndex);
				childComposites.add(childCompositeIndex, tPeopleAssignmentsUI);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.COMPLETION_BEHAVIOR_TITLE)) {
			if (childObjectIndexes[4] < 1) {
				TCompletionBehavior tCompletionBehavior = new TCompletionBehavior();
				task.setCompletionBehavior(tCompletionBehavior);
				TCompletionBehaviorUI tCompletionBehaviorUI =
				                                              new TCompletionBehaviorUI(
				                                                                        editor,
				                                                                        composite,
				                                                                        this,
				                                                                        SWT.NONE,
				                                                                        tCompletionBehavior,
				                                                                        childObjectIndexes[4],
				                                                                        childCompositeIndex);
				childComposites.add(childCompositeIndex, tCompletionBehaviorUI);
				childObjectIndexes[4]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.DELEGATION_TITLE)) {
			if (childObjectIndexes[5] < 1) {
				TDelegation tDelegation = new TDelegation();
				task.setDelegation(tDelegation);
				TDelegationUI tDelegationUI =
				                              new TDelegationUI(editor, composite, this, SWT.NONE,
				                                                tDelegation, childObjectIndexes[5],
				                                                childCompositeIndex);
				childComposites.add(childCompositeIndex, tDelegationUI);
				childObjectIndexes[5]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE)) {
			if (childObjectIndexes[6] < 1) {
				TPresentationElements tPresentationElements = new TPresentationElements();
				task.setPresentationElements(tPresentationElements);
				TPresentationElementsUI tPresentationElementsUI =
				                                                  new TPresentationElementsUI(
				                                                                              editor,
				                                                                              composite,
				                                                                              this,
				                                                                              SWT.NONE,
				                                                                              tPresentationElements,
				                                                                              childObjectIndexes[6],
				                                                                              childCompositeIndex);
				childComposites.add(childCompositeIndex, tPresentationElementsUI);
				childObjectIndexes[6]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.OUTCOME_TITLE)) {
			if (childObjectIndexes[7] < 1) {
				TQuery tQuery = new TQuery();
				tQuery.setPart("");
				tQuery.setQueryLanguage("");
				tQuery.getContent().add(0, "");
				task.setOutcome(tQuery);
				TQueryUI tQueryUI =
				                    new TQueryUI(editor, detailArea, this, SWT.NONE, tQuery,
				                                 childObjectIndexes[7], childCompositeIndex,
				                                 HTEditorConstants.OUTCOME_TITLE);
				childComposites.add(childCompositeIndex, tQueryUI);
				childObjectIndexes[7]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.SEARCHBY_TITLE)) {
			if (childObjectIndexes[8] < 1) {
				TExpression tExpression = new TExpression();
				tExpression.setExpressionLanguage("");
				task.setSearchBy(tExpression);
				TExpressionUI tExpressionUI =
				                              new TExpressionUI(editor, composite, this, SWT.NONE,
				                                                tExpression, childObjectIndexes[8],
				                                                childCompositeIndex,
				                                                HTEditorConstants.SEARCHBY_TITLE);
				childComposites.add(childCompositeIndex, tExpressionUI);
				childObjectIndexes[8]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.RENDERINGS_TITLE)) {
			if (childObjectIndexes[9] < 1) {
				TRenderings tRenderings = new TRenderings();
				task.setRenderings(tRenderings);
				TRenderingsUI tRenderingsUI =
				                              new TRenderingsUI(editor, composite, this, SWT.NONE,
				                                                tRenderings, childObjectIndexes[9],
				                                                childCompositeIndex);
				childComposites.add(childCompositeIndex, tRenderingsUI);
				childObjectIndexes[9]++;
				childCompositeIndex++;
			}
		}

		centralUtils.marshal(editor);
	}
	
	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the section
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
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(200);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);

		TableItem x1 = new TableItem(table, SWT.NONE);
		x1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor editorw = new TableEditor(table);

		Label lblLang = new Label(table, SWT.NONE);
		lblLang.setText("  " + "Language");
		editorw.grabHorizontal = true;
		editorw.setEditor(lblLang, x1, 0);

		editorw = new TableEditor(table);
		Text txtLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtLang);
		editorw.grabHorizontal = true;
		editorw.setEditor(txtLang, x1, 1);

		editorw = new TableEditor(table);
		Label lblOwnerRequired = new Label(table, SWT.NONE);
		lblOwnerRequired.setText("     " + "Value");
		editorw.grabHorizontal = true;
		editorw.setEditor(lblOwnerRequired, x1, 2);

		editorw = new TableEditor(table);
		Combo cmbOwnerRequired = new Combo(table, SWT.NONE);
		cmbOwnerRequired.setItems(new String[] { "yes", "no" });
		cmbOwnerRequired.select(0);
		textBoxesList.add(1, cmbOwnerRequired);
		editorw.grabHorizontal = true;
		editorw.setEditor(cmbOwnerRequired, x1, 3);

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
		if (task.getName() != null)
			((Text) textBoxesList.get(0)).setText(task.getName());
		Combo comboBox = (Combo) textBoxesList.get(1);
		if (task.getActualOwnerRequired() != null)
			comboBox.select(comboBox.indexOf(task.getActualOwnerRequired().toString().toLowerCase()));
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
			                                               (ArrayList<TDocumentation>) task.getDocumentation();
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

			if (task.getInterface() != null) {
				TTaskInterface interfaceObject = (TTaskInterface) task.getInterface();
				TTaskInterfaceUI tTaskInterfaceUI =
				                                    new TTaskInterfaceUI(editor, detailArea, this,
				                                                         SWT.NONE, interfaceObject,
				                                                         childObjectIndexes[1],
				                                                         childCompositeIndex);
				tTaskInterfaceUI.initialize(editor);
				childComposites.add(childCompositeIndex, tTaskInterfaceUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (task.getPriority() != null) {
				TPriorityExpr priorityObject = (TPriorityExpr) task.getPriority();
				TPriorityExprUI tPriorityUI =
				                              new TPriorityExprUI(editor, detailArea,
				                                                  childCompositeIndex,
				                                                  childObjectIndexes[2], SWT.NONE,
				                                                  this, priorityObject);
				tPriorityUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPriorityUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;

			}
			if (task.getPeopleAssignments() != null) {
				TPeopleAssignments peopleAssignmentObject =
				                                            (TPeopleAssignments) task.getPeopleAssignments();
				TPeopleAssignmentsUI tPeopleAssingmentsUI =
				                                            new TPeopleAssignmentsUI(
				                                                                     editor,
				                                                                     detailArea,
				                                                                     this,
				                                                                     SWT.NONE,
				                                                                     peopleAssignmentObject,
				                                                                     childObjectIndexes[3],
				                                                                     childCompositeIndex);
				tPeopleAssingmentsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPeopleAssingmentsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;

			}
			if (task.getCompletionBehavior() != null) {

				TCompletionBehavior tCompletionBehaviorObject =
				                                                (TCompletionBehavior) task.getCompletionBehavior();
				TCompletionBehaviorUI tCompletionBehaviorUI =
				                                              new TCompletionBehaviorUI(
				                                                                        editor,
				                                                                        detailArea,
				                                                                        this,
				                                                                        SWT.NONE,
				                                                                        tCompletionBehaviorObject,
				                                                                        childObjectIndexes[4],
				                                                                        childCompositeIndex);
				tCompletionBehaviorUI.initialize(editor);
				childComposites.add(childCompositeIndex, tCompletionBehaviorUI);
				childCompositeIndex++;
				childObjectIndexes[4]++;
			}
			if (task.getDelegation() != null) {
				TDelegation tDelegationObject = (TDelegation) task.getDelegation();
				TDelegationUI tDelegationUI =
				                              new TDelegationUI(editor, detailArea, this, SWT.NONE,
				                                                tDelegationObject,
				                                                childObjectIndexes[5],
				                                                childCompositeIndex);
				tDelegationUI.initialize(editor);
				childComposites.add(childCompositeIndex, tDelegationUI);
				childCompositeIndex++;
				childObjectIndexes[5]++;
			}
			if (task.getPresentationElements() != null) {
				TPresentationElements tPresentationElementsObject =
				                                                    (TPresentationElements) task.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI =
				                                                   new TPresentationElementsUI(
				                                                                               editor,
				                                                                               detailArea,
				                                                                               this,
				                                                                               SWT.NONE,
				                                                                               tPresentationElementsObject,
				                                                                               childObjectIndexes[6],
				                                                                               childCompositeIndex); 
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[6]++;
			}
			if (task.getOutcome() != null) {
				TQuery toObject = task.getOutcome();
				TQueryUI tQueryUI =
				                    new TQueryUI(editor, detailArea, this, SWT.NONE, toObject,
				                                 childObjectIndexes[7], childCompositeIndex,
				                                 HTEditorConstants.OUTCOME_TITLE);
				tQueryUI.initialize(editor);
				childComposites.add(childCompositeIndex, tQueryUI);
				childCompositeIndex++;
				childObjectIndexes[7]++;
			}
			if (task.getSearchBy() != null) {
				TExpression tSearchByObject = (TExpression) task.getSearchBy();
				TExpressionUI tSearchByUI =
				                            new TExpressionUI(editor, detailArea, this, SWT.NONE,
				                                              tSearchByObject,
				                                              childObjectIndexes[8],
				                                              childCompositeIndex,
				                                              HTEditorConstants.SEARCHBY_TITLE);
				tSearchByUI.initialize(editor);
				childComposites.add(childCompositeIndex, tSearchByUI);
				childCompositeIndex++;
				childObjectIndexes[8]++;
			}

			if (task.getRenderings() != null) {
				TRenderings tRenderingsObject = (TRenderings) task.getRenderings();
				TRenderingsUI tRenderingsUI =
				                              new TRenderingsUI(editor, detailArea, this, SWT.NONE,
				                                                tRenderingsObject,
				                                                childObjectIndexes[9],
				                                                childCompositeIndex);
				tRenderingsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tRenderingsUI);
				childCompositeIndex++;
				childObjectIndexes[9]++;
			}
		}
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
		System.err.println("refresh childresn called:" + itemName + "checked string" +
		                   HTEditorConstants.SEARCHBY_TITLE);
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			task.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					} 
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			this.childObjectIndexes[1]--;
			task.setInterface(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
					if (tTaskInterfaceUI.getObjectIndex() > childObjectIndex) {
						tTaskInterfaceUI.setObjectIndex(tTaskInterfaceUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}
			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.PRIORITY_TITLE)) {
			this.childObjectIndexes[2]--;
			task.setPriority(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
					if (tPriorityUI.getObjectIndex() > childObjectIndex) {
						tPriorityUI.setObjectIndex(tPriorityUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE)) {
			this.childObjectIndexes[3]--;
			task.setPeopleAssignments(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

					if (tPeopleAssignmentsUI.getObjectIndex() > childObjectIndex) {
						tPeopleAssignmentsUI.setObjectIndex(tPeopleAssignmentsUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.COMPLETION_BEHAVIOR_TITLE)) {
			this.childObjectIndexes[4]--;
			task.setCompletionBehavior(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}
					if (tCompletionBehaviorUI.getObjectIndex() > childObjectIndex) {
						tCompletionBehaviorUI.setObjectIndex(tCompletionBehaviorUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.DELEGATION_TITLE)) {
			this.childObjectIndexes[5]--;
			task.setDelegation(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}

					if (tDelegationUI.getObjectIndex() > childObjectIndex) {
						tDelegationUI.setObjectIndex(tDelegationUI.getObjectIndex() - 1);
					}
				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE)) {
			this.childObjectIndexes[6]--;
			task.setPresentationElements(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}
				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}
					if (tPresentationElementsUI.getObjectIndex() > childObjectIndex) {
						tPresentationElementsUI.setObjectIndex(tPresentationElementsUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.OUTCOME_TITLE)) {
			this.childObjectIndexes[7]--;
			task.setOutcome(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}
				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}
					if (tQueryUI.getObjectIndex() > childObjectIndex) {
						tQueryUI.setObjectIndex(tQueryUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.SEARCHBY_TITLE)) {
			System.err.println("refresh childresn called and went into the elseif:" + itemName);
			this.childObjectIndexes[8]--;
			task.setSearchBy(null);;
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}
				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TQueryUI) {
					TQueryUI tQueryUI = (TQueryUI) compositeInstance;
					if (tQueryUI.getCompositeIndex() > childCompositeIndex) {
						tQueryUI.setCompositeIndex(tQueryUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TExpressionUI) {
					TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
					if (tExpressionUI.getCompositeIndex() > childCompositeIndex) {
						tExpressionUI.setCompositeIndex(tExpressionUI.getCompositeIndex() - 1);
					}
					if (tExpressionUI.getObjectIndex() > childObjectIndex) {
						tExpressionUI.setObjectIndex(tExpressionUI.getObjectIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.RENDERINGS_TITLE)) {
			this.childObjectIndexes[9]--;
			task.setRenderings(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
					if (tTaskInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tTaskInterfaceUI.setCompositeIndex(tTaskInterfaceUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPriorityExprUI) {
					TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
					if (tPriorityUI.compositeIndex > childCompositeIndex) {
						tPriorityUI.setCompositeIndex(tPriorityUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
					TPeopleAssignmentsUI tPeopleAssignmentsUI =
					                                            (TPeopleAssignmentsUI) compositeInstance;
					if (tPeopleAssignmentsUI.getCompositeIndex() > childCompositeIndex) {
						tPeopleAssignmentsUI.setCompositeIndex(tPeopleAssignmentsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TCompletionBehaviorUI) {
					TCompletionBehaviorUI tCompletionBehaviorUI =
					                                              (TCompletionBehaviorUI) compositeInstance;
					if (tCompletionBehaviorUI.getCompositeIndex() > childCompositeIndex) {
						tCompletionBehaviorUI.setCompositeIndex(tCompletionBehaviorUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TDelegationUI) {
					TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
					if (tDelegationUI.getCompositeIndex() > childCompositeIndex) {
						tDelegationUI.setCompositeIndex(tDelegationUI.getCompositeIndex() - 1);
					}
				}

				else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElements =
					                                                (TPresentationElementsUI) compositeInstance;
					if (tPresentationElements.getCompositeIndex() > childCompositeIndex) {
						tPresentationElements.setCompositeIndex(tPresentationElements.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TRenderingsUI) {
					TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
					if (tRenderingsUI.getCompositeIndex() > childCompositeIndex) {
						tRenderingsUI.setCompositeIndex(tRenderingsUI.getCompositeIndex() - 1);
					}

					if (tRenderingsUI.getObjectIndex() > childObjectIndex) {
						tRenderingsUI.setObjectIndex(tRenderingsUI.getObjectIndex() - 1);
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
		task = (TTask) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(task.getDocumentation()
				                               .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TTaskInterfaceUI) {
				TTaskInterfaceUI tTaskInterfaceUI = (TTaskInterfaceUI) compositeInstance;
				tTaskInterfaceUI.taskInterface=task.getInterface();
				tTaskInterfaceUI.refreshLogic(textEditor);
				tTaskInterfaceUI.loadModel(task.getInterface());
			} else if (compositeInstance instanceof TPriorityExprUI) {
				TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
				tPriorityUI.loadModel(task.getPriority());
			} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
				TPeopleAssignmentsUI tPeopleAssingmentsUI =
				                                            (TPeopleAssignmentsUI) compositeInstance;
				tPeopleAssingmentsUI.tPeopleAssignments = task.getPeopleAssignments();
				tPeopleAssingmentsUI.refreshLogic(textEditor);
				tPeopleAssingmentsUI.loadModel(task.getPeopleAssignments());

			} else if (compositeInstance instanceof TCompletionBehaviorUI) {
				TCompletionBehaviorUI tCompletionBehaviorUI =
				                                              (TCompletionBehaviorUI) compositeInstance;
				tCompletionBehaviorUI.completionBehavior = task.getCompletionBehavior();
				tCompletionBehaviorUI.refreshLogic(textEditor);
				tCompletionBehaviorUI.loadModel(task.getCompletionBehavior());
			} else if (compositeInstance instanceof TDelegationUI) {
				TDelegationUI tDelegationUI = (TDelegationUI) compositeInstance;
				tDelegationUI.tDelegation = task.getDelegation();
				tDelegationUI.refreshLogic(textEditor);
				tDelegationUI.loadModel(task.getDelegation());

			} else if (compositeInstance instanceof TPresentationElementsUI) {
				TPresentationElementsUI tPresentationElementsUI =
				                                                  (TPresentationElementsUI) compositeInstance;
				tPresentationElementsUI.presentationElements = task.getPresentationElements();
				tPresentationElementsUI.refreshLogic(textEditor);
				tPresentationElementsUI.loadModel(task.getPresentationElements());

			} else if (compositeInstance instanceof TQueryUI) {
				TQueryUI tQueryUI = (TQueryUI) compositeInstance;
				tQueryUI.query = task.getOutcome();
				tQueryUI.refreshLogic(textEditor);
				tQueryUI.loadModel(task.getOutcome());
			} else if (compositeInstance instanceof TExpressionUI) {
				TExpressionUI tExpressionUI = (TExpressionUI) compositeInstance;
				tExpressionUI.expression = task.getSearchBy();
				tExpressionUI.refreshLogic(textEditor);
				tExpressionUI.loadModel(task.getSearchBy());

			} else if (compositeInstance instanceof TRenderingsUI) {
				TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
				tRenderingsUI.renderings = task.getRenderings();
				tRenderingsUI.refreshLogic(textEditor);
				tRenderingsUI.loadModel(task.getRenderings());

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

	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}
}
