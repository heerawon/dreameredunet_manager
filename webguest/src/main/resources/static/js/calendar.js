/*
  Author: Jack Ducasse;
  Version: 0.1.0;
  (◠‿◠✿)

  참고한 라이브러리 - 수정버전
  https://github.com/jackducasse/caleandar
*/
var Calendar = function(model, options, date){
    // Default Values
    this.Options = {
        NavShow: true,
        NavVertical: false,
        NavLocation: '',
        DateTimeShow: true,
        DateTimeFormat: 'yyyy,mm',
        DatetimeLocation: '',
        EventClick: '',
        EventTargetWholeDay: false,
        DisabledDays: [],
        ModelChange: model
    };
    // Overwriting default values
    for(var key in options){
        this.Options[key] = typeof options[key]=='string'?options[key].toLowerCase():options[key];
    }

    model?this.Model=model:this.Model={};
    this.Today = new Date();

    this.Selected = this.Today
    this.Today.Month = this.Today.getMonth();
    this.Today.Year = this.Today.getFullYear();
    if(date){this.Selected = date}
    this.Selected.Month = this.Selected.getMonth();
    this.Selected.Year = this.Selected.getFullYear();

    // // console.error(model);
    // console.error(options);
    // // console.error(date);
    if(options.EventClick){
        this.EventClick = options.EventClick;
    }
    this.Selected.Days = new Date(this.Selected.Year, (this.Selected.Month + 1), 0).getDate();
    this.Selected.FirstDay = new Date(this.Selected.Year, (this.Selected.Month), 1).getDay();
    this.Selected.LastDay = new Date(this.Selected.Year, (this.Selected.Month + 1), 0).getDay();

    this.Prev = new Date(this.Selected.Year, (this.Selected.Month - 1), 1);
    if(this.Selected.Month==0){this.Prev = new Date(this.Selected.Year-1, 11, 1);}
    this.Prev.Days = new Date(this.Prev.getFullYear(), (this.Prev.getMonth() + 1), 0).getDate();
};

