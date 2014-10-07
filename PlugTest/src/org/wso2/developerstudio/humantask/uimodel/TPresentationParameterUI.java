package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TPresentationParameter;

public class TPresentationParameterUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TPresentationParameter presentationParameter;
	protected int compositeIndex;
	protected int objectIndex;

	public TPresentationParameterUI(XMLEditor textEditor,
			Composite parentComposite, int compositeIndex, int objectIndex,
			int styleBit, Composite parentTagContainer, Object modelParent)
			throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				HTEditorConstants.PRESENTATION_PARAMETER_TITLE);
		this.objectIndex = objectIndex;
		this.presentationParameter = (TPresentationParameter) modelParent;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex = compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		presentationParameter.setName(((Text) textBoxesList.get(0)).getText());
		presentationParameter.setType(new QName(((Text) textBoxesList.get(1))
				.getText()));
		presentationParameter.getContent().remove(0);
		presentationParameter.getContent().add(0,
				((Text) textBoxesList.get(2)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationParametersUI parentContainer = (TPresentationParametersUI) parentTagContainer;
		parentContainer.refreshChildren(
				HTEditorConstants.PRESENTATION_PARAMETER_TITLE, compositeIndex,
				objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentTagComposite = this.getParent();
		this.dispose();
		parentTagComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(presentationParameter.getName());
		((Text) textBoxesList.get(1)).setText(presentationParameter.getType()
				.getLocalPart());
		((Text) textBoxesList.get(2)).setText((String) presentationParameter
				.getContent().get(0));
	}

	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit
				.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(4, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));
		Label lblName = formToolkit.createLabel(detailAreaInnerComposite,
				"Name", SWT.NONE);
		Text txtName = formToolkit.createText(detailAreaInnerComposite, "",
				SWT.NONE);
		textBoxesList.add(0, txtName);
		Label lblType = formToolkit.createLabel(detailAreaInnerComposite,
				"Type", SWT.NONE);
		Text txtType = formToolkit.createText(detailAreaInnerComposite, "",
				SWT.NONE);
		textBoxesList.add(1, txtType);
		Label lblValue = formToolkit.createLabel(detailAreaInnerComposite,
				"Value", SWT.NONE);
		Text txtValue = formToolkit.createText(detailAreaInnerComposite, "",
				SWT.NONE);
		textBoxesList.add(2, txtValue);

	}

	public void loadModel(Object model) {
		presentationParameter = (TPresentationParameter) model;
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
