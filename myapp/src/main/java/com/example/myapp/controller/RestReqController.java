package com.example.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class RestReqController {

    @Autowired
    private WebClient webClient;

    @PostMapping("/java_service")
    public String serviceRequest(@RequestParam("file") MultipartFile file, @RequestParam("message") String message) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder(); // 멀티파트 폼 데이터를 구성
        bodyBuilder.part("message", message); // 폼 데이터
        bodyBuilder.part("file", file.getResource()); // 폼 데이터, 파일

        String result = webClient.post()
                .uri("/detect") // POST 방식으로 요청. 엔드포인트는 /detect
                .contentType(MediaType.MULTIPART_FORM_DATA) // 파일이 전송되므로
                .body(BodyInserters.fromMultipartData(bodyBuilder.build())) // 폼 데이터를 요청 본문으로 설정
                .retrieve() // 요청을 실행하고 응답을 받음
                .bodyToMono(String.class) // 본문을 String 타입으로 변환
                .block(); // 비동기처리를 동기적으로 블록해서 결과를 반환
        return result;
    }
    @PostMapping("/buysell_service")
    public ResponseEntity<String> serviceRequest() {
        String result = webClient.post()
                .uri("http://localhost:8088/buyNsell") // FastAPI 서버의 주소
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 설정
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 처리

        System.out.println("result: " +result);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/today_service")
    public ResponseEntity<String> serviceRequest(@RequestBody TodayRequest request) {
        String stockName = request.getStockName();

        // 디버그용 출력
        System.out.println("Stock Name: " + stockName);

        // 폼 데이터 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("stock_name", stockName);

        // 디버그용 출력
        System.out.println("Stock Name: " + stockName);

        String result = webClient.post()
                .uri("http://localhost:8088/today") // FastAPI 서버의 주소
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 설정
                .bodyValue(formData) // 폼 데이터 설정
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 처리

        System.out.println("result: " +result);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/realtime_service")
    public ResponseEntity<String> serviceRequest(@RequestBody RealtimeRequest request) {
        String stockName = request.getStockName();
        String close = request.getClose();

        // 디버그용 출력
        System.out.println("Stock Name: " + stockName);
        System.out.println("Close: " + close);

        // 폼 데이터 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("stock_name", stockName);
        formData.add("close", close);

        // 폼 데이터 출력 확인
        formData.forEach((key, value) -> System.out.println(key + ": " + value));

        // FastAPI 서버로 전송
        String result = webClient.post()
                .uri("http://localhost:8088/realtime") // FastAPI 서버의 주소
                .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 폼 데이터 설정
                .bodyValue(formData) // 폼 데이터 설정
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 동기 처리

        System.out.println("Result: " + result);

        return ResponseEntity.ok(result);
    }
}
