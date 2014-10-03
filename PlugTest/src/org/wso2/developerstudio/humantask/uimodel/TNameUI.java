package org.wso2.developerstudio.humantask.uimodel;

import java.util.jar.Attributes.Name;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPriorityExpr;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TText;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TNameUI extends AbstractEndTagSection {
	Composite container;
	public int objectIndex;
	protected int compositeIndex;
	TText tName;

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

	public TNameUI(XMLEditor editor, Composite parent, int compositeIndex,
			int objectIndex, int style, Composite container, Object modelParent) {
		super(editor, parent, container, style, "Name");
		this.objectIndex = objectIndex;
		tName = (TText) modelParent;
		this.container = container;
		this.compositeIndex = compositeIndex;
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		tName.setLang(textBoxes.get(0).getText());
		tName.getContent().remove(0);
		tName.getContent().add(0, textBoxes.get(1).getText());
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationElementsUI parentContainer = (TPresentationElementsUI) container;
		parentContainer.refreshChildren("Name", compositeIndex, objectIndex);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo = this.getParent();
		System.out.println("Disposing widget number " + compositeIndex);
		this.dispose();
		tempCompo.layout(true, true);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		textBoxes.get(0).setText(tName.getLang());
		// System.out.println("Documentation value 2 :"+(String)documentation.getContent().get(0));
		//textBoxes.get(1).setText(tName.getContent().get(0).toString());

	}

	@Override
	public void fillDetailArea() {
		Composite innerZComp = toolkit.createComposite(detailArea);
		innerZComp.setLayout(new GridLayout(4, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblName = toolkit.createLabel(innerZComp, "Language", SWT.NONE);
		// lblName.setBounds(20, 23, 100, 15);
		Text txtLang = toolkit.createText(innerZComp, "", SWT.NONE);
		// txtLang.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtLang);
		Label lblReference = toolkit.createLabel(innerZComp, "Value", SWT.NONE);
		// lblReference.setBounds(252, 23, 100, 15);
		Text txtValue = toolkit.createText(innerZComp, "", SWT.NONE);
		// txtValue.setBounds(384, 23, 100, 21);
		textBoxes.add(1, txtValue);
	}

	public void loadModel(Object model) {
		tName = (TText) model;
	}
}