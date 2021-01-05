<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up Page</title>
    <meta name="description" content="Sign Up Page">
    <meta name="author" content="Sookyeong">
    <link rel="icon" type="image/png" href="image/icia-logo.png">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <!-- Header -->
    <header id="header">
        <h1><a href="https://www.icia.co.kr/"><img src="image/icia-logo.png" alt=""></a></h1>
    </header>
    <div id="message">${message }</div>
    <!-- Secion -->
    <section id="section">
        <form action="Join" method="post">
            <div class="id">
                <h3 class="join__title"><label for="id">아이디</label></h3>
                <span class="input__space"><input type="text" name="joinInfo" minlength="3" maxlength="10" title="ID" required></span>
            </div>
            <div class="pw">
                <h3 class="join__title"><label for="password">비밀번호</label></h3>
                <span class="input__space"><input type="password" name="joinInfo" minlength="4" maxlength="10" title="비밀번호 입력" required></span>
            </div>
            <div class="pw2">
                <h3 class="join__title"><label for="password">비밀번호 재확인</label></h3>
                <span class="input__space"><input type="password" id="password" minlength="4" maxlength="10" title="비밀번호 재확인 입력" required onBlur="pwdCheck()"></span>
            </div>
            <div class="name">
                <h3 class="join__title"><label for="name">이름</label></h3>
                <span class="input__space"><input type="text" name="joinInfo" title="이름" required></span>
            </div>
            
           
            <div class="tel">
                <h3 class="join__title"><label for="tel">휴대전화</label></h3>
                <div class="tel__wrap">
               
                    <div class="tel__wrap__input">
                        <span class="input__space"><input type="tel" name="joinInfo" placeholder="전화번호 입력"></span>
                    
                    </div>
                </div>
            </div>
            <div class="submit">
                <button type="submit" id="submit">가입하기</button>
            </div>
        </form>
        <span onClick="movePage(true)">[LogIn]</span>
        <span onClick="movePage(false)">[Previous]</span>
    </section>
    <!-- Footer -->
    <footer id="footer">
        <span class="footer__icon"><a href="https://www.icia.co.kr/"><img src="image/icia-logo.png" alt="" id="footer__icon"></a></span>
        <span class="footer__rights">Copyright <b>ICIA.</b> All Rights Reserved.</span>
    </footer>
</body>
<script>
function movePage(selection){
	var form = document.createElement("form");
	
	form.action = (selection)?"LogInForm":"${prev}";
	form.method = "post";
	
	// Current Page정보
	var page = document.createElement("input");
	page.type = "hidden";
	page.name = "page";
	page.value = "JoinForm";
	form.appendChild(page);
	
	document.body.appendChild(form);
	form.submit();
}

function pwdCheck(){
	// 패스워드와 패스워드재확인의 데이터가 일치하는지 확인
	var pwd = document.getElementsByName("joinInfo")[1];
	var pwdCopy = document.getElementById("password");
	
	if(pwd.value != pwdCopy.value){
		pwd.value = "";
		pwdCopy.value = "";
		pwd.placeholder = "패스워드가 일치하지 않아요~ 다시 입력 부탁드려요"
		pwd.focus();
	}
}
</script>
</html>
