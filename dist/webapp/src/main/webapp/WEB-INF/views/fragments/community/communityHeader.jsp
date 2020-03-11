<link rel="stylesheet" href="../resources/css/common/dropdown.css" type="text/css" />

<script type="text/javascript">
    var contentPopup_id = "contentPopup";
    var addNoteBtn_id = "addNoteBtn";
    var contentTags_id = "contentTags";
    var contentMsg_id = "contentMsg";
    var contentTitle_id = "contentTitle";
    var contentTagsVisualisation_id = "contentTagsVisualisation";
    var postContentBtn_id = "postContentBtn";
    var statCoverage_id = "statCoverage";
    var radioStartDate_id = "radioStartDate";
    var coverRadioPeriods_id = "coverRadioPeriods";
    
    var TOO_LITTLE_TAGS_LABEL = '<spring:message code="postTagsValidation1" />';
    var TOO_MUCH_TAGS_LABEL = '<spring:message code="postTagsValidation2" />';
    var TOO_BIG_POST_LABEL = '<spring:message code="postTagsValidation3" />';
    var TOO_SMALL_POST_BODY_LABEL = '<spring:message code="postLimitation1" />';
</script>

<div id="signInHeader">
    <div class="leftFloat">
        <a href="https://multitracking.info">
            <img src="../resources/img-res/landing/logo.png" 
                 title="<spring:message code='squirel' />" 
                 alt="<spring:message code='squirel' />" 
                 style="z-index:300; position: relative;" />
        </a>
    </div>
    
<!--    <img class="leftFloat earthImgAsset" src="../resources/img-res/ico/flags/earth.png" 
         alt="<spring:message code='locale' />"/>-->
    
    <div id="addNoteBtn" class="leftFloat">+</div>
        
    <div class="languageAssets droplink">
        <div class="langText"><a href="#" ><spring:message code="language" />&nbsp;
            <i class="fa fa-sort-desc"></i></a>
        </div>
        <ul>
            <li class="eng"><a href="?locale=en" target="_blank" class="lang">English</a></li>
            <li class="ru"><a href="?locale=ru" target="_blank" class="lang">Russian</a></li>
            <li class="de"><a href="?locale=de" target="_blank" class="lang">German</a></li>
<!--            <li class="it"><a href="?locale=it" target="_blank" class="lang">Italian</a></li>
            <li class="by"><a href="?locale=by" target="_blank" class="lang">Belarus</a></li>
            <li class="ua"><a href="?locale=ua" target="_blank" class="lang">Ukraine</a></li>-->
        </ul>
    </div>

<!--    <div class="leftFloat">
        <ul>
            <li>
                <input type="text" id="geotag" placeholder="${geotag}" />
                <ul>
                    <li>We</li>
                    <li>do</li>
                    <li>better</li>
                </ul>
            </li>
        </ul>
    </div>-->
    
    <img id="smallUserLogoIMG" width="30" height="30" class="rightFloat" src="#" alt="<spring:message code='odriveLogo' />"/>
    <div id="smallUserName" class="rightFloat landingHeaderText"></div>

    <div id="coverRadioPeriods" class="rightFloat">
        <input type="radio" class="inline-block statCoverageSelectMarker" name="statCoverage" id="radioDay" value="0" /> 
        <label for="radioDay" class="labelStyle_13 inline-block"><spring:message code="day" /></label>

        <input type="radio" class="inline-block statCoverageSelectMarker" name="statCoverage" id="radioWeek" value="1"/> 
        <label for="radioWeek" class="labelStyle_13 inline-block"><spring:message code="week" /></label>

        <input type="radio" class="inline-block statCoverageSelectMarker" name="statCoverage" id="radioMonth" value="2" checked /> 
        <label for="radioMonth" class="labelStyle_13 inline-block"><spring:message code="month" /></label><br/>

        <input id="radioStartDate" data-dojo-type="dijit/form/DateTextBox" 
            class="labelStyle_13 rightFloat radioStartDateAssets" />  
    </div>
    <!--at the moment the element is to show only on FIT page-->
    <div id="recomendedCaloriesConsumption" class="totalBlockAssets rightFloat" style="display:none;">
        <div class="headerTextBold_0_7 rightFloat" ><spring:message code="recomendedCalConsumption" /></div>
        <div id="recomendedEatCaloriesAmount" class="totalCaloriesValue headerTextBold_21 rightFloat" ></div>
    </div>

    <a id="loginPopupBtn" class="rightFloat landingHeaderTextAssets"><spring:message code="login" /></a>
    
    <div class="rightFloat landingMenuAssets">
        <a id="home" class="communityHeaderTextAssets"></a>
        <a id="sport" class="communityHeaderTextAssets"></a>
        <a id="logistic" class="communityHeaderTextAssets"></a>
        <a id="food" class="communityHeaderTextAssets"></a>
        <a id="secure" class="communityHeaderTextAssets"></a>
    </div>
    
</div>
<div style="height:7%;"></div>
<!--<div style="margin-bottom:4.6%;"></div>-->
