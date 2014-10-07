package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.wb.swt.SWTResourceManager;

//import TDocumentationComposite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TNotifications;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.uimodel.TNotificationsUI;
import org.wso2.developerstudio.humantask.uimodel.TTasksUI;

public class Transition extends Composite {
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	static int y;
	int i = 0;
	private Text txtNewText;
	TTasksUI composite3;
	TNotificationsUI notificationsUI;
	Composite here;
	TTasks tLogicalPeopleGroups;
	boolean pressed;
	XMLEditor textEditor;
	int compositeCount;
	// ArrayList<Section> secs = new ArrayList<Section>();
	final ArrayList<TLogicalPeopleGroupComposite2> LPGArrayList = new ArrayList<TLogicalPeopleGroupComposite2>();
	// private Section secTestSectionClass_1;
	private TTasksUI xx;
	 int notificationsCompositeCount;
	TNotificationsUI secNotifications;
	TNotifications tNotifications;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Transition(final XMLEditor editor, final Composite parent, int style) {
		super(parent, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		this.textEditor = editor;
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		here = this;
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FormLayout());
		setLayoutData(new GridData(GridData.FILL_BOTH));

		// set the minimum width and height of the scrolled content - method 2
		// create the scrolledComposite
		final ScrolledComposite sc2 = new ScrolledComposite(this, SWT.BORDER
				| SWT.V_SCROLL);
		sc2.setAlwaysShowScrollBars(true);
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);
		FormData fd_sc2 = new FormData();
		fd_sc2.right = new FormAttachment(100);
		fd_sc2.bottom = new FormAttachment(100);
		fd_sc2.top = new FormAttachment(0, 5);
		fd_sc2.left = new FormAttachment(0, 5);
		sc2.setLayoutData(fd_sc2);
		sc2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		this.setData(sc2);

		final Composite c2 = new Composite(sc2, SWT.NONE);
		c2.setBackground(SWTResourceManager.getColor(0, 51, 102));
		c2.setToolTipText("dsd");
		sc2.setContent(c2);

		// final ArrayList<FormData> FormDataArrayList = new
		// ArrayList<FormData>();
		c2.setLayout(new GridLayout(1, true));

		// /////////////////////////////////////////
		final Section secLPGS = toolkit.createSection(c2, Section.TWISTIE
				| Section.TITLE_BAR);

		secLPGS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		RowLayout secTaskRL1 = new RowLayout();
		secTaskRL1.type = SWT.VERTICAL;
		// secTasks.setLayoutData(new RowData(615, SWT.DEFAULT));
		secLPGS.setLayout(secTaskRL1);
		// secLPGS.setTitleBarBackground(new Color(getDisplay(), 121,77,171));
		toolkit.paintBordersFor(secLPGS);
		secLPGS.setText("Logical People GroupsXYZ");

		final ScrolledComposite sc_SecLPGS = new ScrolledComposite(secLPGS,
				SWT.H_SCROLL | SWT.V_SCROLL);
		sc_SecLPGS.setExpandVertical(true);
		// sc_SecLPGS.setBackground(SWTResourceManager.getColor(165, 42, 42));
		toolkit.adapt(sc_SecLPGS);
		toolkit.paintBordersFor(sc_SecLPGS);
		secLPGS.setClient(sc_SecLPGS);
		sc_SecLPGS.setExpandHorizontal(true);

		final Composite comp_sc_SecLPGS = new Composite(sc_SecLPGS, SWT.NONE);
		toolkit.adapt(comp_sc_SecLPGS);
		toolkit.paintBordersFor(comp_sc_SecLPGS);
		sc_SecLPGS.setContent(comp_sc_SecLPGS);
		// sc_SecLPGS.setMinSize(comp_sc_SecLPGS.computeSize(SWT.DEFAULT,
		// SWT.DEFAULT));

		RowLayout rL1 = new RowLayout();
		comp_sc_SecLPGS.setLayout(new GridLayout(1, false));

