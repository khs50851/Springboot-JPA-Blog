package com.hydev.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hydev.blog.model.RoleType;
import com.hydev.blog.model.User;
import com.hydev.blog.repository.UserRepository;

@RestController //html파일이 아니라 data를 리턴해주는 컨트롤러
public class DummyControllerTest {
	
	//의존성 주입
	@Autowired // DummyControllerTest 이게 메모리에 뜰때 아래 userRepository도 같이 메모리에 뜸
	private UserRepository userRepository;
	
	@DeleteMapping("dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제에 실패 하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제 되었습니다. id : "+id;
	}
	
	
	// save 함수는 id를 전달하지 않으면 insert를 해주고 id를 전달하면 해당 id에 대해 데이터가 있으면 update
	@Transactional // 컨트롤러 호출시 시작하고 메소드 종료시 commit 실행
	@PutMapping("/dummy/user/{id}") // get이랑 같은데 이건 put이므로 알아서 구분
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터 받을건데 requestbody 필요. json 데이터를 요청 -> Java Object로 스프링이 알아서 변경해줌(메세지 컨버터의 Jackson라이브러리)
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정 실패");
			
		}); // 해당 유저를 찾음
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(user); 첫번째 방법 transactional 안붙인상태
		
		// 더티 체킹
		return user;
	}
	
	
	
	
	
	
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//http://localhost:8000/blog/dummy/user?page=1 뒤에 ?page= 해당 페이지 번호 붙여서 이동가능
	// 한페이지당 2건에 데이터를 리턴 받아 볼 예정
	// 스프링부트에서 제공하는 페이징 기능 이용
	
	/* 기본형
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){
		
		Page<User> users =  userRepository.findAll(pageable);
		return users;
	}
	*/
	
	/* 변환 1 : 페이징한 유저 정보만
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){
		
		List<User> users =  userRepository.findAll(pageable).getContent(); // content 키 값은 list이므로 이렇게 가져올 수 있음
		return users;
	}
	*/
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){
		
		Page<User> pagingUser =  userRepository.findAll(pageable); 
		/* 이런식으로 분기처리도 가능
		if(pagingUser.isFirst()) {
			
		}
		if(pagingUser.isLast()) {
			
		}
		*/
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/5
	@GetMapping("dummy/user/{id}")
	public User detail(@PathVariable int id) { // id 파라미터 그대로 적음
		
		
		
		/* Throw Supplier
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		*/
		
		// 위의 것을 람다식으로 바꾼것
		User user = userRepository.findById(id).orElseThrow(()-> { // new supplier~ 이 이후 다 생략 가능
			return new IllegalArgumentException("해당 사용자는 없습니다. id : "+id);
		});
		
		
		
		/* orElseGet 정리
		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
				// TODO Auto-generated method stub
				return new User(); // 정상적으로 id 2번 이런식으로 셀렉트 타면 바로 user가 들어오지만 5번같이 없는 id번호로 하면 이 메소드가 실행되서 빈객체를 유저에 넣음
				
		}
 	 }); // 타입이 optional인데 만약 저장되지 않은 id 4번을 찾으면 db에서 못찾으면 user가 null이 되어버림
		 그럼 return null이니 프로그램에 문제가 있을 수 있음
		 그래서 optional로 user객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return하라는 뜻
		 optional은 많은 함수를 반환함 첫번째가 .get 바로 findById에서 뽑아서 줌
		 .orElseGet : 없으면 객체 하나 만들어서 user에 넣음 (여기에 넣을 파라미터는 supplier타입(그리고 제네릭 부분에 ?라 되어있는데 여기에 new Supplier<User>() 이런식으로 익명 객체 거기에 이 인터페이스가 들고있는 get 오버라이딩 해서 호출
	*/	
		
		// 요청 : 웹브라우저
		// user 객체 : 자바 오브젝트
		// user 객체를 리턴하는데 웹 브라우저는 이런 자바 오브젝트를 이해하지 못함
		// user 객체가 리턴될때 웹 브라우저가 이해할 수 있는 데이터로 변환(가장좋은게 json)
		// 스프링 부트 = 메세지 컨버터 기능으로 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 메세지 컨버터가 Jackson이라는 라이브러리를 호출해서 
		// User오브젝트를 json으로 변환해서 브라우저에 던져줌
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join(요청)
	// http의 바디에 유저네임,패스워드,이메일 데이터를 가지고 (요청) 하면 아래 매개변수들이 들어감
	@PostMapping("/dummy/join")
	public String join(User user) { //변수명 정확히 적으면 RequestParam 생략 가능
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		user.setRole(RoleType.USER); // 일반 스트링으로 값 못넣음 
		userRepository.save(user);
		// 세이브할때 role에 ? 자리에 null이 들어가서 디폴트 설정한 user가 안들어감 그래서
		// Dynamicinsert 어노테이션을 붙여야함 이렇게 하면 jpa에서 insert할때 null인 필드 제외시켜줌
		return "회원 가입이 완료 되었습니다.";
		
	}
	
}
