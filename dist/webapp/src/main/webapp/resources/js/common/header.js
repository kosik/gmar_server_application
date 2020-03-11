var qaContentType = "QA";
var postTags = new Array();
var MAX_POST_BODY = 20000;
var MAX_POST_TITLE = 255;
var MIN_POST_BODY = 50;

var loginPopup  = null;
var contentPopup = null;

require(["dojo/ready", "dojo/on", "dijit/registry", "dojo/dom", 
    "dojo/cookie", "dojo/keys"], 
function(ready, on, registry, dom, cookie, keys) {

    validateContent = function(contentItem, postTags, iPostContentBtn){
        if(postTags.length < 1){
            addErrorMsg(iPostContentBtn, TOO_LITTLE_TAGS_LABEL, "after");
            return;
        }

        if(Object.prototype.hasOwnProperty.call(contentItem, _HEADLINE)){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
        
        var heading = contentItem.headline.value.trim();
        if(heading.length < MIN_POST_BODY){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
        
        if(Object.prototype.hasOwnProperty.call(contentItem, "content")){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
        
        var postContent = contentItem.content.value.trim();
        if(postContent.length < MIN_POST_BODY){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
 
    }

    addWildPost = function addWildPost(iContentPopup, iPostContentBtn, iContentTitle,
        iContentMsg, postTags, tagsGUI_id, contentType, iLangCode, postId){
        clearFormError(iContentPopup);
        
        if(postTags.length < 1){
            addErrorMsg(iPostContentBtn, TOO_LITTLE_TAGS_LABEL, "after");
            return;
        }
        var tags = new Array();
        postTags.forEach(function(element, index, array){
            tags.push(document.getElementById(element).title);
        });
        
        var title = document.getElementById(iContentTitle).value.trim();
        if(title.length > MAX_POST_TITLE){
            title = title.substr(MAX_POST_TITLE);
        }
        
        var heading = document.getElementById(headline_id).value.trim();
        if(heading.length < MIN_POST_BODY){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
        
        var postContent = document.getElementById(iContentMsg).value.trim();
        if(postContent.length < MIN_POST_BODY){
            addErrorMsg(iPostContentBtn, TOO_SMALL_POST_BODY_LABEL, "after");
            return;
        }
        
        var seoTitle = document.getElementById(seo_title_id).value.trim();
        
        var selectedVal = document.getElementById(typeId);
        var type = selectedVal.options[selectedVal.selectedIndex].value;
        
        var data = {
            title: title,
            seoTitle: seoTitle,
            content:postContent,
            headline: heading,
            contentItemType: type,
            tags: JSON.stringify(tags)
        };
        
       if(undefined != postId || null != postId){
           data.contentId = postId;
       }
        
        if(undefined != contentType || null != contentType){
            data.type = contentType;
        }

        if(undefined != iLangCode){
            var lang = document.getElementById(iLangCode);
            var langCode = lang.options[lang.selectedIndex].value;
            data.lang = langCode;
        }
        
        var url = servConfigs.getHost() + servConfigs.getSecurePref()
                + servConfigs.getAddContentUrl();
        
        sendJsonPost(url, data, function(jsonResult) {
            if (jsonResult.success == false){
                return;
            } else {
                document.getElementById(iContentTitle).value = "";
                document.getElementById(iContentMsg).value = "";
                document.getElementById(tagsGUI_id).innerHTML = "";
                var i = 0;
                while(i < postTags.length){
                    var id = tags[i++];
                    destroyNode(id);
                    var ind = postTags.indexOf(id);
                    postTags.splice(ind, 1);
                }
                if(undefined != contentPopup){
                    contentPopup.close();
                }
            }
        }, null);        
    }//addWilldPost

    /**
     * 
     * @param {type} iPostTags JS Array to keep tags
     * @param {type} iContentTags GUI field of user interaction
     * @returns {undefined}
     */
    addTag = function addTag(iPostTags, iContentTags, tagsGUI_id){
        var tag = document.getElementById(iContentTags).value;
        tag = tag.trim();
        if(tag.length < 2){
            //TODO add constraint message
            return;
        }
        tag = tag.toLowerCase();
        document.getElementById(iContentTags).value = "";
        
        var tagsArray = tag.split(' ').filter(function(item) {
            return item.indexOf('.') < 0;
        });//collectiong words separeted by space into array
        //NOTE we need it only in case arcticle editiong. In all other cases 
        //here should come single word
        
        var aLen = tagsArray.length;
        for (var i = 0; i < aLen; i++) {
            if (iPostTags.length > 2) {//max amount of tags per article
                return;
            }

            var tagId = "contentTag-" + generatepass(4);
            iPostTags.push(tagId);
            tag = tagsArray[i];
            if(tag.length < 2){
                //small tags are not allowed
                //but catch here some small tag is would be strange
                //cause of it came from Server side during post edit
                continue;
            }
            
            var tagString = "<li id='" + tagId
                    + "' title='" + tag + "'><a href='#'>" + tag + "</a></li>";

            addDom(tagString, tagsGUI_id, "last");

            on(dom.byId(tagId), "click", function(tagId, tag) {
                destroyNode(this.id);
                var ind = iPostTags.indexOf(this.id);
                iPostTags.splice(ind, 1);
            });
            
        }//for 
        
    }//addTag
    
    function setupLoginKeyHandler(reference){
        on(reference, "keyup", function(event) {
            switch(event.keyCode) {
                case keys.ENTER:
                    event.preventDefault();
                    login();
                    break;
            }
        });
    }

    function login() {
        clearFormError(loginPopupContent_id);
        var passVal = document.getElementById(loginPass_id).value;
        if(!validatePassword(passVal)){
            addErrorMsg(loginPopupBottomStub_id, INVALID_PASS_LABEL, "after");
            return;
        }
        var mailVal = document.getElementById(loginUserMail_id).value;
        if(!validateEmail(mailVal)){
            addErrorMsg(loginPopupBottomStub_id, INVALID_EMAIL_LABEL, "after");
            return;
        }
        
        //it could be executed from diff /path/ levels
        var url = servConfigs.getHost() + servConfigs.getLoginUrl();
        
        sendJsonPost(url, {
            pass: passVal,
            userMail: mailVal
        }, function(jsonResult) {
            if (jsonResult.success == false){
                addErrorMsg(loginPopupBottomStub_id, INCORRET_LOGIN_DATA_LABEL, "after");
                return;
            } else {
                dojo.cookie(loginUserMail_id, document.getElementById(loginUserMail_id).value, {expires: 90});
                var redirect_url = servConfigs.getHost() + jsonResult.model.redirect_url;
                window.location = redirect_url;
            }
        }, null);
    }

    function headerInit(){
        var loginBtn = document.getElementById(loginBtn_id);
        if(null != loginBtn){
            //there is no nececity to include login POPUP on FIT page for the moment
            on(loginBtn, "click", login);
            setupLoginKeyHandler(loginBtn);
            setupLoginKeyHandler(document.getElementById(loginPass_id));            
            var mailVal = dojo.cookie(loginUserMail_id);
            if(null != mailVal)
                document.getElementById(loginUserMail_id).value = mailVal;
        }
        
        if(null != dom.byId(facebookLoginRegSmall_id)){
            //there is no nececity to include facebook lib on FIT page for the moment
            on(dom.byId(facebookLoginRegSmall_id), "click", permissionsReq);
        }
        if(null != dom.byId(googleLoginRegSmall_id)){
            //there is no nececity to include google lib on FIT page for the moment
            on(dom.byId(googleLoginRegSmall_id), "click", function(){
                googlePermissionRequest(state_login);
            });
        }
        
        if(null != dom.byId(loginPopup_id)){
            on(dom.byId(loginPopup_id), "click", function(){
            loginPopup = showPopup(loginPopup_id, loginPopupContent_id, ["below-centered"]);
        });
        }

        
        var noteBtn = dom.byId(addNoteBtn_id);//we use little different HTML Header on landing
        if(null != noteBtn){
            on(dom.byId(addNoteBtn_id), "click", function(){
                contentPopup = showPopup(addNoteBtn_id, contentPopup_id, ["below-centered"]);
            });            
        }

        //        ********* content popup **********
        var tagsRef = dom.byId(contentTags_id);//we use different HTML markap on landing page
        if(null != tagsRef)
            initPopup();
        
        //NOTE prevention of future dates selection
        if(!typeof radioStartDate_id === "undefined"){
            dijit.byId(radioStartDate_id).constraints.max = new Date();
        }
    }
    
    function initPopup(){
        on(dom.byId(contentTags_id), "keyup", function(event) {
            switch(event.keyCode) {
                case keys.SPACE:
                    event.preventDefault();
                    addTag(postTags, contentTags_id, contentTagsVisualisation_id);
                    break;
                case keys.ENTER:
                    event.preventDefault();
                    addTag(postTags, contentTags_id, contentTagsVisualisation_id);
                    break;
            }
        });
        
        on(dom.byId(postContentBtn_id), "click", function(){
            addWildPost(contentPopup_id, postContentBtn_id, contentTitle_id, 
            contentMsg_id, postTags, contentTagsVisualisation_id, qaContentType);
            
        });
    }
    
    //##########################################################################
    ready(function() {
        headerInit();
        var ramblerTop100URL = "https://scounter.rambler.ru/top100.cnt?3124011";
        var myImage = new Image(10, 10);
        myImage.src = ramblerTop100URL;
    });

});