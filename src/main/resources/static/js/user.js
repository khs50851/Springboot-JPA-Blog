let index = {
	init:function(){
		$("#btn-save").on("click",()=>{
			this.save();
		}); // 첫번쨰 파라미터는 어떤 이벤트가 될지, 두번째 파라미터는 무엇을 할지
	},
	
	save:function(){
		//alert('user의 save 함수 호출됨');
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		//console.log(data);
		$.ajax().done().fail(); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
	
}

index.init();