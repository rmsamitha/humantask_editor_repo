package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

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
import org.wso2.developerstudio.humantask.models.TPresentationParameter;
import org.wso2.developerstudio.humantask.models.TPresentationParameters;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * The UI class representing the "presentationParameters" xml element in the .ht file
 * All the functionalities of that element are performed in this class, by
 * implementing and overriding the abstract super class methods.
 */
public class TPresentationParametersUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TPresentationParameters presentationParameters;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
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
	public TPresentationParametersUI(XMLEditor textEditor,
			Composite parentComposite, Composite parentTagContainer,
			int styleBit, Object modelParent, int objectIndex,
			int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE,
						HTEditorConstants.PRESENTATION_PARAMETER_TITLE },
				HTEditorConstants.PRESENTATION_PARAMETERS_TITLE);
		this.presentationParameters = (TPresentationParameters) modelParent;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[2];
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
		presentationParameters.setExpressionLanguage(((Text) textBoxesList
				.get(0)).getText());
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
		TPresentationElementsUI presentationElementsUI = (TPresentationElementsUI) parentTagContainer;
		presentationElementsUI.refreshChildren(
				HTEditorConstants.PRESENTATION_PARAMETERS_TITLE,
				getCompositeIndex(), getObjectIndex());
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
		ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) presentationParameters
				.getDocumentation();
		for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
				.size(); documentationGroupIndex++) {
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					detailArea, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this,
					documentationGroup.get(childObjectIndexes[0]));
			tDocumentationUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}

		ArrayList<TPresentationParameter> presentationParameterGroup = new ArrayList<TPresentationParameter>();
		presentationParameterGroup = (ArrayList<TPresentationParameter>) presentationParameters
				.getPresentationParameter();
		for (int presentationParameterGroupIndex = 0; presentationParameterGroupIndex < presentationParameterGroup
				.size(); presentationParameterGroupIndex++) {
			TPresentationParameterUI tPresentationParameterUI;
			tPresentationParameterUI = new TPresentationParameterUI(editor,
					detailArea, childCompositeIndex, childObjectIndexes[1],
					SWT.NONE, this,
					presentationParameterGroup.get(childObjectIndexes[1]));
			tPresentationParameterUI.initialize(editor);
			childComposites.add(childCompositeIndex, tPresentationParameterUI);
			childCompositeIndex++;
			childObjectIndexes[1]++;
		}
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
		if(presentationParameters
				.getExpressionLanguage()!=null)
		((Text) textBoxesList.get(0)).setText(presentationParameters
				.getExpressionLanguage());
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			presentationParameters.getDocumentation().add(
					childObjectIndexes[0], tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					composite, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		if (selection
				.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETER_TITLE)) {
			TPresentationParameter tPresentationParameter = new TPresentationParameter();
			tPresentationParameter.setName("");
			tPresentationParameter.getContent().add(new String(""));
			presentationParameters.getPresentationParameter().add(
					childObjectIndexes[0], tPresentationParameter);
			TPresentationParameterUI tPresentationParameterUI = new TPresentationParameterUI(
					editor, composite, childCompositeIndex,
					childObjectIndexes[1], SWT.NONE, this,
					tPresentationParameter);
			childComposites.add(childCompositeIndex, tPresentationParameterUI);
			childObjectIndexes[1]++;
			childCompositeIndex++;
		}
		centralUtils.marshal(editor);
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
	
		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
		table.setLinesVisible(true);
		for (int i = 0; i < 2; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setWidth(100);
		}

		TableItem tblRow1 = new TableItem(table, SWT.NONE);
		tblRow1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		TableEditor tblEditor = new TableEditor(table);

		Label lblLang = new Label(table, SWT.NONE);
		lblLang.setText("Language");
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(lblLang, tblRow1, 0);

		tblEditor = new TableEditor(table);
		Text txtLang = new Text(table, SWT.BORDER);
		textBoxesList.add(0, txtLang);
		tblEditor.grabHorizontal = true;
		tblEditor.setEditor(txtLang, tblRow1, 1);

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
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			presentationParameters.getDocumentation().remove(childObjectIndex);
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
				} else if (compositeInstance instanceof TPresentationParameterUI) {

					TPresentationParameterUI tPresentationParameterUI = (TPresentationParameterUI) compositeInstance;
					if (tPresentationParameterUI.compositeIndex > childCompositeIndex) {
						tPresentationParameterUI
								.setCompositeIndex(tPresentationParameterUI
										.getCompositeIndex() - 1);
					}
				} else {

				}

			}
		} else if (itemName
				.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETER_TITLE)) {
			this.childObjectIndexes[1]--;
			presentationParameters.getPresentationParameter().remove(
					childObjectIndex);
			for (Composite compositeInstance : childComposites) {
				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.getCompositeIndex() > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
				} else if (compositeInstance instanceof TPresentationParameterUI) {

					TPresentationParameterUI tPresentationParameterUI = (TPresentationParameterUI) compositeInstance;
					if (tPresentationParameterUI.compositeIndex > childCompositeIndex) {
						tPresentationParameterUI
								.setCompositeIndex(tPresentationParameterUI
										.getCompositeIndex() - 1);
					}
					if (tPresentationParameterUI.objectIndex > childObjectIndex) {
						tPresentationParameterUI
								.setObjectIndex(tPresentationParameterUI
										.getObjectIndex() - 1);
					}
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
		presentationParameters = (TPresentationParameters) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(presentationParameters.getDocumentation()
						.get(tDocumentationUI.getObjectIndex()));
			}else if (compositeInstance instanceof TPresentationParameterUI) {
				TPresentationParameterUI tPresentationParameterUI = (TPresentationParameterUI) compositeInstance;
				tPresentationParameterUI.loadModel(presentationParameters
						.getPresentationParameter().get(
								tPresentationParameterUI.objectIndex));
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

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}
}
