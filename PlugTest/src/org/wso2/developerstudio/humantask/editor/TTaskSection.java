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
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;

public class TTaskSection extends AbstractParentTagSection {
	XMLEditor editor;
	ScrolledComposite rootScrolledComposite;

	public TTaskSection(XMLEditor editor, Composite parent, int style,
			ScrolledComposite rootScrolledComposite) throws JAXBException {
		super(editor, parent, style,new String[]{"a","b","c"},5);
		setText("Task");
		
		this.editor = editor;
		this.rootScrolledComposite = rootScrolledComposite;
	}

	@Override
	public void refreshLogic(XMLEditor editor, 
			Composite composite, ScrolledComposite sc3) throws JAXBException {
		int j = 0;
		try {
			centralUtils.unmarshalMe(editor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		ArrayList<TTask> groups = new ArrayList<TTask>();
		if (editor.getRootElement().getLogicalPeopleGroups() != null) {
			groups = (ArrayList<TTask>) editor.getRootElement()
					.getTasks().getTask();
		}

		for (int i = 0; i < groups.size(); i++) {
			TTaskInterfaceSection tNot;
			if ((childComposites.size() == groups.size())) {
				tNot = (TTaskInterfaceSection) composites.get(j);
				tNot.initialize(j, editor);
			} else {
				tNot = new TTaskInterfaceSection(editor, composite, j,SWT.NONE);
				tNot.initialize(j, editor);
				composites.add(j, tNot);

			}
			tNot.updated = true;
			sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			sc3.layout(true, true);
			innerSection.layout(true, true);
			j++;
			System.out.println("j  value" + j);
		}
		compositeIndex = j;
		System.out.println("refresh l value is " + compositeIndex);
	}


	@Override
	public void fillDetailArea(final Composite composite) {
		Label lblNewLabel = toolkit.createLabel(composite,
				"Label in TTask Section", SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);

		Button btnAddLPG = new Button(composite, SWT.NONE);
		btnAddLPG.setText("plus");
		//btnNewgroup
		//btnNewgroup.setText("yyyy");
		//System.out.println(btnNewgroup.isDisposed());
		/*btnAddLPG.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Section scc2;
				try {
					scc2 = new TTaskSection(editor, composite, Section.TWISTIE
							| Section.TITLE_BAR, rootScrolledComposite);
					scc2.setExpanded(false);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});*/
		

		TTaskInterfaceSection TISx = new TTaskInterfaceSection(editor,
				composite, 2, Section.TWISTIE | Section.TITLE_BAR);
		TISx.setExpanded(false);
		//System.out.println("created a Task Interface section");

		Section sc3 = new TTaskInterfaceSection(editor, composite, 1,
				Section.TWISTIE | Section.TITLE_BAR);
		sc3.setExpanded(false);
		// sc3.setText("hooyee");
	}
	/*
	 * 
	 * String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) 
	 */

	@Override
	public void newButtonLogic(String selection,
			ScrolledComposite sc3,
			final XMLEditor editor, final Composite parent) throws JAXBException {
	//	btnNewgroup.addListener(SWT.Selection, new Listener() {

		//	@Override
		//	public void handleEvent(Event event) {
				System.out.println("in the Task class newButtonLogic class");
				Section scc2;
				try {
					scc2 = new TTaskSection(editor, parent, Section.TWISTIE
							| Section.TITLE_BAR, rootScrolledComposite);
					scc2.setExpanded(false);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//}
	//	});

	}
}
