var loginPopupBtn_id = "loginPopupBtn";
var logoSubTitle_id = "logoSubTitle";
var avatarUploader = null;
var smallUserName_id = "smallUserName";
var smallUserLogoIMG_id = "smallUserLogoIMG";

var savePersonalInfo_id = "savePersonalInfo";// we use the js throught wholel 
//portal but the DOM Element only on 
//user profile page. So that we keep it here
var globalUserProfileData = null;
var physicalCofficients = [1.2, 1.375, 1.55, 1.725, 1.9];

function avatarUploadedHandler(up, file, info) {
    var data = JSON.parse(info.response);

    if (data.success == true) {
        setImageAsUserAvatar(data.model.fileDescriptor.fileName);
    } else {
        alert(FILE_WAS_NOT_UPLOADED_LABEL);
    }
}

require(["dijit/registry", "dojo/ready", "dojo/on"],
        function(registry, ready, on) {

            function getPersonalUserInfo() {
                var url = servConfigs.getHost() + servConfigs.getSecurePref() + servConfigs.getUserInfoAPI();

                sendJsonPost(url, null, function(jsonResult) {
                    if (jsonResult.success == true) {
                        applyStyle([loginPopupBtn_id], {display: "none"});
                        applyStyle([smallUserLogoIMG_id], {display: "inline-block"});

                        globalUserProfileData = jsonResult;

                        var imgPath = servConfigs.getHost() + "repository/home/" +
                                jsonResult.model.user.id + "/userAvatar.img";

                        var avaPath = 'url("' + imgPath + '")';

                        document.getElementById(smallUserLogoIMG_id).src = imgPath;

                        var logoTitle = jsonResult.model.user.personalInfo.firstName + " " +
                                jsonResult.model.user.personalInfo.lastName;
                        document.getElementById(smallUserName_id).innerHTML = logoTitle;

                        additionalHandlers(jsonResult, logoTitle, imgPath);
                    }//
                });
            }//getPersonalUserInfo

            function additionalHandlers(jsonResult, logoTitle, imgPath) {
                //*********** FOR PERSONALSETTINGS PAGE
                var rtnRef = document.getElementById(savePersonalInfo_id);
                if (rtnRef != undefined) {
                    document.getElementById(personalUserName_id).innerHTML = logoTitle;
                    document.getElementById(bigUserLogo_id).src = imgPath;
                    fillPersonalInfo(jsonResult.model.user.personalInfo, jsonResult.model.vitalStats);
                }

                //*********** User can use /flow page either without active login sessin.
                //            Nevertheles he able to post or to comment only after login.

                applyStyle([addNoteBtn_id], {display: "inline-block"});
            }//additionalHandlers

            function updatePersonalInfo() {
                clearFormError(personalInfo_id);

                if ("" == document.getElementById(firstName_id).value &&
                        "" == document.getElementById(lastName_id).value) {
                    addErrorMsg(firstName_id, SURNAME_REQUIRED_LABEL, "after");
                    return;
                }

                var gender = document.getElementById(gender_id).value;

                var phoneNumber = document.getElementById(phoneNumber_id).value;
                if (phoneNumber.length < 10 || phoneNumber.length > 15) {
                    addErrorMsg(phoneNumber_id, PHONE_NUMBER_VALIDATION_LABEL, "after");
                    return;
                }

                var timeZone = document.getElementById(timeZone_id);
                var timeZoneId = timeZone.options[timeZone.selectedIndex].value;
                if (timeZoneId == null || "" == timeZoneId || timeZoneId.indexOf("*") != -1) {
                    addErrorMsg(timeZone_id, INVALID_TIMEZONE_LABEL, "after");
                    return;
                }

                var currencySelect = document.getElementById(userCurrecies_id);
                var currency = currencySelect.options[currencySelect.selectedIndex].value;

                var birthday = registry.byId(birthday_id).get("value");
                if (birthday == null || birthday == "") {
                    addErrorMsg(birthday_id, BORN_DATE_REQUIRED_LABEL, "after");
                    return;
                }

                var physicalActivityLevel = document.getElementById(physicalActivityLevel_id).value;

                sendJsonPost(servConfigs.getUpdateUserInfoAPI(), {
                    firstName: document.getElementById(firstName_id).value,
                    lastName: document.getElementById(lastName_id).value,
                    gender: gender,
                    timeZone: timeZoneId,
                    birthday: birthday,
                    phoneNumber: phoneNumber,
                    coefficient: physicalActivityLevel,
                    currency: currency
                }, function(jsonResult) {
                    if (jsonResult.success == true) {
                        //we do reload only for case currencies was changed.
                        //we should update the page. otherwise user will not see actual currency
                        window.location.reload();
                    } else {
                        if (jsonResult.fieldErrors.length > 0) {
                            showMessages(jsonResult.fieldErrors);
                        } else {
                            addErrorMsg(saveCardBtn_id, LENGTH_VALIDATION_LABEL, "before");
                        }
                    }
                });

            }// update User Info

            function fillPersonalInfo(pData, vitalStats) {
                document.getElementById(firstName_id).value = pData.firstName;
                document.getElementById(lastName_id).value = pData.lastName;
                document.getElementById(gender_id).value = pData.gender;
                document.getElementById(timeZone_id).value = pData.timeZone;
                document.getElementById(userCurrecies_id).value = pData.currency;

                if (null != pData.phoneNumber)
                    document.getElementById(phoneNumber_id).value = pData.phoneNumber;

                var birthday = registry.byId(birthday_id);
                birthday.set("value", new Date(pData.birthday));

                if (null != vitalStats && undefined != vitalStats) {
                    var index = physicalCofficients.indexOf(vitalStats.coefficient);
                    document.getElementById(physicalActivityLevel_id).selectedIndex = index;
                }
            }

            setImageAsUserAvatar = function setImageAsUserAvatar(fileUid) {
                sendJsonPost(servConfigs.getChangeUserAvatarURL(),
                        {fileId: fileUid}, function(jsonResult) {
                    if (jsonResult.success == true) {
//                var refreshTail = generatepass(5);
//                var sAva = document.getElementById(smallUserLogoIMG_id);
//                    sAva.src.backgroundImage = 'url("/repository/home/' 
//                        + jsonResult.model.userId + '/userAvatar.img?a='+refreshTail+'")';
                        //TODO do it without page reloading
                        window.location.reload();
                    }
                }, null);

            }//setImageAsUserAvatar

// #############################################################################
            ready(function() {
                getPersonalUserInfo();
                var url = servConfigs.getHost() + servConfigs.getSecurePref() + servConfigs.getDataUploadUrl();
                avatarUploader = buildUploader(smallUserLogoIMG_id, url,
                        [{title: "JPG", extensions: "jpg"},
                            {title: "PNG", extensions: "png"},
                            {title: "GIF", extensions: "gif"},
                            {title: "BMP", extensions: "bmp"},
                            {title: "MP3", extensions: "mp3"}
                        ], avatarUploadedHandler);

                avatarUploader.init();

                var savePersonalInfoBtn = document.getElementById(savePersonalInfo_id);
                if (undefined != savePersonalInfoBtn) {
                    on(savePersonalInfoBtn, "click", updatePersonalInfo);
                }

            });// ready


        });