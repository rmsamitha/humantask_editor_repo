package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;

public class TTaskInterfaceSection2 extends AbstractNonReptitiveSection{

	public TTaskInterfaceSection2(XMLEditor editor, Composite parent,int index, int style) {
		super(editor, parent, index, style, "toolTipText");
		setText("Interface");
		//setSize(500, 300);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void btnUpdateHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillDetailArea() {
		// TODO Auto-generated method stub
		Label lblPortType = toolkit.createLabel(composite_SecTASK_SC , "PPPPortType",
				SWT.NONE);
		lblPortType.setBounds(20, 23, 100, 15);
		Text txtPortType = toolkit.createText(composite_SecTASK_SC, "", SWT.NONE);
		try {
			txtPortType.setBounds(152, 23, 100, 21);

			//textBoxes.add(0, txtPortType);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Label lblOperation = toolkit.createLabel(composite_SecTASK_SC, "Operation",
				SWT.NONE);
		lblOperation.setBounds(252, 23, 100, 15);
		Text txtOperation = toolkit.createText(composite_SecTASK_SC, "", SWT.NONE);
		txtOperation.setBounds(384, 23, 100, 21);
		//textBoxes.add(1, txtOperation);
	}

	@Override
	public void btnRemoveHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException {
		// TODO Auto-generated method stub
		
	}

}
