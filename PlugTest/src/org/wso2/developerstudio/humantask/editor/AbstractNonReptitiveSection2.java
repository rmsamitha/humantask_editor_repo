package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public abstract class AbstractNonReptitiveSection2 extends Section {
	protected FormToolkit toolkit;
	protected ArrayList<Text> textBoxes;
	protected int compositeIndex = 0;

	public AbstractNonReptitiveSection2(final XMLEditor editor, Composite parent,
			int style) {
		//(final XMLEditor textEditor,
		//Composite parent, final int compositeIndexs, int style,
		//String sectionTitle) 
		
		super(parent, Section.TWISTIE | Section.TITLE_BAR);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		setTitleBarBackground(new Color(getDisplay(), 200, 10, 12));
		setText("Abstract Parent Section-Test");
		setExpanded(true);
		textBoxes = new ArrayList<Text>();
		toolkit = new FormToolkit(Display.getCurrent());

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		setClient(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		/*final Composite composite = new Composite(this, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		setTextClient(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button btnNewButton = new Button(composite, SWT.NONE);
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("New");

		Button btnRefresh = new Button(composite, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		toolkit.adapt(btnNewButton, true, true);
		btnRefresh.setText("Refresh");

		// <<Layouting controls
		RowLayout secTaskRL = new RowLayout();
		secTaskRL.type = SWT.VERTICAL;
		this.setLayout(secTaskRL);
		toolkit.paintBordersFor(this);

		final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(
				this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setBackground(SWTResourceManager.getColor(165, 42,
				42));
		toolkit.adapt(scrolledComposite_1);
		toolkit.paintBordersFor(scrolledComposite_1);
		this.setClient(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);

		final Composite composite_SecTASK_SC = new Composite(scrolledComposite_1,
				SWT.NONE);
		composite_SecTASK_SC.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_RED));
		// composite_SecTASK_SC.set
		toolkit.adapt(composite_SecTASK_SC);
		toolkit.paintBordersFor(composite_SecTASK_SC);
		scrolledComposite_1.setContent(composite_SecTASK_SC);
		scrolledComposite_1.setMinSize(composite_SecTASK_SC.computeSize(
				SWT.DEFAULT, SWT.DEFAULT));

		RowLayout rL = new RowLayout();
		GridLayout gl_composite_SecTASK_SC = new GridLayout(1, false);
		// gl_composite_SecTASK_SC.
		gl_composite_SecTASK_SC.marginLeft = 5;
		composite_SecTASK_SC.setLayout(gl_composite_SecTASK_SC);

		// layouting controls>>

		
		
		Section sctnNewSection = toolkit.createSection(composite_SecTASK_SC, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnNewSection = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_sctnNewSection.heightHint = 33;
		gd_sctnNewSection.widthHint = 418;
		sctnNewSection.setLayoutData(gd_sctnNewSection);
		toolkit.paintBordersFor(sctnNewSection);
		sctnNewSection.setText("Section in AbstractParentSection 1");
		
		Section sctnNewSection2 = toolkit.createSection(composite_SecTASK_SC, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnNewSection2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_sctnNewSection2.heightHint = 33;
		gd_sctnNewSection2.widthHint = 418;
		sctnNewSection2.setLayoutData(gd_sctnNewSection2);
		toolkit.paintBordersFor(sctnNewSection2);
		sctnNewSection2.setText("Section in AbstractParentSection 2");

		fillDetailArea(composite_SecTASK_SC);
		
		final ArrayList<Section> sectionsAL = new ArrayList<Section>();

		
		 * btnRefresh.addListener(SWT.Selection, new Listener() {
		 * 
		 * @Override public void handleEvent(Event event) { try {
		 * refreshLogic(editor, composites, composite, sc3); } catch
		 * (JAXBException e) { e.printStackTrace(); } } });
		 
		btnNewButton.addListener(SWT.Selection, new Listener() {
			int i = 0;

			@Override
			public void handleEvent(Event event) {
				try {
					newButtonLogic(i, sectionsAL, editor, composite_SecTASK_SC);
				} catch (JAXBException e) {
					e.printStackTrace();
				}

			}

		});
*/
	}

	public abstract void btnUpdateHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void btnRemoveHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;

	public abstract void initialize(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;

	public abstract void fillDetailArea();
}
