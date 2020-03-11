var emptyCardsWgt = null;
var upPostTags = new Array();
var contentUploader = null;
var microdata = ' itemscope itemtype="http://schema.org/ImageObject"';
var postId = "postId";//the parameter to update particualr article by IDs
var fit_service_products_price = [7, 5, 2];

function addArticleImageHandler(up, file, info) {
    var data = JSON.parse(info.response);

    if (data.success == true) {
        var url = servConfigs.getHost() + servConfigs.getContentSuff()
                + globalUserProfileData.model.user.id + "/"
                + data.model.fileDescriptor.fileName
                + ".jpg";

        var image = '<br/><br/><span' + microdata + ' ><img src="' + url + '" itemprop="contentUrl" alt="#" style="width:95%;"/></span><br/><br/>';

        var msg = document.getElementById(upContentMsg_id);
        msg.value = msg.value + image;

    } else {
        alert(FILE_WAS_NOT_UPLOADED_LABEL);
    }
}

require(["dojo/ready", "dojo/parser", "dojox/grid/DataGrid", "dojo/on", "dojo/dom",
    "dojo/keys", "dijit/registry", "widgets/CreditCard", "widgets/EmptyBlock"],
        function(ready, parser, DataGrid, on, dom, keys, registry, CreditCard, EmptyBlock) {

            function getActiveNotExpiredServices() {
                sendJsonPost(servConfigs.getActiveProducts(), null, function(jsonResult) {
                    if (jsonResult.success == true) {
                        if (jsonResult.model.activeProducts.length > 0) {
                            var fitLogger = getActiveFitLogger(jsonResult);
                            if (null != fitLogger) {
                                var expDate = new Date(fitLogger.serviceExpirationDate);
                                showCountDownComponent(expDate, fitProductExpirationCountdown_id);
                                applyStyle([fitCounterRoot_id], {display: "inline-block"});
                            }
                        }
                        if (jsonResult.model.freeFitLoggerStatus.length == 0) {
                            if (-1 == jsonResult.model.freeFitLoggerStatus.indexOf(2)) {
                                applyStyle([freeTrialFitLogger_id, buy4_id], {display: "inline-block"});
                            }
                        }
                    }
                });
            }//getActiveNotExpiredServices

            function getActiveFitLogger(jsonResult) {
                var item = 0;
                while (item < jsonResult.model.activeProducts.length) {
                    var activeProduct = jsonResult.model.activeProducts[item++];
                    var productId = activeProduct.boughtProduct.id;
                    if (productId == 1 || productId == 2 || productId == 3 || productId == 4) {
                        return activeProduct;
                    }
                }
                return null;
            }

            showCountDownComponent = function showCountDownComponent(expDate, targetId) {
                new Countdown({
                    rangeHi: "day",
                    year: expDate.getFullYear(),
                    month: expDate.getMonth() + 1,
                    day: expDate.getUTCDate(),
                    width: 230,
                    height: 50,
                    target: targetId
                });
            }//showCountDownComponent
            

    function initHandlers() {
        initTooltip([buy_btn_prefix_id + "1"], 
            CHANE_CURRENCY_TOOLTIP); 

        var buyBtn4 = document.getElementById("freeTrialFitLogger");

        on(document.getElementById(freeTrialFitLogger_id), "click", function() {
            orderProductWithPP(2);
        });
        on(buyBtn4, "click", function() {
            orderProductWithPP(2);
        });
        
    }//

    function initContent() {
        var tags = document.getElementById(upContentTags_id);
        if(typeof tags !="undefined" && tags != null && tags.value != ""){
            //NOTE: in case we do edit some post
            addTag(upPostTags, upContentTags_id, upContentTagsVisualisation_id);
        }
        on(tags, "keyup", function(event) {
            switch (event.keyCode) {
                case keys.SPACE:
                    event.preventDefault();
                    addTag(upPostTags, upContentTags_id,
                        upContentTagsVisualisation_id);
                    break;
                case keys.ENTER:
                    event.preventDefault();
                    addTag(upPostTags, upContentTags_id,
                            upContentTagsVisualisation_id);
                    break;
            }
        });

        on(document.getElementById(upPostContentBtn_id), "click", function() {
            var title = document.getElementById(upContentTitle_id).value;
            if (null == title || "" == title) {
                addErrorMsg(contentErrorMessages_id, TITLE_REQUIRED_LABEL, "last");
                return;
            }
            var headline = document.getElementById(headline_id).value;
            if (null == headline || "" == headline) {
                addErrorMsg(headline_id, TITLE_REQUIRED_LABEL, "last");
                return;
            }
//            var seoTitle = document.getElementById(seo_title_id).value;
//            if (null == seoTitle || "" == seoTitle) {
//                addErrorMsg(seo_title_id, TITLE_REQUIRED_LABEL, "last");
//                return;
//            }
//            var contentItem = new Object();
            
            var contentId = gup(postId);
            addWildPost(content_id, contentErrorMessages_id, upContentTitle_id,
                    upContentMsg_id, upPostTags, upContentTagsVisualisation_id, 
                    undefined, langCode_id, contentId);
        });

        var url = servConfigs.getHost() + servConfigs.getSecurePref() + servConfigs.getDataUploadUrl();
        contentUploader = buildUploader(upAddImage_id, url,
            [{title: "JPG", extensions: "jpg"},
                {title: "PNG", extensions: "png"},
                {title: "GIF", extensions: "gif"},
                {title: "BMP", extensions: "bmp"},
                {title: "MP3", extensions: "mp3"},
                {title: "PDF", extensions: "pdf"}
            ], addArticleImageHandler);

        contentUploader.init();
    }//initContent

// #############################################################################
    ready(function() {
        toggleClass(userProfilePage_id, grayscale_class);
        initHandlers();
//      getCreditCards();
        getActiveNotExpiredServices();
        initContent();
    });//ready

});//require