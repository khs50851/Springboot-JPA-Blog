let index = {
	init:function(){
		$("#btn-save").on("click",()=>{ // 이건 화살표 함수인데 function(){} 이렇게 안하는 이유는 this를 바인딩 하기 위해 function으로 사용하면 this값이 달라질 수 있음 펑션 사용할거면 let _this = this; 이렇게 먼저 바인딩 해놓고 아래에 _this.save() 이렇게 사용
			this.save();
		}); // 첫번쨰 파라미터는 어떤 이벤트가 될지, 두번째 파라미터는 무엇을 할지
		
		
		$("#btn-login").on("click",()=>{ // 이건 화살표 함수인데 function(){} 이렇게 안하는 이유는 this를 바인딩 하기 위해 function으로 사용하면 this값이 달라질 수 있음 펑션 사용할거면 let _this = this; 이렇게 먼저 바인딩 해놓고 아래에 _this.save() 이렇게 사용
			this.login();
		}); 
	},
	
	save:function(){
		//alert('user의 save 함수 호출됨');
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		// console.log(data);
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바스크립트 오브젝트로 변환
		$.ajax({
			// 회원 가입 수행 요청 응답의 결과가 정상이면 done 아니면 fail
			// ajax 호출시 default가 비동기 호출
			type:"POST",
			url:"/blog/api/user/",
			data:JSON.stringify(data), // 위에 변수 받은 data는 자바스크립트 오브젝트인데 자바쪽으로 던지면 자바가 이해를 못함 그래서 json으로 변경해야함
			// 이렇게 보내는 데이터는 http body 데이터임 그래서 마임타입을 지정해야함 contentType으로 지정
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
			
			
		}).done(function(resp){
			alert("회원 가입이 완료 되었습니다.");
			console.log(resp);
			location.href="/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	},
	
	login:function(){
		//alert('user의 save 함수 호출됨');
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
		}
		// console.log(data);
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바스크립트 오브젝트로 변환
		$.ajax({
			// 회원 가입 수행 요청 응답의 결과가 정상이면 done 아니면 fail
			// ajax 호출시 default가 비동기 호출
			type:"POST",
			url:"/blog/api/user/login",
			data:JSON.stringify(data), // 위에 변수 받은 data는 자바스크립트 오브젝트인데 자바쪽으로 던지면 자바가 이해를 못함 그래서 json으로 변경해야함
			// 이렇게 보내는 데이터는 http body 데이터임 그래서 마임타입을 지정해야함 contentType으로 지정
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
			
			
		}).done(function(resp){
			alert("로그인이 완료 되었습니다");
			console.log(resp);
			location.href="/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	}
}

index.init();