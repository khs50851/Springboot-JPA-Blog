let index = {
	init:function(){
		$("#btn-save").on("click",()=>{ // 이건 화살표 함수인데 function(){} 이렇게 안하는 이유는 this를 바인딩 하기 위해 function으로 사용하면 this값이 달라질 수 있음 펑션 사용할거면 let _this = this; 이렇게 먼저 바인딩 해놓고 아래에 _this.save() 이렇게 사용
			this.save();
		}); // 첫번쨰 파라미터는 어떤 이벤트가 될지, 두번째 파라미터는 무엇을 할지
		$("#btn-delete").on("click",()=>{ 
			this.deleteById();
		});
		$("#btn-update").on("click",()=>{ 
			this.update();
		});
		$("#btn-reply-save").on("click",()=>{ 
			this.replySave();
		});
		
		//$("#btn-login").on("click",()=>{ // 이건 화살표 함수인데 function(){} 이렇게 안하는 이유는 this를 바인딩 하기 위해 function으로 사용하면 this값이 달라질 수 있음 펑션 사용할거면 let _this = this; 이렇게 먼저 바인딩 해놓고 아래에 _this.save() 이렇게 사용
		//	this.login();
		//}); 
	},
	save:function(){
		//alert('user의 save 함수 호출됨');
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		}
		$.ajax({
			type:"POST",
			url:"/api/board",
			data:JSON.stringify(data), // 위에 변수 받은 data는 자바스크립트 오브젝트인데 자바쪽으로 던지면 자바가 이해를 못함 그래서 json으로 변경해야함
			// 이렇게 보내는 데이터는 http body 데이터임 그래서 마임타입을 지정해야함 contentType으로 지정
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
		}).done(function(resp){
			alert("글쓰기가 완료 되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}, // save 함수 끝
	// delete 함수 시작
	deleteById:function(){ 
		let id = $("#id").text(); // 아이디 값의 밸류값이 아닌 text를 뽑아야함
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			console.log(resp.status);
			alert("삭제가 완료되었습니다.");
			
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}, //delete 함수 끝 
	// update 함수 시작
	update:function(){
		let id=$("#id").val();
		
		let data = {
			title:$("#title").val(),
			content:$("#content").val(),
		};
		
		console.log(id);
		console.log(data);
		$.ajax({
			type:"PUT",
			url:"/api/board/"+id,
			data:JSON.stringify(data), // 위에 변수 받은 data는 자바스크립트 오브젝트인데 자바쪽으로 던지면 자바가 이해를 못함 그래서 json으로 변경해야함
			// 이렇게 보내는 데이터는 http body 데이터임 그래서 마임타입을 지정해야함 contentType으로 지정
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
		}).done(function(resp){
			console.log(resp);
			alert("글수정이 완료 되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}, // update 함수 끝
	
	replySave:function(){ // replySave 시작
		//alert('user의 save 함수 호출됨');
		let data = {
			userid:$("#userid").val(),
			content:$("#reply-content").val(),
			boardid:$("#boardid").val()
		}
		
		console.log(data);
		
		$.ajax({
			type:"POST",
			url:`/api/board/${data.boardid}/reply`,
			data:JSON.stringify(data), // 위에 변수 받은 data는 자바스크립트 오브젝트인데 자바쪽으로 던지면 자바가 이해를 못함 그래서 json으로 변경해야함
			// 이렇게 보내는 데이터는 http body 데이터임 그래서 마임타입을 지정해야함 contentType으로 지정
			contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
		}).done(function(resp){
			alert("댓글 작성이 완료 되었습니다.");
			console.log(resp);
			location.href=`/board/${data.boardid}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	},
	
	replyDelete:function(boardid,replyid){ // 댓글삭제 시작
		
		$.ajax({
			type:"DELETE",
			url:`/api/board/${boardid}/reply/${replyid}`,
			dataType:"json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 버퍼로 오기때문에 모든 것이 String(문자열)임 근데 생긴게 json(json으로 지정하면)이라면 -> javascript오브젝트로 변경해줌
		}).done(function(resp){
			alert("댓글 삭제가  완료 되었습니다.");
			console.log(resp);
			location.href=`/board/${boardid}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		
	} // 댓글 삭제 끝
	
}

index.init();