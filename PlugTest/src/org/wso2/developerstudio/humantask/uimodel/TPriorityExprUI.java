package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPriorityExpr;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTaskInterface;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TPriorityExprUI extends AbstractEndTagSection {
	Composite container;
	public int objectIndex;
	protected int compositeIndex;
	TPriorityExpr tPriorityExpr;
	
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

	public TPriorityExprUI(XMLEditor editor, Composite parent,
			int compositeIndex, int objectIndex, int style,
			Composite container, Object modelParent) {
		super(editor, parent, container, style, "Priority");
		this.objectIndex = objectIndex;
		tPriorityExpr = (TPriorityExpr) modelParent;
		this.container = container;
		this.compositeIndex =compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		tPriorityExpr.setExpressionLanguage(textBoxes.get(0).getText());
		tPriorityExpr.getContent().remove(0);
		tPriorityExpr.getContent().add(0,textBoxes.get(1).getText());
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI parentContainer = (TTaskUI) container;
		parentContainer.refreshChildren("Priority",compositeIndex, objectIndex);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		textBoxes.get(0).setText(tPriorityExpr.getExpressionLanguage());
		textBoxes.get(1).setText((String)tPriorityExpr.getContent().get(0));

	}

	@Override
	public void fillDetailArea() {
		Label lblExprLang= toolkit.createLabel(detailArea, "Expression Language", SWT.NONE);
		lblExprLang.setBounds(20, 23, 100, 15);
		Text txtExprLang = toolkit.createText(detailArea, "", SWT.NONE);
		txtExprLang.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtExprLang);
		Label lblValue = toolkit.createLabel(detailArea, "Value",
				SWT.NONE);
		lblValue.setBounds(252, 23, 100, 15);
		Text txtValue= toolkit.createText(detailArea, "", SWT.NONE);
		txtValue.setBounds(384, 23, 100, 21);
		textBoxes.add(1, txtValue);

	}

	public void loadModel(Object model){
		tPriorityExpr = (TPriorityExpr) model;
	}
}
