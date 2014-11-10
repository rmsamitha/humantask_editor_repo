/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.humantask.editor;

import javax.xml.namespace.QName;

/**
 * This class holds all the constants used in HT Editor
 * 
 */
public class HTEditorConstants {

	public static final String HT_DEFAULT_NAMESPACE =
	                                                  "org.oasis_open.docs.ns.bpel4people.ws_humantask._200803";

	public static final String UI_EDITOR_TITLE = "UI Editor";

	public static final String HT_JAXB_CONTEXT = "org.wso2.developerstudio.humantask.models";

	public static final String UPDATE_BUTTON_TOOLTIP = "Update XML";

	public static final String REMOVE_BUTTON_TOOLTIP = "Remove";

	public static final String ADD_CHILD_BUTTON_TOOLTIP = "Add Child Element";

	public static final String PLUGIN_IMAGE_SYMBOLIC_NAME = "PlugTest";

	public static final String UPDATE_BUTTON_IMAGE = "icons/rsz_rsz_software_update.png";

	public static final String REMOVE_BUTTON_IMAGE = "icons/rsz_close-2-icon.png";
	// "icons/rsz_1removebtn.png";
	// "icons/rsz_rsz_1rsz_112.png";

	public static final String EDIT_BUTTON_IMAGE =
	                                               "icons/rsz_rsz_1412072977_plus_add_blueedited.png";

	public static final String LOGICAL_PEOPLE_GROUPS_TITLE = "Logical People Groups";

	public static final String NOTIFICATION_TITLE = "Notification";

	public static final String DOCUMENTATION_TITLE = "Documentation";

	public static final String NAME_TAG_TITLE = "Name";

	public static final String PRESENTATION_ELEMENTS_TITLE = "Presentation Elements";

	public static final String SUBJECT_TITLE = "Subject";

	public static final String DESCRIPTION_TITLE = "Description";

	public static final String PRESENTATION_PARAMETERS_TITLE = "Presentation Parameters";

	public static final String PRESENTATION_PARAMETER_TITLE = "Presentation Parameter";

	public static final String PRIORITY_TITLE = "Priority";

	public static final String INTERFACE_TITLE = "Interface";

	public static final String TASKS_TITLE = "Tasks";

	public static final String TASK_TITLE = "Task";

	public static final String NOTIFICATIONS_TITLE = "Notifications";

	public static final String POTENTIAL_OWNERS_TITLE = "Potential Owners";

	public static final String EXCLUDED_OWNERS_TITLE = "Excluded Owners";

	public static final String TASK_INITIATOR_TITLE = "Task Initiator";

	public static final String TASK_STAKE_HOLDERS_TITLE = "Task Stake Holders";

	public static final String BUSINESS_ADMINISTRATORS_TITLE = "Business Administrators";

	public static final String RECIPIENTS_TITLE = "Recipients";

	public static final String FROM_TITLE = "From";

	public static final String PEOPLE_ASSIGNMENTS_TITLE = "People Assignments";

	public static final String GENERIC_HUMAN_ROLE_TITLE = "Generic Human Role";

	public static final String SEQUENCE_TITLE = "Sequence";

	public static final String PARALLEL_TITLE = "Parrelel";

