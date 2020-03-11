//TODO refactor getDayUserChallenges in order to transform incom day challenges into an Array in very first time.
//
////activePeriod This variable is for definition of actual statistics/schedule period
//0 = day, 1 = week, 2 = Month
var vitalInfoPref = "bmiAbsent";

var colors = ["red", "blue", "#A31A7E", "chartreuse", "black", "white", "#9bbb59",
    "orange", "brown", "coral", "darkgrey", "#4f81bd", "#c0504d", "darkmagenta", "green"];
var bmi_overweight_levels = ["slight_adiposity", "adiposity"];

var emptyBlocks = false;
var routesPagginator = null;

var timeCategorysStat = null;
var geoTimeStatistics = null;
var challengeCalloriesStatistics = null;
var userGeolocations = null;
var totalGeoplaceQuantity = null;
var usersRootLogPage = 0;
var userRoutesGrid = null;
var challengesList = null;
var stateStore = null;
var emptyBlockRoutesBlock = null;
var emptyBlockCategoriesChart = null;
var emptyBlockRoundChart = null;
var emptyVitalInfoBlock = null;
var error_userVitalInfoAbsenceCode = 4;

function getCategorysStatUrl() {
    var activePeriod = parseInt(getCheckedRadioValue(statCoverage_id));
    switch (activePeriod) {
        case 0:
            return servConfigs.getDayChallenges();
        case 1:
            return servConfigs.getWeekChallenges();
        case 2:
            return servConfigs.getMonthChallenges();
    }
}// getCategorysStatUrl

