package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.wso2.developerstudio.humantask.editor.AbstractChildTagComposite;
import org.wso2.developerstudio.humantask.editor.AbstractEndTagSection;
import org.wso2.developerstudio.humantask.editor.TNotificationParentComposite;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TDocumentationUI extends AbstractEndTagSection {
	Composite container;
	public int objectIndex;
	
	
	public int getObjectIndex() {
		System.out.println("Changing before object Index "+objectIndex);
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
		System.out.println("Changing object Index "+objectIndex);
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

	protected int compositeIndex;
	TDocumentation documentation;
	
	public TDocumentationUI(XMLEditor editor, Composite parent,
			int compositeIndex, int objectIndex, int style,
			Composite container, Object modelParent) {
		super(editor, parent, style, "Documentation");
		this.objectIndex = objectIndex;
		documentation = (TDocumentation) modelParent;
		this.container = container;
		this.compositeIndex =compositeIndex;
		System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr Docu  Composite Index is :"+compositeIndex);
		System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr Docu Object Index is :"+objectIndex);
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		
		documentation.setLang(textBoxes.get(0).getText());
		documentation.getContent().remove(0);
		documentation.getContent().add(0,textBoxes.get(1).getText());
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI parentContainer = (TTaskUI) container;
		System.out.println("Removing object index :"+objectIndex);
		parentContainer.refreshChildren("Documentation",compositeIndex, objectIndex);
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo = this.getParent();
		System.out.println("Disposing widget number "+compositeIndex);
		this.dispose();
		tempCompo.layout(true, true);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		System.out.println("Documentation value :"+documentation.getLang());
		//textBoxes.get(0).setText(documentation.getLang());
		textBoxes.get(0).setText(objectIndex+"");
		System.out.println("Documentation value 2 :"+(String)documentation.getContent().get(0));
		textBoxes.get(1).setText(compositeIndex+"");
		//textBoxes.get(1).setText((String)documentation.getContent().get(0));
	}

	@Override
	public void fillDetailArea() {
		Composite innerZComp=toolkit.createComposite(detailArea);
		innerZComp.setLayout(new GridLayout(4,true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                1, 1));
		Label lblName = toolkit.createLabel(innerZComp, "Language", SWT.NONE);
		//lblName.setBounds(20, 23, 100, 15);
		Text txtLang = toolkit.createText(innerZComp, "", SWT.NONE);
		//txtLang.setBounds(152, 23, 100, 21);
		textBoxes.add(0, txtLang);
		Label lblReference = toolkit.createLabel(innerZComp, "Value",
				SWT.NONE);
		//lblReference.setBounds(252, 23, 100, 15);
		Text txtValue= toolkit.createText(innerZComp, "", SWT.NONE);
		//txtValue.setBounds(384, 23, 100, 21);
		textBoxes.add(1, txtValue);

	}

	public void loadModel(Object model){
		documentation = (TDocumentation) model;
	}
}
