package org.wso2.developerstudio.humantask.editor;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class SectionCode{
	Section section;
	private Text txtNewText;
	public SectionCode() {
		
	}

	public Section getSection(FormToolkit toolkit,Composite parent) {
		
		section= toolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR|SWT.FILL);
		section.setText("Section from passed code");
		
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Composite composite_1 = toolkit.createComposite(section, SWT.NONE);
		toolkit.paintBordersFor(composite_1);
		section.setClient(composite_1);
		composite_1.setLayout(new FormLayout());
		
		Label lblTextContent = toolkit.createLabel(composite_1, "Text Content", SWT.NONE);
		FormData fd_lblTextContent = new FormData();
		fd_lblTextContent.top = new FormAttachment(0, 5);
		fd_lblTextContent.left = new FormAttachment(0);
		lblTextContent.setLayoutData(fd_lblTextContent);
		
		txtNewText = toolkit.createText(composite_1, "New Text", SWT.NONE);
		FormData fd_txtNewText = new FormData();
		fd_txtNewText.top = new FormAttachment(0,5);
		fd_txtNewText.right = new FormAttachment(100);
		fd_txtNewText.left = new FormAttachment(0, 180);
		txtNewText.setLayoutData(fd_txtNewText);
		
		
		
		return section;
	}
	
	
}
