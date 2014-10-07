package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDescription;
import org.wso2.developerstudio.humantask.models.TText;

public class TDescriptionUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TDescription description;
	protected int compositeIndex;
	protected int objectIndex;
	
	public TDescriptionUI(XMLEditor textEditor, Composite parentComposite, int compositeIndex,
			int objectIndex, int style, Composite container, Object modelParent) throws JAXBException {
		super(textEditor, parentComposite, container, style, HTEditorConstants.DESCRIPTION_TITLE);
		this.objectIndex = objectIndex;
		this.description = (TDescription) modelParent;
		this.parentTagContainer = container;
		this.compositeIndex = compositeIndex;
		setExpanded(true);
	}
	
	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		description.setLang(((Text) textBoxesList.get(0)).getText());
		description.getContent().remove(0);
		description.getContent().add(0, ((Text) textBoxesList.get(1)).getText());
		centralUtils.marshalMe(textEditor);
		}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationElementsUI parentContainer = (TPresentationElementsUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.DESCRIPTION_TITLE,compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(description.getLang());
		//textBoxes.get(1).setText(tName.getContent().get(0).toString());
	}

	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(4, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblLang = formToolkit.createLabel(detailAreaInnerComposite, "Language", SWT.NONE);
		Text txtLang = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(0, txtLang);
		Label lblReference = formToolkit.createLabel(detailAreaInnerComposite, "Value", SWT.NONE);
		Text txtReference = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(1, txtReference);
	}

	public void loadModel(Object model) {
		description = (TDescription) model;
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
