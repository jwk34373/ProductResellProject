var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click',  function() {
            _this.save();
        })
        $('#btn-update').on('click', function(){
            _this.update();
        })
        $('#btn-delete').on('click', function(){
            _this.delete();
        })
        $('#btn-requestLogin').on('click', function(){
            _this.login();
        })
        $('#btn-requestSignup').on('click', function(){
            _this.signup();
        })
    },
    save : function() {
        var formData = new FormData();
        formData.append("title", $('#title').val());
        formData.append("content", $('#content').val());
        formData.append("file", $('#file')[0].files[0]);

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            processData: false,
            contentType: false,
            data: formData
        }).done(function() {
            alert('물품이 등록됨');
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var formData = new FormData();
        formData.append("title", $('#title').val());
        formData.append("content", $('#content').val());
        formData.append("file", $('#file')[0].files[0]);

            var id = $('#id').val();

            $.ajax({
                type: 'PUT',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 수정되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },
    delete : function () {
            var id = $('#id').val();

            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                alert('글이 삭제되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },
    login : function () {
        var data = {
            userId: $('#userId').val(),
            userPwd: $('#userPwd').val()
        };
        $.ajax({
            type: 'POST',
            url: '/login/request',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('login success');
            window.location.href = '/';
        }).fail(function (error) {
            alert('login fail');
            //data: JSON.stringify(data)
        });
    },
    signup: function(){
        var data = {
            userId: $('#userId').val(),
            userPwd: $('#userPwd').val(),
            userPwdCheck: $('#userPwdCheck').val(),
            name: $('#userName').val(),
            role: $(':radio[name="role"]:checked').val()
        };
        $.ajax({
            type: 'POST',
            url: '/signup/request',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('sign up.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();
