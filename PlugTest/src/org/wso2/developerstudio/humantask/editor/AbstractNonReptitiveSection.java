package org.wso2.developerstudio.humantask.editor;

//import java.awt.Button;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;

public abstract class AbstractNonReptitiveSection extends Section {
	protected final FormToolkit toolkit;
	protected CentralUtils centralUtils;
	protected ArrayList<Text> textBoxes;
	protected Composite detailArea;
	protected Composite composite_SecTASK_SC ;

	//private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	public AbstractNonReptitiveSection(final XMLEditor textEditor,Composite parent, final int compositeIndex,int style,String sectionTitle) {
		super(parent, Section.TWISTIE
				| Section.TITLE_BAR);
		
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		setText("SectionTest Class extended by sectionWhhhh");
		setExpanded(true);
		textBoxes = new ArrayList<Text>();
		toolkit = new FormToolkit(Display.getCurrent());

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		setBackground(SWTResourceManager.getColor(0, 51,0));
		setTitleBarBackground(new Color(getDisplay(),0, 200, 12)) ;
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
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

				composite_SecTASK_SC = new Composite(scrolledComposite_1,
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

		/*final ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBackground(SWTResourceManager.getColor(102, 102, 204));
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		setClient(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		
		detailArea= new Composite(scrolledComposite, SWT.NONE);
		detailArea.setBackground(SWTResourceManager.getColor(204, 102, 255));
		detailArea.setSize(5000, 100);
		toolkit.adapt(detailArea);
		toolkit.paintBordersFor(detailArea);
		detailArea.setLayout(null);
		
		scrolledComposite.setContent(detailArea);*/
		
		
		try {
			fillDetailArea();
		} catch (NullPointerException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//scr.setClient(detailArea);
		Composite textClientComposite = toolkit.createComposite(this,SWT.NONE);
		setTextClient(textClientComposite);
		textClientComposite.setLayout(null);
		
		Button btnUpdate = toolkit.createButton(textClientComposite, "Update",
				SWT.NONE);
		btnUpdate.setBounds(0, 0, 79, 20);
		btnUpdate.setText("XXUpdate");
		
		try {
			initialize(compositeIndex, textEditor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

		btnUpdate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					btnUpdateHandleLogic(compositeIndex, textEditor);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}

			
		});
		
		
	}

	public abstract void btnUpdateHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void btnRemoveHandleLogic(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;

	public abstract void initialize(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;

	public abstract void fillDetailArea();

}
