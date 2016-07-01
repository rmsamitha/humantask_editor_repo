package org.wso2.developerstudio.humantask.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
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
import org.wso2.developerstudio.humantask.uimodel.TNotificationsUI;
import org.wso2.developerstudio.humantask.uimodel.TTasksUI;
import org.xml.sax.SAXException;

/**
 * The UI class representing the "humanInteractions" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
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
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class.getName());

	/**
	 * Call the super abstract class to set the UI and initialize class's
	 * attribute variables
	 * 
	 * @param textEditor
	 * @param parentComposite
	 * @param parentTagContainer
	 * @param styleBit
	 * @param modelParent
	 * @param objectIndex
	 * @param compositeIndex
	 * @throws JAXBException
	 */
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

	/**
	 * Update the values of attributes of the section and marshal into the
	 * TextEditor when the update button of that section is clicked
	 * 
	 * @param textEditor
	 */
	@Override
	public void onBtnUpdate(XMLEditor textEditor) {
		for (int namespaceItemIndex = 0; namespaceItemIndex < namespaceItemList.size(); namespaceItemIndex++) {
			String suffixOf_localPart =
			                            namespaceItemList.get(namespaceItemIndex).txtPrefix.getText();
			String namespaceTxt = namespaceItemList.get(namespaceItemIndex).txtNamespace.getText();
			if (suffixOf_localPart == "" || suffixOf_localPart == null ||
			    suffixOf_localPart.isEmpty()) {
				suffixOf_localPart = "(value is not set)";
				namespaceItemList.get(namespaceItemIndex).txtPrefix.setText(suffixOf_localPart);
			}
			if (namespaceTxt == "" || namespaceTxt == null || namespaceTxt.isEmpty()) {
				namespaceTxt = "(value is not set)";
				namespaceItemList.get(namespaceItemIndex).txtNamespace.setText(namespaceTxt);
			}
			humanInteractions.getOtherAttributes().put(new QName(HTEditorConstants.XMLNS_TITLE +
			                                                     suffixOf_localPart), namespaceTxt);

		}

		humanInteractions.setQueryLanguage(((Text) textBoxesList.get(0)).getText());
		humanInteractions.setTargetNamespace(((Text) textBoxesList.get(1)).getText());
		humanInteractions.setExpressionLanguage(((Text) textBoxesList.get(2)).getText());
		try {
			centralUtils.marshal(textEditor);
		} catch (JAXBException e) {
			LOG.info(e.getMessage());
		}
		parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void onBtnRemove(XMLEditor textEditor) throws JAXBException {
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

		detailArea.setSize(detailArea.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		try {
			nodeList = centralUtils.getotherattrs().getElementsByTagName("htd:tHumanInteractions");

		} catch (ParserConfigurationException e) {
			LOG.info(e.getMessage());
		}
		if (nodeList.getLength() == 0) {
			try {
				nodeList =
				           centralUtils.getotherattrs()
				                       .getElementsByTagName("htd:humanInteractions");
			} catch (ParserConfigurationException e) {
				MessageDialog.openError(Display.getDefault().getActiveShell(),
				                        "Parser Configuration Error",
				                        "Parser ConfigurationError Occured");
			}
		}

		NamedNodeMap m1 = nodeList.item(0).getAttributes();

		if (detailAreaInnerComposite != null) {
			detailAreaInnerComposite.dispose();
		}

		for (Composite nsitem : namespaceItemList) {
			nsitem.dispose();
		}

		detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		namespaceItemList.clear();

		// add the field for xmlns:htd
		for (int k = 0; k < m1.getLength(); k++) {

			// If the attribute is only a xmlns:htd
			if (m1.item(k).getNodeName() == "xmlns:htd") {
				if (xmlnsHtdComposite != null) {
					xmlnsHtdComposite.dispose();
				}
				xmlnsHtdComposite =
				                    new NamespaceItemComposite(detailAreaInnerComposite, SWT.NONE,
				                                               2);
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
			// If the attribute is only a xml namespace>> starts with xmlns: and
			// not xmlns:htd
			if (m1.item(k).getNodeType() == Node.ATTRIBUTE_NODE &&
			    m1.item(k).getNodeName() != "targetNamespace" &&
			    m1.item(k).getNodeName() != "queryLanguage" &&
			    m1.item(k).getNodeName() != "expressionLanguage" &&
			    m1.item(k).getNodeName() != "xmlns:htd" &&
			    m1.item(k).getNodeName().startsWith("xmlns:")) {

				final int tempindex = namespaceItemList.size();
				namespaceItemList.add(new NamespaceItemComposite(detailAreaInnerComposite,
				                                                 SWT.NONE, tempindex));
				final NamespaceItemComposite namedItemComposite = namespaceItemList.get(tempindex);

				namedItemComposite.txtPrefix.setText(m1.item(k).getNodeName().substring(6));
				namedItemComposite.txtNamespace.setText(m1.item(k).getNodeValue());

				namedItemComposite.btnRemoveNamespace.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						namespaceItemList.remove(namespaceItemList.indexOf(namedItemComposite));
						namedItemComposite.dispose();
						parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT,
						                                                    SWT.DEFAULT));

					}
				});

			} /*
			   * else if (m1.item(k).getNodeName().contains(":") &&
			   * m1.item(k).getNodeName() != "xmlns:htd") {
			   * System.out.println("\n\n special:" + m1.item(k).getNodeName() +
			   * " |node value: " +
			   * m1.item(k).getNodeValue());
			   * humanInteractions.getOtherAttributes().put(new
			   * QName(m1.item(k).getNodeName()),
			   * m1.item(k).getNodeValue());
			   * 
			   * } e
			   */else {
				// do nothing
			}

		}

		if (btnAddNamespace != null) {
			btnAddNamespace.dispose();
		}

		btnAddNamespace = new Button(detailArea, SWT.NONE);
		btnAddNamespace.setBounds(373, 20, 91, 29);
		btnAddNamespace.setText("Add Namespace");

		// adding listner to add more rows
		btnAddNamespace.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				final int tempindex = namespaceItemList.size();
				namespaceItemList.add(new NamespaceItemComposite(detailAreaInnerComposite,
				                                                 SWT.NONE, tempindex));
				final NamespaceItemComposite namespaceItemComposite =
				                                                      namespaceItemList.get(tempindex);
				namespaceItemComposite.txtPrefix.setText("");
				namespaceItemComposite.txtNamespace.setText("");

				namespaceItemComposite.btnRemoveNamespace.addListener(SWT.Selection,
				                                                      new Listener() {

					                                                      @Override
					                                                      public void handleEvent(Event event) {
						                                                      namespaceItemList.remove(namespaceItemList.indexOf(namespaceItemComposite));
						                                                      namespaceItemComposite.dispose();
						                                                      parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT,
						                                                                                                          SWT.DEFAULT));

					                                                      }
				                                                      });

				parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

			}
		});

		parentComposite.setSize(parentComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// refresh the elements
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
					LOG.info(e.getMessage());
				}

				childComposites.add(childCompositeIndex, tImportUI);
				childCompositeIndex++;
				childObjectIndexes[0]++;
			}

			if (humanInteractions.getLogicalPeopleGroups() != null) {
				TLogicalPeopleGroups tLogicalPeopleGroup =
				                                           humanInteractions.getLogicalPeopleGroups();
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
					LOG.info(e.getMessage());
				}

				childComposites.add(childCompositeIndex, tLogicalPeopleGroupsUI);
				childCompositeIndex++;
				childObjectIndexes[1]++;
			}

			if (humanInteractions.getTasks() != null) {
				TTasks tTasks = humanInteractions.getTasks();
				TTasksUI tTasksUI = null;
				try {
					tTasksUI =
					           new TTasksUI(editor, detailArea, this, SWT.NONE, tTasks,
					                        childObjectIndexes[2], childCompositeIndex);
					tTasksUI.initialize(editor);
				} catch (JAXBException e) {
					LOG.info(e.getMessage());
				}

				childComposites.add(childCompositeIndex, tTasksUI);
				childCompositeIndex++;
				childObjectIndexes[2]++;
			}

			if (humanInteractions.getNotifications() != null) {
				TNotifications tLogicalPeopleGroup = humanInteractions.getNotifications();
				TNotificationsUI tNotificationsUI = null;
				try {
					tNotificationsUI =
					                   new TNotificationsUI(editor, detailArea, this, SWT.NONE,
					                                        tLogicalPeopleGroup,
					                                        childObjectIndexes[3],
					                                        childCompositeIndex);
					tNotificationsUI.initialize(editor);
				} catch (JAXBException e) {
					LOG.info(e.getMessage());
				}

				childComposites.add(childCompositeIndex, tNotificationsUI);
				childCompositeIndex++;
				childObjectIndexes[3]++;
			}
		}
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
	public void onCreateNewChild(String selection, ScrolledComposite sc3, XMLEditor editor,
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

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the
	 * section
	 * 
	 * @param composite
	 */
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

	/**
	 * Load the JAXB model objects into the UI model from the top to bottom of
	 * the tree structure of the model whenever a tab change occurs from text
	 * editor to the UI editor.
	 * 
	 * @param model
	 * @throws JAXBException
	 */
	public void loadModel(Object model) throws JAXBException {
		humanInteractions = (THumanInteractions) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TTasksUI) {
				TTasksUI tTasksUI = (TTasksUI) compositeInstance;
				tTasksUI.tasks = humanInteractions.getTasks();
				tTasksUI.onPageRefresh(textEditor);
				tTasksUI.loadModel(humanInteractions.getTasks());
			} else if (compositeInstance instanceof TLogicalPeopleGroupsUI) {
				TLogicalPeopleGroupsUI tLogicalPeopleGroupsUI =
				                                                (TLogicalPeopleGroupsUI) compositeInstance;
				tLogicalPeopleGroupsUI.logicalPeopleGroups =
				                                             humanInteractions.getLogicalPeopleGroups();
				tLogicalPeopleGroupsUI.onPageRefresh(textEditor);
				tLogicalPeopleGroupsUI.loadModel(humanInteractions.getLogicalPeopleGroups());
			} else if (compositeInstance instanceof TNotificationsUI) {
				TNotificationsUI tNotificationsUI = (TNotificationsUI) compositeInstance;
				tNotificationsUI.notifications = humanInteractions.getNotifications();
				tNotificationsUI.onPageRefresh(textEditor);
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
