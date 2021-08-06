

/**onclick이벤트 **/
function createDiv(sDay, eDay) {
    var x = $('#sDay').val();
    var y = $('#eDay').val();
    let day1 = new Date(x);
    let day2 = new Date(y);
    var day = Math.ceil(day2.getTime() - day1.getTime());
    var dayday = day / (1000 * 3600 * 24) + 1; //일

    /*plandiv1안으로 들어가짐*/
    const plandiv2 = document.createElement('div');

    plandiv2.classList.add('infobox');

    for (var i = 0; i < dayday; i++) {
        const newDiv = document.createElement('div');
        newDiv.setAttribute("name",'cDay');
        newDiv.id = "num";
        newDiv.classList.add(i);


        const newDivList = document.createElement('div');
        newDivList.id = "result"

        const newBtn = document.createElement('button');
        newBtn.innerText = "+";

        const newBr = document.createElement('br');
        const newBr2 = document.createElement('br');

        let newA = document.createElement('a');

        newA.addEventListener("click", function () {
            window.open("test", "test", "width+600, height=400");
        });

        newA.id = i;
        const count = i;

        newBtn.addEventListener('click', function () {
            window.name = "plForm"
            url = "planLikeList?id=" + count;
            window.open(url, "liForm", "width=600,height=400");
        });

        const newText = document.createTextNode((i + 1) + 'Day');

        newDiv.appendChild(newText);
        newDiv.appendChild(newBr);
        newDiv.appendChild(newBtn);
        newDiv.appendChild(newBr2)
        plandiv2.appendChild(newDiv);
        newDiv.appendChild(newA);
    }
    var targetDiv = document.getElementsByClassName("plandiv1")[0];
    targetDiv.insertBefore(plandiv2, targetDiv.childNodes[1]);
}

/*동일 아이디값의 밸류 가져오기*/
function stealName(){
    document.getElementById("sDay2").value = document.getElementById("sDay").value;
    document.getElementById("eDay2").value = document.getElementById("eDay").value;
}

/*새로고침기능*/
function refreshPage() {
    window.location.reload();
    alert("날짜를 다시 선택하여 주세요")
    return;
}

/*파라미터 반환*/
function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) {
            sval = temp[1];
        }
    }
    return sval;
}

/*부모창으로 값 전달*/
function setParentText() {
    const obj_length = document.getElementsByName("likeList").length;
    let listTest = [];
    for (let i = 0; i < obj_length; i++) {
        if (document.getElementsByName("likeList")[i].checked == true) {
            listTest += document.getElementsByName("likeList")[i].value + " ";
            window.close();
        }
    }

    opener.document.getElementById(getParam("id")).innerText = listTest.toString();
    window.close();
}

/**달력**/
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
        if (flag && !isValidStr(sDay)) {
            var sdp = sDate.datepicker().data("datepicker");
            sdp.selectDate(new Date(sDay.replace(/-/g, "/")));
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