function createCalendar(calendar, element, adjuster){
    if(typeof adjuster !== 'undefined'){
        var newDate = new Date(calendar.Selected.Year, calendar.Selected.Month + adjuster, 1);
        calendar = new Calendar(calendar.Model, calendar.Options, newDate);
        element.innerHTML = '';
    }else{
        for(var key in calendar.Options){
            typeof calendar.Options[key] != 'function' && typeof calendar.Options[key] != 'object' && calendar.Options[key]?element.className += " " + key + "-" + calendar.Options[key]:0;
        }
    }
    var months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

    function AddSidebar(){
        var sidebar = document.createElement('div');
        sidebar.className += 'cld-sidebar';

        var monthList = document.createElement('ul');
        monthList.className += 'cld-monthList';

        for(var i = 0; i < months.length - 3; i++){
            var x = document.createElement('li');
            x.className += 'cld-month';
            var n = i - (4 - calendar.Selected.Month);
            // Account for overflowing month values
            if(n<0){n+=12;}
            else if(n>11){n-=12;}
            // Add Appropriate Class
            if(i==0){
                x.className += ' cld-rwd cld-nav';
                x.addEventListener('click', function(){
                    typeof calendar.Options.ModelChange == 'function'?calendar.Model = calendar.Options.ModelChange():calendar.Model = calendar.Options.ModelChange;
                    createCalendar(calendar, element, -1);
                });
            }
            else if(i==months.length - 4){
                x.className += ' cld-fwd cld- ';
                x.addEventListener('click', function(){
                    typeof calendar.Options.ModelChange == 'function'?calendar.Model = calendar.Options.ModelChange():calendar.Model = calendar.Options.ModelChange;
                    createCalendar(calendar, element, 1);
                } );
            }
            else{
                if(i < 4){x.className += ' cld-pre';}
                else if(i > 4){x.className += ' cld-post';}
                else{x.className += ' cld-curr';}

                //prevent losing var adj value (for whatever reason that is happening)
                (function () {
                    var adj = (i-4);
                    //x.addEventListener('click', function(){createCalendar(calendar, element, adj);console.log('kk', adj);} );
                    x.addEventListener('click', function(){
                        typeof calendar.Options.ModelChange == 'function'?calendar.Model = calendar.Options.ModelChange():calendar.Model = calendar.Options.ModelChange;
                        createCalendar(calendar, element, adj);
                    });
                    x.setAttribute('style', 'opacity:' + (1 - Math.abs(adj)/4));
                    x.innerHTML += months[n].substr(0,3);
                }()); // immediate invocation

                if(n==0){
                    var y = document.createElement('li');
                    y.className += 'cld-year';
                    if(i<5){
                        y.innerHTML += calendar.Selected.Year;
                    }else{
                        y.innerHTML += calendar.Selected.Year + 1;
                    }
                    monthList.appendChild(y);
                }
            }
            monthList.appendChild(x);
        }
        sidebar.appendChild(monthList);
        if(calendar.Options.NavLocation){
            document.getElementById(calendar.Options.NavLocation).innerHTML = "";
            document.getElementById(calendar.Options.NavLocation).appendChild(sidebar);
        }
        else{element.appendChild(sidebar);}
    }

    var mainSection = document.createElement('div');
    mainSection.className += "cld-main";

    //년월 생성
    function AddDateTime(){
        var datetime = document.createElement('div');
        datetime.className += "cld-datetime";
        if(calendar.Options.NavShow && !calendar.Options.NavVertical){
            var rwd = document.createElement('div');
            rwd.className += " cld-rwd cld-nav";
            rwd.addEventListener('click', async function(){
                //비동기 처리
                if(typeof calendar.Options.ModelChange == 'function'){
                    const newDate = new Date(calendar.Selected.Year, calendar.Selected.Month - 1,1);
                    calendar.Model = await calendar.Options.ModelChange(newDate);
                }else{
                    calendar.Model = calendar.Options.ModelChange
                }
                await createCalendar(calendar, element, -1);
            });
            datetime.appendChild(rwd);
        }
        var today = document.createElement('div');
        today.className += ' today';
        today.innerHTML = `${calendar.Selected.Year}년 ${months[calendar.Selected.Month]}`;
        datetime.appendChild(today);
        if(calendar.Options.NavShow && !calendar.Options.NavVertical){
            var fwd = document.createElement('div');
            fwd.className += " cld-fwd cld-nav";
            fwd.addEventListener('click', async function(){
                //비동기 처리
                if(typeof calendar.Options.ModelChange == 'function'){
                    const newDate = new Date(calendar.Selected.Year, calendar.Selected.Month + 1,1);
                    calendar.Model = await calendar.Options.ModelChange(newDate);
                }else{
                    calendar.Model = calendar.Options.ModelChange
                }
                createCalendar(calendar, element, 1);
            } );
            datetime.appendChild(fwd);
        }
        if(calendar.Options.DatetimeLocation){
            document.getElementById(calendar.Options.DatetimeLocation).innerHTML = "";
            document.getElementById(calendar.Options.DatetimeLocation).appendChild(datetime);
        }
        else{mainSection.appendChild(datetime);}
    }

    // 요일 생성
    function AddLabels(){
        var labels = document.createElement('ul');
        labels.className = 'cld-labels';
        var labelsList = ["일", "월", "화", "수", "목", "금", "토"];
        for(var i = 0; i < labelsList.length; i++){
            var label = document.createElement('li');
            label.className += "cld-label";
            label.innerHTML = labelsList[i];
            labels.appendChild(label);
        }
        mainSection.appendChild(labels);
    }
    // 날짜 생성
    function AddDays(){
        // Create Number Element
        function DayNumber(n){
            var number = document.createElement('div');
            number.className += "cld-number";
            number.innerHTML += `<b>${n}</b>`;
            return number;
        }
        var days = document.createElement('ul');
        days.className += "cld-days";
        // Previous Month's Days
        for(var i = 0; i < (calendar.Selected.FirstDay); i++){
            var day = document.createElement('li');
            day.className += "cld-day prevMonth";
            //Disabled Days
            var d = i%7;
            for(var q = 0; q < calendar.Options.DisabledDays.length; q++){
                if(d===calendar.Options.DisabledDays[q]){
                    day.className += " disableDay";
                }
            }

            var number = DayNumber((calendar.Prev.Days - calendar.Selected.FirstDay) + (i+1));
            day.appendChild(number);

            days.appendChild(day);
        }


        // Current Month's Days
        for(var i = 0; i < calendar.Selected.Days; i++){
            var day = document.createElement('li');
            day.className += "cld-day currMonth";
            //Disabled Days
            var d = (i + calendar.Selected.FirstDay)%7;
            for(var q = 0; q < calendar.Options.DisabledDays.length; q++){
                if(d==calendar.Options.DisabledDays[q]){
                    day.className += " disableDay";
                }
            }
            var number = DayNumber(i+1);
            // Check Date against Event Dates
            // console.log(calendar);

            for(var n = 0; n < calendar.Model.length; n++){

                var evDate = calendar.Model[n].Date;
                var toDate = new Date(calendar.Selected.Year, calendar.Selected.Month, (i+1));

                if(evDate.getTime() == toDate.getTime()){
                    number.className += " eventday";

                    if(calendar.Model[n]?.Title){
                        let title;
                        if(typeof calendar.Model[n].Link === 'string'){
                            title = document.createElement('a');
                            title.setAttribute('href', calendar.Model[n].Link);
                            title.innerHTML += calendar.Model[n].Title;
                        }else{
                            title = document.createElement('span');
                            title.innerHTML = calendar.Model[n].Title;
                        }
                        title.classList.add('cld-title');
                        number.appendChild(title);
                    }


                    // 스케줄 입력
                    if(calendar.Model[n]?.Schedule){
                        if(typeof calendar.Model[n]?.Schedule ==="object" || calendar.Model[n]?.Schedule.length){

                            // 스케줄 작성
                            for(var j of calendar.Model[n].Schedule){
                                let elm;
                                if(j.link){
                                    elm = document.createElement('a' )
                                    elm.setAttribute('href',j.link);
                                }else{
                                    elm = document.createElement('span')
                                }

                                //클래스 추가
                                elm.classList.add('cld-schedule');
                               if(j.type){
                                    elm.classList.add(j.type);
                                }
                                elm.innerHTML = `${j.time ||''}${j.title}`;
                                number.appendChild(elm);
                            }
                        }
                    }
                }
            }
            day.appendChild(number);
            // If Today..
            if((i+1) == calendar.Today.getDate() && calendar.Selected.Month == calendar.Today.Month && calendar.Selected.Year == calendar.Today.Year){
                day.className += " today";
            }
            days.appendChild(day);
        }
        // Next Month's Days
        // Always same amount of days in calander
        var extraDays = 6;
        if(days.children.length>35){extraDays = 6;}
        else if(days.children.length<29){extraDays = 20;}

        for(var i = 0; i < (extraDays - calendar.Selected.LastDay); i++){
            var day = document.createElement('li');
            day.className += "cld-day nextMonth";
            //Disabled Days
            var d = (i + calendar.Selected.LastDay + 1)%7;
            for(var q = 0; q < calendar.Options.DisabledDays.length; q++){
                if(d==calendar.Options.DisabledDays[q]){
                    day.className += " disableDay";
                }
            }

            var number = DayNumber(i+1);
            day.appendChild(number);

            days.appendChild(day);
        }
        mainSection.appendChild(days);
    }

    element.appendChild(mainSection);

    if(calendar.Options.NavShow && calendar.Options.NavVertical){
        AddSidebar();
    }
    if(calendar.Options.DateTimeShow){
        AddDateTime();
    }
    AddLabels();
    AddDays();
}

// 캘린더 생성
function calendar(el, data, settings){
    var obj = new Calendar(data, settings);
    createCalendar(obj, el);
}
// 캘린더 생성
function calendarSel(el, data, settings, date){
    var obj = new Calendar(data,settings,date);
    createCalendar(obj, el);
}