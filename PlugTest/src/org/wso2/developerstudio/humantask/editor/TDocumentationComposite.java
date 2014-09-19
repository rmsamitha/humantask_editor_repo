package org.wso2.developerstudio.humantask.editor;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
//import org.eclipse.wb.swt.SWTResourceManager;

public class TDocumentationComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtNewText;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TDocumentationComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(1, false));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		//scrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 65;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		//scrolledComposite.setLayoutData(new RowData(308, 87));
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));
		
		Section sctnDocumentation = toolkit.createSection(composite, Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnDocumentation = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_sctnDocumentation.heightHint = 64;
		gd_sctnDocumentation.widthHint = 582;
		sctnDocumentation.setLayoutData(gd_sctnDocumentation);
		//sctnDocumentation.setBounds(10, 10, 472, 52);
		
		sctnDocumentation.setText("Documentation");
		sctnDocumentation.setExpanded(true);
		
		//sctnDocumentation.setLayoutData(fd1);
		toolkit.paintBordersFor(sctnDocumentation);
		
		Composite composite_1 = toolkit.createComposite(sctnDocumentation, SWT.NONE);
		toolkit.paintBordersFor(composite_1);
		sctnDocumentation.setClient(composite_1);
		composite_1.setLayout(new FormLayout());
		
		Label lblTextContent = toolkit.createLabel(composite_1, "Text Content", SWT.NONE);
		FormData fd_lblTextContent = new FormData();
		fd_lblTextContent.top = new FormAttachment(0, 5);
		fd_lblTextContent.left = new FormAttachment(0);
		lblTextContent.setLayoutData(fd_lblTextContent);
		
		txtNewText = toolkit.createText(composite_1, "New Text", SWT.NONE);
		FormData fd_txtNewText = new FormData();
		fd_txtNewText.top = new FormAttachment(0,5);
		fd_txtNewText.right = new FormAttachment(100);
		fd_txtNewText.left = new FormAttachment(0, 180);
		txtNewText.setLayoutData(fd_txtNewText);
		scrolledComposite.setContent(composite);

	}
}
