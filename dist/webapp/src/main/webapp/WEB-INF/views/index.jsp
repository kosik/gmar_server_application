<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#">
<head>
    <meta charset="utf-8">
    <meta name='yandex-verification' content='71d178e81f6a2294' />
    <meta name="msvalidate.01" content="54D327D4B2511BEDE6C18FD721D8C8C7" />
    <meta name="google-site-verification" content="QBs5Yfou7HWDkVej94lt1DiLVG6ZuCvOIQxt5NiXo-s" />
    <meta charset="utf-8" />
    <title><spring:message code="appTitle" /></title>

    <meta name="title" content="MULTI-TRACKING" />
    <meta name="keywords" content='<spring:message code="seoTags" />' />
    <meta name="description" content='<spring:message code="seoSummary" />' />
    
    <meta property="og:type" content="website" />
    <meta property="og:url" content="https://multitracking.info" />
    <meta property="og:site_name" content="multitracking.info" />
    <meta property="og:description" content='<spring:message code="seoSummary" />' />
    <meta property="og:image" content="https://multitracking.info/resources/img-res/common/logo_big.png" />

    <meta property="twitter:card" content="summary" />
    <meta property="twitter:url" content="http://multitracking.info" />
    <meta property="twitter:title" content='<spring:message code="seoTags" />' />
    <meta property="twitter:description" content='<spring:message code="seoSummary" />' />
    <meta property="twitter:site" content="multitracking.info" />
    <meta property="twitter:image" content="https://multitracking.info/resources/img-res/common/logo_big.png" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="resources/js/thirdparty/bootstrap/dist/css/bootstrap.css" media="screen" title="no title" charset="utf-8">
    <link rel="stylesheet/less" type="text/css" href="resources/css/master.less">
    <script src="resources/js/thirdparty/less.min.js" charset="utf-8"></script>

    <script src="https://yandex.st/dojo/1.9.1/dojo/dojo.js" 
        data-dojo-config="isDebug: false, async: true, parseOnLoad: true, tlmSiblingOfDojo: false, baseUrl: '../resources/js/'"></script>
    
    <script type="text/javascript" src="resources/js/common/commonsLib.js" ></script>
    <script type="text/javascript" src="resources/js/common/servConfigs.js"></script>
    <script type="text/javascript" src="resources/js/sosIndex.js" ></script>
        
    <%@ include file="fragments/metrics.jsp" %>
    
    <script type="text/javascript">
        var regBtn_id = "regBtn";
        var regEmail_id = "regEmail";
    </script>
  </head>
  <body>
    <header>
        <div class="col-md-2 sos-text">
          <h1><a href="index.html">BUTTON SOS</a></h1>
        </div>
        <div class="col-md-8 center-block">
          <ul>
            <li><a href="https://multitracking.info/content"><spring:message code="whatIsIt" /></a></li>
            <li><a href="https://multitracking.info/content"><spring:message code="particularities" /></a></li>
            <li><a href="https://multitracking.info/content"><spring:message code="analytics" /></a></li>
            <li><a href="#contactsEl"><spring:message code="contacts" /></a></li>
          </ul>
        </div>
        <div class="col-md-2 right-block">
          <!-- icons -->
          <div class="lang">
            <span><a href="https://twitter.com/odrive_info"><img src="resources/images/landing/twiter.png" alt="" width="25" height="25" /></a></span>
            <span><a href="https://www.facebook.com/infoodrive"><img src="resources/images/landing/facebook.png" alt="" width="25" height="25" /></a></span>
            <span><a href="https://vk.com/multitracking"><img src="resources/images/landing/vk.png" alt="" width="25" height="25" /></a></span>
