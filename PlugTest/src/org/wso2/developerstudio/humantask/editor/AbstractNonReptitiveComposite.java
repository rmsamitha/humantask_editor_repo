package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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

public abstract class AbstractNonReptitiveComposite extends Composite {
	protected final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	protected CentralUtils centralUtils;
	protected ArrayList<Text> textBoxes;
	protected Composite detailArea;

	public AbstractNonReptitiveComposite(final XMLEditor textEditor,
			Composite parent, final int compositeIndex, int style,
			String sectionTitle) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		textBoxes = new ArrayList<Text>();
		try {
			centralUtils = CentralUtils.getInstance(textEditor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		toolkit.adapt(this);
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);
		final ScrolledComposite scrolledComposite = new ScrolledComposite(this,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setLayout(new GridLayout());
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		final Composite innerComposite = new Composite(scrolledComposite,
				SWT.NONE);
		scrolledComposite.setContent(innerComposite);
		innerComposite.setLayout(new FormLayout());

		final Section innerSection = toolkit.createSection(innerComposite,
				Section.TWISTIE | Section.TITLE_BAR);
		innerSection.setLayout(new GridLayout());
		innerSection.setText(sectionTitle);
		innerSection.setExpanded(true);
		detailArea = toolkit.createComposite(innerSection, SWT.NONE);
		fillDetailArea();
		innerSection.setClient(detailArea);
		Button btnUpdate = toolkit.createButton(innerSection, "Update",
				SWT.NONE);
		innerSection.setTextClient(btnUpdate);
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

	public abstract void initialize(int compositeIndex, XMLEditor textEditor)
			throws JAXBException;

	public abstract void fillDetailArea();

}
