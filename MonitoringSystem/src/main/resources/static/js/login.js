$(function() {
    $('#btnLogin').on("click", function(){
        	var nickname = $('#nickname').val();
        	if(nickname == '') {
        	    alert('아이디를 입력해주세요.');
        	    return;
        	}
        	var password = $('#password').val();
        	if(password == '') {
        	    alert('비밀번호를 입력해주세요.');
        	    return;
        	}
            var data = {nickname : nickname, password : password};
            	$.ajax({
            		url : '/login',
            		data: data,
                    type: "POST",
            		success : function(response) {
            		    if(response == 'fail'){
                            alert("아이디 또는 비밀번호를 잘못 입력하셨습니다.")
                        	window.location.href = '/login';
                        } else {
                            let memberId = response.split('/')[1]
                            setNotification(memberId);
                            window.location.href = '/';
                        }
            		},
            		error : function() {
            		    alert('실패!');
            		}
            	});
        });
})
function setNotification(id) {

        const eventSource = new EventSource('/subscribe/' + id);

        eventSource.addEventListener("sse", function (event) {
            console.log(event.data);

            const data = JSON.parse(event.data);

            (async () => {
                // 브라우저 알림
                const showNotification = () => {

                    const notification = new Notification('설정하신 조건에 맞는 리뷰가 새로 등록되었습니다.', {
                        body: data.content
                    });

                    setTimeout(() => {
                        notification.close();
                    }, 10 * 1000);

                    notification.addEventListener('click', () => {
                        window.open(data.url, '_blank');
                    });
                }

                // 브라우저 알림 허용 권한
                let granted = false;

                if (Notification.permission === 'granted') {
                    granted = true;
                } else if (Notification.permission !== 'denied') {
                    let permission = await Notification.requestPermission();
                    granted = permission === 'granted';
                }

                // 알림 보여주기
                if (granted) {
                    showNotification();
                }
            })();
        })
}