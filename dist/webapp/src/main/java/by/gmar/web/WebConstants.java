package by.gmar.web;

/**
 *
 * @author s.kosik
 */
public final class WebConstants {
    final public static String ENERGY_URL = "energy";
    final public static String GET_PHONE_VERIFICATION_PIN = "getPhoneVerificationPin";
    final public static String VERIFY_PHONE_PIN = "verifyPin";
    final public static String WEB_PIN_CODE = "code";
    
//  social
    final public static String WEB_MESSAGE = "sendMessage";
    final public static String WEB_FRIEND = "friend";
    final public static String VK_LOGIN = "vk";
    final public static String TWITTER_LOGIN = "twitter";

    final public static String GET_PRODUCTS = "products";
    final public static String ADD_CREDIT_CARD = "addCreditCard";
    final public static String GET_CREDIT_CARDS = "getCreditCards";
    final public static String ORDER_PRODUCT_URL = "orderProduct";
//    final public static String YA_CHECK_URL = "yaCheckOrder";
//    final public static String YA_AVISO_URL = "yaAviso";
    final public static String YA_CHECK_URL = "yaAvisoURL";
    final public static String YA_AVISO_URL = "yaCheckURL";

    final public static String USER_ACTIVE_SERVICES_URL = "activeProducts";

//    COMMON
    final public static String SLASH = "/";
    final public static String RESULT = "result";
    final public static String REDIRECT = "redirect_url";
    final public static String SECURE = "secure/";
    final public static String CONTENT_DUPLICTE = "content";
    final public static String CONTENT = "content/";
    final public static String CONTENT_POST = "post/";
    final public static String STATUS = "status";//404 500 OTHER HTTP REQ
    final public static String PP_STATUS = "ppStatus";

    final public static String DATA_UPLOAD_URL = "dataUpload";
    final public static String DATA_UPLOAD_SPRING_URL = "dataUpload2";
    final public static String SET_IMAGE_AS_AVATAR = "setAvatarImage";
    final public static String ATTACH_SOS_FILE = "attachSOSFile";
    final public static String ATTACH_FILE_TO_CONTENT_ITEM = "attachContentItemFile";
    final public static String ATTACH_PRESENTATION_TO_CONTENT_ITEM = "attachPresentation";

    final public static String ACCESS_DENIED = "accsessDenied";

//  ENTER
    final public static String SIGNIN_REGISTER_URL = "/register";
//    final public static String LOGIN_URL = "/login";
    final public static String LOGIN_URL2 = "/login2";//we front of some strange CORS issue and /login endpoint did not respond
    final public static String AUTOLOGIN_URL = "/autologin";
    final public static String AUTOREGISTRATION_URL = "/autoregistration";

    final public static String GET_FACEBOOK_PERMISSIONS = "facebookPermission";
    final public static String GET_GOOGLE_PERMISSIONS = "googlePermission";
    final public static String SIGNIN_INTERNAL_REDIRECT_PAGE_URL = "signin/signin";
    final public static String SIGNIN_PAGE_URL = "signin";
    final public static String TWITTER_CALLBACK_URL = "twitterCallback";

//    PROFILE_PAGE
    final public static String PERSONAL_SETTINGS_PAGE_LAYOUT = "profile";
    final public static String GET_USER_INFO_API = "getUserInfo";
    final public static String UPDATE_USER_INFO_API = "updateUserInfo";

    final public static String GET_LASTREGISTERED_GEOLOCATION_API = "getLastRegistredGeoLocation";
    final public static String PUT_GEOLOCATION_API = "trackLocation";
    final public static String SYNCH_LOCATION_API = "synchLocations";

    final public static String GET_MONTH_TIME_CAT_STAT_API = "getMonthTimeDistSpeed";
    final public static String GET_WEEK_TIME_CAT_STAT_API = "getWeekTimeDistSpeed";
    final public static String GET_DAY_TIME_CAT_STAT_API = "getDayTimeDistSpeed";

    final public static String GET_DAY_USER_CHALLENGES_API = "getDayChallenges";
    final public static String GET_WEEK_USER_CHALLENGES_API = "getWeekChallenges";
    final public static String GET_MONTH_USER_CHALLENGES_API = "getMonthChallenges";

    final public static String GET_USER_GEOLOCATIONS = "getUserLocations";
    final public static String GET_USER_MONTH_GEOLOCATIONS = "getMonthLocations";
    final public static String GET_USER_WEEK_GEOLOCATIONS = "getWeekLocations";
    final public static String GET_USER_DAY_GEOLOCATIONS = "getDayLocations";

//    NAVIGATOR_PAGE
    final public static String NAVIGATOR_PAGE_URL = "navigator";
    final public static String NEW_TRIP_PAGE_URL = "newTrip";

    final public static String MONITOR = "monitor";
    final public static String COMMUNITY_PAGE_URL = "community";
    final public static String CREATE_TEAM_URL = "createTeam";
    final public static String ADD_SECURITY_PAL_URL = "addSecurityPal";
    final public static String INVITE_CONTACT = "contactInvitation";
    final public static String PUT_INVITE_FRIEND = "inviteFriend";//degrecated
        
    final public static String GET_USER_TEAMS = "getUserTeams";
    final public static String GET_TEAM_MEMBERS = "getTeamMembers";

    final public static String FIT_PAGE = "fit";

    final public static String PUT_USER_VITAL_INFO_API = "addUserVitalInfo";
    final public static String GET_LATEST_USER_VITAL_INFO = "latestUserVitalInfo";
    final public static String GET_CHALLENGE_TYPES = "getChallenges";
    final public static String PUT_CHALLENGES = "addChallenge";
    final public static String GET_USER_BMI = "getUserBMI";
    final public static String GET_SEARCH_USER_CONTACTS = "searchUserContacts";
    final public static String GET_THREADS = "threads";

    final public static String FLOW = "flow";
    final public static String GET_POST_BY_ID = "getContent";
    final public static String ADD_CONTENT = "addContent";
    final public static String LOAD_CONTENT = "loadContent";
    final public static String LOAD_RANDOM_CONTENT = "loadRandomContent";
    final public static String GET_POST = "post";
    final public static String ADD_COMMENT = "addComment";
    final public static String GET_COMMENTS = "loadComments";

    final public static String SOS = "sos";
    final public static String LATEST_ALARMS = "latestAlarms";

    final public static String TATOEBA_IMPORT = "/tatoebaImport";
    final public static String WORD_PHRASE_MATCHES = "/getWordLinguisticUsage";

}
