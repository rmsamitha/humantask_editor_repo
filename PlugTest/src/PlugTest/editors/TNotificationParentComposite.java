package PlugTest.editors;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroups;

public class TNotificationParentComposite extends TMultiParentComposite {

	public TNotificationParentComposite(XMLEditor editor, Composite parent,
			int style) throws JAXBException {
		super(editor, parent, style);
	}

	@Override
	public void refreshLogic(XMLEditor editor, ArrayList<Composite> composites,
			Composite composite, ScrolledComposite sc3) {
		int j = 0;
		try {
			ch2.docToTHumanInteraction(editor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		ArrayList<TLogicalPeopleGroup> groups = new ArrayList<TLogicalPeopleGroup>();
		if (CentralUtils.gettHumanInteractions().getLogicalPeopleGroups() != null) {
			groups = (ArrayList<TLogicalPeopleGroup>) CentralUtils
					.gettHumanInteractions().getLogicalPeopleGroups()
					.getLogicalPeopleGroup();
		}
		for (int i = 0; i < groups.size(); i++) {
			TNotificationComposite tNot;
			if ((composites.size() == groups.size())) {
				tNot = (TNotificationComposite) composites.get(j);
				tNot.initialize(j, ch2);
				System.out.println("Equals");
			} else {
				tNot = new TNotificationComposite(editor, composite, j,
						SWT.NONE);
				composites.add(j, tNot);
				// int
				// index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
				System.out.println("Not Equals");
			}

			composites.get(j)
					.setBounds(20, (j + 1) * 110, sc3.getSize().x, 100);
			sc3.setMinSize(secLogicalPeopleGroups.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));
			sc3.layout(true, true);
			secLogicalPeopleGroups.layout(true, true);
			j++;
			System.out.println("j  value" + j);
		}
		index = j;
		System.out.println("refresh l value is " + index);

	}

	@Override
	public void newButtonLogic(int i, ArrayList<Composite> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException {
		if (i == 0) {
			i = index;
		}
		System.out.println("new button l value is " + i);
		TNotificationComposite tNot = new TNotificationComposite(editor,
				composite, i, SWT.NONE);
		composites.add(i, tNot);
		sc3.setMinSize(secLogicalPeopleGroups.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		sc3.layout(true, true);
		secLogicalPeopleGroups.layout(true, true);

		System.out.println("hikz value is " + i);
		if (CentralUtils.gettHumanInteractions().getLogicalPeopleGroups() == null) {
			TLogicalPeopleGroups tLogicalPeopleGroups = new TLogicalPeopleGroups();
			CentralUtils.gettHumanInteractions().setLogicalPeopleGroups(
					tLogicalPeopleGroups);
		}
		i++;

	}

	@Override
	public void fillDetailArea(Composite composite) {
		Label lblNewLabel = toolkit.createLabel(composite, "New Label",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		Text txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);
		txtNewText.setBounds(102, 23, 61, 21);
		textBoxes.add(txtNewText);
		

	}

}
