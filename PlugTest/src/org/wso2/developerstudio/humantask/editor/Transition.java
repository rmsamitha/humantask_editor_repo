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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.models.TTasks;
import org.wso2.developerstudio.humantask.uimodel.TTasksUI;

public class Transition extends Composite {
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private TTasksUI taskUI;
	private TTasks tasks;
	private XMLEditor textEditor;
	private int compositeCount;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class
			.getName());

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

		final ScrolledComposite parentScrolledComposite = new ScrolledComposite(this, SWT.BORDER
				| SWT.V_SCROLL);
		parentScrolledComposite.setAlwaysShowScrollBars(true);
		parentScrolledComposite.setExpandHorizontal(true);
		parentScrolledComposite.setExpandVertical(true);
		FormData parentScrolledCompositeFormLayoutData = new FormData();
		parentScrolledCompositeFormLayoutData.right = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.bottom = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.top = new FormAttachment(0, 5);
		parentScrolledCompositeFormLayoutData.left = new FormAttachment(0, 5);
		parentScrolledComposite.setLayoutData(parentScrolledCompositeFormLayoutData);
		parentScrolledComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		this.setData(parentScrolledComposite);

		Composite baseContainer = new Composite(parentScrolledComposite, SWT.NONE);
		baseContainer.setBackground(SWTResourceManager.getColor(0, 51, 102));
		parentScrolledComposite.setContent(baseContainer);

		baseContainer.setLayout(new GridLayout(1, true));

		Section logicalPeopleGroupsSection = toolkit.createSection(baseContainer, Section.TWISTIE
				| Section.TITLE_BAR);
		GridData logicalPeopleGroupSectionGridData = new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1);

		logicalPeopleGroupsSection.setLayoutData(logicalPeopleGroupSectionGridData);
		RowLayout logicalPeopleGroupSectionRowLayout = new RowLayout();
		logicalPeopleGroupSectionRowLayout.type = SWT.VERTICAL;
		logicalPeopleGroupsSection.setLayout(logicalPeopleGroupSectionRowLayout);
		toolkit.paintBordersFor(logicalPeopleGroupsSection);
		logicalPeopleGroupsSection.setText(HTEditorConstants.LOGICAL_PEOPLE_GROUPS_TITLE);
		logicalPeopleGroupsSection.setExpanded(true);

		ScrolledComposite logicalPeopleGroupsInnerScrolledComposite = new ScrolledComposite(logicalPeopleGroupsSection,
				SWT.H_SCROLL | SWT.V_SCROLL);
		logicalPeopleGroupsInnerScrolledComposite.setExpandVertical(true);

		toolkit.adapt(logicalPeopleGroupsInnerScrolledComposite);
		toolkit.paintBordersFor(logicalPeopleGroupsInnerScrolledComposite);
		logicalPeopleGroupsSection.setClient(logicalPeopleGroupsInnerScrolledComposite);
		logicalPeopleGroupsInnerScrolledComposite.setExpandHorizontal(true);

		Composite logicalPeopleGroupsInnerComposite = new Composite(logicalPeopleGroupsInnerScrolledComposite, SWT.NONE);
		toolkit.adapt(logicalPeopleGroupsInnerComposite);
		toolkit.paintBordersFor(logicalPeopleGroupsInnerComposite);
		logicalPeopleGroupsInnerScrolledComposite.setContent(logicalPeopleGroupsInnerComposite);
		logicalPeopleGroupsInnerComposite.setLayout(new GridLayout(1, false));
		logicalPeopleGroupsSection.layout();

		try {
			CentralUtils centralUtils = CentralUtils.getInstance(textEditor);
			centralUtils.setBasicUI(parentScrolledComposite, baseContainer);
		} catch (JAXBException e) {
			LOG.info(e.getMessage());
		}

		Section notificationsSection = toolkit.createSection(baseContainer, Section.TWISTIE
				| Section.TITLE_BAR);
		GridData notificationsSectionGridData = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1);

		notificationsSection.setLayoutData(notificationsSectionGridData);
		toolkit.paintBordersFor(notificationsSection);
		notificationsSection.setText(HTEditorConstants.NOTIFICATION_TITLE);
		notificationsSection.setExpanded(true);
		toolkit.paintBordersFor(notificationsSection);
		
		if (compositeCount < 1) {
			try {
				CentralUtils centralUtils = CentralUtils.getInstance(textEditor);
				centralUtils.unmarshalMe(textEditor);
				if (textEditor.getRootElement().getTasks() == null) {
					tasks = new TTasks();
					textEditor.getRootElement().setTasks(tasks);
				}
				taskUI = new TTasksUI(textEditor, baseContainer, this, SWT.NONE, textEditor
						.getRootElement().getTasks(), 0, 0);
				compositeCount++;
			} catch (JAXBException e) {
				LOG.info(e.getMessage());
			}
		}

	}

	public void refreshChildren(int childCompositeIndex, int childObjectIndex) {
			tasks = null;
	}

	public void loadModel(Object model) throws JAXBException {
		tasks = (TTasks) model;
		taskUI.tasks = (TTasks) model;
		taskUI.refreshLogic(textEditor);
		taskUI.loadModel(model);
		
	}

}