require(["dojo/ready", "widgets/VitalInfo", "widgets/CustomTab", "widgets/Pagginator",
    "widgets/EmptyBlock", "dojo/parser", "dojox/grid/DataGrid", 
    "dojo/store/Memory", "dojo/data/ObjectStore",
    "dojo/date/locale", "dojo/on", "dijit/registry", "dojo/dom", "dojo/domReady!"], 
    function(ready, VitalInfo, CustomTab, Pagginator,
        EmptyBlock, parser, DataGrid, Memory, ObjectStore, locale, 
        on, registry, dom) {

    function formatDate(datum) {
        var d = new Date(datum);
        return locale.format(d, {datePattern: "y-M-d"});
    }

    function formatAssotiatedTitle(obj) {
        return obj.name;
    }

    function formatDistance(obj) {
        return parseInt(obj.distance);
    }

    function showUserRoutes(data, total) {
        var dataStore = new ObjectStore({objectStore: new Memory({data: data, idProperty: "id"})});
        if (null == userRoutesGrid) {
            var userRoutesStructure = [
                {name: TITLE_LABEL, field: "associatedLocation", classes: "userRoutesBlockTableTitle", width: "44%", formatter: formatAssotiatedTitle},
                {name: DATE_LABEL, field: "createTime", width: "15%", formatter: formatDate},
                {name: DISTANCE_LABEL, field: "timeDist", classes: "userRoutesBlockTableDistance", width: "15%", formatter: formatDistance},
                {name: TYPE_LABEL, field: "associatedLocation", classes: "userRoutesBlockTableTypeId", width: "25%",
                    formatter: function(item) {
                        return "<img src='../resources/img-res/ico/place/e" + item.placeType + ".png' width='23' height='23' />";
                    }
                }
            ];
            
            userRoutesGrid = new DataGrid({
                store: dataStore,
                structure: userRoutesStructure,
            }, USER_ROUTES_BLOCK_TABLE_DIV);
            userRoutesGrid.startup();

            userRoutesGrid.on("SelectionChanged", function() {
                var items = this.selection.getSelected();
                var geoId = parseInt(items[0].id);
                var index = getIdIndex(geoId, userGeolocations);
                var value = userGeolocations[index];
                addMarker(value.geolocation.lat, value.geolocation.lng);
            });

        } else {
            userRoutesGrid.setStore(dataStore);
        }
        routesPagginator.setTotalItems(total);
    }

    function getDayGeolocationsUrl() {
        var result = {
            geolocationsURL: null,
            geolocationStatsURL: null
        };
        
        var activePeriod = parseInt(getCheckedRadioValue(statCoverage_id));
        switch (activePeriod) {
            case 0:
                result.geolocationsURL = servConfigs.getUserDayGeolocations();
                result.geolocationStatsURL = servConfigs.getDayTimeCategorysStat();
                return result;
                break;
            case 1:
                result.geolocationsURL = servConfigs.getUserWeekGeolocations();
                result.geolocationStatsURL = servConfigs.getWeekTimeCategorysStat()
                return result; 
                break;
            case 2:
                result.geolocationsURL = servConfigs.getUserMonthGeolocations();
                result.geolocationStatsURL = servConfigs.getMonthTimeCategorysStat();
                return result;
                break;
        }
    }// end getDayGeolocationsUrl

    function getGeolocationData(url, page) {
        var date = registry.byId(radioStartDate_id).get("value");

        if (date == null || date == "")
            date = new Date();
        else
            date = new Date(date);

        if (page == undefined) {
            dataRequest = {date: date};
        } else if (null == page) {
            dataRequest = {date: date, page: 0};
        } else {
            dataRequest = {date: date, page: page};
        }

        sendJsonPost(url.geolocationsURL, dataRequest, function(jsonResult) {
            if (jsonResult.success == true) {
                if (null != jsonResult.model.userGeolocations) {
                    userGeolocations = jsonResult.model.userGeolocations;
                    totalGeoplaceQuantity = jsonResult.model.totalGeoplaceQuantity;
                    if (totalGeoplaceQuantity != 0) {
                        hideEmptyBlocks();
                        showUserRoutes(userGeolocations, totalGeoplaceQuantity);
                    }
                    return;
                }//user locations 
            }
        }, null);
        
        sendJsonPost(url.geolocationStatsURL, dataRequest, function(jsonResult) {
            if (jsonResult.success == true) {
                if (null != jsonResult.model.geoTimeStatistics) {
                    geoTimeStatistics = jsonResult.model.geoTimeStatistics;
                    if(undefined != geoTimeStatistics.velocity){
                        var maxVelocity = geoTimeStatistics.velocity.maxVelocity;
                        var avgVelocity = geoTimeStatistics.velocity.avgVelocity;

                        document.getElementById(maxSpeed_id).innerHTML = 
                                parseFloat(maxVelocity).toFixed(1)+KM_H_LABEL;
                        document.getElementById(avgSpeed_id).innerHTML = 
                                parseFloat(avgVelocity).toFixed(1)+KM_H_LABEL;
                        applyStyle([maxSpeedBlock_id, avgSpeedBlock_id], {display: "inline-block"});
                    }
                    if(undefined != geoTimeStatistics.altitude){
                        document.getElementById(minAltitudeValue_id).innerHTML = 
                                parseFloat(geoTimeStatistics.altitude.min).toFixed(1)+METER_LABEL;
                        document.getElementById(maxAltitudeValue_id).innerHTML = 
                                parseFloat(geoTimeStatistics.altitude.max).toFixed(1)+METER_LABEL;
                        document.getElementById(avgAltitudeValue_id).innerHTML = 
                                parseFloat(geoTimeStatistics.altitude.avg).toFixed(1)+METER_LABEL;
                    }
                    
                }
            } else {
                if(jsonResult.messages.length == 0 || jsonResult.messages[0].code == 9){
                    emptyBlockCategoriesChart.setInfoText(FEATURE_LIMITATION_LABEL);
                    emptyBlockRoutesBlock.setInfoText(FEATURE_LIMITATION_LABEL);
                }
            }
        });
    }// end getGeolocationData

    function putEmptyGeolocationBlocks() {
        applyStyle([USER_ROUTES_BLOCK_CONTENT_DIV, USER_ROUTES_PAGGINATION_DIV, 
            ROUND_CATEGORIES_CHART_BLOCK_DIV, CATEGORIES_CHART_DIV], 
            {display: "none"});
            
        applyStyle([EMPTY_ROUND_DATA_DIV, EMPTY_DISTANCE_DATA_DIV, EMPTY_ROUTES_DATA_DIV], {display: "block"});
    
        if(null == emptyBlockRoutesBlock)
            emptyBlockRoutesBlock = new EmptyBlock({infoText: INFO_MESSAGE0, }, EMPTY_ROUND_DATA_DIV);
        if(null == emptyBlockCategoriesChart)
            emptyBlockCategoriesChart = new EmptyBlock({infoText: INFO_MESSAGE0, }, EMPTY_DISTANCE_DATA_DIV);
        if(null == emptyBlockRoundChart)
            emptyBlockRoundChart = new EmptyBlock({infoText: INFO_MESSAGE0}, EMPTY_ROUTES_DATA_DIV);
        
        emptyBlocks = true;
        customTabCallback(0);
    }

    function hideEmptyBlocks() {
        applyStyle([EMPTY_ROUND_DATA_DIV, EMPTY_DISTANCE_DATA_DIV, EMPTY_ROUTES_DATA_DIV], 
            {display: "none"});

        emptyBlocks = false;
        customTabCallback(0);
    }

    function showTotalTime(input) {
        var minutes = parseInt(input.minutesMotion);
        if(1440 < minutes){
            document.getElementById(totalMotionTime_id).innerHTML = (minutes / 1440).toFixed(1) + DAYS_LABEL;
            return;
        }
        if (60 < minutes) {
            document.getElementById(totalMotionTime_id).innerHTML = (minutes / 60).toFixed(1) + HOUR_LABEL;
        } else {
            document.getElementById(totalMotionTime_id).innerHTML = minutes + MIN_LABEL;
        }
    }

    function showTotalCaloriesAndTime(input) {
        if (null != input.totalCalories) {
            document.getElementById(totalCaloriesValue_id).innerHTML = input.totalCalories + CAL_LABEL;
            document.getElementById(newBurntValue_id).innerHTML = input.burntGrammes + GR_LABEL;
            if(1000 < input.burntGrammes){
                var kg = input.burntGrammes / 1000;
                document.getElementById(newBurntValue_id).innerHTML = kg.toFixed(1) + KG_LABEL;
            }
        }
    }

    function customTabCallback(tabIndex) {
        applyStyle([USER_ROUTES_BLOCK_CONTENT_DIV, ROUND_CATEGORIES_CHART_BLOCK_DIV,
            CATEGORIES_CHART_DIV], {display: "none"});
        applyStyle([EMPTY_ROUND_DATA_DIV, EMPTY_DISTANCE_DATA_DIV, EMPTY_ROUTES_DATA_DIV], 
            {display: "none"});

        switch (parseInt(tabIndex)) {
            case 0:
                if(emptyBlocks){
                    applyStyle([EMPTY_ROUTES_DATA_DIV], {display: "block"});
                } else {
                    applyStyle([USER_ROUTES_BLOCK_CONTENT_DIV], {display: "block"});
                }
                break;
            case 1:
                if(emptyBlocks || null == geoTimeStatistics){
                    applyStyle([EMPTY_DISTANCE_DATA_DIV], {display: "block"});
                } else {
                    applyStyle([CATEGORIES_CHART_DIV], {display: "block"});
                    var chartData = assembleDistanceChartData(geoTimeStatistics);
                    showColumnsChart(chartData.values, DISTANCE_CHART_DIV, METRES_LABEL,
                            {labels: chartData.labels});
                }
                break;
            case 2:
                if(emptyBlocks || null == geoTimeStatistics){
                    applyStyle([EMPTY_ROUND_DATA_DIV], {display: "block"});
                } else {
                    applyStyle([ROUND_CATEGORIES_CHART_BLOCK_DIV], {display: "block"});
                    var chartData = assembleTime(geoTimeStatistics);
                    showCategoriesChart(chartData, ROUND_CATEGORIES_CHART_DIV, ROUND_CATEGORIES_CHART_LEGEND_DIV);
                }
                break;
        }
    }// customTabCallback
    
    function getStatisticsData(url) {
        var date = registry.byId(radioStartDate_id).get("value");
        if (date == null || date == "")
            date = new Date();
        else
            date = new Date(date);

        sendJsonPost(url, {date: date}, function(jsonResult) {
            if (jsonResult.success == true) {
                if (null != jsonResult.model.challengeCalloriesStatistics) {
                    challengeCalloriesStatistics = jsonResult.model.challengeCalloriesStatistics;
                    showTotalCaloriesAndTime(challengeCalloriesStatistics);
                    showTotalTime(challengeCalloriesStatistics);

                    var date = getFirstChallengeData(challengeCalloriesStatistics);
                    if (null != date) {
                        var firstDate = new Date();
                        firstDate.setTime(date);
                        var dayChallengesCalendar = registry.byId(dayChallengesCalendar_id);
                        dayChallengesCalendar.set("value", firstDate);
                    } else {
                        challengeStubProcedure(INFO_MESSAGE2);
                        return;
                    }
                }// 
                
            } else {
                var error_val = jsonResult.messages[0].code;
                if (error_userVitalInfoAbsenceCode == error_val && null == emptyVitalInfoBlock) {
                    emptyVitalInfoBlock = new EmptyBlock({infoText: SET_CHALLENGE_MNG_VITAL_INFO_LABEL}, 
                        CHALLENGES_MANAGER_CONTENT_DIV);                        
                    return;
                }
                challengeStubProcedure(FEATURE_LIMITATION_LABEL);
            }
        }, null);
    }//end getStatisticsData

    function assembleDistanceChartData(data) {
        var result = {
            values: new Array(),
            labels: new Array()
        };
        var categories = data.overallTimeCategoryStat;
        var i = 0;
        Object.getOwnPropertyNames(categories).forEach(function(val) {
            var metersVal = parseInt(categories[val].distance);
            result.values.push({
                tooltip: metersVal + " " + METRES_LABEL,
                y: metersVal,
                color: colors[i]
            });

            result.labels.push({
                value: ++i,
                text: getCalegoryLabel(parseInt(val))
            });
        });
        return result;
    }

    function assembleTime(data) {
        var result = new Array();
        var categories = data.overallTimeCategoryStat;
        var onePercent = parseInt(data.minutesMotion) / 100;

        Object.getOwnPropertyNames(categories).forEach(function(val) {
            var percentageAmount = parseInt(categories[val].time) / onePercent;
            result.push({
                y: categories[val].time,
                tooltip: categories[val].time + " " + MINUTES_LABEL,
                text: getCalegoryLabel(val) + " " + parseFloat(percentageAmount).toFixed(2) + "%"
            });
        });
        return result;
    }

    function sendVitalData(data) {
        if(!data){
            alert(VITAL_PARAMS_VALIDATION_LABEL);
            return;
        }
        sendJsonPost(servConfigs.getVitalInfoApiUrl(), data, function(jsonResult) {
            if (jsonResult.success == true) {
                location.reload(); 
            } else {
                //TODO CHANGE IT TO SERVER SIDE MSG
                alert(VITAL_PARAMS_VALIDATION_LABEL);
            }
        });//end sendJSONpost

    }//END sendVitalData

    function getBMI(){
        sendJsonPost(servConfigs.getUserBMI(), null, function(jsonResult) {
            if (jsonResult.success == true) {
                document.getElementById(currentUserWeight_id).innerHTML = 
                    globalUserProfileData.model.vitalStats.weight;
                
                document.getElementById(userIdealWeight).innerHTML = 
                    jsonResult.model.userBMI.idealWeightDifference+KG_LABEL;
            
                var bmiValue = jsonResult.model.userBMI.bmiValue;
                document.getElementById(bigBMIInfoCircle_id).innerHTML = bmiValue;
                
                if(bmi_overweight_levels.indexOf(jsonResult.model.userBMI.bmiLevel) > -1){
                    document.getElementById(userIdealWeightLabel_id).innerHTML = BMI_NAME_3_LABEL;
                }

                document.getElementById(bmiTitle_id).innerHTML = 
                    jsonResult.model.userBMI.bmiTitle;
                
                document.getElementById(bmiDescription_id).innerHTML = 
                    jsonResult.model.userBMI.bmiDescription;
        
                domConstruct.place(document.getElementById(recomendedCaloriesConsumption_id), 
                    CUSTOM_TAB_DIV, "last");
                applyStyle([recomendedCaloriesConsumption_id], {display: "inline-block"});
                document.getElementById(recomendedEatCaloriesAmount_id).innerHTML = 
                        jsonResult.model.userBMI.recomendedEatCaloriesAmount+CAL_LABEL;
                
                document.getElementById(burnPossibility_id).innerHTML = 
                        jsonResult.model.userBMI.burnPossibility.calories +CAL_LABEL;

                var challengeTitle = BY_LABEL + " "+jsonResult.model.userBMI.burnPossibility.duration + 
                        MIN_LABEL + " " + OF_LABEL + " " + jsonResult.model.userBMI.burnPossibility.challenge.title;
                document.getElementById(burnPossibilityChallenge_id).innerHTML = challengeTitle;
                
            } else {
                new VitalInfo({perfix: vitalInfoPref, height: HEIGHT_LABEL,
                    width: WIDTH_LABEL, chest: CHEST_LABEL, waist: WAIST_LABEL,
                    submit: SUBMIT_LABEL, callbackFunction: sendVitalData}, 
                    BODY_MASS_INDEX_CONTENT_DIV);
            }
        });//end sendJsonPost
        
    }

    function initTooltips(){
        initTooltip([recomendedCaloriesConsumption_id, recomendedEatCaloriesAmount_id],
            RECOMENDED_CALORIES_CONSUPTION_TOOLTIP); 
        initTooltip([maxSpeedBlock_id, maxSpeed], MAX_SPEED_TOOLTIP);
        initTooltip([avgSpeed_id, avgSpeedBlock_id], AVG_SPEED_TOOLTIP);
        initTooltip([maxAltitudeValue_id, maxAltitude_id], MAX_HEIGHT_TOOLTIP);
        initTooltip(avgAltitude_id, AVG_HEIGHT_TOOLTIP);
        initTooltip(minAltitude_id, MIN_HEIGHT_TOOLTIP);
        initTooltip([totalMotionTime_id, totalMotionBlock_id], MOTION_TIME_TOOLTIP);
        
        initTooltip([totalCaloriesValue_id, totalCalloriesBlock_id], TOTAL_CALORIES_TOOLTIP);
        initTooltip([newBurntValue_id], BURNT_GRAMMES_TOOLTIP);
        initTooltip([statistics_btn_pref + "-0"], ROOTS_BTN_TOOLTIP);
        initTooltip([statistics_btn_pref + "-1"], DISTANSE_BTN_TOOLTIP);
        initTooltip([statistics_btn_pref + "-2"], TIME_BTN_TOOLTIP);

        initTooltip([bodyMassIndexContent_id, bodyMassIndexBlock_id], BMI_CONTENT_TOOLTIP, "above");
        initTooltip([bmiCentralContent_id], CHALLENGE_RECOMENDATION_TOOLTIP);
        initTooltip([clusteredDayChallengeChart_id], CLUSTERED_DAY_CHALLENGE_TOOLTIP);
        initTooltip([saveChallenge_id], CHALLENGES_MANAGER_TOOLTIP);
    }

    function init() {
        putEmptyGeolocationBlocks();
        toggleClass(fitPage_id, grayscale_class);

        var customTab = new CustomTab({
            prefix: "statistics",
            tButtons: [
                {title: TAB_ROUTES_LABEL, imgUrl: "../resources/img-res/ico/common/userRoutesLogICO2.png"},
                {title: TAB_CATEGORIES_STAT_LABEL, imgUrl: "../resources/img-res/ico/common/customTabStatICO.png"},
                {title: TAB_ROUND_CHART_LABEL, imgUrl: "../resources/img-res/ico/common/roundChartStatisticsICO.png"}
            ],
            callbackFunction: customTabCallback
        }, CUSTOM_TAB_DIV);

        routesPagginator = new Pagginator({
            prefix: "userRoutes",
            nextLable: NEXT_LABEL,
            previousLable: PREVIOUS_LABEL,
            totalLable: TOTAL_LABEL,
            callbackFunction: function(page) {
                getGeolocationData(getDayGeolocationsUrl(), page);
            },
            totalItems: totalGeoplaceQuantity
        }, USER_ROUTES_PAGGINATION_DIV);

        getStatisticsData(getCategorysStatUrl());

        getChallengesList();
        getBMI();
        
        on(dom.byId(addNewVitalData_id), "click", function(evt) {
            var vitPrefix = "bmiNewVitalData" + generatepass(5);
            var ditalDataBlock = new VitalInfo({
                perfix: vitPrefix, 
                height: HEIGHT_LABEL, width: WIDTH_LABEL, 
                chest: CHEST_LABEL, waist: WAIST_LABEL, submit: SUBMIT_LABEL, 
                callbackFunction: sendVitalData}, BODY_MASS_INDEX_CONTENT_DIV);
            
            ditalDataBlock.getLatestVitalInfo();//to initialize view with actual data
        });//on NEW VITAL DATA.

        on(registry.byId(radioStartDate_id), "change", function(evt) {
            getStatisticsData(getCategorysStatUrl());
            getGeolocationData(getDayGeolocationsUrl(), 0);
        });//on

//        dijit.byId(dayChallengesCalendar_id).constraints.max = new Date();
    } //init end

    //##########################################################################
    ready(function() {
        init();
        initializeMap(null, null, STATISTICS_MAP_DIV);
        initTooltips();
    });//ready end

});

