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
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.HTEditorConstants;
import org.wso2.developerstudio.humantask.editor.XMLEditor;
import org.wso2.developerstudio.humantask.models.TDocumentation;
import org.wso2.developerstudio.humantask.models.TTaskInterface;

public class TTaskInterfaceUI extends AbstractParentTagSection {
	private int[] childObjectIndexes;
	public TTaskInterface taskInterface;
	private int objectIndex;
	private int compositeIndex;
	private int childCompositeIndex;
	private Composite parentTagContainer;
	private XMLEditor textEditor;
	private ArrayList<Composite> childComposites = new ArrayList<Composite>();

	public TTaskInterfaceUI(XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, Object objectModel,
			int objectIndex, int compositeIndex) throws JAXBException {
		super(textEditor, parentComposite, parentTagContainer, styleBit,
				new String[] { HTEditorConstants.DOCUMENTATION_TITLE },
				HTEditorConstants.INTERFACE_TITLE);
		this.taskInterface = (TTaskInterface) objectModel;
		this.setObjectIndex(objectIndex);
		this.setCompositeIndex(compositeIndex);
		this.textEditor = textEditor;
		this.parentTagContainer = parentTagContainer;
		this.childObjectIndexes = new int[1];
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
		parentContainer.refreshChildren(HTEditorConstants.INTERFACE_TITLE,
				compositeIndex, objectIndex);
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
	public void fillDetailArea(Composite composite) {
		Composite detailAreaInnerComposite = formToolkit
				.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(4, true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));
		Label lblPortType = formToolkit.createLabel(detailAreaInnerComposite,
				"PortType", SWT.NONE);
		lblPortType.setBounds(20, 23, 100, 15);
		Text txtPortType = formToolkit.createText(detailAreaInnerComposite, "",
				SWT.NONE);
		txtPortType.setBounds(152, 23, 100, 21);
		textBoxesList.add(0, txtPortType);
		Label lblOperation = formToolkit.createLabel(detailAreaInnerComposite,
				"Operation", SWT.NONE);
		lblOperation.setBounds(252, 23, 100, 15);
		Text txtOperation = formToolkit.createText(detailAreaInnerComposite,
				"", SWT.NONE);
		txtOperation.setBounds(384, 23, 100, 21);
		textBoxesList.add(1, txtOperation);
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
		for (Composite composite : childComposites) {
			composite.dispose();
		}
		for (int childObjectIndexesElementIndex = 0; childObjectIndexesElementIndex < childObjectIndexes.length; childObjectIndexesElementIndex++) {
			childObjectIndexes[childObjectIndexesElementIndex] = 0;
		}
		childComposites.clear();
		childCompositeIndex = 0;

		ArrayList<TDocumentation> documentationGroup = (ArrayList<TDocumentation>) taskInterface
				.getDocumentation();
		for (int documentationGroupIndex = 0; documentationGroupIndex < documentationGroup
				.size(); documentationGroupIndex++) {
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					detailArea, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this,
					documentationGroup.get(childObjectIndexes[0]));
			tDocumentationUI.initialize(editor);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childCompositeIndex++;
			childObjectIndexes[0]++;
		}

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang("");
			tDocumentation.getContent().add(new String(""));
			taskInterface.getDocumentation().add(childObjectIndexes[0],
					tDocumentation);
			TDocumentationUI tDocumentationUI = new TDocumentationUI(editor,
					composite, childCompositeIndex, childObjectIndexes[0],
					SWT.NONE, this, tDocumentation);
			childComposites.add(childCompositeIndex, tDocumentationUI);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		}
		centralUtils.marshalMe(editor);
	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase(HTEditorConstants.DOCUMENTATION_TITLE)) {
			this.childObjectIndexes[0]--;
			taskInterface.getDocumentation().remove(childObjectIndex);
			for (Composite compositeInstance : childComposites) {

				if (compositeInstance instanceof TDocumentationUI) {
					TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
					if (tDocumentationUI.compositeIndex > childCompositeIndex) {
						tDocumentationUI.setCompositeIndex(tDocumentationUI
								.getCompositeIndex() - 1);
					}
					if (tDocumentationUI.objectIndex > childObjectIndex) {
						tDocumentationUI.setObjectIndex(tDocumentationUI
								.getObjectIndex() - 1);
					}
				} else {

				}

			}
		}
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;

	}

	public void loadModel(Object model) throws JAXBException {
		taskInterface = (TTaskInterface) model;
		for (Composite compositeInstance : childComposites) {
			if (compositeInstance instanceof TDocumentationUI) {
				TDocumentationUI tDocumentationUI = (TDocumentationUI) compositeInstance;
				tDocumentationUI.loadModel(taskInterface.getDocumentation()
						.get(tDocumentationUI.objectIndex));
			}
		}
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