<!--            <span class="ru active"><a href="?locale=en" target="_blank">RUS</a></span>-->
            <span class="ru"><a href="?locale=en" target="_blank">RUS</a></span>
            <!--<span class="eng "><a href="?locale=en" target="_blank">ENG</a></span>-->
          </span>
        </div>
    </header>

    <section class="main">
      <div class="col-md-2">

      </div>

      <div class="col-md-8">
        <h1><spring:message code="title1" /></h1>
        <p class="intro"><spring:message code="btnSosDescr" /></p>
        <div class="iphone">
          <img src="resources/images/landing/iphone.png" alt="" width="80%" height="80%"/>
        </div>
        <div class="apps">
          <a href="#" class="apple"><img src="resources/images/landing/appstore.png" alt=""/></a>
          <a href="https://play.google.com/store/apps/details?id=info.ml" class="android"><img src="resources/images/landing/google.png" alt="" /></a>
        </div>
      </div>

      <div class="col-md-2">

      </div>
    </section>

    <section class="scope">
      <div class="col-md-2">

      </div>

      <div class="col-md-8">
        <h1><spring:message code="posibilities" /></h1>
        <div class="jumbs">
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/people.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="communityFeedback" /></p>
              <p class="cont-txt"><spring:message code="communityFeedbackDescr" /></p>
            </div>
          </div>
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/gps.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="autoPositioning" /></p>
              <p class="cont-txt"><spring:message code="autoPositioningDescr" /></p>
            </div>
          </div>
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/mic.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="quickVoice" /></p>
              <p class="cont-txt"><spring:message code="quickVoiceDescr" /></p>
            </div>
          </div>
        </div><br/>
        <div class="jumbs jumbs-2">
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/disabled.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="handyChildren" /></p>
              <p class="cont-txt"><spring:message code="handyChildrenDescr" /></p>
            </div>
          </div>
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/message.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="dengerAlarm" /></p>
              <p class="cont-txt"><spring:message code="dengerAlarmDescr" /></p>
            </div>
          </div>
          <div class="col-md-4 jumb">
            <div class="rect" style="background-image: url('resources/images/landing/phone.png');">
            </div>
            <div class="txt">
              <p class="head-txt"><spring:message code="onlySmatphone" /></p>
              <p class="cont-txt"><spring:message code="onlySmatphoneDescr" /></p>
            </div>
          </div>
        </div>
      </div>

      <div class="col-md-2">

      </div>
    </section>

    <section class="subscribe">
      <h2><spring:message code="subscribeUs" /></h2>
      <h3><spring:message code="free" /></h3>
      <form class="" action="index.htm" method="post">
        <div class="input-group email-inp">
            <input class="form-control" type="regEmail" name="regEmail" id="regEmail" 
                value="" placeholder='<spring:message code="enterEmail" />' required>
        </div>
        <div class="input-group btn-inp">
          <input class="form-control btn btn-default" type="button" 
                 name="regBtn" id="regBtn" 
                 value='<spring:message code="sent" />'>
        </div>
      </form>
    </section>

    <!-- <section class="news">
      <h1>Статьи</h1>
      <div class="col-md-2">

      </div>
      <div class="col-md-8">
        <div class="col-md-4 new">
          <p class="date">12/02/16</p>
          <div class="social-inf">
            <span><span><img src="" alt="" /></span>15</span>
            <span><span><img src="" alt="" /></span>28</span>
          </div>
          <h4>Как выбрать трекер</h4>
          <span class="tags">#TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG </span>
          <p class="pre-content">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam. dafsds sdfa df asdf sdf asdf asdf asdf a/..</p>
        </div>
        <div class="col-md-4 new">
          <p class="date">12/02/16</p>
          <div class="social-inf">
            <span><span><img src="" alt="" /></span>15</span>
            <span><span><img src="" alt="" /></span>28</span>
          </div>
          <h4>Как выбрать трекер</h4>
          <span class="tags">#TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG </span>
          <p class="pre-content">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam. dafsds sdfa df asdf sdf asdf asdf asdf a/..</p>
        </div>
        <div class="col-md-4 new">
          <p class="date">12/02/16</p>
          <div class="social-inf">
            <span><span><img src="" alt="" /></span>15</span>
            <span><span><img src="" alt="" /></span>28</span>
          </div>
          <h4>Как выбрать трекер</h4>
          <span class="tags">#TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG #TAG </span>
          <p class="pre-content">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam. dafsds sdfa df asdf sdf asdf asdf asdf a/..</p>
        </div>
      </div>
      <div class="col-md-2">

      </div>
    </section> -->

    <footer>
      <div class="col-md-offset-2" id="contactsEl">
        <h1 style="margin-left: 12px;"><spring:message code="contacts" /></h1>
      </div>

      <div class="col-md-8 col-md-offset-2 conte" style="padding: 0px;">
        <div class="col-md-3">
          <span class="typ"><spring:message code="supportTeam" /></span>
          <span>support@multitracking.info</span>
        </div>
        <div class="col-md-3">
          <span class="typ"><spring:message code="otherQuestions" /></span>
          <span>team@multitracking.info</span>
        </div>
        <div class="col-md-2 soc">
          <span><a href="https://twitter.com/mlt_security"><img src="resources/images/landing/twiter.png" alt="" width="25" height="25" /></a></span>
          <span><a href="https://www.facebook.com/MultiTrackingINFO"><img src="resources/images/landing/facebook.png" alt="" width="25" height="25" /></a></span>
          <span><a href="https://vk.com/multitracking"><img src="resources/images/landing/vk.png" alt="" width="25" height="25" /></a></span>
        </div>
        <div class="col-md-4">
          <div class="apps">
            <a href="#" class="apple"><img src="resources/images/landing/appstore.png" alt=""/></a>
            <a href="https://play.google.com/store/apps/details?id=info.ml" class="android"><img src="resources/images/landing/google.png" alt="" /></a>
          </div>
        </div>
      </div>

    </footer>

    <div class="legal">
      Copyright (c) 2015 All Rights Reserved.
    </div>

  </body>
</html>
