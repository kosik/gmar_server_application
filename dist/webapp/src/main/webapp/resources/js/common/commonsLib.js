var maximizedElementsArray = null;
var navigationMaximaze = false;
var topScrollAmount = 100;

var FOOTERCONTENT_DIV = "footerContent";

var MIN_VALIDE_AGE = 5;
var MAX_VALID_AGE = 200;
function ageValidation(birthdate){
    var minimalAge = new Date();
    minimalAge.setFullYear(minimalAge.getFullYear() - MIN_VALIDE_AGE);
    if (birthdate.getTime() > minimalAge.getTime()) {
        return false;
    }
    
    var maxAge = new Date();
    maxAge.setFullYear(maxAge.getFullYear() - MAX_VALID_AGE);
    if (birthdate.getTime() < maxAge.getTime()) {
        return false;
    }
    
    return true;
}

function removeAllChild(ref){
    var myNode = document.getElementById(ref);
    while (myNode.firstChild) {
        myNode.removeChild(myNode.firstChild);
    }
}

/**
 * Loop throught array to find out if there is exists propName value
 */
//function findByPropNameAndValue(propName, items, value){
//NOTE: IE+WIN does not support the language structure at this moment. So that we have rewritten it
//    for(item of items){
//        if(item[propName] == value)
//            return item;
//    }
//    return null;
//}

function findByPropNameAndValue(propName, items, value){
    var item = 0;
    while(items.length >= item){
        var obj = items[item++];
        if(obj != undefined && obj[propName] == value)
            return obj;
    }
    return null;
}

function buildUploader(browseButtonId, uploadUrl, mimeTypes, callbackFunction){
    var uploader = new plupload.Uploader({
        runtimes : 'html5,flash,silverlight,html4',
        browse_button : browseButtonId,
        url : uploadUrl,
        filters : {
            max_file_size : '5mb',
            mime_types: mimeTypes
        },
        init: {
            PostInit: function() {
                document.getElementById(browseButtonId).onclick = function() {
                    uploader.start();
                    return false;
                };
            },
            FilesAdded: function(up, files) {
                uploader.start();
            },
            UploadProgress: function(up, file) {},
            FileUploaded: callbackFunction,
            Error: function(up, err) {}
         }
    });
    return uploader;
}

function insertAfter(newNode, referenceNode) {
    referenceNode.parentNode.insertBefore(newNode, referenceNode.nextSibling);
}

function validatePassword(password) {
    var minLength = 8,
        valid = true;
    password = password.trim();
    if(password.length < 8){
        valid = false;
        
    }
    return valid;
}

function objectFindByKey(array, key, value) {
//var existFlag = objectFindByKey(obj, "text", value.dayInterval);
    for (var i = 0; i < array.length; i++) {
        if (array[i][key] === value) {
            return array[i];
        }
    }
    return null;
}

function setTopScrollAmount(scrollAmount){
    topScrollAmount = scrollAmount;
}

function getTopScrollAmount(){
    return topScrollAmount;
}

function getIdIndex(id, dataList){
    if(id == null || id == "" || dataList == null)
        return -1;
    var index = 0;
    while(index < dataList.length) {
        if(dataList[index].id == id)
            return index;
        index++;
    }
    return -1;
}

function gup(name){
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    regex = new RegExp(regexS);
    var results = regex.exec(window.location.href);
    if (results == null)
        return "";
    else
        return results[1];
}

function getCheckedRadioValue(radioGroupName) {
   var rads = document.getElementsByName(radioGroupName),
       i;
   for (i=0; i < rads.length; i++)
      if (rads[i].checked)
          return rads[i].value;
   return null; // or undefined, or your preferred default for none checked
}

