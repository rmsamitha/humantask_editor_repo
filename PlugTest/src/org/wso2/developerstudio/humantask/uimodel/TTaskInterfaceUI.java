package org.wso2.developerstudio.humantask.uimodel;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.w3c.dom.NodeList;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TTaskInterface;
import org.xml.sax.SAXException;

/**
 * The UI class representing the "taskInterface" xml element of
 * type "tTaskInterface"in the .ht file.
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TTaskInterfaceUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TTaskInterface taskInterface;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	private NodeList nodeList = null;

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
	public TTaskInterfaceUI(XMLEditor textEditor, Composite parentComposite,
	                        Composite parentTagContainer, int styleBit, Object objectModel,
	                        int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
		      new String[] { HTEditorConstants.DOCUMENTATION_TITLE },
		      HTEditorConstants.INTERFACE_TITLE);
		this.taskInterface = (TTaskInterface) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
		setExpanded(false);
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
		taskInterface.setPortType(new QName(((Combo) textBoxesList.get(0)).getText()));
		taskInterface.setOperation(((Combo) textBoxesList.get(1)).getText());
		taskInterface.setResponsePortType(new QName(((Combo) textBoxesList.get(2)).getText()));
		taskInterface.setResponseOperation(((Combo) textBoxesList.get(3)).getText());

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
		TTaskUI parentContainer = (TTaskUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.INTERFACE_TITLE, compositeIndex,
		                                objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
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
		if (taskInterface.getPortType() != null)
			((Combo) textBoxesList.get(0)).setText(taskInterface.getPortType().getLocalPart()
			                                                    .toString());
		if (taskInterface.getOperation() != null)
			((Combo) textBoxesList.get(1)).setText(taskInterface.getOperation());
		if (taskInterface.getResponsePortType() != null)
			((Combo) textBoxesList.get(2)).setText(taskInterface.getResponsePortType()
			                                                    .getLocalPart().toString());
		if (taskInterface.getResponseOperation() != null)
			((Combo) textBoxesList.get(3)).setText(taskInterface.getResponseOperation());
	}
	
	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the section
	 * 
	 * @param composite
	 */
	@Override
	public void fillDetailArea(Composite composite) {

		Composite innerZComp = formToolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(1, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final Table table = new Table(innerZComp, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(200);
		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(150);
		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		try {
			nodeList = centralUtils.getWSDL(xmlEditor).getElementsByTagName("wsdl:portType");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Label lblPortType = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblPortType.setText("  " + "Port Type ");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblPortType, tblRow1, 0);
		String[] portTypeList = new String[nodeList.getLength()];
		String[] operationList = new String[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++) {
			portTypeList[i] =
			                  nodeList.item(i).getAttributes().getNamedItem("name")
			                          .getTextContent();
			operationList[i] =
			                   nodeList.item(i).getChildNodes().item(1).getAttributes()
			                           .getNamedItem("name").getTextContent();
		}
		tblEditor = new TableEditor(table);
		final Combo cmbPortType = new Combo(table, SWT.NONE);
		cmbPortType.setItems(portTypeList);
		cmbPortType.select(0);
		textBoxesList.add(0, cmbPortType);

		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbPortType, tblRow1, 1);

		tblEditor = new TableEditor(table);
		Label lblOperation = new Label(table, SWT.NONE);
		lblOperation.setText("     " + "Operation");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblOperation, tblRow1, 2);

		tblEditor = new TableEditor(table);

		final Combo cmbOperation = new Combo(table, SWT.NONE);
		cmbOperation.select(0);
		textBoxesList.add(1, cmbOperation);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbOperation, tblRow1, 3);

		TableItem tblRow2 = new TableItem(table, SWT.NONE);
		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tblEditor = new TableEditor(table);

		Label lblResponsePortType = new Label(table, SWT.NONE);
		lblResponsePortType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblResponsePortType.setText("  " + HTEditorConstants.RESPONSE_PORT_TYPE_ATTRIBUTE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblResponsePortType, tblRow2, 0);

		tblEditor = new TableEditor(table);
		final Combo cmbResponsePortType = new Combo(table, SWT.NONE);
		cmbResponsePortType.setItems(portTypeList);
		cmbResponsePortType.select(0);
		textBoxesList.add(2, cmbResponsePortType);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(cmbResponsePortType, tblRow2, 1);

		tblEditor = new TableEditor(table);

		Label lblResponseOperation = new Label(table, SWT.NONE);
		lblResponseOperation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblResponseOperation.setText("     " + HTEditorConstants.RESPONSE_OPERATION_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblResponseOperation, tblRow2, 2);
		tblEditor = new TableEditor(table);
		final Combo cmbResponseOperation = new Combo(table, SWT.NONE);
		cmbResponseOperation.select(0);
		tblEditor.setEditor(cmbResponseOperation, tblRow2, 3);
		textBoxesList.add(3, cmbResponseOperation);
		tblEditor.grabHorizontal = true;
		cmbPortType.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e1) {
				try {
					NodeList nodeLists =
					                     centralUtils.getWSDL(textEditor)
					                                 .getElementsByTagName("wsdl:portType");
					NodeList childNodeList =
					                         nodeLists.item(cmbPortType.getSelectionIndex())
					                                  .getChildNodes();
					ArrayList<String> operationsList = new ArrayList<String>();
					for (int i = 0; i < childNodeList.getLength(); i++) {
						if (childNodeList.item(i).hasAttributes())
							operationsList.add(childNodeList.item(i).getAttributes()
							                                .getNamedItem("name").getTextContent());
					}
					String[] operations = new String[operationsList.size()];

					cmbOperation.setItems(operationsList.toArray(operations));
					cmbOperation.select(cmbOperation.indexOf(operationsList.get(0)));
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		cmbResponsePortType.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e1) {
				try {
					NodeList nodeLists =
					                     centralUtils.getWSDL(textEditor)
					                                 .getElementsByTagName("wsdl:portType");
					NodeList childNodeList =
					                         nodeLists.item(cmbResponsePortType.getSelectionIndex())
					                                  .getChildNodes();
					ArrayList<String> operationsList = new ArrayList<String>();
					for (int i = 0; i < childNodeList.getLength(); i++) {
						if (childNodeList.item(i).hasAttributes())
							operationsList.add(childNodeList.item(i).getAttributes()
							                                .getNamedItem("name").getTextContent());
					}
					String[] operations = new String[operationsList.size()];

					cmbResponseOperation.setItems(operationsList.toArray(operations));
					cmbResponseOperation.select(cmbOperation.indexOf(operationsList.get(0)));
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
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

		ArrayList<TDocumentation> documentationGroup =
		                                               (ArrayList<TDocumentation>) taskInterface.getDocumentation();
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

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3, XMLEditor editor,
	                           Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			taskInterface.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI =
			                                    new TDocumentationUI(editor, composite,
			                                                         childCompositeIndex,
			                                                         childObjectIndexes[0],
			                                                         SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		centralUtils.marshal(editor);
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
			taskInterface.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.getObjectIndex() > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI.getObjectIndex() - 1);
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
		taskInterface = (TTaskInterface) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(taskInterface.getDocumentation()
				                                        .get(tDocumentationUI.getObjectIndex()));
			}
		}
	}

	/**
	 * Set this Section's object index(index of only this type of class objects
	 * in the parent) as per the order created in This Section's parent.
	 * 
	 * @param objectIndex
	 */
	public int getObjectIndex() {
		return objectIndex;
	}

	/**
	 * Returns this Section's object index(index of only this type of class
	 * objects in the parent) as per the order created in its parent
	 * 
	 * @return objectIndex
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
