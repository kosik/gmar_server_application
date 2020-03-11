var ENABLED_STAR_ICO_PATH = "resources/img-res/ico/landingStarM1.png";
var DISABLED_STAR_ICO_PATH = "resources/img-res/ico/landingStarM0.png";
var TIMESLIDE_INTERVAL = 5000;

var fbTokenData = null;
var MALE = "male";
var currentAnimation = "1";
var slider = null;

window.onscroll = function(event){
    if (typeof event.preventDefault != 'undefined')
        event.preventDefault();
    else
        return false;
};

var arrow_keys_handler = function(e) {
    switch (e.keyCode) {
        case 37:
        case 39:
        case 38:
        case 40: // Arrow keys
        case 34:
        case 33:
        case 32:
            e.preventDefault();
            break; // Space
        default:
            break; // do not block other keys
    }
};
window.addEventListener("keydown", arrow_keys_handler, false);
//window.removeEventListener("keydown", arrow_keys_handler, false);

require(["dojo/ready", "dojo/on", "dijit/registry", "dojo/dom", "dijit/form/ComboBox",
    "dojox/widget/AutoRotator", "dojox/widget/rotator/Fade", "dojo/cookie", 
    "dojo/query", "dojo/aspect", "dojo/keys", "dojo/domReady!"], function(ready, on, registry, dom, 
    ComboBox, AutoRotator, Fade, cookie, query, aspect, keys) {

    function init(){
        constructRotator();
        var loginBtn = document.getElementById(loginBtn_id);
        on(loginBtn, "click", login);
        setupLoginKeyHandler(loginBtn);
        
        setupLoginKeyHandler(document.getElementById(loginPass_id));
        
        var mailVal = dojo.cookie(loginUserMail_id);
        if(null != mailVal)
            document.getElementById(loginUserMail_id).value = mailVal;
        
        on(dom.byId(registerBtn_id), "click", register);
        
        query(".landingBoolet").on("click", function(event){
                var slideIndex = controlRotator(this.id);
                slider.go(slideIndex);
        });//query
        
//        on(dom.byId(facebookRegister_id), "click", permissionsReq);
        on(dom.byId(facebookLoginRegSmall_id), "click", permissionsReq);
    }//init

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
    
    function controlRotator(elementId){
        query(".landingBoolet").forEach(function(node) {
            node.src = DISABLED_STAR_ICO_PATH;
        });
        document.getElementById(elementId).src = ENABLED_STAR_ICO_PATH;
        var slideNumber = (parseInt(elementId.charAt(elementId.length-1)) - 1);
        return slideNumber;
    }
    
    function constructRotator(){
        //NOTE registry.byId(landingRotator_id); does not returns reference to Declaratively
        //created Rotator because it was not inherit from widjet. 
        //So that we have to create it programaticaly. GET RID OF IT ASAP.
        slider = new AutoRotator({
                transition: "dojox.widget.rotator.crossFade",
                duration: TIMESLIDE_INTERVAL,
                pauseOnManualChange: false,
                cycles: 3,
                panes: [
                    {className: "pane", innerHTML: dom.byId("pane1").innerHTML},
                    {className: "pane", innerHTML: dom.byId("pane2").innerHTML},
                    {className: "pane", innerHTML: dom.byId("pane3").innerHTML},
                    {className: "pane", innerHTML: dom.byId("pane4").innerHTML}
                ]}, dom.byId(landingRotator_id));
            
        aspect.after(slider, "go", function(method, args){
            method = method + 1;
            method = method % 4;
            if(method == 0)
                method = 4;
            controlRotator("landingBoolet" + method);
        }, true);
    }
    
    function login() {
//            alert("Thank you for curiosity. The project is in pre-release state. We will contact you soon and give you free trial period.");
//            return;
        var passVal = document.getElementById(loginPass_id).value;
        var mailVal = document.getElementById(loginUserMail_id).value;
        sendJsonPost(servConfigs.getLoginUrl(), {
            pass: passVal,
            userMail: mailVal
        }, function(jsonResult) {
            if (jsonResult.success == false)
                alert(INCORRET_LOGIN_DATA_LABEL);
            else {
                dojo.cookie(loginUserMail_id, document.getElementById(loginUserMail_id).value, {expires: 90});
                window.location = jsonResult.model.redirect_url;
            }
        }, null);
    }
    
    function register() {
        if("" == document.getElementById(registerName_id).value && 
                "" == document.getElementById(registerFamily_id).value){
            alert(SURNAME_REQUIRED_LABEL);
            return;
        }
        
        var email = document.getElementById(email_id).value;
        if (!validateEmail(email)) {
            alert(INVALID_EMAIL_LABEL);
            return;
        }
        
        var userGender = true;
        var gender = document.getElementById(gender_id).value;
        if (gender.toLowerCase() != MALE_LABEL.toLowerCase()) {
            userGender = false;
        }
        
        var timeZone = document.getElementById(rigisterTimeZone_id);
        var timeZoneId = timeZone.options[timeZone.selectedIndex].value;
        if(timeZoneId == null || "" == timeZoneId || timeZoneId.indexOf("*") != -1){
            alert(INVALID_TIMEZONE_LABEL);
            return;
        }

        var regPassVal = document.getElementById(registerPassword_id).value;
        if (!validatePassword(regPassVal)) {
            alert(PASSWORD_INCORRECT_LABEL);
            return;
        }
        
        var bornDate = registry.byId(registerBornDate_id).get("value");
        if(null == bornDate || bornDate == ""){
            alert(BORN_DATE_REQUIRED_LABEL);
            return;
        }
        
        sendJsonPost(servConfigs.getRegisterUrl(), {
            firstName: document.getElementById(registerName_id).value,
            lastName: document.getElementById(registerFamily_id).value,
            email: email,
            gender: userGender,
            timeZone: timeZoneId,
            password: regPassVal,
            birthday: bornDate
        }, function(jsonResult) {
//            alert("Thank you for the curiosity. The project is in pre-release state. We will contact you soon and give you free trial period.");
//            return;
            if (jsonResult.success == false){
                if(0 != jsonResult.fieldErrors.length){
                    alert(jsonResult.fieldErrors[0].message);
                }
                return;
            }
            else
                window.location = jsonResult.model.redirect_url;

        }, null);
    }
    
//#############################################################################
    ready(function() {

        init();
    });

});