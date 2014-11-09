package org.wso2.developerstudio.humantask.editor;

import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

public class Transition extends Composite {
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private THumanInteractions humanInteractions;
	private THumanInteractionsUI humanInteractionsUI;
	private XMLEditor textEditor;
	Composite baseContainer;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class.getName());

	public Transition(final XMLEditor textEditor, final Composite parentComposite, int styleBit) {
		super(parentComposite, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		this.textEditor = textEditor;
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FormLayout());
		setLayoutData(new GridData(GridData.FILL_BOTH));

		final ScrolledComposite parentScrolledComposite =
		                                                  new ScrolledComposite(this, SWT.BORDER |
		                                                                              SWT.V_SCROLL);
		parentScrolledComposite.setAlwaysShowScrollBars(true);
		parentScrolledComposite.setExpandHorizontal(true);
		parentScrolledComposite.setExpandVertical(true);
		FormData parentScrolledCompositeFormLayoutData = new FormData();
		parentScrolledCompositeFormLayoutData.right = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.bottom = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.top = new FormAttachment(0, 5);
		parentScrolledCompositeFormLayoutData.left = new FormAttachment(0, 5);
		parentScrolledComposite.setLayoutData(parentScrolledCompositeFormLayoutData);
		// parentScrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		this.setData(parentScrolledComposite);

		baseContainer = new Composite(parentScrolledComposite, SWT.NONE);
		baseContainer.setBackground(SWTResourceManager.getColor(204, 153, 255));
		parentScrolledComposite.setContent(baseContainer);

		baseContainer.setLayout(new GridLayout(1, true));
		try {
			CentralUtils centralUtils = CentralUtils.getInstance(textEditor);
			centralUtils.setBasicUI(parentScrolledComposite, baseContainer);
			centralUtils.unmarshal(textEditor);
		} catch (JAXBException e) {
			LOG.info(e.getMessage());
		}

		if (textEditor.getRootElement() == null) {
			humanInteractions = new THumanInteractions();
			textEditor.setRootElement(humanInteractions);
		}
		try {
			humanInteractionsUI =
			                      new THumanInteractionsUI(textEditor, baseContainer, this,
			                                               SWT.NONE, textEditor.getRootElement(),
			                                               0, 0);
		} catch (JAXBException e1) {
			LOG.info(e1.getMessage());
		}

	}

	public void refreshLogic(XMLEditor editor) throws JAXBException {
		System.out.println("in transition refresh logic");
		if (humanInteractions != null) {
			humanInteractionsUI.dispose();
			humanInteractionsUI =
			                      new THumanInteractionsUI(textEditor, baseContainer, this,
			                                               SWT.NONE, textEditor.getRootElement(),
			                                               0, 0);
			humanInteractionsUI.initialize(editor);
		}
	}

	public void loadModel(Object model) throws JAXBException {
		humanInteractions = (THumanInteractions) model;
		humanInteractionsUI.humanInteractions = (THumanInteractions) model;

		humanInteractionsUI.refreshLogic(textEditor);
		humanInteractionsUI.loadModel(model);
		System.out.println("initialized called in loadmodel of transition: texteditor val:" +
		                   textEditor == null);
		humanInteractionsUI.initialize(textEditor);

	}

}