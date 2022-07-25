package com.sp.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sp.app.domain.Member;
import com.sp.app.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public void insertMember(Member dto) throws Exception {
		try {
			memberRepository.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Override
	public Member loginMember(Map<String, String> map) throws Exception {
		Member dto = null;
		
		try {
			dto = memberRepository.loginMember(map.get("UserId"), map.get("UserPwd"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return dto;
	}
	

	@Override
	public void updateMember(Member dto) throws Exception {
		try {
			// .save()는 없으면 추가되고, 있으면 수정이 된다.
			memberRepository.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Override
	public void deleteMember(String UesrId) throws Exception {
		try {
			// .deleteById()를 통해서 찾아 지울 수 있음
			memberRepository.deleteById(UesrId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	// 게시글 상세
	@Override
	public Member readMember(String id) {
		Member dto = null;
		
		try {
			// Optional클래스는 null값에 대한 처리를 좀 더 깔끔하게 할 수 있도록 도와준다.
			Optional<Member> member = memberRepository.findById(id);
			dto= member.get();
		} catch (NoSuchElementException e) {
			// 데이터 없으면 오류
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}


	// 카카오 로그인	
	@SuppressWarnings("deprecation")
	@Override
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            
			sb.append("&client_id=본인발급키"); //본인이 발급받은 key
			sb.append("&redirect_uri=본인설정주소"); // 본인이 설정한 주소
            
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
            
			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
            
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);
            
			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
            
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
			//System.out.println("access_token : " + access_Token);
			//System.out.println("refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return access_Token;
	}


	@Override
	public HashMap<String, Object> getKakaoInfo(String access_Token) {
		HashMap<String, Object> kakaoInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
		URL url = new URL(reqURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		// 요청에 필요한 Header에 포함될 내용
		conn.setRequestProperty("Authorization", "Bearer " + access_Token);

		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line = "";
		String result = "";

		while ((line = br.readLine()) != null) {
			result += line;
		}
		System.out.println("response body : " + result);

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);

		JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
		JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

		String user_name = properties.getAsJsonObject().get("nickname").getAsString();
		String user_id = kakao_account.getAsJsonObject().get("email").getAsString();

		kakaoInfo.put("user_name", user_name);
		kakaoInfo.put("user_id", user_id);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return kakaoInfo;
	}
	
	
}
