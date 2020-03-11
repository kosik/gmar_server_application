package by.gmar.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author s.kosik
 */
public interface ODKeys {

    final public static String CLICKATELL_USER = "clickatell.user"; 
    final public static String CLICKATELL_PASS = "clickatell.pass";
    final public static String CLICKATELL_KEY = "clickatell.rest.client";
    
    final public String SOS = "sos";
    final public String USER_TEAMS = "userTeams";
    final public String TEAM_MEMBERS = "members";
    
    final public String YANDEX_CHECKOUT_FORM_URL = "yandex.checkout.form.url";
    final public String PROVIDE_CREDIT_CARD_MSG = "provideCreditCard";
    
    final public String VITAL_INF_REQUIRE_KEY = "vitalInfoRequired";
    final public String TOO_BIG_FILE_KEY = "tooBigFile";
    final public String WRONG_INCOME_DATA_KEY = "wrongIncomeData";
    final public String INVALID_DATA_MSG = "unsupportIncomeData";
    
    //MESSAGE KEYS END
    final public String GOOGLE_PUBLIC_API_KEY = "google.public.api_key";
    final public String GOOGLE_MAPS_SRV_URL_KEY = "google.maps.service_url";
    final public int DEFAULT_MESSAGE_AMOUNT = 10; 
    final public int MAX_ABOUT_LENGTH = 2000;
    final public String EMAIL_NULL_KEY = "emailNull";
    final public String ID = "id";
    final static String WEB_PERSONAL_PROFILE = "socialInfo";
    
    final static String PAGE_SIZE = "page.size";
    final static String IMPORT_IN_PROGRESS_MSG = "importInProgress";
    final static String PRODUCT_IS_ABSENT_MSG = "productIsAbsent";    
    final static String THIRD_PARTY_SERVICE_ERROR_MSG = "thirdPartyServiceError";
    final static String FEATURE_LIMITATION_MSG = "featureLimitation";
    final static Date STUB_BORN_DATE = new Date(-2205253194000l);//1900-02-13 07:42:10 DO NOT CHANGE IT
    final static String DEF_TIME_ZONE = "CET";
    final static String DEF_LANG_TAG = "default.language.tag";
    final static String BASE_CURRENCY_KEY = "base.currency";
    final static String MALE = "male";
    final static int PASSWORD_LENGTH = 9;
    
    final static String ACCESS_DENIED = "accessDenied";
    
    final static String FCM_JSON_CONF = "google.fcm.json.conf";
    final static String FCM_REALTIME_DATABASE_URL = "google.fcm.realtime.database.url";
    final static String FCM_KEY = "google.fcm.key";
    final static String FCM_DOWNSTREAM_URL = "google.fcm.downstream.url";
            
    final static String TWITTER_API_KEY = "twitter.api.key";
    final static String TWITTER_API_SECRET = "twitter.api.secret";
    final static String TWITTER_ACCESS_TOKEN = "twitter.access.token";
    final static String TWITTER_TOKEN_SECRET = "twitter.token.secret";

}