function validateEmail(elementValue) {
    emailPattern = /^[a-zA-Z0-9._]+[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(elementValue);
}

function generatepass(plength) {
    temp = "";
    keylist = "abcdefghijklmnopqrstuvwxyz123456789";
    for (i = 0; i < plength; i++)
        temp += keylist.charAt(Math.floor(Math.random() * keylist.length));
    return temp;
}

require(["dojo/dom-geometry", "dojo/dom-class", "dojo/on", "dojo/ready", "dojo/query", 
    "dojo/dom", "dojo/_base/xhr", "dijit/registry", "dojo/dom-style",  
    "dojo/dom-construct", "dijit/TooltipDialog", "dijit/Tooltip", "dijit/popup", 
    "dojox/grid/DataGrid", "dojo/domReady!"], 
    function(domGeom, domClass, on, ready, query, dom, xhr, registry, domStyle, 
    domConstruct, TooltipDialog, Tooltip, popup, DataGrid, domReady) {

    showPopup = function showPopup(aroundItemId, popupContentId, orientation){
        //NOTE orientation is spoused to be Strings Array
        var popupContent = dom.byId(popupContentId);
        applyStyle([popupContent], {display: "inline-block"});
        var myDialog = new TooltipDialog({
            content: popupContent
        });
        
        var aroundElement = document.getElementById(aroundItemId);
        popup.open({
            popup: myDialog,
            around: aroundElement,
            orient: orientation//after-centered
        });
        
        return popup;
    }//showPopup

    addDom = function addDom(stringifiedDom, elementId, position){
        domConstruct.place(stringifiedDom, elementId, position);//after last
    }

    addOverlay = function addOverlay(overlayNode, position){
        var id = generatepass(9);
        var overlayElement = '<div class="loadingOverlay" id="'+id+'"></div>';
        domConstruct.place(overlayElement, overlayNode, position);
        return id;
    }
    
    destroyNode = function destroyNode(id) {
        var rootRef = dom.byId(id);
        domConstruct.destroy(rootRef);
    }

    addErrorMsg = function addErrorMsg(elementId, message, position) {
        var errElement = '<div class="valmsg error-icon error">\n\
                </div><div class="valmsg error-text labelStyle_13_bold">' + message + '</div>';
        domConstruct.place(errElement, elementId, position);//after last
    }
    
    showMessages = function(fieldErrors) {
        var i = 0;
        while (i < fieldErrors.length) {
            var eItem = fieldErrors[i++];
            addErrorMsg(eItem.field, eItem.message, "after");
        }
    }

    clearFormError = function(elementId) {
        var rootRef = dom.byId(elementId);
        query(".valmsg", rootRef).forEach(function(node) {
            domConstruct.destroy(node);
        });
    }

    setupFooterPosition = function setupFooterPosition(lastDivOnThePage, assetHeight){
        var node = document.getElementById(lastDivOnThePage);// document.body;
        var includeScroll = true;
        var output = domGeom.position(node, includeScroll);
        var height = output.h + output.y + assetHeight;

        node = document.getElementById(FOOTERCONTENT_DIV);
        node.style.top = height + "px";

        return height;
    }

    getElementHeight = function getElementHeight(element){
        var node = document.getElementById(element);// document.body;
        var includeScroll = true;
        var output = domGeom.position(node, includeScroll);
        var height = output.h;

        return height;
    }

    applyStyle = function applyStyle(idsArray, compoundStyle){
        for(var i = 0; i < idsArray.length; i++){
            domStyle.set(idsArray[i], compoundStyle);
        }
    }//applyStyle
    
    toggleClass = function toggleClass(nodeId, className){
        //todo refactor other domClass entrance here with the toggle()
        domClass.toggle(nodeId, className);
    }

    sendJsonPost = function sendJsonPostLocal(urlData, contentData, 
        loadFunction, errorFunction, requestHeaders, sync) {
        var ajaxCallParameters = {
            url: urlData,
            handleAs: servConfigs.getJsonContentType(),
            timeout: servConfigs.getServerTimeout(),
            load: loadFunction 
//            headers: { cross-domain feature
//                "X-Requested-With": null
//            }
        };

        if (null != contentData) {
            ajaxCallParameters.content = contentData;
        }

        if(undefined != requestHeaders && null != requestHeaders){
            ajaxCallParameters.headers = requestHeaders;
        }
        
        if(undefined != sync && null != sync){
            ajaxCallParameters.sync = sync;
        }

        xhr.post(ajaxCallParameters);
    }
            
            
    initTooltip = function initTooltip(id, msg, tooltipPosition) {
        var conf = {label:msg};
        
        if (Array.isArray(id)){
            conf.connectId = id;
        } else {
            conf.connectId = new Array();
            conf.connectId.push(id);
        }
        if(undefined != tooltipPosition && tooltipPosition != ""){
            conf.position = tooltipPosition;
        }
        new Tooltip(conf);
    }

    setupScrollNavigationPanel = function setupScrollNavigationPanel(elementsArray) {
        //navigation userListWrapper 
        maximizedElementsArray = elementsArray;
        var footerRoot = document.getElementById("footerContent");
        on(window, "scroll", function(evt) {
            var scrollTop = dojo._docScroll().y;

            if (getTopScrollAmount() < scrollTop && !navigationMaximaze) {
//                for(item of maximizedElementsArray){ IE does not recognise the construction
                for (var i = 0; i < maximizedElementsArray.length ; i++) {
                    domClass.add(maximizedElementsArray[i], "navigationMaximaze");
                }
                navigationMaximaze = true;
                return;
            }
            if (getTopScrollAmount() > scrollTop) {
                navigationMaximaze = false;
//                for (item of maximizedElementsArray){IE Does not recognise the construction
                for (var i = 0; i < maximizedElementsArray.length ; i++) {
                    domClass.remove(maximizedElementsArray[i], "navigationMaximaze");
                }
                return;
            }
        });//on
    }
            
    //##########################################################################
    ready(function() {
//        setupScrollNavigationPanel();

    });//ready end

});