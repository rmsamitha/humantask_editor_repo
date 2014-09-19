package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;

public class TTaskSection extends AbstractParentSection {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	XMLEditor editor;
	public TTaskSection(XMLEditor editor, Composite parent, int style) {
		super(editor, parent, style);
		setText("Task");
		this.editor=editor;
		//setSize(600, SWT.DEFAULT);
		
		
	}

	

	//@Override
	public void newButtonLogic(int i, ArrayList<Section> sectionsAL,
			XMLEditor editor, Composite composite)
			throws JAXBException {
		if (i == 0) {
			i = compositeIndex;
		}
		/*
		 * System.out.println("new button l value is " + i);
		 * TNotificationComposite tNot = new TNotificationComposite(editor,
		 * composite, i, SWT.NONE); composites.add(i, tNot);
		 * sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		 * sc3.layout(true, true); innerSection.layout(true, true);
		 * 
		 * System.out.println("hikz value is " + i); if
		 * (editor.getRootElement().getLogicalPeopleGroups() == null) {
		 * TLogicalPeopleGroups tLogicalPeopleGroups = new
		 * TLogicalPeopleGroups();
		 * editor.getRootElement().setLogicalPeopleGroups(
		 * tLogicalPeopleGroups); } i++;
		 */
		//
	/*	TTaskInterfaceSection TIS = new TTaskInterfaceSection(editor, this, i,			
				Section.TWISTIE | Section.TITLE_BAR);
		System.out.println("created a Task Interface section");
		GridData gd_secTestSectionClass = new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1);
		gd_secTestSectionClass.widthHint = 620;
		gd_secTestSectionClass.heightHint = 89;
		TIS.setLayoutData(gd_secTestSectionClass);
		i++;*/
	}

	@Override
	public void fillDetailArea(final Composite composite) {
		Label lblNewLabel = toolkit.createLabel(composite, "Label in TTask Section",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);
		
		Button btnAddLPG = new Button(composite, SWT.NONE);
		btnAddLPG.setText("plus");
		btnAddLPG.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Section scc2=new TTaskSection(editor, composite,  Section.TWISTIE | Section.TITLE_BAR);
				scc2.setExpanded(false);
				
			}
		});
		

		
		TTaskInterfaceSection2 TISx = new TTaskInterfaceSection2(editor, composite, 2,			
				Section.TWISTIE | Section.TITLE_BAR);
		System.out.println("created a Task Interface section");
		

		
		Section sc3=new TTaskInterfaceSection2(editor, composite, 1,Section.TWISTIE | Section.TITLE_BAR);
		sc3.setExpanded(false);
		//sc3.setText("hooyee");
	}



	@Override
	public void refreshLogic(XMLEditor editor, ArrayList<Composite> composites,
			Composite composite, ScrolledComposite sc3) throws JAXBException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void newButtonLogic(String selection, int i,
			ArrayList<Composite> composites, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		// TODO Auto-generated method stub
		
	}
}
