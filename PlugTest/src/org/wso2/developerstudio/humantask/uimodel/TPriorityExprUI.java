package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TPriorityExpr;
import org.wso2.developerstudio.humantask.models.TTaskInterface;
import org.wso2.developerstudio.humantask.models.TText;

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

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		tPriorityExpr.setExpressionLanguage(((Text) textBoxesList.get(0)).getText());
		tPriorityExpr.getContent().remove(0);
		tPriorityExpr.getContent().add(0,((Text) textBoxesList.get(1)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		AbstractParentTagSection parentContainer = (AbstractParentTagSection) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.PRIORITY_TITLE,compositeIndex, objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxesList.get(0)).setText(tPriorityExpr.getExpressionLanguage());
		//((Text) textBoxesList.get(1)).setText((String)tPriorityExpr.getContent().get(0));
	}

	@Override
	public void fillDetailArea() {
		Label lblExprLang= formToolkit.createLabel(detailArea, "Expression Language", SWT.NONE);
		Text txtExprLang = formToolkit.createText(detailArea, "", SWT.NONE);
		textBoxesList.add(0, txtExprLang);
		Label lblValue = formToolkit.createLabel(detailArea, "Value",
				SWT.NONE);
		Text txtValue= formToolkit.createText(detailArea, "", SWT.NONE);
		textBoxesList.add(1, txtValue);
	}

	public void loadModel(Object model){
		tPriorityExpr = (TPriorityExpr) model;
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
