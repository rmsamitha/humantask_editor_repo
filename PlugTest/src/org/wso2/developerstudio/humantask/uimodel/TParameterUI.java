package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TParameter;

/**
 * The UI class representing the "parameter" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TParameterUI extends AbstractParentTagSection {
	public TParameter parameter;
	protected int objectIndex;
	private int compositeIndex;
	private Composite parentTagContainer;

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
	public TParameterUI(XMLEditor textEditor,
			Composite parentComposite, Composite parentTagContainer, int style,
			Object modelParent, int objectIndex, int compositeIndex)
			throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, style,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE },
				HTEditorConstants.PARAMETER_TITLE);
		this.parameter = (TParameter) modelParent;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		setExpanded(false);
			
	}
	
	/**
	 * Update the values of attributes(xml attributes) of the section and xml
	 * element content value and marshal into the TextEditor when the update
	 * button of that section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		parameter.setName(((Text) textBoxesList.get(0)).getText());	
		parameter.setType(new QName(((Text) textBoxesList.get(1)).getText()));
		if (parameter.getContent().size() != 0)  
			parameter.getContent().remove(0);
		parameter.getContent().add(0,((Text) textBoxesList.get(2)).getText());
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
		TLogicalPeopleGroupUI logicalPeopleGroupUI = (TLogicalPeopleGroupUI) parentTagContainer;
		logicalPeopleGroupUI.refreshChildren(HTEditorConstants.PARAMETER_TITLE,
				getCompositeIndex(), getObjectIndex());
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
		
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
	

	}
	
	/**
	 * Initialize or set the values of attributes and xml content(if available) whenever a tab change occur
	 * from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if(parameter.getName()!=null)	((Text) textBoxesList.get(0)).setText(parameter.getName());
		if(parameter.getType()!=null)	((Text) textBoxesList.get(1)).setText(parameter.getType().getLocalPart());
		if (parameter.getContent().size() != 0)		((Text) textBoxesList.get(2)).setText((String) parameter.getContent().get(0));

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {

	}
	
	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element content (if available) in the section
	 * 
	 * @param composite
	 */
	@Override
	public void fillDetailArea(Composite composite) {
		
		Composite detailAreaInnerComposite = formToolkit
				.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));
		
		final Table table = new Table(detailArea, SWT.NONE);
		//table.setLinesVisible(false);
		
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(150);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(150);

		TableItem tableRow1 = new TableItem(table, SWT.NONE);
		tableRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
		TableEditor tblEditor = new TableEditor(table);
		
		Label lblName = new Label(table, SWT.NONE|SWT.BORDER_SOLID);
		lblName.setText("  "+HTEditorConstants.NAME_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblName, tableRow1, 0);
		
		tblEditor = new TableEditor(table);
		Text txtName = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtName);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtName, tableRow1, 1);

		tblEditor = new TableEditor(table);
		Label lbType= new Label(table, SWT.NONE);
		lbType.setText("    "+ HTEditorConstants.TYPE_ATTRIBUTE_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lbType, tableRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtType = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtType);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtType, tableRow1, 3);
		
		
		TableItem tblRow2 = new TableItem(table, SWT.NONE);
		
		tblRow2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		tblEditor = new TableEditor(table);

		Label lblValue = new Label(table, SWT.NONE);
		lblValue.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblValue.setText("  "+HTEditorConstants.VALUE_TAG_TITLE);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblValue, tblRow2, 0);

		tblEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(2, txtValue);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtValue, tblRow2, 1);
		
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
		
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object objectModel) throws JAXBException {
		parameter = (TParameter) objectModel;
		
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