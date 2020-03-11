var servConfigs = {
    servIp: "https://multitracking.info",
    host: "https://multitracking.info/",
    servPort: "80",
    serverTimeout: 99999,
    jsonContentType: "json",
    securePref: "secure/",
    contentPref: "content/",
    postDir: "post/",

    contentSuff: "repository/home/",
    loadContent: "loadContent",
    loadRandomContent: "loadRandomContent",
    loadComments: "loadComments",
    addComment: "addComment",
    
    userLocation: "/userLocation.html",
    startFilter: "/filterTransportations.html",
    dataUploadUrl: "dataUpload2",
    changeUserAvatar: "setAvatarImage",
    
    dayTimeCategorysStat: "getDayTimeDistSpeed",
    weekTimeCategorysStat: "getWeekTimeDistSpeed",
    monthTimeCategorysStat: "getMonthTimeDistSpeed",
    
    userLocations: "getUserLocations",
    userMonthGeolocations: "getMonthLocations",
    userWeekGeolocations: "getWeekLocations",
    userDayGeolocations: "getDayLocations",
    
    dayChallenges: "getDayChallenges",
    weekChallenges: "getWeekChallenges",
    monthChallenges: "getMonthChallenges",
    
    newTripUrl: "/secure/createTransportation.html",
    registerUrl: "register",
    inviteFriend: "inviteFriend", 
    loginUrl: "login2",
    userInfoAPI: "getUserInfo",
    updateUserInfoAPI: "updateUserInfo",
    
    lastGeolocationAPI: "getLastRegistredGeoLocation",
    addGeolocationAPI: "trackLocation",
    
    createTeamURL: "createTeam",
    groups: "getGroups",
    
    vitalInfoAPIURL: "addUserVitalInfo",
    challengesList: "getChallenges",
    addChallenge: "addChallenge",
    userBMI: "getUserBMI",
    latestUserVitalInfo: "latestUserVitalInfo",
    searchUserContacts: "searchUserContacts",
    
    sendMessage: "sendMessage",
    userThreads: "threads",
    addContent: "addContent",
    
    addCreditCard: "addCreditCard",
    getCreditCardsURL: "getCreditCards",
    productsURL: "../products",
    orderProductURL : "orderProduct",
    activeProductsURL: "activeProducts",
    
    getContentSuff: function(){
        return this.contentSuff;
    },
    getLoadCommentsURL: function(){
        return this.loadComments;
    },
    getAddCommentURL: function(){
        return this.addComment;
    },
    getPostDirURL: function(){
        return this.postDir;
    },
    getLoadRandomContentURL: function(){
        return this.loadRandomContent;
    },
    getLoadContentURL: function(){
        return this.loadContent;
    },
    getContentPref: function(){
        return this.contentPref;
    },
    getSecurePref: function(){
        return this.securePref;
    },
    getAddContentUrl: function(){
        return this.addContent;
    },
    getUserThreads: function(){
        return this.userThreads;
    },
    sendMessageUrl: function(){
        return this.sendMessage;
    },
    inviteFriendUrl: function(){
        return this.inviteFriend;
    },
    getSearchUserContactsUrl: function(){
        return this.searchUserContacts;
    },
    getActiveProducts: function(){
        return this.activeProductsURL;
    },
    getOrderProductURL: function(){
        return this.orderProductURL;
    },
    getProducts: function(){
        return this.productsURL;
    },
    getCreditCards: function(){
        return this.getCreditCardsURL;
    },
    getAddCreditCard: function(){
        return this.addCreditCard;
    },

    getCreateTeamURL: function(){
        return this.createTeamURL;
    },
    getHost: function(){
        return this.host;
    },
    getChangeUserAvatarURL: function(){
        return this.changeUserAvatar;
    },
    getLatestUserVitalInfo: function(){
        return this.latestUserVitalInfo;
    },
    
    getUserBMI:function(){
        return this.userBMI;
    },
    getDayChallenges: function(){
        return this.dayChallenges;
    },
    getWeekChallenges: function (){
        return this.weekChallenges;
    },
    getMonthChallenges: function(){
        return this.monthChallenges;
    },
    getAddChallengeUrl: function(){
        return this.addChallenge;
    },
    getChallengesList: function(){
        return this.challengesList;
    },
    getVitalInfoApiUrl: function(){
        return this.vitalInfoAPIURL;
    },
    getUserGeolocations: function(){
        return this.userLocations;
    },
    getUserMonthGeolocations: function(){
        return this.userMonthGeolocations;
    },
    getUserWeekGeolocations: function(){
        return this.userWeekGeolocations;
    },
    getUserDayGeolocations: function(){
        return this.userDayGeolocations;
    },
    getGroups: function(){
        return this.groups;
    },
    getAddGeolocationAPI: function(){
        return this.addGeolocationAPI;
    },
    getLastRegisteredGeolocationAPI: function(){
        return this.lastGeolocationAPI;
    },
    getUserInfoAPI: function(){
        return this.userInfoAPI;
    },
    getUpdateUserInfoAPI: function(){
        return this.updateUserInfoAPI;
    },
    getLoginUrl: function() {
        return this.loginUrl;
    },
    getRegisterUrl: function() {
        return this.registerUrl;
    },
    getNewTripUrl: function() {
        return this.newTripUrl;
    },
    getMonthTimeCategorysStat: function() {
        return this.monthTimeCategorysStat;
    },
    getDayTimeCategorysStat: function() {
        return this.dayTimeCategorysStat;
    },
    getWeekTimeCategorysStat: function() {
        return this.weekTimeCategorysStat;
    },
    getDataUploadUrl: function() {
        return this.dataUploadUrl;
    },
    getStartFilter: function() {
        return this.startFilter;
    },
    getUserLocation: function() {
        return this.userLocation;
    },
    getJsonContentType: function() {
        return  this.jsonContentType;
    },
    getServerTimeout: function() {
        return this.serverTimeout;
    },
    getServerPort: function() {
        return this.servPort;
    },
    getServerIp: function() {
        return this.servIp;
    }
}