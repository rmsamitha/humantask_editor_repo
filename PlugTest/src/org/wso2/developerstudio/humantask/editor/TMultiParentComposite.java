package org.wso2.developerstudio.humantask.editor;

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
import org.eclipse.swt.widgets.Combo;
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
	protected int compositeIndex = 0;
	protected CentralUtils centralUtils;
	protected Section innerSection;
	protected int i = 0;
	public TMultiParentComposite(final XMLEditor editor, Composite parent,
			int style) throws JAXBException {
		super(parent, style);
		centralUtils=CentralUtils.getInstance(editor);
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

		final ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);

		/*FormData fd_sc2 = new FormData();
		fd_sc2.left = new FormAttachment(0);
		fd_sc2.right = new FormAttachment(100, -10);
		fd_sc2.top = new FormAttachment(0, 10);
		fd_sc2.bottom = new FormAttachment(100);*/
		scrolledComposite.setLayout(new GridLayout(1,true));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		final Composite innerComposite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(innerComposite);
		innerComposite.setLayout(new GridLayout());
		
		innerSection = toolkit.createSection(innerComposite,
				Section.TWISTIE | Section.TITLE_BAR);
		final FormData fd_secLogicalPeopleGroups = new FormData();
		fd_secLogicalPeopleGroups.bottom = new FormAttachment(100, 0);
		fd_secLogicalPeopleGroups.right = new FormAttachment(100, -10);
		fd_secLogicalPeopleGroups.top = new FormAttachment(0, 10);
		fd_secLogicalPeopleGroups.left = new FormAttachment(0, 10);
		innerSection.setLayout(new GridLayout());
		toolkit.paintBordersFor(innerSection);
		innerSection.setText("Logical People Groups");
		innerSection.setExpanded(true);
		final ScrolledComposite sc3 = new ScrolledComposite(innerSection, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		toolkit.adapt(sc3);
		
		innerSection.setClient(sc3);
		sc3.setLayout(new GridLayout(1,true));
		sc3.setExpandHorizontal(true);
		sc3.setExpandVertical(true);
	
		final Composite composite = toolkit.createComposite(sc3,
				SWT.NONE);
		toolkit.paintBordersFor(composite);
		
		composite.setLayout(new GridLayout(1,true));
		fillDetailArea(composite);
		
		Composite textCompo = toolkit.createComposite(innerSection,
				SWT.None);
		textCompo.setLayout(new GridLayout(2, true));
		Button btnNewButton = toolkit.createButton(textCompo, "New Group",
				SWT.NONE);

		Button btnRefresh = toolkit
				.createButton(textCompo, "Refresh", SWT.NONE);
		final Combo combo = new Combo(textCompo, SWT.NONE);
        combo.setItems(new String[] {"a", "b", "c", "d", "e"});
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
			

			@Override
			public void handleEvent(Event event) {
				try {
					String s=combo.getItem(combo.getSelectionIndex());
					newButtonLogic(s,compositeIndex, composites, sc3, editor, composite);
					
					compositeIndex++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}

			}

		});
		innerSection.setTextClient(textCompo);
	}

	public abstract void refreshLogic(XMLEditor editor,
			ArrayList<Composite> composites, Composite composite,
			ScrolledComposite sc3) throws JAXBException;

	public abstract void newButtonLogic(String selection,int i, ArrayList<Composite> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException;

	public abstract void fillDetailArea(Composite composite) ;

}
