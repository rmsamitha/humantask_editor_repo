package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;


public abstract class AbstractEndTagSection extends Section {
	protected final FormToolkit formToolkit;
	protected CentralUtils centralUtils;
	protected ArrayList<Widget> textBoxesList;
	protected Composite detailArea;
	protected Composite parentTagContainer;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class
			.getName());
    public AbstractEndTagSection(final XMLEditor textEditor,
			Composite parentComposite,Composite parentTagContainer, int styleBit,
			String sectionTitle) throws JAXBException {
        super(parentComposite, Section.TWISTIE | Section.TITLE_BAR);
        formToolkit= new FormToolkit(Display.getCurrent());
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        setText(sectionTitle);
        setExpanded(false);
        textBoxesList = new ArrayList<Widget>();
        this.parentTagContainer=parentTagContainer;
        setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                formToolkit.dispose();
            }
        });
        setBackground(SWTResourceManager.getColor(0, 51, 0));
        setTitleBarBackground(new Color(getDisplay(), 228,210,247));
        formToolkit.adapt(this);
        formToolkit.paintBordersFor(this);
        centralUtils = CentralUtils.getInstance(textEditor);
        RowLayout sectionRowLayout = new RowLayout();
        sectionRowLayout.type = SWT.VERTICAL;
        this.setLayout(sectionRowLayout);
        formToolkit.paintBordersFor(this);

        final ScrolledComposite sectionParentScrolledComposite = new ScrolledComposite(
                this, SWT.H_SCROLL | SWT.V_SCROLL);
        sectionParentScrolledComposite.setBackground(SWTResourceManager.getColor(165, 42,
                42));
        formToolkit.adapt(sectionParentScrolledComposite);
        formToolkit.paintBordersFor(sectionParentScrolledComposite);
        this.setClient(sectionParentScrolledComposite);
        sectionParentScrolledComposite.setExpandHorizontal(true);
        sectionParentScrolledComposite.setExpandVertical(true);

        detailArea = formToolkit.createComposite(sectionParentScrolledComposite, SWT.NONE);
        detailArea.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_DARK_RED));
        formToolkit.adapt(detailArea);
        formToolkit.paintBordersFor(detailArea);
        sectionParentScrolledComposite.setContent(detailArea);
        GridLayout detailAreaGridLayout = new GridLayout(1, false);
        detailAreaGridLayout.marginLeft = 5;
        detailArea.setLayout(detailAreaGridLayout);
        try {
            fillDetailArea();
        } catch (NullPointerException e) {
            LOG.info(e.getMessage());
        }
        ToolBar toolBarTitle = new ToolBar(this, SWT.FLAT | SWT.RIGHT | SWT.SHADOW_OUT);
        setTextClient(toolBarTitle);
        ToolItem btnUpdate = new ToolItem(toolBarTitle, SWT.NONE);
        btnUpdate.setToolTipText(HTEditorConstants.UPDATE_BUTTON_TOOLTIP);
        btnUpdate.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME, HTEditorConstants.UPDATE_BUTTON_IMAGE));

        ToolItem btnRemove = new ToolItem(toolBarTitle, SWT.CHECK);
        btnRemove.setToolTipText(HTEditorConstants.REMOVE_BUTTON_TOOLTIP);
        btnRemove.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,HTEditorConstants.REMOVE_BUTTON_IMAGE));

       
        btnRemove.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                try {
					btnRemoveHandleLogic(textEditor);
					centralUtils.redraw();
				} catch (JAXBException e) {
					LOG.info(e.getMessage());
				}
              }
        });

        btnUpdate.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                try {
                    btnUpdateHandleLogic(textEditor);
                } catch (JAXBException e) {
                	LOG.info(e.getMessage());
                }
            }

        });

    }
 

    public abstract void btnUpdateHandleLogic(XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException;

	public abstract void initialize(XMLEditor textEditor)
			throws JAXBException;

	public abstract void fillDetailArea();

	public void drawBorder(final Composite composite) {
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				e.gc.drawLine(0, 0, composite.getParent().getBounds().width, 0);
			}

		});
	}
}
