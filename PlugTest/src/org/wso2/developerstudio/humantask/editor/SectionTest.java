package org.wso2.developerstudio.humantask.editor;

import java.awt.GridLayout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;

public class SectionTest extends Section{
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	public SectionTest(Composite parent, int style) {
		super(parent, Section.TWISTIE
				| Section.TITLE_BAR);
		setBackground(SWTResourceManager.getColor(0, 51,0));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		setTitleBarBackground(new Color(getDisplay(), 200, 10, 12)) ;
		setTouchEnabled(true);
		setText("SectionTest Class extended by section");
		setExpanded(true);
		//setBackground(SWTResourceManager.getColor(153, 153, 51));
		this.setLayout(new org.eclipse.swt.layout.GridLayout());
		/*addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});*/
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		
		final ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		//scrolledComposite.
		//scrolledComposite.setMinHeight(30);
		//scrolledComposite.setSize(700, 40);
		scrolledComposite.setBackground(SWTResourceManager.getColor(102, 102, 204));
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		setClient(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(204, 102, 255));
		composite.setSize(5000, 100);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(null);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(5, 5, 91, 29);
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("New Button");
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(new Point(0, 0));
		
		btnNewButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				scrolledComposite.setMinHeight(30);
				scrolledComposite.setSize(700, 40);
			}
		});
		
	}
}
