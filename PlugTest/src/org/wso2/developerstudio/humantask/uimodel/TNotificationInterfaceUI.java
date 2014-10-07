package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotificationInterface;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TNotificationInterfaceUI extends AbstractParentTagSection {
	int[] childObjectIndexes;
	public TNotificationInterface notificationInterface; // Change the type of model object
	int objectIndex; // this is this elements object index
	int compositeIndex; // this is this elements composite index
	int childCompositeIndex;
	Composite container;
	XMLEditor editor;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	boolean refreshed = true;

	public TNotificationInterfaceUI(XMLEditor editor, Composite parent, Composite container,
			int style, Object modelParent, int objectIndex, int compositeIndex)
			throws JAXBException {
		super(editor, parent,container, style, new String[] { "Documentation" }, "Interface");
		// TTasks tasks=(TTasks)modelParent;
		System.out.println(objectIndex);
		this.notificationInterface = (TNotificationInterface) modelParent;
		this.objectIndex = objectIndex;
		this.compositeIndex = compositeIndex;
		this.editor = editor;
		this.container = container;
		childObjectIndexes = new int[1];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		notificationInterface.setOperation(((Text) textBoxes.get(0)).getText());
		notificationInterface.setPortType(
				new QName(	((Text) textBoxes.get(0)).getText()	));
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TNotificationUI tNotificationUI = (TNotificationUI) container;
		tNotificationUI.refreshChildren("", compositeIndex, objectIndex);

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
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Documentation")) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang(childCompositeIndex + "");
			tDocumentation.getContent().add(
					new String(childObjectIndexes[0] + ""));
			notificationInterface.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tNot = new TDocumentationUI(editor, composite,
					childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
					tDocumentation);
			childComposites.add(childCompositeIndex, tNot);
			// sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT,
			// SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			System.out.println("Number of CC" + childComposites.size());
			// centralUtils.addInstance(TNotificationInterface);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} 

		// sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fillDetailArea(Composite composite) {
		Composite innerZComp =new Composite(composite,SWT.NONE);
		innerZComp.setLayout(new GridLayout(4, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblName = toolkit.createLabel(innerZComp, "PortType", SWT.NONE);
		// lblName.setBounds(20, 23, 100, 15);
		Text txtName = toolkit.createText(innerZComp, "", SWT.BORDER);
		// txtName.setBounds(152, 23, 100, 21);
		// txtName.setLayoutData(new GridData();
		textBoxes.add(0, txtName);
		Label lblReference = toolkit.createLabel(innerZComp,
				"Operation", SWT.NONE);
		Text txtName2 = toolkit.createText(innerZComp, "", SWT.BORDER);
		// txtName.setBounds(152, 23, 100, 21);
		// txtName.setLayoutData(new GridData();
		textBoxes.add(1, txtName2);
		// lblReference.setBounds(252, 23, 100, 15);
		
		// combo.setBounds(384, 23, 100, 21);
	

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxes.get(0)).setText(notificationInterface.getPortType().getLocalPart());
		((Text) textBoxes.get(1)).setText(notificationInterface.getOperation());

	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException { /////////////////////////Start from this/////////////////////
		ArrayList<TDocumentation> documentationGroup = new ArrayList<TDocumentation>();
		documentationGroup = (ArrayList<TDocumentation>) notificationInterface
				.getDocumentation();
		
		for(Composite c:childComposites){
			c.dispose();
			System.out.println("Disposed");
			}
			childComposites.clear();
			System.out.println("XC Size is :"+childComposites.size());
			
			
			childCompositeIndex=0;
			for (int j = 0; j < childObjectIndexes.length; j++) {
				childObjectIndexes[j]=0;
			}
	
		
		if (childComposites.size() == 0) {
			for (int i = 0; i < documentationGroup.size(); i++) {
				TDocumentationUI tNot;

				try {
					tNot = new TDocumentationUI(editor, compositeDetailArea,
							childCompositeIndex, childObjectIndexes[0],
							SWT.NONE, this,
							documentationGroup.get(childObjectIndexes[0]));
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[0]++;
				} catch (JAXBException e) {
					e.printStackTrace();

				}
			}	
			
			
		}

	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		System.out.println("Number of CC" + childComposites.size());
		System.out.println("Removing object index taskui outer:"
				+ childObjectIndex);
		if (itemName.equalsIgnoreCase("Documentation")) {
			this.childObjectIndexes[0]--;
			System.out.println("Removing object index taskui:"
					+ childObjectIndex);
			notificationInterface.getDocumentation().remove(childObjectIndex);
			for (Composite c : childComposites) {

				if (c instanceof TDocumentationUI) {
					System.out.print("Giya");
					TDocumentationUI d = (TDocumentationUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
					if (d.objectIndex > childObjectIndex) {
						d.setObjectIndex(d.getObjectIndex() - 1);
					}
				

				} 

			}
		} 
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;

	}

	public void loadModel(Object model) throws JAXBException {
		notificationInterface = (TNotificationInterface) model;
		for (Composite c : childComposites) {
			if (c instanceof TDocumentationUI) {
				TDocumentationUI d = (TDocumentationUI) c; // children node type
				d.loadModel(notificationInterface.getDocumentation().get(d.objectIndex));
			} 
		}
	}
}
