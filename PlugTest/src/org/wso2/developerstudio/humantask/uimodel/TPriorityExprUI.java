package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
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
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;

/**
 * The UI class representing the "priority" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TPriorityExprUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TPriorityExpr tPriorityExpr;
	protected int compositeIndex;
	protected int objectIndex;
	
	public TPriorityExprUI(XMLEditor textEditor, Composite parentComposite,
			int compositeIndex, int objectIndex, int styleBit,
			Composite parentTagContainer, Object modelParent) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit, HTEditorConstants.PRIORITY_TITLE);
		this.objectIndex = objectIndex;
		this.tPriorityExpr = (TPriorityExpr) modelParent;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex =compositeIndex;
		setExpanded(true); 
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
		tPriorityExpr.setExpressionLanguage(((Text) textBoxesList.get(0)).getText());
		if (tPriorityExpr.getContent().size() != 0)  
			tPriorityExpr.getContent().remove(0);
		
		tPriorityExpr.getContent().add(0,((Text) textBoxesList.get(1)).getText());
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
		AbstractParentTagSection parentContainer = (AbstractParentTagSection) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.PRIORITY_TITLE,compositeIndex, objectIndex);
		centralUtils.marshal(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
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
		if(tPriorityExpr.getExpressionLanguage()!=null)
		((Text) textBoxesList.get(0)).setText(tPriorityExpr.getExpressionLanguage());
		if(tPriorityExpr.getContent().size()!=0)
		((Text) textBoxesList.get(1)).setText((String)tPriorityExpr.getContent().get(0));
	}
	
	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes and element contents (if available) in the section
	 */
	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit
				.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));
	
		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		for (int i = 0; i < 2; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(100);
		}

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblExprLang = new Label(table, SWT.NONE);
		lblExprLang.setText("Language");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblExprLang, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtValue);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtValue, tblRow1, 1);
		
		Label lblContent = new Label(table, SWT.NONE);
		lblContent.setText("Content");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblContent, tblRow1, 2);

		tblEditor = new TableEditor(table);
		Text txtContent = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtContent);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtContent, tblRow1, 3);
	}

	/**
	 * Load the JAXB model objects into the UI model whenever a tab change
	 * occurs from text editor to the UI editor.
	 * 
	 * @param model
	 */
	public void loadModel(Object model){
		tPriorityExpr = (TPriorityExpr) model;
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
