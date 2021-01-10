var main = {    // 변수의 속성으로 function 을 추가한 이유
                // 다른 js 파일에 동일한 이름의 function 이 존재한다면, 브라우저의 scope 는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js 에게 덮어씌워져버림. 그래서 var main 이란 객체를 만들어 해당 객체에서 필요한 모든 function 을 선언하는 것
                // 그렇게 하면 main 객체 안에서만 function 이 유효하기 때문에 다른 js 와 겹칠 위험이 사라짐
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {  // btn-update 란 id 를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때 update function 을 실행하도록 이벤트를 등록
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';     // 글 등록이 성공하면 메인페이지(/)로 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {  // 위에서 등록했던 update function
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,   // 어느 게시글을 수정할지 URL Path 로 구분하기 위해 Path 에 id 를 추가
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';    // 글 수정이 성공하면 메인페이지(/)로 이동
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
    }

};

main.init();