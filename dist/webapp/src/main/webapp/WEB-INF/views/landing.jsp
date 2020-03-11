<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#">
<head>
    <meta name='yandex-verification' content='71d178e81f6a2294' />
    <meta name="msvalidate.01" content="54D327D4B2511BEDE6C18FD721D8C8C7" />
    <meta name="google-site-verification" content="QBs5Yfou7HWDkVej94lt1DiLVG6ZuCvOIQxt5NiXo-s" />
    <meta charset="utf-8" />
    <title><spring:message code="appTitle" /></title>

    <meta name="title" content="MULTI-TRACKING" />
    <meta name="keywords" content='<spring:message code="seoTags" />' />
    <meta name="description" content='<spring:message code="seoSummary" />' />
    
    <meta property="og:type" content="website" />
    <meta property="og:url" content="http://multitracking.info" />
    <meta property="og:site_name" content="multitracking.info" />
    <meta property="og:description" content='<spring:message code="seoSummary" />' />
    <meta property="og:image" content="https://multitracking.info/resources/img-res/common/logo_big.png" />

    <meta property="twitter:card" content="summary" />
    <meta property="twitter:url" content="http://multitracking.info" />
    <meta property="twitter:title" content='<spring:message code="seoTags" />' />
    <meta property="twitter:description" content='<spring:message code="seoSummary" />' />
    <meta property="twitter:site" content="multitracking.info" />
    <meta property="twitter:image" content="https://multitracking.info/resources/img-res/common/logo_big.png" />
    
    <link rel="stylesheet" href="resources/index.css" type="text/css" 
          media="(min-width: 960px)"/>
    <link rel="stylesheet" href="resources/index_small.css" type="text/css" 
          media="(max-width: 960px)" />

    <link rel="stylesheet" href="resources/css/common/headerFooterSmall.css" type="text/css" 
        media="(max-width: 960px)" />
    <link rel="stylesheet" href="resources/css/common/headerFooter.css" type="text/css" 
        media="(min-width: 960px)" />    

    <link rel="stylesheet" href="resources/css/common/fonts.css" type="text/css" />
    <link rel="stylesheet" href="resources/css/common/common.css" type="text/css" />
    <link rel="stylesheet" href="resources/css/common/shapes.css" type="text/css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" />
    
    <script src="https://yandex.st/dojo/1.9.1/dojo/dojo.js" 
    data-dojo-config="isDebug: false, async: true, parseOnLoad: true, tlmSiblingOfDojo: false, baseUrl: '../resources/js/'"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/dojo/1.9.1/dijit/themes/tundra/tundra.css" />

    <script type="text/javascript" src="resources/js/common/servConfigs.js" ></script>
    <script type="text/javascript" src="resources/js/common/commonsLib.js" ></script>
    <script type="text/javascript" src="resources/js/landing.js" ></script>
    <script type="text/javascript" src="resources/js/common/header.js" ></script>
    
    <script src="https://connect.facebook.com/en_US/sdk.js"></script>
    <script type="text/javascript" src="resources/js/social/FBconfigs.js" ></script>
    <script type="text/javascript" src="resources/js/social/facebook.js" ></script>

    <script type="text/javascript" src="resources/js/social/google.js" ></script>
    <script type="text/javascript" src="resources/js/social/googleConf.js" ></script>
    
    <%@ include file="fragments/metrics.jsp" %>
    
    <script type="text/javascript">
        var registrationFields_id = "registrationFields";
        var errorMessages_id = "errorMessages";
        var registerName_id = "registerName";
        var registerFamily_id = "registerFamily";
        var email_id = "email";
        var gender_id = "gender";
        var registerPassword_id = "registerPassword";
        var registerBtn_id = "registerBtn";
        var registerBornDate_id = "registerBornDate";
        var facebookRegister_id = "facebookRegister";
        var licenseAgreement_id = "licenseAgreement";
        var SRV_SLIDE1 = "slide1", SRV_SLIDE2 = "slide2", SRV_SLIDE3 = "slide3", 
                SRV_SLIDE4 = "slide4", SRV_SLIDE5 = "slide5";
        var mobileBtn_id = "mobileBtn", rootsBtn_id = "rootsBtn", 
                foodBtn_id = "foodBtn", communityBtn_id = "communityBtn", 
                securityBtn_id = "securityBtn";
        var logistic_id = "logistic", sport_id = "sport", home_id = "home", 
                food_id = "food", secure_id = "secure";
    
        var MALE_LABEL = '<spring:message code="genderMale" />';
        var SURNAME_REQUIRED_LABEL = '<spring:message code="infomessage1" />';
        var PASSWORD_INCORRECT_LABEL = '<spring:message code="infomessage2" />';
        var BORN_DATE_REQUIRED_LABEL = '<spring:message code="infomessage4" />';
        var LICENSE_AGREEMENT_NOTE_LABEL = '<spring:message code="licenseAgreementNote" />';
        var BIRTHDAY_VALIDATION_LABEL = '<spring:message code="birthdayValidationMsg" />';
        var LANDING_SLOGAN_LABEL1 = '<spring:message code="landingSlogan1" />';
        var LANDING_SLOGAN_LABEL2 = '<spring:message code="landingSlogan2" />';
        var slideAnimation_id = "slideAnimation";
        var landingSlogan1_id = "landingSlogan1";
        var landingSlogan2_id = "landingSlogan2";
        
    </script>
</head>

<body class="tundra">
    <%@ include file="fragments/landing/header.jsp" %>
    <%@ include file="fragments/landing/loginPopup.jsp" %> <!--due to relative path we to images we did the copy-->
    <%@ include file="fragments/landing/centerContent.jsp" %>
    <%@ include file="fragments/community/communityFooter.jsp" %>
</body>
            
</html>