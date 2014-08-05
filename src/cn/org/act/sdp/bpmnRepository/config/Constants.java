package cn.org.act.sdp.bpmnRepository.config;

/**
 * This class manage all constants used in this project.
 * 
 * @author <a href=��mailto:linwei@act.buaa.edu.cn��>Lin Wei</a>
 * @version $Revision: 1.5 $ $Date: 2010-05-13 03:19:10 $
 */
public class Constants {

	/**
	 * this constant refers to the saving operation
	 */
	public static final String SAVE_OPERATION = "saveOpr";

	/**
	 * this constant refers to the deleting operation
	 */
	public static final String DELETE_OPERATION = "deleteOpr";

	/**
	 * this constant refers to the getting operation
	 */
	public static final String GET_OPERATION = "getOpr";

	/**
	 * this constant refers to the getting-max-element operation
	 */
	public static final String GET_MAX_OPERATION = "getMaxOpr";

	/**
	 * this constant refers to the getting-min-element operation
	 */
	public static final String GET_MIN_OPERATION = "getMinOpr";

	/**
	 * this constant refers to the getting-by-id operation
	 */
	public static final String GET_BY_ID_OPERATION = "getByIdOpr";

	/**
	 * this constant refers to the getting-by-name operation
	 */
	public static final String GET_BY_NAME_OPERATION = "getByNameOpr";

	/**
	 * this constant refers to the getting-all operation
	 */
	public static final String GETALL_OPERATION = "getAllOpr";

	/**
	 * this constant refers to the getting operation which ignores the job id
	 */
	public static final String GET_IGNORE_JOB_OPERATION = "getIgnoreJobOpr";

	/**
	 * this constant refers to the getting-all operation which ignores the job
	 * id
	 */
	public static final String GETALL_IGNORE_JOB_OPERATION = "getAllIgnoreJobOpr";

	/**
	 * this constant refers to the deleting operation which ignores the job id
	 */
	public static final String DELETE_IGNORE_JOB_OPERATION = "deleteIgnoreJobOpr";

	/**
	 * this constant refers to the clearing operation
	 */
	public static final String CLEAR_OPERATION = "clearOpr";

	/**
	 * this constant refers to the memory table-creating operation
	 */
	public static final String CREATE_MEM_OPERATION = "createMemOpr";

	/**
	 * this constant refers to the file table-creating operation
	 */
	public static final String CREATE_FILE_OPERATION = "createFileOpr";

	/**
	 * this constant refers to the table-dropping operation
	 */
	public static final String DROP_OPERATION = "dropOpr";

	/**
	 * this constant refers to the table-dropping operation
	 */
	public static final String UPDATE_OPERATION = "updateOpr";

	/**
	 * this constant indicates the local path of the config-file of the
	 * connection pool
	 */
	public static final String LOCAL_POOL_CONFIG_PATH = "conf/pool-config-pr.xml";

	/**
	 * this constant indicates the local path of the config-file of the sql
	 * manager
	 */
	public static final String LOCAL_SQL_MANAGE_PATH = "conf/sql-statements-pr.xml";

	public static final String BPMN_TABLE = "bpmnTable";

	public static final String GRAPH_TABLE = "graphTable";

	public static final String Anotation_TABLE = "anotationTable";

	public static final String GET_BY_KEYWORD = "getbykey";

	public static final String SAVE_BY_FLEX = "savebyflex";

	public static final String SET_DERIVE = "setDrive";

	public static final String CORRELATIVITY = "correlativity";

	// add by nevor
	// TODO add operation and table
	public static final String DOMAIN_TABLE = "domainTable";
	public static final String MULTI_TABLE = "multiTable";
	public static final String TITLE_TABLE = "titleTable";
	public static final String META_TABLE = "metaTable";
	public static final String COMMON_TABLE = "commonTable";
	public static final String PETRI_TABLE = "petriTable";
	public static final String TAG_TABLE = "tagTable";
	public static final String RELATION_TABLE = "relationTable";
	public static final String USER_TABLE = "userTable";
	public static final String ANNOTATION_TABLE = "annotationTable";

	public static final String GET_BY_DOMAIN_ID_OPERATION = "getByDomainId";
	public static final String GET_LATEST_BY_TITLE_ID_OPERATION = "getLatestByTitleId";
	public static final String GET_VERSION_BY_TITLE_ID_OPERATION = "getVersionByTitleId";
	public static final String UPDATE_VERSIONS_OPERATION = "updateVersionsOpr";
	public static final String GET_BY_PROPERTY_OPERATION = "getByPropertyOpr";
	public static final String GET_CONTENT_OPERATION = "getContentOpr";
	public static final String UPDATE_CONTENT_OPERATION = "updateContentOpr";
	public static final String REMOVE_BY_PROPERTY_OPERATION = "removeByPropertyOpr";
	public static final String UPDATE_BY_PROPERTY_OPERATION = "updateByPropertyOpr";
	public static final String SEARCH_OPERATION = "searchOpr";
	public static final String GET_BY_TITLE_ID_OPERATION = "getByTitleIdOpr";
	public static final String REMOVE_BY_TWO_PROPERTY_OPERATION = "removeByTwoPropertyOpr";
	public static final String GET_ALL_VERSIONS_BY_TITLE_ID_OPERATION = "getAllVersionsByTitleIdOpr";
	public static final String GET_BY_TAG_ID_OPERATION = "getByTagIdOpr";
	public static final String GET_BPMN_ID_AND_ELEMENT_ID_OPERATION = "getByBpmnIdAndElementIdOpr";

}
