package org.wso2.developerstudio.humantask.editor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.developerstudio.humantask.models.THumanInteractions;
import org.wso2.developerstudio.humantask.models.TImport;
import org.wso2.developerstudio.humantask.models.TLogicalPeopleGroups;
import org.wso2.developerstudio.humantask.models.TNotifications;
import org.wso2.developerstudio.humantask.models.TTasks;
import org.wso2.developerstudio.humantask.uimodel.TImportUI;
import org.wso2.developerstudio.humantask.uimodel.TLogicalPeopleGroupsUI;
import org.wso2.developerstudio.humantask.uimodel.TNameUI;
import org.wso2.developerstudio.humantask.uimodel.TNotificationsUI;
import org.wso2.developerstudio.humantask.uimodel.TTasksUI;
import org.xml.sax.SAXException;

public class THumanInteractionsUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public THumanInteractions humanInteractions;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	private ArrayList<NamespaceItemComposite> namespaceItemList;
	public Composite parentComposite;
	private NodeList nodeList = null;
	Button btnAddNamespace;
	public Composite detailAreaInnerComposite = null;
	protected ToolItem btnGenerateHTConfig;
	private NamespaceItemComposite xmlnsHtdComposite = null;
	private Text txtTargetNamespace;

	public THumanInteractionsUI(XMLEditor textEditor, Composite parentComposite,
	                            Composite parentTagContainer, int styleBit, Object modelParent,
	                            int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.IMPORT_TITLE,
		                    HTEditorConstants.LOGICAL_PEOPLE_GROUPS_TITLE,
		                    HTEditorConstants.TASKS_TITLE, HTEditorConstants.NOTIFICATIONS_TITLE },
		      HTEditorConstants.HUMAN_INTERACTIONS_TITLE);
		this.humanInteractions = (THumanInteractions) modelParent;
		this.objectIndex = objectIndex;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.textEditor = textEditor;
		this.childObjectIndexes = new int[4];
		namespaceItemList = new ArrayList<NamespaceItemComposite>();
		this.parentComposite = super.parentComposite;
		setExpanded(true);

	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) {
		System.out.println("\n huaaaaa clicked update");

		System.out.println("\n Checking available otherattributes when update clicked:");
		for (Entry<QName, String> entry : (humanInteractions.getOtherAttributes()).entrySet()) {

			System.out.println("prefix:" + entry.getKey().getPrefix() + "local part:" + entry.getKey().getLocalPart() + "| value " +
			                   entry.getValue());
			// i++;
		}

		System.out.println("check null:");
		System.out.println(humanInteractions.getOtherAttributes() == null);
		System.out.println("\n size before clearing otherattributes list:" +
		                   humanInteractions.getOtherAttributes().size());
		/*
		 * for(Iterator<Map.Entry<QName, String>> it =
		 * humanInteractions.getOtherAttributes().entrySet().iterator();
		 * it.hasNext(); ) {
		 * Map.Entry<QName, String> entry = it.next();
		 * if(entry.getKey().getLocalPart().startsWith("xmlns:")) {
		 * System.out.println("an item removed.");
		 * it.remove();
		 * }
		 * }
		 */
		// humanInteractions.getOtherAttributes().clear();
		System.out.println("size after clearing otherattributes list:" +
		                   humanInteractions.getOtherAttributes().size());

		System.out.println("namespace itemlist size:" + namespaceItemList.size());
		for (int namespaceItemIndex = 0; namespaceItemIndex < namespaceItemList.size(); namespaceItemIndex++) {
			String suffixOf_localPart =
			                            ((NamespaceItemComposite) namespaceItemList.get(namespaceItemIndex)).txtPrefix.getText();
			String namespaceTxt =
			                      ((NamespaceItemComposite) namespaceItemList.get(namespaceItemIndex)).txtNamespace.getText();
			if (suffixOf_localPart == "" || suffixOf_localPart == null ||
			    suffixOf_localPart.isEmpty()) {
				suffixOf_localPart = "(value is not set)";
				System.out.println("empty caught");
				((NamespaceItemComposite) namespaceItemList.get(namespaceItemIndex)).txtPrefix.setText(suffixOf_localPart);
			}
			if (namespaceTxt == "" || namespaceTxt == null || namespaceTxt.isEmpty()) {
				namespaceTxt = "(value is not set)";
				System.out.println("empty caught");
				((NamespaceItemComposite) namespaceItemList.get(namespaceItemIndex)).txtNamespace.setText(namespaceTxt);
			}
			humanInteractions.getOtherAttributes().put(new QName(HTEditorConstants.XMLNS_TITLE +
			                                                     suffixOf_localPart), namespaceTxt);
			/*
			 	humanInteractions.getOtherAttributes().put(new QName(m1.item(k).getNodeName()),
				                                           m1.item(k).getNodeValue()); */

		}

		if (humanInteractions.getOtherAttributes().size() > 0) {
			System.out.println("in updation :in if in btnUpdateHandleLogic method");
			int i = 0;
			int ss = humanInteractions.getOtherAttributes().size(); // get(new
			                                                        // QName(HTEditorConstants.XMLNS_TITLE
			                                                        // +
			                                                        // "aaa"));
			System.out.println("size of otherattributes now is :" + ss);

			for (Entry<QName, String> entry : (humanInteractions.getOtherAttributes()).entrySet()) {
				// System.out.println("in for in if in update method");
				System.out.println("prefix:" + entry.getKey().getPrefix() + "| local part:" +
				                   entry.getKey().getLocalPart() + "| value " + entry.getValue());
				i++;
			}

		}

		humanInteractions.setQueryLanguage(((Text) textBoxesList.get(0)).getText());
		humanInteractions.setTargetNamespace(((Text) textBoxesList.get(1)).getText());
		humanInteractions.setExpressionLanguage(((Text) textBoxesList.get(2)).getText());
		try {
			centralUtils.marshal(textEditor);
		} catch (JAXBException e) {
			System.out.println("error while marshalling at updating");
			e.printStackTrace();
		}
		// parentComposite = super.parentComposite;
		parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		System.out.println("btnUpdateHandleLogic over \n \n");
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void refreshLogic(XMLEditor editor) {// throws JAXBException {
		System.out.println("\n in refresh logic method:");
		System.out.println("\n Checking otherattributes when jsut refresh occured:");
		try {
			for (Entry<QName, String> entry : (humanInteractions.getOtherAttributes()).entrySet()) {

				System.out.println("local part:" + entry.getKey().getLocalPart() + "| value " +
				                   entry.getValue());
				// i++;
			}
		} catch (Exception e1) {
			// do nothing. this exception is expected at some occasions
		}

		detailArea.setSize(detailArea.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		try {
			nodeList = centralUtils.getotherattrs().getElementsByTagName("htd:tHumanInteractions");
			System.out.println("nodelist size in refresh logic method:(htd:tHumanInteractions)" +
			                   nodeList.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (nodeList.getLength() == 0) {
			try {
				nodeList =
				           centralUtils.getotherattrs()
				                       .getElementsByTagName("htd:humanInteractions");
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			System.out.println("nodelist size in refresh logic method:(htd:humanInteractions):" +
			                   nodeList.getLength());
		}
		NamedNodeMap m1 = nodeList.item(0).getAttributes();
		System.out.println("no of attributes:" + m1.getLength());

		// if (btnAddNamespace != null) {

		// if (detailAreaInnerComposite != null) {
		try {
			detailAreaInnerComposite.dispose();
			System.out.println("detailAreaComposite disposed.");
		} catch (Exception e) {
			// this exception is expected at a random occasion while running the
			// project.
			// e.printStackTrace();
		}
		for (Composite nsitem : namespaceItemList) {
			nsitem.dispose();
		}

		// }

		// parentComposite = super.parentComposite;
		// parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT,
		// SWT.DEFAULT));

		// final Composite
		detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		namespaceItemList.clear();
		System.out.println("size after clearing list: " + namespaceItemList.size());

		// add xmlns:htd
		for (int k = 0; k < m1.getLength(); k++) {

			// If the attribute is only a xml namespace>> starts with xmlns:
			if (m1.item(k).getNodeName() == "xmlns:htd") {
				if (xmlnsHtdComposite != null) {
					xmlnsHtdComposite.dispose();
				}
				xmlnsHtdComposite =
				                    new NamespaceItemComposite(detailAreaInnerComposite, SWT.NONE,
				                                               2);
				System.out.println("pooops:" + m1.item(k).getNodeName());
				xmlnsHtdComposite.txtPrefix.setText(m1.item(k).getNodeName().substring(6));
				xmlnsHtdComposite.txtNamespace.setText(m1.item(k).getNodeValue());
				xmlnsHtdComposite.txtPrefix.setEditable(false);
				xmlnsHtdComposite.txtNamespace.setEditable(false);
				xmlnsHtdComposite.txtPrefix.setToolTipText("This Namespace cannot be editted");
				xmlnsHtdComposite.txtNamespace.setToolTipText("This Namespace cannot be editted");

				xmlnsHtdComposite.btnRemoveNamespace.setEnabled(false);

			}
		}
		
		for (int k = 0; k < m1.getLength(); k++) {
			System.out.println("node name-" + m1.item(k).getNodeName()+
			                   " |node value- " + m1.item(k).getNodeValue());
		}

		for (int k = 0; k < m1.getLength(); k++) {

			// If the attribute is only a xml namespace>> starts with xmlns:
			if (m1.item(k).getNodeType() == Node.ATTRIBUTE_NODE &&
			    m1.item(k).getNodeName() != "targetNamespace" &&
			    m1.item(k).getNodeName() != "queryLanguage" &&
			    m1.item(k).getNodeName() != "expressionLanguage" &&
			    m1.item(k).getNodeName() != "xmlns:htd" &&
			    m1.item(k).getNodeName().startsWith("xmlns:")) {

				System.out.println("Adding prevailed item");
				System.out.println("node name:xmlns:" + m1.item(k).getNodeName().substring(6) +
				                   " |node value: " + m1.item(k).getNodeValue());
				final int tempindex = namespaceItemList.size();
				namespaceItemList.add(new NamespaceItemComposite(detailAreaInnerComposite,
				                                                 SWT.NONE, tempindex));
				final NamespaceItemComposite namedItemComposite = namespaceItemList.get(tempindex);

				namedItemComposite.txtPrefix.setText(m1.item(k).getNodeName().substring(6));
				namedItemComposite.txtNamespace.setText(m1.item(k).getNodeValue());

				(namedItemComposite.btnRemoveNamespace).addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						namespaceItemList.remove((namespaceItemList.indexOf(namedItemComposite)));
						namedItemComposite.dispose();
						parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT,
						                                                    SWT.DEFAULT));

						/*
						 * for (NamespaceItemComposite
						 * singleNamedItemComposite : namespaceItemList)
						 * { if (namedItemComposite.itemIndex <
						 * singleNamedItemComposite.itemIndex) {
						 * singleNamedItemComposite.itemIndex =
						 * singleNamedItemComposite.itemIndex - 1;
						 * 
						 * }
						 * 
						 * }
						 */
					}
				});

			} /*else if (m1.item(k).getNodeName().contains(":") &&
			           m1.item(k).getNodeName() != "xmlns:htd") {
				System.out.println("\n\n special:" + m1.item(k).getNodeName() + " |node value: " +
				                   m1.item(k).getNodeValue());
				humanInteractions.getOtherAttributes().put(new QName(m1.item(k).getNodeName()),
				                                           m1.item(k).getNodeValue());

			} */else {
				System.out.println("\n\nelse case..!!! this is a user made otherrrr attribute>>node name:" +
				                   m1.item(k).getNodeName() +
				                   " |node value: " +
				                   m1.item(k).getNodeValue());
			}

		}

		try {
			btnAddNamespace.dispose();
			System.out.println("btnaddNAMespace disposed.");
		} catch (Exception e) {
			System.out.println("btnaddNAMespace is already disposed.");
			// e.printStackTrace();
		}
		btnAddNamespace = new Button(detailArea, SWT.NONE);
		btnAddNamespace.setBounds(373, 20, 91, 29);
		btnAddNamespace.setText("Add Namespace");

		// adding listner to add more rows
		btnAddNamespace.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println("new button clicked. list size now is:" +
				                   namespaceItemList.size());
				final int tempindex = namespaceItemList.size();
				namespaceItemList.add(new NamespaceItemComposite(detailAreaInnerComposite,
				                                                 SWT.NONE, tempindex));
				final NamespaceItemComposite namespaceItemComposite =
				                                                      namespaceItemList.get(tempindex);
				namespaceItemComposite.txtPrefix.setText("");
				namespaceItemComposite.txtNamespace.setText("");

				(namespaceItemComposite.btnRemoveNamespace).addListener(SWT.Selection,
				                                                        new Listener() {

					                                                        @Override
					                                                        public void handleEvent(Event event) {
						                                                        namespaceItemList.remove((namespaceItemList.indexOf(namespaceItemComposite)));
						                                                        namespaceItemComposite.dispose();
						                                                        parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT,
						                                                                                                            SWT.DEFAULT));

						                                                        /*
																				 * for
																				 * (
																				 * NamespaceItemComposite
																				 * singleNamedItemComposite
																				 * :
																				 * namespaceItemList
																				 * )
																				 * {
																				 * if
																				 * (
																				 * namedItemComposite
																				 * .
																				 * itemIndex
																				 * <
																				 * singleNamedItemComposite
																				 * .
																				 * itemIndex
																				 * )
																				 * {
																				 * singleNamedItemComposite
																				 * .
																				 * itemIndex
																				 * =
																				 * singleNamedItemComposite
																				 * .
																				 * itemIndex
																				 * -
																				 * 1
																				 * ;
																				 * 
																				 * }
																				 * 
																				 * }
																				 */
					                                                        }
				                                                        });
				// parentComposite=

				parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

			}
		});

		parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// refresh elements
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;
		if (humanInteractions != null) {
			ArrayList<TImport> importGroup = (ArrayList<TImport>) humanInteractions.getImport();
			for (int importGroupIndex = 0; importGroupIndex < importGroup.size(); importGroupIndex++) {
				TImportUI tImportUI = null;
				try {
					tImportUI =
					            new TImportUI(editor, detailArea, childCompositeIndex,
					                          childObjectIndexes[0], SWT.NONE, this,
					                          importGroup.get(childObjectIndexes[0]));
					tImportUI.initialize(editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				childComposites.add(childCompositeIndex, tImportUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			if (humanInteractions.getLogicalPeopleGroups() != null) {
				TLogicalPeopleGroups tLogicalPeopleGroup =
				                                           (TLogicalPeopleGroups) humanInteractions.getLogicalPeopleGroups();
				TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI = null;
				try {
					tLogicalPeopleGroupsUI =
					                         new TLogicalPeopleGroupsUI(editor, detailArea, this,
					                                                    SWT.NONE,
					                                                    tLogicalPeopleGroup,
					                                                    childObjectIndexes[1],
					                                                    childCompositeIndex);
					tLogicalPeopleGroupsUI.initialize(editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				childComposites.add(childCompositeIndex, tLogicalPeopleGroupsUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (humanInteractions.getTasks() != null) {
				TTasks tTasks = (TTasks) humanInteractions.getTasks();
				TTasksUI tTasksUI = null;
				try {
					tTasksUI =
					           new TTasksUI(editor, detailArea, this, SWT.NONE, tTasks,
					                        childObjectIndexes[2], childCompositeIndex);
					tTasksUI.initialize(editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				childComposites.add(childCompositeIndex, tTasksUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;
			}

			if (humanInteractions.getNotifications() != null) {
				TNotifications tLogicalPeopleGroup =
				                                     (TNotifications) humanInteractions.getNotifications();
				TNotificationsUI tNotificationsUI = null;
				try {
					tNotificationsUI =
					                   new TNotificationsUI(editor, detailArea, this, SWT.NONE,
					                                        tLogicalPeopleGroup,
					                                        childObjectIndexes[3],
					                                        childCompositeIndex);
					tNotificationsUI.initialize(editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				childComposites.add(childCompositeIndex, tNotificationsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;
			}
		}

		/*
		 * System.out.println("\n Checking otherattributes after refresh occured:"
		 * );
		 * for (Entry<QName, String> entry :
		 * (humanInteractions.getOtherAttributes()).entrySet()) {
		 * 
		 * System.out.println("local part:" +
		 * entry.getKey().getLocalPart() + "| value " + entry.getValue());
		 * //i++;
		 * }
		 */

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if (humanInteractions != null) {
			if (humanInteractions.getQueryLanguage() != null) {
				((Text) textBoxesList.get(0)).setText(humanInteractions.getQueryLanguage());
			}
			if (humanInteractions.getTargetNamespace() != null) {
				((Text) textBoxesList.get(1)).setText(humanInteractions.getTargetNamespace());
			}
			if (humanInteractions.getExpressionLanguage() != null) {
				((Text) textBoxesList.get(2)).setText(humanInteractions.getExpressionLanguage());
			}
		}
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3, XMLEditor editor,
	                           Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.IMPORT_TITLE)) {
			TImport tImport = new TImport();
			tImport.setImportType("");
			tImport.setLocation("");
			tImport.setNamespace("");
			humanInteractions.getImport().add(childObjectIndexes[0], tImport);
			TImportUI tNot =
			                 new TImportUI(editor, composite, childCompositeIndex,
			                               childObjectIndexes[0], SWT.NONE, this, tImport);
			childComposites.add(childCompositeIndex, tNot);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase(HTEditorConstants.TASKS_TITLE)) {
			if (childObjectIndexes[2] < 1) {
				TTasks tTasks = new TTasks();
				humanInteractions.setTasks(tTasks);
				TTasksUI tTasksUI =
				                    new TTasksUI(editor, composite, this, SWT.NONE, tTasks,
				                                 childObjectIndexes[2], childCompositeIndex);
				childComposites.add(childCompositeIndex, tTasksUI);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.LOGICAL_PEOPLE_GROUPS_TITLE)) {
			if (childObjectIndexes[1] < 1) {
				TLogicalPeopleGroups tLogicalPeopleGroups = new TLogicalPeopleGroups();
				humanInteractions.setLogicalPeopleGroups(tLogicalPeopleGroups);
				TLogicalPeopleGroupsUI tLogicalPeopleGroupUI =
				                                               new TLogicalPeopleGroupsUI(
				                                                                          editor,
				                                                                          composite,
				                                                                          this,
				                                                                          SWT.NONE,
				                                                                          tLogicalPeopleGroups,
				                                                                          childObjectIndexes[1],
				                                                                          childCompositeIndex);
				childComposites.add(childCompositeIndex, tLogicalPeopleGroupUI);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		} else if (selection.equalsIgnoreCase(HTEditorConstants.NOTIFICATIONS_TITLE)) {
			if (childObjectIndexes[3] < 1) {
				TNotifications tNotifications = new TNotifications();
				humanInteractions.setNotifications(tNotifications);
				TNotificationsUI tNotificationsUI =
				                                    new TNotificationsUI(editor, composite, this,
				                                                         SWT.NONE, tNotifications,
				                                                         childObjectIndexes[3],
				                                                         childCompositeIndex);
				childComposites.add(childCompositeIndex, tNotificationsUI);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			}
		}
		centralUtils.marshal(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		final Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(1, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		final Table table = new Table(detailArea, SWT.MULTI);
		table.setLinesVisible(true);

		new TableColumn(table, SWT.NONE).setWidth(160);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(160);
		new TableColumn(table, SWT.NONE).setWidth(300);

		TableItem tableRow1 = new TableItem(table, SWT.NONE);
		tableRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		tblEditor = new TableEditor(table);
		Label lblQueryLang = new Label(table, SWT.NONE);
		lblQueryLang.setText("  " + HTEditorConstants.QUERY_LANGUAGE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblQueryLang, tableRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtQueryLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtQueryLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtQueryLang, tableRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblTargetNamespace = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblTargetNamespace.setText("     " + HTEditorConstants.TARGET_NAMESPACE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblTargetNamespace, tableRow1, 2);

		tblEditor = new TableEditor(table);
		txtTargetNamespace = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtTargetNamespace);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtTargetNamespace, tableRow1, 3);

		TableItem tblRow2 = new TableItem(table, SWT.NONE);

		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblEditor = new TableEditor(table);

		Label lblExpressionLanguage = new Label(table, SWT.NONE);
		lblExpressionLanguage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblExpressionLanguage.setText("  " + HTEditorConstants.EXPRESSION_LANGUAGE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblExpressionLanguage, tblRow2, 0);

		tblEditor = new TableEditor(table);
		Text txtExpressionLanguage = new Text(table, SWT.BORDER);
		textBoxesList.add(2, txtExpressionLanguage);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtExpressionLanguage, tblRow2, 1);

		tblEditor = new TableEditor(table);
		Label lblValue2 = new Label(table, SWT.NONE);
		lblValue2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue2.setText("");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue2, tblRow2, 2);

		tblEditor = new TableEditor(table);
		Label lblValue3 = new Label(table, SWT.NONE);
		lblValue3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue3.setText("");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue3, tblRow2, 3);

		// btnGenerateHTConfig setting
		// Eventhough this btnGenerateHTConfig is not in the detail area setting
		// it is done in this fillDetailArea method
		btnGenerateHTConfig = new ToolItem(titleToolBar, SWT.NONE);
		btnGenerateHTConfig.setToolTipText(HTEditorConstants.GENERATE_HT_CONFIG_BUTTON_TITLE);
		btnGenerateHTConfig.setWidth(27);
		btnGenerateHTConfig.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                            HTEditorConstants.GENERATE_HT_CONFIG_BUTTON_IMAGE));
		btnGenerateHTConfig.setText(HTEditorConstants.GENERATE_HT_CONFIG_BUTTON_TITLE);

		// textEditor=super.xmlEditor;
		btnGenerateHTConfig.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					centralUtils.fillConfigObject(textEditor);
					centralUtils.writeHTConfig(textEditor);
				} catch (ParserConfigurationException | SAXException | IOException | JAXBException
				        | CoreException e) {
					MessageDialog.openError(Display.getCurrent().getActiveShell(),
					                        HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.CONFIG_ERROR_MESSAGE);
				} catch (NullPointerException e) {
					MessageDialog.openError(Display.getCurrent().getActiveShell(),
					                        HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.GENERATE_CONFIG_ERROR_MESSAGE);
				}
			}
		});

		btnRemove.dispose();
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex, int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.IMPORT_TITLE)) {
			this.childObjectIndexes[0]--;
			humanInteractions.getImport().clear();
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TImportUI) {
					TImportUI tImportUI = (TImportUI) compositeInstance;
					if (tImportUI.getCompositeIndex() > childCompositeIndex) {
						tImportUI.setCompositeIndex(tImportUI.getCompositeIndex() - 1);
					}
					if (tImportUI.getObjectIndex() > childObjectIndex) {
						tImportUI.setObjectIndex(tImportUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
					TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
					                                                (TLogicalPeopleGroupsUI) compositeInstance;
					if (tLogicalPeopleGroupsUI.getCompositeIndex() > childCompositeIndex) {
						tLogicalPeopleGroupsUI.setCompositeIndex(tLogicalPeopleGroupsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TTasksUI) {
					TTasksUI tTasksUI = (TTasksUI) compositeInstance;
					if (tTasksUI.getCompositeIndex() > childCompositeIndex) {
						tTasksUI.setCompositeIndex(tTasksUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TNotificationsUI) {
					TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
					if (tNotificationsUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationsUI.setCompositeIndex(tNotificationsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.LOGICAL_PEOPLE_GROUPS_TITLE)) {
			this.childObjectIndexes[1]--;
			humanInteractions.setLogicalPeopleGroups(null);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TImportUI) {
					TImportUI tImportUI = (TImportUI) compositeInstance;
					if (tImportUI.getCompositeIndex() > childCompositeIndex) {
						tImportUI.setCompositeIndex(tImportUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
					TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
					                                                (TLogicalPeopleGroupsUI) compositeInstance;
					if (tLogicalPeopleGroupsUI.getCompositeIndex() > childCompositeIndex) {
						tLogicalPeopleGroupsUI.setCompositeIndex(tLogicalPeopleGroupsUI.getCompositeIndex() - 1);
					}
					if (tLogicalPeopleGroupsUI.getObjectIndex() > childObjectIndex) {
						tLogicalPeopleGroupsUI.setObjectIndex(tLogicalPeopleGroupsUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TTasksUI) {
					TTasksUI tTasksUI = (TTasksUI) compositeInstance;
					if (tTasksUI.getCompositeIndex() > childCompositeIndex) {
						tTasksUI.setCompositeIndex(tTasksUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TNotificationsUI) {
					TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
					if (tNotificationsUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationsUI.setCompositeIndex(tNotificationsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.TASKS_TITLE)) {
			this.childObjectIndexes[2]--;
			humanInteractions.setTasks(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TImportUI) {
					TImportUI tImportUI = (TImportUI) compositeInstance;
					if (tImportUI.getCompositeIndex() > childCompositeIndex) {
						tImportUI.setCompositeIndex(tImportUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
					TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
					                                                (TLogicalPeopleGroupsUI) compositeInstance;
					if (tLogicalPeopleGroupsUI.getCompositeIndex() > childCompositeIndex) {
						tLogicalPeopleGroupsUI.setCompositeIndex(tLogicalPeopleGroupsUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TTasksUI) {
					TTasksUI tTasksUI = (TTasksUI) compositeInstance;
					if (tTasksUI.getCompositeIndex() > childCompositeIndex) {
						tTasksUI.setCompositeIndex(tTasksUI.getCompositeIndex() - 1);
					}
					if (tTasksUI.getObjectIndex() > childObjectIndex) {
						tTasksUI.setObjectIndex(tTasksUI.getObjectIndex() - 1);
					}
				} else if (compositeInstance instanceof TNotificationsUI) {
					TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
					if (tNotificationsUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationsUI.setCompositeIndex(tNotificationsUI.getCompositeIndex() - 1);
					}

				} else {

				}

			}
		} else if (itemName.equalsIgnoreCase(HTEditorConstants.NOTIFICATIONS_TITLE)) {
			this.childObjectIndexes[3]--;
			humanInteractions.setNotifications(null);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TImportUI) {
					TImportUI tImportUI = (TImportUI) compositeInstance;
					if (tImportUI.getCompositeIndex() > childCompositeIndex) {
						tImportUI.setCompositeIndex(tImportUI.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
					TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
					                                                (TLogicalPeopleGroupsUI) compositeInstance;
					if (tLogicalPeopleGroupsUI.getCompositeIndex() > childCompositeIndex) {
						tLogicalPeopleGroupsUI.setCompositeIndex(tLogicalPeopleGroupsUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TTasksUI) {
					TTasksUI tTasksUI = (TTasksUI) compositeInstance;
					if (tTasksUI.getCompositeIndex() > childCompositeIndex) {
						tTasksUI.setCompositeIndex(tTasksUI.getCompositeIndex() - 1);
					}

				} else if (compositeInstance instanceof TNotificationsUI) {
					TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
					if (tNotificationsUI.getCompositeIndex() > childCompositeIndex) {
						tNotificationsUI.setCompositeIndex(tNotificationsUI.getCompositeIndex() - 1);
					}
					if (tNotificationsUI.getObjectIndex() > childObjectIndex) {
						tNotificationsUI.setObjectIndex(tNotificationsUI.getObjectIndex() - 1);
					}
				} else {

				}

			}
		}
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;
	}

	public void loadModel(Object model) throws JAXBException {
		humanInteractions = (THumanInteractions) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TTasksUI) {
				TTasksUI tTasksUI = (TTasksUI) compositeInstance;
				tTasksUI.tasks = humanInteractions.getTasks();
				tTasksUI.refreshLogic(textEditor);
				tTasksUI.loadModel(humanInteractions.getTasks());
			} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
				TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
				                                                (TLogicalPeopleGroupsUI) compositeInstance;
				tLogicalPeopleGroupsUI.logicalPeopleGroups =
				                                             humanInteractions.getLogicalPeopleGroups();
				tLogicalPeopleGroupsUI.refreshLogic(textEditor);
				tLogicalPeopleGroupsUI.loadModel(humanInteractions.getLogicalPeopleGroups());
			} else if (compositeInstance instanceof TNotificationsUI) {
				TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
				tNotificationsUI.notifications = humanInteractions.getNotifications();
				tNotificationsUI.refreshLogic(textEditor);
				tNotificationsUI.loadModel(humanInteractions.getNotifications());

			}
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
