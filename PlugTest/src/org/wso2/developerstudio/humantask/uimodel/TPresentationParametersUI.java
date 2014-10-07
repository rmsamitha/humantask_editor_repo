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
import org.wso2.developerstudio.humantask.models.TPresentationParameter;
import org.wso2.developerstudio.humantask.models.TPresentationParameters;

public class TPresentationParametersUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TPresentationParameters presentationParameters;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();

	public TPresentationParametersUI(XMLEditor textEditor,
			Composite parentComposite, Composite parentTagContainer,
			int styleBit, Object modelParent, int objectIndex,
			int compositeIndex) throws JAXBException {
		super(
				textEditor,
				parentComposite,
				parentTagContainer,
				styleBit,
				new String[] { HTEditorConstants.PRESENTATION_PARAMETER_TITLE },
				HTEditorConstants.PRESENTATION_PARAMETERS_TITLE);
		this.presentationParameters = (TPresentationParameters) modelParent;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		presentationParameters.setExpressionLanguage(((Text) textBoxesList
				.get(0)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationElementsUI presentationElementsUI = (TPresentationElementsUI) parentTagContainer;
		presentationElementsUI.refreshChildren(
				HTEditorConstants.PRESENTATION_PARAMETERS_TITLE,
				getCompositeIndex(), getObjectIndex());
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);

	}

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

		ArrayList<TPresentationParameter> presentationParameterGroup = new ArrayList<TPresentationParameter>();
		presentationParameterGroup = (ArrayList<TPresentationParameter>) presentationParameters
				.getPresentationParameter();
		for (int presentationParameterGroupIndex = 0; presentationParameterGroupIndex < presentationParameterGroup
				.size(); presentationParameterGroupIndex++) {
			TPresentationParameterUI tPresentationParameterUI;
			tPresentationParameterUI = new TPresentationParameterUI(editor,
					detailArea, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this,
					presentationParameterGroup.get(childObjectIndexes[0]));
			tPresentationParameterUI.initialize(editor);
			childComposites.add(childCompositeIndex, tPresentationParameterUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(presentationParameters
				.getExpressionLanguage());
	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection
				.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETER_TITLE)) {
			TPresentationParameter tPresentationParameter = new TPresentationParameter();
			tPresentationParameter.setName("");
			tPresentationParameter.getContent().add(new String(""));
			presentationParameters.getPresentationParameter().add(
					childObjectIndexes[0], tPresentationParameter);
			TPresentationParameterUI tPresentationParameterUI = new TPresentationParameterUI(
					editor, composite, childCompositeIndex,
					childObjectIndexes[0], SWT.NONE, this,
					tPresentationParameter);
			childComposites.add(childCompositeIndex, tPresentationParameterUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		centralUtils.marshalMe(editor);
	}

	@Override
	public void fillDetailArea(Composite composite) {
		Composite detailAreaInnerComposite = formToolkit
				.createComposite(composite);
		detailAreaInnerComposite.setLayout(new GridLayout(4, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));
		Label lblLang = formToolkit.createLabel(detailAreaInnerComposite,
				"Expression Language", SWT.NONE);
		Text txtLang = formToolkit.createText(detailAreaInnerComposite, "",
				SWT.BORDER);
		textBoxesList.add(0, txtLang);

	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName
				.equalsIgnoreCase(HTEditorConstants.PRESENTATION_PARAMETER_TITLE)) {
			this.childObjectIndexes[0]--;
			presentationParameters.getPresentationParameter().remove(
					childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TPresentationParameterUI) {

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

	}

	public void loadModel(Object model) {
		presentationParameters = (TPresentationParameters) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TPresentationParameterUI) {
				TPresentationParameterUI tPresentationParameterUI = (TPresentationParameterUI) compositeInstance;
				tPresentationParameterUI.loadModel(presentationParameters
						.getPresentationParameter().get(
								tPresentationParameterUI.objectIndex));
			}
		}
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

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
