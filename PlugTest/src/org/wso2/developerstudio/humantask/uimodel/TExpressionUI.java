package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TExpression;
import org.eclipse.swt.widgets.Text;

/**
 * The UI class representing the "expression" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TExpressionUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TExpression expression;
	protected int objectIndex;
	private int compositeIndex;
	private Composite parentTagContainer;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();
	private String sectionTitle;

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
	public TExpressionUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int style, Object modelParent,
			int objectIndex, int compositeIndex, String sectionTitle)
			throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, style,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE },
				sectionTitle);
		this.expression = (TExpression) modelParent;
		this.compositeIndex = compositeIndex;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
		this.sectionTitle = sectionTitle;
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
		expression.setExpressionLanguage(((Text) textBoxesList.get(0))
				.getText());

		if (expression.getContent().size() != 0)
			expression.getContent().remove(0);
		expression.getContent().add(0, ((Text) textBoxesList.get(1)).getText());
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
		AbstractParentTagSection parentTagSection = (AbstractParentTagSection) parentTagContainer;
		parentTagSection.refreshChildren(sectionTitle, getCompositeIndex(),
				getObjectIndex());
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
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		if (expression.getExpressionLanguage() != null) {
			((Text) textBoxesList.get(0)).setText(expression
					.getExpressionLanguage());
		}
		if (expression.getContent().size() != 0) {
			((Text) textBoxesList.get(1)).setText((String) expression
					.getContent().get(0));
		}

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

		final Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);

		new TableColumn(table, SWT.NONE).setWidth(175);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);
		new TableColumn(table, SWT.NONE).setWidth(100);

		TableItem tableRow1 = new TableItem(table, SWT.NONE);
		tableRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tableEditor = new TableEditor(table);

		Label lblExpressionLang = new Label(table, SWT.NONE | SWT.BORDER_SOLID);
		lblExpressionLang.setText("  "
				+ HTEditorConstants.EXPRESSION_LANGUAGE_ATTRIBUTE_TITLE);
		tableEditor.grabHorizontal = true;
		tableEditor.setEditor(lblExpressionLang, tableRow1, 0);

		tableEditor = new TableEditor(table);
		Text txtExpressionLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtExpressionLang);
		tableEditor.grabHorizontal = true;
		tableEditor.setEditor(txtExpressionLang, tableRow1, 1);

		tableEditor = new TableEditor(table);
		Label lblValue = new Label(table, SWT.NONE);
		lblValue.setText("     "+HTEditorConstants.VALUE_TAG_TITLE);
		tableEditor.grabHorizontal = true;
		tableEditor.setEditor(lblValue, tableRow1, 2);

		tableEditor = new TableEditor(table);
		Text txtValue = new Text(table, SWT.BORDER);
		textBoxesList.add(1, txtValue);
		tableEditor.grabHorizontal = true;
		tableEditor.setEditor(txtValue, tableRow1, 3);

		setTitleBarBackground(new Color(getDisplay(), 228, 210, 247));
		btnNewgroup.dispose();
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
		expression = (TExpression) objectModel;
		
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