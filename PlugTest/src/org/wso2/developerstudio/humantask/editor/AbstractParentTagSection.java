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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

public abstract class AbstractParentTagSection extends Section {
	protected final FormToolkit formToolkit;
	protected ArrayList<Widget> textBoxesList;
	protected CentralUtils centralUtils;
	protected Section innerSection;
	protected Composite parentTagContainer;
	protected Composite detailArea;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class
			.getName());

	public AbstractParentTagSection(final XMLEditor textEditor, Composite parentComposite,
			Composite parentTagContainer, int styleBit, final String[] dropDownItems,
			String sectionTitle) throws JAXBException {
		super(parentComposite, Section.TWISTIE | Section.TITLE_BAR);
		formToolkit = new FormToolkit(Display.getCurrent());
		setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		setText(sectionTitle);
		setExpanded(true);
		centralUtils = CentralUtils.getInstance(textEditor);
		textBoxesList = new ArrayList<Widget>();
		this.parentTagContainer = parentTagContainer;
		setLayout(new GridLayout(1, false));
		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.setTitleBarBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});

		final ScrolledComposite sectionParentScrolledComposite = new ScrolledComposite(
				this, SWT.H_SCROLL);
		sectionParentScrolledComposite.setMinHeight(-1);
		formToolkit.adapt(sectionParentScrolledComposite);
		formToolkit.paintBordersFor(sectionParentScrolledComposite);
		this.setClient(sectionParentScrolledComposite);
		sectionParentScrolledComposite.setExpandHorizontal(true);
		sectionParentScrolledComposite.setExpandVertical(true);
		sectionParentScrolledComposite.setLayout(new GridLayout(1, false));

		detailArea = new Composite(sectionParentScrolledComposite, SWT.NONE);
		formToolkit.adapt(detailArea);
		formToolkit.paintBordersFor(detailArea);
		fillDetailArea(detailArea);
		sectionParentScrolledComposite.setContent(detailArea);
		detailArea.setLayout(new GridLayout(1, true));
		ToolBar toolBarTitle = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		setTextClient(toolBarTitle);

		ToolItem btnNewgroup = new ToolItem(toolBarTitle, SWT.NONE);
		btnNewgroup.setToolTipText(HTEditorConstants.ADD_CHILD_BUTTON_TOOLTIP);
		btnNewgroup.setWidth(27);
		btnNewgroup.setImage(ResourceManager.getPluginImage(
				HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
				HTEditorConstants.EDIT_BUTTON_IMAGE));

		ToolItem btnUpdate = new ToolItem(toolBarTitle, SWT.NONE);
		btnUpdate.setToolTipText(HTEditorConstants.UPDATE_BUTTON_TOOLTIP);
		btnUpdate.setImage(ResourceManager.getPluginImage(
				HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
				HTEditorConstants.UPDATE_BUTTON_IMAGE));

		ToolItem btnRemove = new ToolItem(toolBarTitle, SWT.CHECK);
		btnRemove.setToolTipText(HTEditorConstants.REMOVE_BUTTON_TOOLTIP);
		btnRemove.setImage(ResourceManager.getPluginImage(
				HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
				HTEditorConstants.REMOVE_BUTTON_IMAGE));
		final Shell shell = new Shell(this.getDisplay());
		final Menu menu = new Menu(shell, SWT.POP_UP);

		ArrayList<MenuItem> menuItemArrayList = new ArrayList<MenuItem>();
		for (int dropDownItemIndex = 0; dropDownItemIndex < dropDownItems.length; dropDownItemIndex++) {
			menuItemArrayList.add(new MenuItem(menu, SWT.NONE));
			menuItemArrayList.get(dropDownItemIndex).setText(
					dropDownItems[dropDownItemIndex]);
			final String selection = dropDownItems[dropDownItemIndex];

			menuItemArrayList.get(dropDownItemIndex).addListener(SWT.Selection,
					new Listener() {
						public void handleEvent(Event event) {
							LOG.info("item selected: RADIO item");
							try {
								newButtonLogic(selection,
										sectionParentScrolledComposite, textEditor,
										detailArea);
								centralUtils.redraw();
							} catch (JAXBException e) {
								LOG.info(e.getMessage());
							}
						}
					});

		}

		btnNewgroup.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				menu.setVisible(true);
			}
		});

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

	public abstract void refreshLogic(XMLEditor editor) throws JAXBException;

	public abstract void initialize(XMLEditor textEditor) throws JAXBException;

	public abstract void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException;

	public abstract void fillDetailArea(Composite composite);

	public abstract void refreshChildren(String itemName,
			int childCompositeIndex, int childObjectIndex);

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