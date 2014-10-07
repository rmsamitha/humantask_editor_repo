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
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TText;

public class TTaskSubjectUI extends AbstractEndTagSection{
	private Composite parentTagContainer;
	private TText tSubject;
	protected int compositeIndex;
	protected int objectIndex;
	
	public TTaskSubjectUI(XMLEditor editor, Composite parent, int compositeIndex,
			int objectIndex, int style, Composite container, Object modelParent) throws JAXBException {
		super(editor, parent, container, style, HTEditorConstants.SUBJECT_TITLE);
		this.objectIndex = objectIndex;
		this.tSubject = (TText) modelParent;
		this.parentTagContainer = container;
		this.compositeIndex = compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		tSubject.setLang(((Text) textBoxesList.get(0)).getText());
		tSubject.getContent().remove(0);
		tSubject.getContent().add(0, ((Text) textBoxesList.get(1)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationElementsUI parentContainer = (TPresentationElementsUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.SUBJECT_TITLE, compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(tSubject.getLang());
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
		Label lblValue = formToolkit.createLabel(detailAreaInnerComposite, "Value", SWT.NONE);
		Text txtValue = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(1, txtValue);
		
	}

	public void loadModel(Object model) {
		tSubject = (TText) model;
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
