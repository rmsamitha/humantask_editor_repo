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
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TTaskInterface;
import org.wso2.developerstudio.humantask.models.TText;

public class TTaskInterfaceUI extends AbstractEndTagSection {
	private Composite parentTagContainer;
	private TTaskInterface taskInterface;
	protected int compositeIndex;
	protected int objectIndex;

	public TTaskInterfaceUI(XMLEditor textEditor, Composite parentComposite,
			int compositeIndex, int objectIndex, int styleBit,
			Composite parentTagContainer, Object modelParent)
			throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				HTEditorConstants.INTERFACE_TITLE);
		this.objectIndex = objectIndex;
		this.taskInterface = (TTaskInterface) modelParent;
		this.parentTagContainer = parentTagContainer;
		this.compositeIndex = compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		taskInterface.setPortType(new QName(((Text) textBoxesList.get(0))
				.getText()));
		taskInterface.setOperation(((Text) textBoxesList.get(1)).getText());
		centralUtils.marshalMe(textEditor);
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI parentContainer = (TTaskUI) parentTagContainer;
		parentContainer.refreshChildren(HTEditorConstants.INTERFACE_TITLE, compositeIndex,
				objectIndex);
		centralUtils.marshalMe(textEditor);
		Composite parentComposite = this.getParent();
		this.dispose();
		parentComposite.layout(true, true);
	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		// textBoxes.get(0).setText(taskInterface.getPortType().getLocalPart().toString());
		((Text) textBoxesList.get(1)).setText(taskInterface.getOperation());
	}

	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite = formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(4, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblPortType = formToolkit.createLabel(detailAreaInnerComposite, "PortType",
				SWT.NONE);
		lblPortType.setBounds(20, 23, 100, 15);
		Text txtPortType = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		txtPortType.setBounds(152, 23, 100, 21);
		textBoxesList.add(0, txtPortType);
		Label lblOperation = formToolkit.createLabel(detailAreaInnerComposite, "Operation",
				SWT.NONE);
		lblOperation.setBounds(252, 23, 100, 15);
		Text txtOperation = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		txtOperation.setBounds(384, 23, 100, 21);
		textBoxesList.add(1, txtOperation);
	}

	public void loadModel(Object model) {
		taskInterface = (TTaskInterface) model;
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
