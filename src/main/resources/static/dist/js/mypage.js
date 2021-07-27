function ViewLayerLogin(){
    if(document.getElementById("PopLogin").style.display=="none"){
        document.getElementById("PopLogin").style.display='inline'
    }else{
        document.getElementById("PopLogin").style.display='none'
    }
}
function ViewLayerMyPage(){
    if(document.getElementById("PopMyPage").style.display=="none"){
        document.getElementById("PopMyPage").style.display='inline'
    }else{
        document.getElementById("PopMyPage").style.display='none'
    }
}



$(document).ready(function() {

    $("#openModalPop").click(function() {
        $("#banner_online").fadeIn();
        $("#modal").fadeIn();
    });

    $("#close_button").click(function(){
        $("#banner_online").fadeOut();
        $("#modal").fadeOut();
    });

});