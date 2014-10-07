package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;

public class TDocumentationUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TDocumentation documentation;
	protected int compositeIndex;
	protected int objectIndex;

	public TDocumentationUI(XMLEditor textEditor, Composite parentComposite,
			int compositeIndex, int objectIndex, int styleBit,
			Composite parentTagContainer, Object objectModel) throws JAXBException {
		super(textEditor, parentComposite,parentTagContainer, styleBit, HTEditorConstants.DOCUMENTATION_TITLE);
		this.objectIndex = objectIndex;
		documentation = (TDocumentation) objectModel;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex =compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		
		documentation.setLang(((Text) textBoxesList.get(0)).getText());
		documentation.getContent().remove(0);
		documentation.getContent().add(0,((Text) textBoxesList.get(1)).getText());
		centralUtils.marshalMe(textEditor);
	
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		AbstractParentTagSection parentContainer = (AbstractParentTagSection) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.DOCUMENTATION_TITLE,compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(documentation.getLang());
		((Text) textBoxesList.get(1)).setText((String)documentation.getContent().get(0));
	}

	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite=formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(4,true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                1, 1));
		Label lblName = formToolkit.createLabel(detailAreaInnerComposite, "Language", SWT.NONE);
		Text txtLanguage = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(0, txtLanguage);
		Label lblReference = formToolkit.createLabel(detailAreaInnerComposite, "Value",
				SWT.NONE);
		Text txtValue= formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(1, txtValue);

	}

	public void loadModel(Object model){
		documentation = (TDocumentation) model;
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
