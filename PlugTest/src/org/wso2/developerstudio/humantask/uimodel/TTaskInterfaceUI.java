package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTaskInterface;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TTaskInterfaceUI extends AbstractEndTagSection {
	
	Composite container;
	public int objectIndex;
	protected int compositeIndex;
	TTaskInterface taskInterface;
	
	
	public TTaskInterfaceUI(XMLEditor editor, Composite parent,
			int compositeIndex, int objectIndex, int style,
			Composite container, Object modelParent) {
		super(editor, parent, style, "Interface");
		this.objectIndex = objectIndex;
		taskInterface = (TTaskInterface) modelParent;
		this.container = container;
		this.compositeIndex =compositeIndex;
		System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr Interface  Composite Index is :"+compositeIndex);
		System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr Interface  Object Index is :"+objectIndex);
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		
		taskInterface.setPortType(new QName(textBoxes.get(0).getText()));
		taskInterface.setOperation(textBoxes.get(1).getText());
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI parentContainer = (TTaskUI) container;
		parentContainer.refreshChildren("Interface",compositeIndex, objectIndex);
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
		textBoxes.get(0).setText(taskInterface.getPortType().getLocalPart().toString());
		textBoxes.get(1).setText(taskInterface.getOperation());

	}

	@Override
	public void fillDetailArea() {
		Label lblPortType= toolkit.createLabel(detailArea, "PortType", SWT.NONE);
		lblPortType.setBounds(20, 23, 100, 15);
		Text txtPortType = toolkit.createText(detailArea, "", SWT.NONE);
		txtPortType.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtPortType);
		Label lblOperation = toolkit.createLabel(detailArea, "Operation",
				SWT.NONE);
		lblOperation.setBounds(252, 23, 100, 15);
		Text txtOperation= toolkit.createText(detailArea, "", SWT.NONE);
		txtOperation.setBounds(384, 23, 100, 21);
		textBoxes.add(1, txtOperation);

	}

	public void loadModel(Object model){
		taskInterface = (TTaskInterface) model;
	}
}
