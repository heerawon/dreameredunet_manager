/*
  Author: Sokcho Kim;
  Version: 0.1.0;
  (◠‿◠✿)

*/

//날짜 세팅에 필요한 주요 값들 미리 세팅
const now = new Date();
const nowDayOfWeek = now.getDay();
const nowDay = now.getDate();
const nowMonth = now.getMonth();
let nowYear = now.getYear();
nowYear += (nowYear < 2000) ? 1900 : 0;

// input type date에 맞춰 세팅(YYYY-mm-dd) 하여 시작, 종료일 반환
const getStartEnd = function (startDt, EndDt) {
    let setStart = startDt.getFullYear()+"-"+("0"+(1+startDt.getMonth())).slice(-2)+"-"+("0"+startDt.getDate()).slice(-2);
    let setEnd = EndDt.getFullYear()+"-"+("0"+(1+EndDt.getMonth())).slice(-2)+"-"+("0"+EndDt.getDate()).slice(-2);

    const returnObj = {
        startDt: setStart,
        endDt: setEnd
    };

    return returnObj;
}

//어제
const getYesterday = function() {
    let yesterday = new Date(nowYear, nowMonth, nowDay-1);

    return getStartEnd(yesterday,new Date());
}

//오늘
const getToday = function() {
    return getStartEnd(now,now);
}

// 이번주
const getThisWeek = function () {
    let weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
    let weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));

    return getStartEnd(weekStartDate,weekEndDate);
}

//이번달
const getThisMonth = function () {
    let monthStartDate = new Date(nowYear, nowMonth ,1);
    let monthEndDate = new Date(nowYear, nowMonth+1, 0);

    return getStartEnd(monthStartDate,monthEndDate);
}

//지난주
const getLastWeek = function () {
    let lastDay = new Date(nowYear, nowMonth, nowDay-7);
    let lastDayOfWeek = lastDay.getDay();
    let weekStartDate = new Date(nowYear, lastDay.getMonth(), lastDay.getDate() - lastDayOfWeek);
    let weekEndDate = new Date(nowYear, lastDay.getMonth(), lastDay.getDate() + (6 - lastDayOfWeek));

    return getStartEnd(weekStartDate,weekEndDate);
}

//지난달
const getLastMonth = function () {
    let monthStartDate = new Date(nowYear, nowMonth-1,1);
    let monthEndDate = new Date(nowYear, nowMonth, 0);

    return getStartEnd(monthStartDate,monthEndDate);
}

//버튼에 따라 시작, 종료일 반환
const getFormatDate = function (type) {
    switch (type){
        case "btn_yesterday":
            return getYesterday();
        case "btn_today":
            return getToday();
        case "btn_this_week":
            return getThisWeek();
        case "btn_this_month":
            return getThisMonth();
        case "btn_last_week":
            return getLastWeek();
        case "btn_last_month":
            return getLastMonth();
        default :
    }
}