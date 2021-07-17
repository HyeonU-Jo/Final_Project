/**onclick이벤트 **/
function createDiv(sDay, eDay) {
    var x = $('#sDay').val();
    var y = $('#eDay').val();
    day1 = new Date(x);
    day2 = new Date(y);
    var day = Math.ceil(day2.getTime() - day1.getTime());

    var dayday2 = day / (1000 * 3600 * 24); //박
    var dayday = day / (1000 * 3600 * 24) + 1; //일
    console.log(dayday2 + "박" + dayday + "일");


    // 1. <div> element 만들기
    for (var i = 0; i < dayday; i++) {

        // 2. <div>에 들어갈 text node 만들기
        const newDiv = document.createElement('div');
        const newBtn = document.createElement('button');
        newDiv.id="num";
        newDiv.classList.add(i);
        newBtn.innerText="+";
        const newText = document.createTextNode((i+1) + 'Day');

        //2-1. 이벤트 발생
        newBtn.addEventListener('click', function () {
            alert("눌렀지롱");
            url="plan_popup";
            window.open(url, "get", "width=600,height=400");
        });

        // 3. <div>에 text node 붙이기
        newDiv.appendChild(newText);
        newDiv.appendChild(newBtn);

        // 4. <body>에 1에서 만든 <div> element 붙이기
        document.body.appendChild(newDiv);
    }
}
/**달력 **/
function datePickerSet(sDate, eDate, flag) {
    //시작 ~ 종료 2개 짜리 달력 datepicker
    if (!isValidStr(sDate) && !isValidStr(eDate) && sDate.length > 0 && eDate.length > 0) {
        sDay = sDate.val();
        eDay = eDate.val();
        if (flag && !isValidStr(sDay) && !isValidStr(eDay)) { //처음 입력 날짜 설정, update...
            var sdp = sDate.datepicker().data("datepicker");
            sdp.selectDate(new Date(sDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
            var edp = eDate.datepicker().data("datepicker");
            edp.selectDate(new Date(eDay.replace(/-/g, "/")));  //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
        }
        //시작일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
        if (!isValidStr(eDay)) {
            sDate.datepicker({
                maxDate: new Date(eDay.replace(/-/g, "/"))
            });
        }
        sDate.datepicker({
            language: 'ko',
            autoClose: true,
            onSelect: function () {
                datePickerSet(sDate, eDate);
            }
        });
        //종료일자 세팅하기 날짜가 없는경우엔 제한을 걸지 않음
        if (!isValidStr(sDay)) {
            eDate.datepicker({
                minDate: new Date(sDay.replace(/-/g, "/"))
            });
        }
        eDate.datepicker({
            language: 'ko',
            autoClose: true,
            onSelect: function () {
                datePickerSet(sDate, eDate);
            },
        });
        //한개짜리 달력 datepicker
    } else if (!isValidStr(sDate)) {
        var sDay = sDate.val();
        if (flag && !isValidStr(sDay)) { //처음 입력 날짜 설정, update...
            var sdp = sDate.datepicker().data("datepicker");
            sdp.selectDate(new Date(sDay.replace(/-/g, "/"))); //익스에서는 그냥 new Date하면 -을 인식못함 replace필요
        }
        sDate.datepicker({
            language: 'ko',
            autoClose: true
        });
    }

    function isValidStr(str) {
        if (str == null || str == undefined || str == "")
            return true;
        else
            return false;
    }
}