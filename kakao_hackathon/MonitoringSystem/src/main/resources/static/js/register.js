$(function() {
    $('#btnCheckName').on("click", function(){
    	var nickname = $('#nickname').val();
    	if(nickname == "") {
    	    $('#id_input_re_1').css("display","none");
            $('#id_input_re_2').css("display", "none");
            return;
    	}
        var data = {nickname : nickname};
        	$.ajax({
        		type : 'post',
        		url : '/memberIdCheck',
        		data : data,
        		success : function(result) {
        		    if(result != 'fail'){
                    	$('#id_input_re_1').css("display","inline-block");
                    	$('#id_input_re_2').css("display", "none");
                    } else {
                    	$('#id_input_re_2').css("display","inline-block");
                    	$('#id_input_re_1').css("display", "none");
                    }
        		},
        		error : function() {
        		    alert('실패!');
        		}
        	});
    });

    $('#password').on('propertychange change keyup paste input', matchPassword)
    $('#passwordCheck').on('propertychange change keyup paste input', matchPassword)

    $('form[name=joinForm]').bind('submit', function() {
        let idCheck = $('#id_input_re_1').css("display");
        if(idCheck == 'none') {
            alert('아이디를 다시 작성해주세요.');
            return false;
        }

        let pwCheck = $('#pw_input_re_2').css("display");
        if(pwCheck == 'none') {
            alert('비밀번호가 일치하지 않습니다.')
            return false;
        }

        let option = $('input[type=radio][name=department]:checked').val();
        if(option == undefined) {
          alert('부서를 선택해주세요');
          return false;
        }
    })
});

function matchPassword() {
    let pw1 = $('#password').val();
    let pw2 = $('#passwordCheck').val();
    if(pw1 == '' || pw2 == '') {
        $('#pw_input_re_1').css("display","none");
        $('#pw_input_re_2').css("display", "none");
        return;
    }

    if(pw1 !== pw2){
        $('#pw_input_re_1').css("display","inline-block");
        $('#pw_input_re_2').css("display", "none");
    } else {
        $('#pw_input_re_2').css("display","inline-block");
        $('#pw_input_re_1').css("display", "none");
    }
}