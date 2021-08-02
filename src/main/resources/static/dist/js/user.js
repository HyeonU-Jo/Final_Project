let index= {
    init: function () {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
    },

    save: function () {
        let data = {
            username: $("#username").val(),
            email: $("#email").val(),
            name: $("#name").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("회원가입이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });   //ajax통신을 이용해서 데이터를 json으로 변경하여 insert요청
    },

    update: function () {
        let data = {
            num: $("#num").val(),
            username: $("#username").val(),
            email: $("#email").val(),
            name: $("#name").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "PUT",
            url: "/member",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            alert("회원수정이 완료되었습니다. 다시 로그인 해주세요.");
            location.href = "/logout";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });   //ajax통신을 이용해서 데이터를 json으로 변경하여 insert요청
    },


}

index.init();