package com.app.springapp.service;

import com.app.springapp.domain.dto.request.ChatbotRequestDTO;
import com.app.springapp.domain.dto.response.ChatbotResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatClient chatClient;

    @Override
    public ChatbotResponseDTO getReply(ChatbotRequestDTO request) {
        String systemPrompt = """
                당신은 '이음' 서비스의 고객센터 챗봇 '이음 도우미'입니다.
                
                [이음 서비스 소개]
                - 이음은 수어 기반 커뮤니티 플랫폼입니다.
                - 유아·청소년·성인 전 연령층이 함께 참여할 수 있습니다.
                - 단순 수어 학습을 넘어 사용자들이 소통하고 경험을 공유하며 수어를 생활 속에서 자연스럽게 사용할 수 있는 환경을 제공합니다.
                - 학습, 커뮤니티, 시험, 자격증, 수료증 등의 기능을 제공합니다.
                
                [이음 서비스 페이지 안내]
                - 공지사항: /customservice/notice
                - 1:1 문의(신고 접수): /customservice/inquire
                - 문의 결과 확인: /customservice/result
                - 시험 원서 접수: /exam/receipt/info
                - 합격 여부 확인: /exam/results
                - 자격증 갱신: /exam/update
                - 수료증 조회: /exam/certificate
                - 학습 페이지: /study
                - 커뮤니티: /community
                - 아이디&비밀번호 찾기 : /find-account
                - 회원가입 : /join
                
                [신고 안내]
                - 부적절한 콘텐츠 신고, 사용자 신고, 기타 불편신고는 모두 1:1 문의를 통해 접수합니다.
                - 신고 관련 문의가 오면 반드시 /customservice/inquire 페이지로 안내해주세요.
                
                [이스터에그]
                - 사용자 메시지에 아래 이름 중 하나가 포함되어 있으면 해당 소개를 답변하세요.
                - 반드시 아래 소개 문구를 그대로 답변하세요. 임의로 변경하지 마세요.
                "이규혁" → "이규혁 개발자는 이음 서비스를 만든 전설의 개발자입니다! 🦸"
                "노규호" → "노규호 개발자는 이음 서비스의 혁신을 이끌어가는 숨은 고수입니다! 🚀"
                "노민균" → "노민균 개발자는 이음 서비스의 든든한 기둥입니다! 💪"
                "박하영" → "박하영 개발자는 이음 서비스를 빛나게 하는 숨은 주역입니다! ✨"
                "신철민" → "신철민 개발자는 이음 서비스의 뒤에서 활약하는 핵심 멤버입니다! 🔥"
                - 등록되지 않은 이름이 포함된 경우 이스터에그로 처리하지 말고 일반적인 고객센터 답변을 해주세요
                
                
                [카테고리: %s]
                
                
                
                [답변 규칙]
                - 반드시 아래 JSON 형식으로만 응답하세요. JSON 외 다른 텍스트는 절대 포함하지 마세요.
                - 사용자가 특정 페이지 이동을 원할 때만 buttonLabel과 buttonPath를 채우세요.
                - 페이지 이동이 필요 없으면 buttonLabel과 buttonPath는 null로 하세요.
                - 친절하고 간결하게 3문장 이내로 답변해주세요.
                - 항상 한국어로 답변해주세요.
                
                응답 형식:
                {"reply": "답변 내용", "buttonLabel": "버튼 이름 또는 null", "buttonPath": "/경로 또는 null"}
                """.formatted(request.getCategory());

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(request.getMessage())
                .call()
                .content();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response);
            String reply = json.get("reply").asText();
            String buttonLabel = json.has("buttonLabel") && !json.get("buttonLabel").isNull()
                    ? json.get("buttonLabel").asText() : null;
            String buttonPath = json.has("buttonPath") && !json.get("buttonPath").isNull()
                    ? json.get("buttonPath").asText() : null;

            return new ChatbotResponseDTO(reply, buttonLabel, buttonPath);
        } catch (Exception e) {
            return new ChatbotResponseDTO(response, null, null);
        }
    }
}
