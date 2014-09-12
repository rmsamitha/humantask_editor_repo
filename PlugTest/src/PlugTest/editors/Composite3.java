package PlugTest.editors;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Text;

public class Composite3 extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	static int y;
	int i = 0;
	private Text txtNewText;
	ArrayList<Section> secs = new ArrayList<Section>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Composite3(XMLEditor editor, Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);

		// set layout type of this parent composite
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 10;
		setLayout(formLayout);

		// set the minimum width and height of the scrolled content - method 2
		// create the scrolledComposite
		final ScrolledComposite sc2 = new ScrolledComposite(this, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		// sc2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		// fd_sc2.right = new FormAttachment(0, 10);
		// fd_sc2.bottom = new FormAttachment(0, 457);
		// fd_sc2.top = new FormAttachment(0);
		// fd_sc2.left = new FormAttachment(0, -10);
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
		// c2.setBackground(SWTResourceManager.getColor(255, 228, 181));
		c2.setLayout(new FormLayout());

		final Section secLogicalPeopleGroups = toolkit.createSection(c2,
				Section.TWISTIE | Section.TITLE_BAR);
		final FormData fd_secLogicalPeopleGroups = new FormData();
		fd_secLogicalPeopleGroups.bottom = new FormAttachment(0, 345);
		fd_secLogicalPeopleGroups.right = new FormAttachment(100, -10);
		fd_secLogicalPeopleGroups.top = new FormAttachment(0, 10);
		fd_secLogicalPeopleGroups.left = new FormAttachment(0, 10);
		secLogicalPeopleGroups.setLayoutData(fd_secLogicalPeopleGroups);
		// secLogicalPeopleGroups.setBackground(SWTResourceManager.getColor(205,
		// 133, 63));
		toolkit.paintBordersFor(secLogicalPeopleGroups);
		secLogicalPeopleGroups.setText("Logical People Groups");
		secLogicalPeopleGroups.setExpanded(true);

		Button btnAddLPG = new Button(secLogicalPeopleGroups, SWT.PUSH);
		/*
		 * add.addSelectionListener(new SelectionAdapter() {
		 * 
		 * @Override public void widgetSelected(SelectionEvent e) { } }); //
		 * sctnNewSection.is
		 */
		secLogicalPeopleGroups.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent e) {
			}

			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				if (!secLogicalPeopleGroups.isExpanded()) {
					fd_secLogicalPeopleGroups.bottom = new FormAttachment(0, y);

				} else {
					fd_secLogicalPeopleGroups.bottom = new FormAttachment(0,
							200);
				}
			}
		});
		secLogicalPeopleGroups.setTextClient(btnAddLPG);
	//	btnAddLPG.setText("+");

		final ScrolledComposite scrolledComposite = new ScrolledComposite(
				secLogicalPeopleGroups, SWT.BORDER | SWT.H_SCROLL
						| SWT.V_SCROLL);
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		secLogicalPeopleGroups.setClient(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		final Composite composite = new Composite(scrolledComposite, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		FormLayout fl_composite = new FormLayout();
		composite.setLayout(fl_composite);
		FormData fd_compo_inner = new FormData();
		fd_compo_inner.left = new FormAttachment(0, 0);

		/*
		 * Section sctnNewSection_2 = toolkit.createSection(composite,
		 * Section.TWISTIE | Section.TITLE_BAR); FormData fd_sctnNewSection_2 =
		 * new FormData(); fd_sctnNewSection_2.bottom = new FormAttachment(0,
		 * 48); fd_sctnNewSection_2.right = new FormAttachment(100, -10);
		 * fd_sctnNewSection_2.top = new FormAttachment(0, 5);
		 * fd_sctnNewSection_2.left = new FormAttachment(0, 5);
		 * sctnNewSection_2.setLayoutData(fd_sctnNewSection_2);
		 * toolkit.paintBordersFor(sctnNewSection_2);
		 * sctnNewSection_2.setText("LogicalPeopleGroup");
		 * sctnNewSection_2.setExpanded(true);
		 */

		final ArrayList<Section> LPGArrayList = new ArrayList<Section>();
		final ArrayList<FormData> FormDataArrayList = new ArrayList<FormData>();
		btnAddLPG.addListener(SWT.Selection, new Listener() {
			int x = 210;
			int LPGindex = 0;
			int LPGTot = 0;
			int topLocation1 = 10;
			int bottomLocation1 = 80;

			public void handleEvent(Event e) {
				sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				c2.layout();
				fd_secLogicalPeopleGroups.bottom = new FormAttachment(0, x);

				y = x;
				x += 100;
				if (LPGTot == 0) {
					System.out.println("initial LPGTot :" + LPGTot);
					System.out.println("initial LPGindex :" + LPGindex);
					// create a new LPG
					Section secLPGItem1 = toolkit.createSection(composite,
							Section.TWISTIE | Section.TITLE_BAR);
					FormData fd_secLPGItem1 = new FormData();
					fd_secLPGItem1.top = new FormAttachment(0, 10);
					fd_secLPGItem1.bottom = new FormAttachment(0, 80);
					fd_secLPGItem1.right = new FormAttachment(100, -10);
					fd_secLPGItem1.left = new FormAttachment(0, 10);
					secLPGItem1.setLayoutData(fd_secLPGItem1);
					// toolkit.paintBordersFor(secLPGItem);
					secLPGItem1.setText("LogicalPeopleGroup:" + 1);
					secLPGItem1.setExpanded(true);
					txtNewText = toolkit.createText(secLPGItem1, "New Text",
							SWT.NONE);
					secLPGItem1.setTextClient(txtNewText);
					LPGArrayList.add(secLPGItem1);
					LPGTot++;
					// LPGindex++;
					FormDataArrayList.add(fd_secLPGItem1);
				}
				//
				//
				else {
					// Section secLPGItem2x = toolkit.createSection(composite,
					// Section.TWISTIE | Section.TITLE_BAR);
					// FormData fd_secLPGItem2 = new FormData();
					// fd_secLPGItem2.top = new FormAttachment(0,topLocation);//
					// topLocation+(80*LPGindex));
					// secLPGItem2x.setLayoutData(fd_secLPGItem2);
					// toolkit.paintBordersFor(secLPGItem);
					// LPGArrayList.add(secLPGItem2x);
					LPGindex++;
					LPGTot++;
					int topLocation = topLocation1 + (80 * LPGindex);
					int bottomLocation = bottomLocation1 + bottomLocation1
							* LPGindex;
					System.out.println("LPGTot :" + LPGTot);
					System.out.println("LPGindex :" + LPGindex);
					System.out.println("toplocation:" + topLocation);
					System.out.println("BottomLocation:" + bottomLocation);
					LPGArrayList.add((Section) toolkit.createSection(composite,
							Section.TWISTIE | Section.TITLE_BAR));
					System.out.println("else");
					FormDataArrayList.add(new FormData());
					(FormDataArrayList.get(LPGindex)).top = new FormAttachment(
							0, topLocation);// topLocation+(80*LPGindex));
					(FormDataArrayList.get(LPGindex)).bottom = new FormAttachment(
							0, bottomLocation);// 80*LPGindex);
					(FormDataArrayList.get(LPGindex)).right = new FormAttachment(
							100, -10);
					(FormDataArrayList.get(LPGindex)).left = new FormAttachment(
							0, 10);
					LPGArrayList.get(LPGindex).setLayoutData(
							(FormDataArrayList.get(LPGindex)));
					LPGArrayList.get(LPGindex).setText(
							"LogicalPeopleGroup:" + LPGTot);
					LPGArrayList.get(LPGindex).setExpanded(true);
					txtNewText = toolkit.createText(LPGArrayList.get(LPGindex),
							"New Text" + LPGindex, SWT.NONE);
					LPGArrayList.get(LPGindex).setTextClient(txtNewText);
				}
			}

		});

		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));

		Section secNotifications = toolkit.createSection(c2, Section.TWISTIE
				| Section.TITLE_BAR);

		FormData fd_sctnNewSection_1 = new FormData();
		// fd_sctnNewSection_1.
		fd_sctnNewSection_1.bottom = new FormAttachment(100, -189);
		fd_sctnNewSection_1.right = new FormAttachment(100, -10);
		fd_sctnNewSection_1.top = new FormAttachment(secLogicalPeopleGroups, 18);
		fd_sctnNewSection_1.left = new FormAttachment(0, 10);
		secNotifications.setLayoutData(fd_sctnNewSection_1);
		toolkit.paintBordersFor(secNotifications);
		secNotifications.setText("Testing");

		TNotificationParentComposite composite3;
		try {
			composite3 = new TNotificationParentComposite(
					editor, secNotifications, SWT.NONE);
			secNotifications.setClient(composite3);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		
		Section secTasks = toolkit.createSection(c2, Section.TWISTIE
				| Section.TITLE_BAR);
		FormData fd_sctnNewSection_4 = new FormData();
		fd_sctnNewSection_4.right = new FormAttachment(secLogicalPeopleGroups,
				0, SWT.RIGHT);
		fd_sctnNewSection_4.bottom = new FormAttachment(secNotifications,
				SWT.BOTTOM);
		fd_sctnNewSection_4.top = new FormAttachment(secNotifications, 20);
		fd_sctnNewSection_4.left = new FormAttachment(secLogicalPeopleGroups,
				0, SWT.LEFT);
		secTasks.setLayoutData(fd_sctnNewSection_4);
		toolkit.paintBordersFor(secTasks);
		secTasks.setText("Tasks");
		//
	}
}
