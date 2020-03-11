var fbTokenData = null;
var MALE = "male";

require(["dojo/ready", "dojo/dom", "dojo/_base/xhr", "dijit/form/ToggleButton", "dojo/_base/connect", "dijit/form/ComboButton", "dijit/Menu",
    "dijit/MenuItem", "dijit/form/DropDownButton", "dijit/TooltipDialog", "dojo/parser",
    "dijit/form/TextBox", "dijit/layout/BorderContainer", "dijit/layout/TabContainer",
    "dijit/layout/ContentPane", "dojo/domReady!", "dojo", "dijit/form/Button", "dijit/registry", "dijit/form/ComboBox"],
        function(ready, dom, xhr, ToggleButton, connect, ComboButton, Menu, MenuItem, DropDownButton, TooltipDialog, parser,
                TextBox, BorderContainer, TabContainer, ContentPane, domReady, dojo, Button, registry) {

            var currentAnimation = "1";

            showRegisterElementLocal = function showRegisterElement() {
                document.getElementById("rightSideElement").style.display = "none";
                document.getElementById("registerElement").style.display = "inline-block";
            }

            fadeAnimationLocal = function fadeAnimation(fadeInString, fadeOutString) {
//         	  dojo.fadeOut({node: "card" + fadeOutString,duration: 5000});
//         	  dojo.fadeIn({node: "card" + fadeInString, duration: 3000});
//         	  fId = "card" + fadeInString;
            }

            fadeExecutorLocal = function fadeExecutor(fadeinId) {
                if (null == fadeinId) {
                    document.getElementById(currentAnimation).className = "bullet";
                    document.getElementById("card_" + currentAnimation).style.display = "none";
                    currentAnimation++;
                    if (currentAnimation > 5)
                        currentAnimation = 1;
                    document.getElementById(currentAnimation).className = "bullit_active";
                    document.getElementById("card_" + currentAnimation).style.display = "inline-block";
                    return;
                }
                document.getElementById(currentAnimation).className = "bullet";
                document.getElementById("card_" + currentAnimation).style.display = "none";
                document.getElementById("card_" + fadeinId).style.display = "inline-block";
                document.getElementById(fadeinId).className = "bullit_active";
                currentAnimation = fadeinId;
            }

            setInterval("fadeExecutorLocal(null)", 15000);

            cookieMailPointer = function cookieMail() {
                return dojo.cookie("userMail");
            }

            cookiePassPointer = function cookiePass() {
                return dojo.cookie("pass");
            }

            facebookRegisterlocal = function facebookRegister(regData) {
                xhr.post({
                    url: "facebookRegister.html",
                    handleAs: "json",
                    timeout: 9000,
                    content: {
                        accessToken: regData.accessToken,
                        expiresIn: regData.expiresIn,
                        userID: regData.userID,
                        signedRequest: regData.signedRequest
                    },
                    load: function(jsonResult) {
                        if (jsonResult.result == "false")
                            alert(notification_3);
                        else {
                            dojo.cookie("userMail", document.getElementById("userMail").value, {expires: 90});
                            window.location = jsonResult.redirect_url;
                        }
                    }
                });
            }

            sendlogdta = function sendLoginData() {
                sendJsonPost(servConfigs.getLoginUrl(), {
                    pass: registry.byId("pass").get("value"),
                    userMail: registry.byId("userMail").get("value")
                }, function(jsonResult) {
                    if (jsonResult.success == false)
                        alert(notification_3);
                    else {
                        dojo.cookie("userMail", document.getElementById("userMail").value, {expires: 90});
                        window.location = jsonResult.model.redirect_url;
                    }
                }, null);
            }

            lBtn1 = new Button({
                showLabel: true,
                onClick: sendlogdta,
                label: localisationOfLabel
            }, "toggleBtn1");
            lBtn1.startup();

            sendRegisterDtaPointer = function sendRegisterDta() {
                email = registry.byId("email").get("value");
                if (!validateEmail(email)) {
                    alert(invalidEmail_label);
                    return;
                }
                if (email == "" || registry.byId("userPass").get("value") == "") {
                    return;
                }

                emailRepeatCheck = registry.byId("emailRepeatCheck").get("value");
                if (email != emailRepeatCheck) {
                    alert(repeat_email_invalidMsg);
                    return;
                }

                var userGender = true;
                if (registry.byId("gender").get("value").toLowerCase() != MALE) {
                    userGender = false;
                }

                sendJsonPost(servConfigs.getRegisterUrl(), {
                    name: registry.byId("userName").get("value"),
                    family: registry.byId("userFamily").get("value"),
                    email: registry.byId("email").get("value"),
                    gender: userGender,
                    timeZone: "America/St_Johns", //add dropdown with values
                    password: registry.byId("userPass").get("value")
                }, function(jsonResult) {
                    if (jsonResult.success == false)
                        alert(jsonResult);
                    else
                        window.location = jsonResult.model.redirect_url;

                }, null);


            }

            lregisterBtn = new Button({
                showLabel: true,
                onClick: sendRegisterDtaPointer,
                label: localisationOfLabel
            }, "registerBtn");
            lregisterBtn.startup();

            ready(function() {
                FB.init({
                    appId: fbCredits.getFbAppId(),
                    status: true,
                    cookie: true,
                    oauth: true
                });

                FB.getLoginStatus(function(response) {
                    if (response.authResponse) {
                        token_info = {
                            "accessToken": response.authResponse.accessToken,
                            "userID": response.authResponse.userID,
                            "expiresIn": response.authResponse.expiresIn,
                            "signedRequest": response.authResponse.signedRequest
                        };

                        fbTokenData = token_info;

                        facebookRegisterlocal(token_info);

                    } else {
                        //silently swallow
                    }
                });

            });

        });