package PlugTest.editors;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public abstract class TMultiParentComposite extends Composite {
	protected final FormToolkit toolkit;
	protected ArrayList<Text> textBoxes; 
	protected int index = 0;
	protected CentralUtils ch2;
	protected Section secLogicalPeopleGroups;

	public TMultiParentComposite(final XMLEditor editor, Composite parent,
			int style) throws JAXBException {
		super(parent, style);
		ch2=CentralUtils.getInstance(editor);
		textBoxes=new ArrayList<Text>();
		toolkit= new FormToolkit(Display.getCurrent());
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 10;
		setLayout(formLayout);

		final ScrolledComposite sc2 = new ScrolledComposite(this, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

		FormData fd_sc2 = new FormData();
		fd_sc2.left = new FormAttachment(0);
		fd_sc2.right = new FormAttachment(100, -10);
		fd_sc2.top = new FormAttachment(0, 10);
		fd_sc2.bottom = new FormAttachment(100);
		sc2.setLayoutData(fd_sc2);
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);

		final Composite c2 = new Composite(sc2, SWT.NONE);
		sc2.setContent(c2);
		
		FormLayout layout2 = new FormLayout();
		c2.setLayout(layout2);
		

		secLogicalPeopleGroups = toolkit.createSection(c2,
				Section.TWISTIE | Section.TITLE_BAR);
		final FormData fd_secLogicalPeopleGroups = new FormData();
		fd_secLogicalPeopleGroups.bottom = new FormAttachment(100, 0);
		fd_secLogicalPeopleGroups.right = new FormAttachment(100, -10);
		fd_secLogicalPeopleGroups.top = new FormAttachment(0, 10);
		fd_secLogicalPeopleGroups.left = new FormAttachment(0, 10);
		secLogicalPeopleGroups.setLayout(new GridLayout());
		toolkit.paintBordersFor(secLogicalPeopleGroups);
		secLogicalPeopleGroups.setText("Logical People Groups");
		secLogicalPeopleGroups.setExpanded(true);
		final ScrolledComposite sc3 = new ScrolledComposite(secLogicalPeopleGroups, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		toolkit.adapt(sc3);
		
		secLogicalPeopleGroups.setClient(sc3);
		FormData fd_sc3 = new FormData();
		fd_sc3.left = new FormAttachment(0);
		fd_sc3.right = new FormAttachment(100, 0);
		fd_sc3.top = new FormAttachment(0, 0);
		fd_sc3.bottom = new FormAttachment(100);
		sc3.setLayoutData(fd_sc3);
		sc3.setExpandHorizontal(true);
		sc3.setExpandVertical(true);
	
		final Composite composite = toolkit.createComposite(sc3,
				SWT.NONE);
		toolkit.paintBordersFor(composite);
		
		composite.setLayout(new GridLayout(1,true));
		fillDetailArea(composite);

		Composite textCompo = toolkit.createComposite(secLogicalPeopleGroups,
				SWT.None);
		textCompo.setLayout(new GridLayout(2, true));
		Button btnNewButton = toolkit.createButton(textCompo, "New Group",
				SWT.NONE);

		Button btnRefresh = toolkit
				.createButton(textCompo, "Refresh", SWT.NONE);
		final ArrayList<Composite> composites = new ArrayList<Composite>();
		sc3.setContent(composite);
		btnRefresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					refreshLogic(editor, composites, composite, sc3);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.addListener(SWT.Selection, new Listener() {
			int i = 0;

			@Override
			public void handleEvent(Event event) {
				try {
					newButtonLogic(i, composites, sc3, editor, composite);
				} catch (JAXBException e) {
					e.printStackTrace();
				}

			}

		});
		secLogicalPeopleGroups.setTextClient(textCompo);
	}

	public abstract void refreshLogic(XMLEditor editor,
			ArrayList<Composite> composites, Composite composite,
			ScrolledComposite sc3) throws JAXBException;

	public abstract void newButtonLogic(int i, ArrayList<Composite> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException;

	public abstract void fillDetailArea(Composite composite) ;

}
