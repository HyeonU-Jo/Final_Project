/*function dayMath(sDay, eDay) {
    var x = $('#sDay').val()
    var y = $('#eDay').val()
    day1 = new Date(x)
    day2 = new Date(y)
    var day = Math.ceil(day2.getTime() - day1.getTime());
    var dayday = day / (1000 * 3600 * 24) + 1;
    console.log(dayday)

}*/
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
    const newDiv = document.createElement('div');
    const newBr= document.createElement('br');
    for (var i = 0; i < dayday; i++) {

        // 2. <div>에 들어갈 text node 만들기
        const newText = document.createTextNode((i+1) + 'Day');
        // 3. <div>에 text node 붙이기
        newDiv.appendChild(newText);
        // 4. <body>에 1에서 만든 <div> element 붙이기
        document.body.appendChild(newDiv);
        document.body.appendChild(newBr);
    }
}