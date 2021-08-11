let index= {
    init: function () {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteById();
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
            if(resp.status===500){
                alert("회원가입에 실패하였습니다.");
            }else{
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            }

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });   //ajax통신을 이용해서 데이터를 json으로 변경하여 insert요청
    },

    update: function () {
        let data = {
            id: $("#id").val(),
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
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });   //ajax통신을 이용해서 데이터를 json으로 변경하여 insert요청
    },

    deleteById: function () {
        let id = $("#id").val();

        $.ajax({
            type: "DELETE",
            url: "/auth/member/"+id,
            dataType: "json"
        }).done(function (resp) {
            alert("회원 탈퇴가 완료되었습니다.");
                location.href = "/logout";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();