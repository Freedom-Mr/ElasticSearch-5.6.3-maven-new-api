package casia.isiteam.api.elasticsearch.common.staitcParms;

import casia.isiteam.api.elasticsearch.util.JSONCompare;

/**
 * ClassName: ElasticResultParms
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/4/29
 * Email: zhiyou_wang@foxmail.com
 */
public class ElasticResultParms extends JSONCompare {
    protected final static String ACKNOWLEDGED = "acknowledged";
    protected final static String _BULK = "_bulk";
    protected final static String _SEARCH = "_search";
    protected final static String _MAPPING = "_mapping";
    protected final static String PRETTY = "pretty";
    protected final static String BAKING = "baking";
    protected final static String _ID = "_id";
    protected final static String _UPDATE = "_update";
    protected final static String _UPDATE_BY_QUERY = "_update_by_query";
    protected final static String INDEX = "index";
    protected final static String SHARD = "shard";
    protected final static String ALLOCATE = "allocate";
    protected final static String REMOVE = "remove";
    protected final static String ALIAS = "alias";
    protected final static String SOURCE = "source";
    protected final static String DEST = "dest";
    protected final static String DOC = "doc";
    protected final static String UPSERT = "upsert";
    protected final static String INLINE = "inline";
    protected final static String SCRIPT = "script";
    protected final static String _CAT = "_cat";
    protected final static String INDICES = "indices";
    protected final static String PROPERTIES = "properties";
    protected final static String RESULT = "result";
    protected final static String UPDATE = "update";
    protected final static String TRUE = "true";
    protected final static String NOOP = "noop";
    protected final static String _QUERY = "_query";
    protected final static String ID = "id";
    protected final static String MATCH = "match";
    protected final static String QUERY = "query";
    protected final static String FOUND = "found";
    protected final static String DELETED = "deleted";
    protected final static String UPDATED = "updated";
    protected final static String CREATED = "created";
    protected final static String BATCHES = "batches";
    protected final static String BATCHE = "batche";
    protected final static String ROLENAME = "roleName";
    protected final static String CREATE = "create";
    protected final static String DELETE = "delete";
    protected final static String NOT_FOUND = "not_found";
    protected final static String FROM = "from";
    protected final static String MASK = "mask";
    protected final static String SIZE = "size";
    protected final static String HITS = "hits";
    protected final static String TOTAL = "total";
    protected final static String _INDEX = "_index";
    protected final static String _TYPE = "_type";
    protected final static String _SOURCE = "_source";
    protected final static String SORT = "sort";
    protected final static String _COUNT = "_count";
    protected final static String COUNT = "count";
    protected final static String HIGHLIGHT = "highlight";
    protected final static String FRAGMENT_SIZE = "fragment_size";
    protected final static String NUMBER_OF_FRAGMENTS = "number_of_fragments";
    protected final static String FIELDS = "fields";
    protected final static String PRE_TAGS = "pre_tags";
    protected final static String POST_TAGS = "post_tags";
    protected final static String SCROLL = "scroll";
    protected final static String _SCROLL_ID = "_scroll_id";
    protected final static String SCROLL_ID = "scroll_id";
    protected final static String _ALL = "_all";
    protected final static String SUCCEEDED = "succeeded";
    protected final static String _SCORE = "_score";
    protected final static String MIN_SCORE = "min_score";
    protected final static String PROFILE = "profile";
    protected final static String _CLOSE = "_close";
    protected final static String _OPEN = "_open";
    protected final static String _CACHE = "_cache";
    protected final static String _CLEAR = "_clear";
    protected final static String CLEAR = "clear";
    protected final static String _SHARDS = "_shards";
    protected final static String _FLUSH = "_flush";
    protected final static String _REFRESH = "_refresh";
    protected final static String _REINDEX = "_reindex";
    protected final static String MUST = "must";
    protected final static String MUST_NOT = "must_not";
    protected final static String FILTER = "filter";
    protected final static String BOOL = "bool";
    protected final static String SHOULD = "should";
    protected final static String RANGE = "range";
    protected final static String RANGES = "ranges";
    protected final static String GTE = "gte";
    protected final static String LTE = "lte";
    protected final static String EXISTS = "exists";
    protected final static String FIELD = "field";
    protected final static String MINIMUM_SHOULD_MATCH = "minimum_should_match";
    protected final static String AGGS = "aggs";
    protected final static String PRECISION_THRESHOLD = "precision_threshold";
    protected final static String AGGREGATIONS = "aggregations";
    protected final static String BUCKETS = "buckets";
    protected final static String VALUE = "value";
    protected final static String DOC_COUNT = "doc_count";
    protected final static String SHARD_SIZE = "shard_size";
    protected final static String MIN_DOC_COUNT = "min_doc_count";
    protected final static String ORDER = "order";
    protected final static String FORMAT = "format";
    protected final static String INTERVAL = "interval";
    protected final static String MISSING = "missing";
    protected final static String EXCLUDES = "excludes";
    protected final static String INCLUDES = "includes";
    protected final static String DATA = "data";
    protected final static String KEY = "key";
    protected final static String KEY_AS_STRING = "key_as_string";
    protected final static String WRAP_LONGITUDE = "wrap_longitude";
    protected final static String TOP_LEFT = "top_left";
    protected final static String BOTTOM_RIGHT = "bottom_right";
    protected final static String LOCATION = "location";
    protected final static String BOUNDS = "bounds";
    protected final static String LON = "lon";
    protected final static String LAT = "lat";
    protected final static String POINTS = "points";
    protected final static String DISTANCE = "distance";
    protected final static String TO = "to";
    protected final static String _DELETE_BY_QUERY = "_delete_by_query";
    protected final static String _SQL = "_sql";
    protected final static String SQL = "sql";
    protected final static String ERRORS = "errors";
    protected final static String ERROR = "error";
    protected final static String KEYED = "keyed";
    protected final static String IP_RANGE = "ip_range";
    protected final static String PRECISION = "precision";
    protected final static String EXTENDED_BOUNDS = "extended_bounds";
    protected final static String MIN = "min";
    protected final static String MAX = "max";
    protected final static String FILTERS = "filters";
    protected final static String OTHER_BUCKET_KEY = "other_bucket_key";
    protected final static String OTHER_DOC = "other_doc";
    protected final static String KEYWORDDOCTOTAL = "KeyWordDocTotal";
    protected final static String MATRIX_STATS = "matrix_stats";
    protected final static String MATRIXINFO = "matrixInfo";
    protected final static String INCOME = "income";
    protected final static String NAME = "name";
    protected final static String MEAN = "mean";
    protected final static String VARIANCE = "variance";
    protected final static String SKEWNESS = "skewness";
    protected final static String COVARIANCE = "covariance";
    protected final static String CORRELATION = "correlation";
    protected final static String TIMED_OUT = "timed_out";
    protected final static String _ALIASES = "_aliases";
    protected final static String _ALIAS = "_alias";
    protected final static String ALIASES = "aliases";
    protected final static String ADD = "add";
    protected final static String ACTIONS = "actions";
    protected final static String _CLUSTER = "_cluster";
    protected final static String REROUTE = "reroute";
    protected final static String FROM_NODE = "from_node";
    protected final static String TO_NODE = "to_node";
    protected final static String MOVE = "move";
    protected final static String COMMANDS = "commands";
    protected final static String _SETTINGS = "_settings";
    protected final static String NUMBER_OF_REPLICAS = "number_of_replicas";
    protected final static String NODE = "node";
    protected final static String CANCEL = "cancel";
    protected final static String _XPACK = "_xpack";
    protected final static String SECURITY = "security";
    protected final static String _AUTHENTICATE = "_authenticate";
    protected final static String USER = "user";
    protected final static String ROLE = "role";
    protected final static String ELASTIC = "elastic";
    protected final static String _PASSWORD = "_password";
    protected final static String PASSWORD = "password";
    protected final static String EMAIL = "email";
    protected final static String ENABLED = "enabled";
    protected final static String FULL_NAME = "full_name";
    protected final static String METADATA = "metadata";
    protected final static String TRANSIENT_METADATA = "transient_metadata";
    protected final static String ROLES = "roles";
    protected final static String _ENABLE = "_enable";
    protected final static String _DISABLE = "_disable";
    protected final static String SETTINGS = "settings";
    protected final static String TRANSIENT = "transient";
    protected final static String _NONE = "none";
    protected final static String OPEN = "open";
    protected final static String ALLOCATION_ENABLE = "cluster.routing.allocation.enable";
    protected final static String REBALANCE_ENABLE = "cluster.routing.rebalance.enable";
    protected final static String NODE_LEFT_DELAYED_TIMEOUT = "index.unassigned.node_left.delayed_timeout";
    protected final static String ALL = "all";
    protected final static String CLUSTER = "cluster";
    protected final static String RUN_AS = "run_as";

    //String
}