	public static final QName PotentialOwners_QNAME =
	                                                  new QName(
	                                                            "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                            "potentialOwners");

	public static final QName ExcludedOwners_QNAME =
	                                                 new QName(
	                                                           "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                           "excludedOwners");

	public static final QName TaskInitiator_QNAME =
	                                                new QName(
	                                                          "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                          "taskInitiator");

	public final static QName TaskStakeholders_QNAME =
	                                                   new QName(
	                                                             "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                             "taskStakeholders");

	public final static QName Recipients_QNAME =
	                                             new QName(
	                                                       "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                       "recipients");

	public final static QName BusinessAdministrators_QNAME =
	                                                         new QName(
	                                                                   "http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803",
	                                                                   "businessAdministrators");

	public static final String LOGICAL_PEOPLE_GROUP_TITLE = "Logical People group";

	public static final String PARAMETER_TITLE = "Parameter";

	public static final String NAME_ATTRIBUTE_TITLE = "Name";

	public static final String TYPE_ATTRIBUTE_TITLE = "Type";

	public static final String REFERENCE_ATTRIBUTE_TITLE = "Reference";

	public static final String HUMAN_INTERACTIONS_TITLE = "Human Interactions";

	public static final String TASK_STAKEHOLDERS_TITLE = "Task Stake Holders";

	public static final String RESULT_TITLE = "Result";

	public static final String PART_ATTRIBUTE_TITLE = "Part";

	public static final String LOCATION_ATTRIBUTE_TITLE = "Location";

	public static final String CONDITION_ATTRIBUTE_TITLE = "Condition";

	public static final String FUNCTION_ATTRIBUTE_TITLE = "Function";

	public static final String LANGUAGE_ATTRIBUTE_TITLE = "Language";

	public static final String ACTUAL_OWNER_REQUIRED_TITLE = "Actual Owner Required";

	public static final String VALUE_ATTRIBUTE = "Value";

	public static final String DELEGATION_TITLE = "Delegation";

	public static final String RENDERINGS_TITLE = "Renderings";

	public static final String RENDERING_TITLE = "Rendering";

	public static final String VALUE_TAG_TITLE = "Value";

	public static final String TO_TITLE = "To";

	public static final String COPY_TITLE = "Copy";

	public static final String COMPLETION_BEHAVIOR_TITLE = "Completion Behavior";

	public static final String COMPLETION_TITLE = "Completion";

	public static final String DEFAULT_COMPLETION_TITLE = "Default Completion";

	public static final String COMPLETION_ACTION_ATTRIBUTE_TITLE = "Completion Action";

	public static final String EXPRESSION_LANGUAGE_ATTRIBUTE_TITLE = "Expression Language";

	public static final String QUERY_LANGUAGE_ATTRIBUTE_TITLE = "Query Language";

	public static final String AGGREGATE_TITLE = "Aggregate";

	public static final String BOOLEAN_EXPR_TITLE = "Condition";

	public static final String OPERATION_ATTRIBUTE = "Operation";

	public static final String PATTERN_AUTOMATIC = "automatic";

	public static final String PATTERN_MANUAL = "manual";

	public static final String POTENTIAL_DELIGATES_ATTRIBUTE = "Potential Delegetes";

	public static final String RESPONSE_PORT_TYPE_ATTRIBUTE = "Response Port Type";

	public static final String PORT_TYPE_ATTRIBUTE_TITLE = "Port Type";

	public static final String RESPONSE_OPERATION_ATTRIBUTE_TITLE = "Response Operation";

	public static final String CONTENT_TYPE_ATTRIBUTE_TITLE = "Content Type";

	public static final String OUTCOME_TITLE = "Outcome";

	public static final String SEARCHBY_TITLE = "Search By";

	public static final String HT_CONFIG_JAXB_CONTEXT = "org.wso2.developerstudio.htconfig.models";

	public static final String PREFIX_TITLE = "Prefix";

	public static final String IMPORT_TITLE = "Import";

	public static final String HTTP_SCHEMAS_XMLSOAP_ORG_WSDL = "http://schemas.xmlsoap.org/wsdl/";

	public static final String INTERNAL_ERROR = "Internal Error";

	public static final String XML_PARSE_ERROR_MESSAGE = "XML Parse Error occured";

	public static final String DOCUMENT_ROOT =
	                                           "<htd:humanInteractions xmlns:htd=\"http://docs.oasis-open.org/ns/bpel4people/ws-humantask/200803\" targetNamespace=\"http://www.example.com\"  />";
	// "<htd:tHumanInteractions/>";

	public static final String HTCONFIG_FILE_NAME = "/htconfig.xml";

	public static final String FILE_NOT_FOUND =
	                                            "WSDL file given in the Import tag's location attribute is not Found";

	public static final String XML_PARSE_ERROR_UI_DESIGN_CANNOT_BE_VIEWED =
	                                                                        "XML Parse Error UI design cannot be viewed";

	public static final String XML_PARSE_ERROR = "XML Parse Error";

	public static final String REMOVE_NAMESPACE_TITLE = "Remove Namespace";

	public static final String NAMESPACE_TITLE = "Namespace";

	static final String XMLNS_TITLE = "xmlns:";

	static final String CONFIG_ERROR_MESSAGE = "Error in generating Config file";

	protected static final String GENERATE_CONFIG_ERROR_MESSAGE =
	                                                              "Please Check Whether You Have Filled Import tag or Task Interface tag and Notification Interface tag is filled. ";

	public static final String GENERATE_HT_CONFIG_BUTTON_TITLE = "Generate HT-Config";

	public static final String GENERATE_HT_CONFIG_BUTTON_IMAGE =
	                                                             "icons/rsz_1gerenate_ht_config_btn2.png";

	public static final String TARGET_NAMESPACE_ATTRIBUTE_TITLE = "Target Namespace";

	public static final String NAMESPACE_ATTRIBUTE_TITLE = "Namespace";

	public static final String IMPORT_TYPE_ATTRIBUTE_TITLE = "Import Type";

}
