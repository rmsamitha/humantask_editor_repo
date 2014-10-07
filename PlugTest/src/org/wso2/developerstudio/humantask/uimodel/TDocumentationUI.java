package org.wso2.developerstudio.humantask.uimodel;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
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
		//setExpanded(true);
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
		//((Text) textBoxesList.get(1)).setText((String)documentation.getContent().get(0));
	}

	@Override
	public void fillDetailArea() {
		Composite detailAreaInnerComposite=formToolkit.createComposite(detailArea);
		detailAreaInnerComposite.setLayout(new GridLayout(1,true));
		detailAreaInnerComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                1, 1));
		/*Label lblName = formToolkit.createLabel(detailAreaInnerComposite, "Language", SWT.NONE);
		Text txtLanguage = formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(0, txtLanguage);
		Label lblReference = formToolkit.createLabel(detailAreaInnerComposite, "Value",
				SWT.NONE);
		Text txtValue= formToolkit.createText(detailAreaInnerComposite, "", SWT.NONE);
		textBoxesList.add(1, txtValue);*/
		Table table = new Table(detailArea, SWT.BORDER | SWT.MULTI);
        table.setLinesVisible(true);
        for (int i = 0; i < 4; i++) {
          TableColumn column = new TableColumn(table, SWT.NONE);
          column.setWidth(100);
        }
      
        TableItem x1= new TableItem(table, SWT.NONE);
          TableEditor editorw = new TableEditor(table);
         
            Label lblName =new Label(table, SWT.NONE);
            lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
            lblName.setText("Language");           
            editorw.grabHorizontal = true;
            editorw.setEditor(lblName, x1, 0);
         
          editorw = new TableEditor(table);
          Text txtLang = new Text(table, SWT.BORDER);
            //txtLang.setBounds(152, 23, 100, 21);
            textBoxesList.add(0, txtLang);
            editorw.grabHorizontal = true;
            editorw.setEditor(txtLang, x1, 1);
         
          editorw = new TableEditor(table);
          Label lblReference = new Label(table,
                    SWT.NONE);
            lblReference.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
            lblReference.setText("Value");
            editorw.grabHorizontal = true;
            editorw.setEditor(lblReference, x1, 2);
           
            editorw = new TableEditor(table);
            Text txtValue= new Text(table, SWT.BORDER);
            textBoxesList.add(1, txtValue);
            editorw.grabHorizontal = true;
            editorw.setEditor(txtValue, x1, 3);
           
            table.setLinesVisible(true);

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