		/*
		 * Composite testingTDocElement22=new
		 * TLogicalPeopleGroupComposite2(0,comp_sc_SecLPGS, SWT.NONE);
		 * toolkit.adapt(testingTDocElement22);
		 * toolkit.paintBordersFor(testingTDocElement22);
		 * 
		 * 
		 * Composite testingTDocElement11=new
		 * TLogicalPeopleGroupComposite2(1,comp_sc_SecLPGS, SWT.NONE);
		 * toolkit.adapt(testingTDocElement11);
		 * toolkit.paintBordersFor(testingTDocElement11);
		 * 
		 * Composite testingTDocElement33=new
		 * TLogicalPeopleGroupComposite2(2,comp_sc_SecLPGS, SWT.NONE);
		 * toolkit.adapt(testingTDocElement33);
		 * toolkit.paintBordersFor(testingTDocElement33);
		 */
		secLPGS.layout();

		Button btnAddLPG = new Button(secLPGS, SWT.NONE);

		toolkit.adapt(btnAddLPG, true, true);
		secLPGS.setTextClient(btnAddLPG);
		secLPGS.layout();
		btnAddLPG.setText("Event+");

		try {
			CentralUtils cu = CentralUtils.getInstance(editor);
			cu.setBasicUI(sc2, c2);
		} catch (JAXBException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// /111
		/*
		 * Section secTasks = toolkit.createSection(c2, Section.TWISTIE |
		 * Section.TITLE_BAR); secTasks.setLayoutData(new GridData(SWT.FILL,
		 * SWT.CENTER, true, false, 1, 1)); //secTasks.setLayoutData(new
		 * GridData(GridData.FILL_BOTH)); // secTasks.setTitleBarBackground(new
		 * Color(getDisplay(), 121,77,171)); RowLayout secTaskRL = new
		 * RowLayout(); secTaskRL.type = SWT.VERTICAL; //
		 * secTasks.setLayoutData(new RowData(615, SWT.DEFAULT));
		 * secTasks.setLayout(secTaskRL); toolkit.paintBordersFor(secTasks);
		 * secTasks.setText("Tasks"); secTasks.setExpanded(true);
		 * 
		 * final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(
		 * secTasks, SWT.H_SCROLL | SWT.V_SCROLL);
		 * //scrolledComposite_1.setBackground
		 * (SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		 * toolkit.adapt(scrolledComposite_1);
		 * toolkit.paintBordersFor(scrolledComposite_1);
		 * secTasks.setClient(scrolledComposite_1);
		 * scrolledComposite_1.setExpandHorizontal(true);
		 * scrolledComposite_1.setExpandVertical(true);
		 * 
		 * final Composite composite_SecTASK_SC = new Composite(
		 * scrolledComposite_1, SWT.NONE);
		 * 
		 * 
		 * composite_SecTASK_SC.setLayout(new GridLayout(1,true));
		 * //toolkit.adapt(composite_SecTASK_SC);
		 * toolkit.paintBordersFor(composite_SecTASK_SC);
		 * scrolledComposite_1.setContent(composite_SecTASK_SC);
		 * composite_SecTASK_SC
		 * .setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		 * scrolledComposite_1.setMinSize(composite_SecTASK_SC.computeSize(
		 * SWT.DEFAULT, SWT.DEFAULT));
		 * 
		 * RowLayout rL = new RowLayout(); GridLayout gl_composite_SecTASK_SC =
		 * new GridLayout(1, true); // gl_composite_SecTASK_SC.
		 * gl_composite_SecTASK_SC.marginLeft = 5; //
		 * composite_SecTASK_SC.setLayout(gl_composite_SecTASK_SC); //
		 * composite_SecTASK_SC.setLayout(new GridLayout(1,true));
		 * 
		 * 
		 * Composite testingTDocElement2=new
		 * TDocumentationComposite(composite_SecTASK_SC, SWT.NONE); GridData
		 * gd_testingTDocElement2 = new GridData(SWT.LEFT, SWT.CENTER, false,
		 * false, 1, 1); gd_testingTDocElement2.heightHint = 36;
		 * testingTDocElement2.setLayoutData(gd_testingTDocElement2);
		 * //testingTDocElement2.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
		 * true, false, 1, 1));
		 * 
		 * 
		 * Composite testingTDocElement1=new
		 * TDocumentationComposite(composite_SecTASK_SC, SWT.NONE); GridData
		 * gd_testingTDocElement1 = new GridData(SWT.LEFT, SWT.CENTER, false,
		 * false, 1, 1); gd_testingTDocElement1.heightHint = 40;
		 * testingTDocElement1.setLayoutData(gd_testingTDocElement1);
		 * 
		 * 
		 * Button btnAddTask = new Button(secTasks, SWT.NONE);
		 * toolkit.adapt(btnAddTask, true, true);
		 * secTasks.setTextClient(btnAddTask); secTasks.layout();
		 * btnAddTask.setText("+");
		 */

		// /Adding class composite<<<

	/*	Section secNotifications = toolkit.createSection(c2, Section.TWISTIE
				| Section.TITLE_BAR);
		
		 * gd_secNotifications.heightHint = 68; gd_secNotifications.widthHint =
		 * 647;
		 
		secNotifications.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		// secNotifications.setTitleBarBackground(new Color(getDisplay(),
		// 121,77,171));
		// secNotifications.setTitleBarForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		toolkit.paintBordersFor(secNotifications);
		secNotifications.setText("Notifications");
		secNotifications.setExpanded(true);

		Button button = new Button(secNotifications, SWT.NONE);
		toolkit.adapt(button, true, true);
		secNotifications.setTextClient(button);
		button.setText("+");
		// secNotifications.setExpanded(true);

		
		 * Composite4 compNotifications= new Composite4(editor,
		 * secNotifications, SWT.NONE);
		 * secNotifications.setClient(compNotifications);
		 

		// secTasks.setSize(composite_SecTASK_SC.computeSize(SWT.DEFAULT,
		// SWT.DEFAULT));

		// Section secTest=new SectionTest(composite_SecTASK_SC, Section.TWISTIE
		// | Section.TITLE_BAR);
		
		 * GridData gd_secTest = new GridData(SWT.LEFT, SWT.CENTER, false,
		 * false, 1, 1); gd_secTest.heightHint = 73; gd_secTest.minimumHeight =
		 * 40; gd_secTest.widthHint = 617; secTest.setLayoutData(gd_secTest);
		 
		// secTest.setText("SectionTest Class extended by section");
		// secTest.setTitleBarGradientBackground(new Color(getDisplay(), 200,
		// 10, 12)) ;
		// secTest.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
		// 1, 1));
		// try {
		
		 * Section scc=new TTaskSection(new XMLEditor(), composite_SecTASK_SC,
		 * Section.TWISTIE | Section.TITLE_BAR); scc.setExpanded(false);
		 

		// Section secTestSectionClass = null;

		// secTestSectionClass_1 = new TTaskInterfaceSection(new XMLEditor(),
		// composite_SecTASK_SC, 1, Section.TWISTIE | Section.TITLE_BAR);

		
		 * GridData gd_secTestSectionClass = new GridData(SWT.LEFT, SWT.CENTER,
		 * false, false, 1, 1); gd_secTestSectionClass.widthHint = 620;
		 * gd_secTestSectionClass.heightHint = 89;
		 
		// secTestSectionClass_1.setLayoutData(gd_secTestSectionClass);
		// scc.setLayoutData(gd_secTestSectionClass);

		// Section secTestSectionClass2=new TTaskInterfaceSection(editor,
		// composite_SecTASK_SC, 2, Section.TWISTIE | Section.TITLE_BAR);

		
		 * Section sctnNewSection = toolkit.createSection(composite_SecTASK_SC,
		 * Section.TWISTIE | Section.TITLE_BAR);
		 * sctnNewSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
		 * false, false, 1, 1)); toolkit.paintBordersFor(sctnNewSection);
		 * sctnNewSection.setText("New Section XX");
		 * sctnNewSection.setTitleBarBackground(new Color(getDisplay(), 200, 10,
		 * 12)) ; sctnNewSection.setExpanded(true);
		 

		
		 * Section sct=new
		 * SectionCode().getSection(toolkit,composite_SecTASK_SC); Section
		 * sct2=new SectionCode().getSection(toolkit,composite_SecTASK_SC);
		 

		
		 * Button btnNewButton_1 = toolkit.createButton(sctnNewSection,
		 * "New Button", SWT.NONE); sctnNewSection.setClient(btnNewButton_1);
		 * secTasks.layout();
		 

		
		 * Composite composite = new Composite(composite_SecTASK_SC, SWT.NONE);
		 * composite.setLayout(null); GridData gd_composite = new
		 * GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		 * gd_composite.widthHint = 610; gd_composite.heightHint = 40;
		 * composite.setLayoutData(gd_composite); toolkit.adapt(composite);
		 * toolkit.paintBordersFor(composite);
		 

		// Section sc3=new TTaskInterfaceSection2(editor, composite_SecTASK_SC,
		// 1,Section.TWISTIE | Section.TITLE_BAR);
		// sc3.setExpanded(false);

		// sc3.setLayoutData(gd_secTestSectionClass);

		// Button btnNewButton_1 = toolkit.createButton(composite, "New Button",
		// SWT.NONE);
		// scc2.setLayoutData(gd_secTestSectionClass);

		
		 * Section sctnNewSection = toolkit.createSection(composite_SecTASK_SC,
		 * Section.TITLE_BAR); GridData zzzz = new GridData(SWT.FILL,
		 * SWT.CENTER, false, false, 1, 1); zzzz.heightHint = 68; zzzz.widthHint
		 * = 647; sctnNewSection.setLayoutData(zzzz);
		 

		
		 * try { xx=new
		 * TTasksUI(editor,composite_SecTASK_SC,here,SWT.NONE,editor
		 * .getRootElement().getTasks(),0,0); } catch (JAXBException e2) { //
		 * TODO Auto-generated catch block e2.printStackTrace(); }
		 

		toolkit.paintBordersFor(secNotifications);*/

		/*
		 * btnAddTask.addListener(SWT.Selection, new Listener() {
		 * 
		 * @Override public void handleEvent(Event event) {
		 */
		pressed = true;
		if (compositeCount < 1) {
			try {
				CentralUtils centralUtils = CentralUtils.getInstance(editor);
				centralUtils.unmarshalMe(editor);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}

			try {
				/*
				 * TLogicalPeopleGroups tLogicalPeopleGroups = null; if
				 * (editor.getRootElement().getLogicalPeopleGroups() == null) {
				 * tLogicalPeopleGroups= new TLogicalPeopleGroups();
				 * editor.getRootElement().setLogicalPeopleGroups(
				 * tLogicalPeopleGroups);
				 * 
				 * }
				 */

				if (editor.getRootElement().getTasks() == null) {
					tLogicalPeopleGroups = new TTasks();
					editor.getRootElement().setTasks(tLogicalPeopleGroups);

				}

				composite3 = new TTasksUI(editor, c2, here, SWT.NONE, editor
						.getRootElement().getTasks(), 0, 0);
				compositeCount++;
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		}

		if (notificationsCompositeCount < 1) {
			try {
				CentralUtils centralUtils = CentralUtils.getInstance(editor);
				centralUtils.unmarshalMe(editor);
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}

			try {
				if (editor.getRootElement().getNotifications() == null) {
					tNotifications= new TNotifications();
					editor.getRootElement().setNotifications(tNotifications);
				}
				secNotifications = new TNotificationsUI(editor, c2, here, SWT.NONE, editor
						.getRootElement().getNotifications(), 0, 0);
				notificationsCompositeCount++;
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		}
		/*
		 * } });
		 */

		// scc2.setExpanded(false);
		// scc2.setSize(400, 800);

		/*
		 * } catch (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 */
		/*
		 * Section scc4=new TTaskSection(editor, composite_SecTASK_SC,
		 * Section.TWISTIE | Section.TITLE_BAR); scc4.setExpanded(false);
		 */

		// //

	}

	public void refreshChildren(int childCompositeIndex, int childObjectIndex) {

		tLogicalPeopleGroups = null;
		tNotifications=null;
	}

	public void loadModel(Object model) throws JAXBException {
		THumanInteractions tHumanInteractions = (THumanInteractions) model;

		if (pressed) {
			composite3.tasks = tHumanInteractions.getTasks();
			composite3.refreshLogic(textEditor);
			composite3.loadModel( tHumanInteractions.getTasks());
			
			
			secNotifications.notifications=tHumanInteractions.getNotifications();			
			secNotifications.refreshLogic(textEditor);
			secNotifications.loadModel(tHumanInteractions.getNotifications());
		}
	}

}