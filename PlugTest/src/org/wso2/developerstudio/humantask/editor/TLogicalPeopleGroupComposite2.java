package org.wso2.developerstudio.humantask.editor;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;

public class TLogicalPeopleGroupComposite2 extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TLogicalPeopleGroupComposite2(int LPGindex,Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FormLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.NONE);
		FormData fd_scrolledComposite = new FormData();
		fd_scrolledComposite.bottom = new FormAttachment(0, 223);
		fd_scrolledComposite.right = new FormAttachment(0, 649);
		fd_scrolledComposite.top = new FormAttachment(0, 3);
		fd_scrolledComposite.left = new FormAttachment(0, 3);
		scrolledComposite.setLayoutData(fd_scrolledComposite);
		//scrolledComposite.setLayoutData(new RowData(308, 87));
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		final Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(255, 255, 0));
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		scrolledComposite.setContent(composite);
		
/////////////
		Section secLPGitem = toolkit.createSection(composite, Section.TWISTIE
				| Section.TITLE_BAR);
		secLPGitem.setBackground(SWTResourceManager.getColor(153, 204, 0));
		GridData gd_secTasks = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_secTasks.widthHint = 600;
		secLPGitem.setLayoutData(gd_secTasks);
		RowLayout secTaskRL=new RowLayout();
		
		
		secTaskRL.type=SWT.VERTICAL;
		//secTasks.setLayoutData(new RowData(615, SWT.DEFAULT));
		secLPGitem.setLayout(secTaskRL);
		toolkit.paintBordersFor(secLPGitem);
		secLPGitem.setText("Logical People Group");
		secLPGitem.setExpanded(true);

		final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(
				secLPGitem,  SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBackground(SWTResourceManager.getColor(165, 42,
				42));
		toolkit.adapt(scrolledComposite_1);
		toolkit.paintBordersFor(scrolledComposite_1);
		secLPGitem.setClient(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		final Composite composite_SecTASK_SC = new Composite(scrolledComposite_1, SWT.NONE);
		composite_SecTASK_SC.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		toolkit.adapt(composite_SecTASK_SC);
		toolkit.paintBordersFor(composite_SecTASK_SC);
		scrolledComposite_1.setContent(composite_SecTASK_SC);
		scrolledComposite_1.setMinSize(composite_SecTASK_SC.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		RowLayout rL=new RowLayout();
		composite_SecTASK_SC.setLayout(new GridLayout(1, false));
		
		Button btnAddDocumentation = new Button(composite_SecTASK_SC, SWT.NONE);
		toolkit.adapt(btnAddDocumentation, true, true);
		btnAddDocumentation.setText("Add Documentation");
		
		Composite testingTDocElement2=new TDocumentationComposite(composite_SecTASK_SC, SWT.NONE);
		GridData gd_testingTDocElement2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_testingTDocElement2.widthHint = 595;
		testingTDocElement2.setLayoutData(gd_testingTDocElement2);
		
		Button btnNewButton = new Button(composite_SecTASK_SC, SWT.NONE);
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(composite_SecTASK_SC, SWT.NONE);
		toolkit.adapt(btnNewButton_1, true, true);
		btnNewButton_1.setText("New Button");
		
		btnAddDocumentation.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Composite testingTDocElement2=new TDocumentationComposite(composite_SecTASK_SC, SWT.NONE);
				GridData gd_testingTDocElement2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
				gd_testingTDocElement2.widthHint = 595;
				testingTDocElement2.setLayoutData(gd_testingTDocElement2);
				composite.setSize(200, 800);
			}
		});

		
		
	}
}
