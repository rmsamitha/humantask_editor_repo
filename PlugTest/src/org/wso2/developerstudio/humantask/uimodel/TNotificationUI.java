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
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TNotification;
import org.wso2.developerstudio.humantask.models.TNotificationInterface;
import org.wso2.developerstudio.humantask.models.TPeopleAssignments;
import org.wso2.developerstudio.humantask.models.TPresentationElements;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;
import org.wso2.developerstudio.humantask.models.TRenderings;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "notification" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TNotificationUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TNotification notification;
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
	public TNotificationUI(XMLEditor textEditor, Composite parentComposite,
	                       Composite parentTagContainer, int styleBit, Object objectModel,
	                       int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
		                    HTEditorConstants.INTERFACE_TITLE, HTEditorConstants.PRIORITY_TITLE,
		                    HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE,
		                    HTEditorConstants.PRESENTATION_ELEMENTS_TITLE,
		                    HTEditorConstants.RENDERINGS_TITLE },
		      HTEditorConstants.NOTIFICATION_TITLE);
		this.notification = (TNotification) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[6];
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
		notification.setName(((Text) textBoxesList.get(0)).getText());
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
		TNotificationsUI tNotificationsUI = (TNotificationsUI) parentTagContainer;
		tNotificationsUI.refreshChildren(HTEditorConstants.NOTIFICATION_TITLE, compositeIndex,
		                                 objectIndex);
		centralUtils.marshal(textEditor);
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);

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
		if (selection.equalsIgnoreCase("Documentation")) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			notification.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tNot =
			                        new TDocumentationUI(editor, composite, childCompositeIndex,
			                                             childObjectIndexes[0], SWT.NONE, this,
			                                             tDocumentation);
			childComposites.add(childCompositeIndex, tNot);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TNotificationInterface tNotificationInterface = new TNotificationInterface();
				tNotificationInterface.setPortType(new QName(""));
				tNotificationInterface.setOperation("");
				notification.setInterface(tNotificationInterface);
				TNotificationInterfaceUI tNot =
				                                new TNotificationInterfaceUI(
				                                                             editor,
				                                                             composite,
				                                                             this,
				                                                             SWT.NONE,
				                                                             tNotificationInterface,
				                                                             childObjectIndexes[1],
				                                                             childCompositeIndex);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[1]++;
				childCompositeIndex++;

			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRIORITY_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0, "");
				notification.setPriority(tPriorityExpr);
				TPriorityExprUI tPriorityExprUI =
				                                  new TPriorityExprUI(editor, composite,
				                                                      childCompositeIndex,
				                                                      childObjectIndexes[2],
				                                                      SWT.NONE, this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tPriorityExprUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PEOPLE_ASSIGNMENTS_TITLE)) { // min
			// ocurs=1
			// max=1
			if (childObjectIndexes[3] < 1) {
				TPeopleAssignments tPeopleAssignments = new TPeopleAssignments();
				notification.setPeopleAssignments(tPeopleAssignments);
				TPeopleAssignmentsUI tNot =
				                            new TPeopleAssignmentsUI(editor, composite, this,
				                                                     SWT.NONE, tPeopleAssignments,
				                                                     childObjectIndexes[3],
				                                                     childCompositeIndex);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.PRESENTATION_ELEMENTS_TITLE)) {// //min
			// ocurs=1
			// max=1
			if (childObjectIndexes[4] < 1) {
				TPresentationElements tPresentationElements = new TPresentationElements();
				notification.setPresentationElements(tPresentationElements);
				TPresentationElementsUI tPresentationElementsUI =
				                                                  new TPresentationElementsUI(
				                                                                              editor,
				                                                                              composite,
				                                                                              this,
				                                                                              SWT.NONE,
				                                                                              tPresentationElements,
				                                                                              childObjectIndexes[4],
				                                                                              childCompositeIndex);
				childComposites.add(childCompositeIndex, tPresentationElementsUI);
				childObjectIndexes[4]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase("Renderings")) {

			if (childObjectIndexes[5] < 1) {
				TRenderings tRenderings = new TRenderings();
				notification.setRenderings(tRenderings);
				TRenderingsUI tNot =
				                     new TRenderingsUI(editor, composite, this, SWT.NONE,
				                                       tRenderings, childObjectIndexes[5],
				                                       childCompositeIndex);

				/*
				 * editor, composite, childCompositeIndex,
				 * childObjectIndexes[2], SWT.NONE, this, tRenderings);
				 */
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[5]++;
				childCompositeIndex++;
			}

		}
		centralUtils.marshal(editor);
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
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Table table = new Table(detailAreaInnerComposite, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(200);

		TableItem tableRow1 = new TableItem(table, SWT.NONE);
		tableRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblName = new Label(table, SWT.NONE);
		lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblName.setText("  " + HTEditorConstants.NAME_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblName, tableRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtLang, tableRow1, 1);

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
		if (notification.getName() != null) {
			((Text) textBoxesList.get(0)).setText(notification.getName());
		}
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
			                                               (ArrayList<TDocumentation>) notification.getDocumentation();
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

			if (notification.getInterface() != null) {
				TNotificationInterface interfaceObject = notification.getInterface();
				TNotificationInterfaceUI tNotificationInterfaceUI =
				                                                    new TNotificationInterfaceUI(
				                                                                                 editor,
				                                                                                 detailArea,
				                                                                                 this,
				                                                                                 SWT.NONE,
				                                                                                 interfaceObject,
				                                                                                 childObjectIndexes[1],
				                                                                                 childCompositeIndex);
				tNotificationInterfaceUI.initialize(editor);
				childComposites.add(childCompositeIndex, tNotificationInterfaceUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (notification.getPriority() != null) {
				TPriorityExpr priorityObject = notification.getPriority();
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
			if (notification.getPeopleAssignments() != null) {
				TPeopleAssignments tPeopleAssignmentObject = notification.getPeopleAssignments();
				TPeopleAssignmentsUI tPeopleAssignmentsUI =
				                                            new TPeopleAssignmentsUI(
				                                                                     editor,
				                                                                     detailArea,
				                                                                     this,
				                                                                     SWT.NONE,
				                                                                     tPeopleAssignmentObject,
				                                                                     childObjectIndexes[3],
				                                                                     childCompositeIndex);
				tPeopleAssignmentsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPeopleAssignmentsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;
			}
			if (notification.getPresentationElements() != null) {
				TPresentationElements tPresentationElementsObject =
				                                                    notification.getPresentationElements();
				TPresentationElementsUI tPreserntationElementsUI =
				                                                   new TPresentationElementsUI(
				                                                                               editor,
				                                                                               detailArea,
				                                                                               this,
				                                                                               SWT.NONE,
				                                                                               tPresentationElementsObject,
				                                                                               childObjectIndexes[4],
				                                                                               childCompositeIndex);
				tPreserntationElementsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tPreserntationElementsUI);
				childCompositeIndex++;
				childObjectIndexes[4]++;
			}
			if (notification.getRenderings() != null) {

				TRenderings tRenderingsObject = notification.getRenderings();
				TRenderingsUI tRenderingsUI =
				                              new TRenderingsUI(editor, detailArea, this, SWT.NONE,
				                                                tRenderingsObject,
				                                                childObjectIndexes[5],
				                                                childCompositeIndex);
				tRenderingsUI.initialize(editor);
				childComposites.add(childCompositeIndex, tRenderingsUI);
				childCompositeIndex++;
				childObjectIndexes[5]++;

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
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			notification.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
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
				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.INTERFACE_TITLE)) {
			this.childObjectIndexes[1]--;
			notification.setInterface(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
					}
					if (tNotificationInterfaceUI.getObjectIndex() > childObjectIndex) {
						tNotificationInterfaceUI.setObjectIndex(tNotificationInterfaceUI.getObjectIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
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
			notification.setPriority(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
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
			notification.setPriority(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
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
			this.childObjectIndexes[4]--;
			notification.setPresentationElements(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
					}
					if (tPresentationElementsUI.getObjectIndex() > childObjectIndex) {
						tPresentationElementsUI.setObjectIndex(tPresentationElementsUI.getObjectIndex() - 1);
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
			this.childObjectIndexes[5]--;
			notification.setRenderings(null);;
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationInterfaceUI) {
					TNotificationInterfaceUI tNotificationInterfaceUI =
					                                                    (TNotificationInterfaceUI) compositeInstance;
					if (tNotificationInterfaceUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationInterfaceUI.setCompositeIndex(tNotificationInterfaceUI.getCompositeIndex() - 1);
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

				} else if (compositeInstance instanceof TPresentationElementsUI) {
					TPresentationElementsUI tPresentationElementsUI =
					                                                  (TPresentationElementsUI) compositeInstance;
					if (tPresentationElementsUI.getCompositeIndex() > childCompositeIndex) {
						tPresentationElementsUI.setCompositeIndex(tPresentationElementsUI.getCompositeIndex() - 1);
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
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model) throws JAXBException {
		notification = (TNotification) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(notification.getDocumentation()
				                                       .get(tDocumentationUI.getObjectIndex()));
			} else if (compositeInstance instanceof TNotificationInterfaceUI) {
				TNotificationInterfaceUI tTaskInterfaceUI =
				                                            (TNotificationInterfaceUI) compositeInstance;
				tTaskInterfaceUI.onPageRefresh(textEditor);
				tTaskInterfaceUI.loadModel(notification.getInterface());
			} else if (compositeInstance instanceof TPriorityExprUI) {
				TPriorityExprUI tPriorityUI = (TPriorityExprUI) compositeInstance;
				tPriorityUI.loadModel(notification.getPriority());
			} else if (compositeInstance instanceof TPeopleAssignmentsUI) {
				TPeopleAssignmentsUI tPeopleAssignmentsUI =
				                                            (TPeopleAssignmentsUI) compositeInstance;
				tPeopleAssignmentsUI.tPeopleAssignments = notification.getPeopleAssignments();
				tPeopleAssignmentsUI.onPageRefresh(textEditor);
				tPeopleAssignmentsUI.loadModel(notification.getPeopleAssignments());

			} else if (compositeInstance instanceof TPresentationElementsUI) {
				TPresentationElementsUI tPresentationElementsUI =
				                                                  (TPresentationElementsUI) compositeInstance;
				tPresentationElementsUI.presentationElements =
				                                               notification.getPresentationElements();
				tPresentationElementsUI.onPageRefresh(textEditor);
				tPresentationElementsUI.loadModel(notification.getPresentationElements());

			} else if (compositeInstance instanceof TRenderingsUI) {
				TRenderingsUI tRenderingsUI = (TRenderingsUI) compositeInstance;
				tRenderingsUI.renderings = notification.getRenderings();
				tRenderingsUI.onPageRefresh(textEditor);
				tRenderingsUI.loadModel(notification.getRenderings());

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
