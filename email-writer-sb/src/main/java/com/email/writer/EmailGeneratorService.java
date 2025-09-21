package com.email.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailGeneratorService {
    private final WebClient webClient;
    private final String apiKey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder,
                                  @Value("${gemini.api.url}") String baseUrl,
                                  @Value("${gemini.api.key}") String geminiAPiKey) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.apiKey = geminiAPiKey;

    }

    public String generateEmailReply(EmailRequest emailRequest) {
        // Build Prompt
        String prompt=buildPrompt(emailRequest);
        //prepare raw Json body
        String requestBody = String.format("""
                  {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """,prompt);
        //Send Request
        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder.
                        path("/v1beta/models/gemini-2.5-flash:generateContent")
                        .build())
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Extract Response


        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root= objectMapper.readTree(response);
           return  root.path("candidates" ).get(0).path("content")
                                              .path("parts")
                                              .get(0).path("text")
                                              .asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt=new StringBuilder();
        prompt.append("Write a polished and professional business email reply. ")
                .append("The reply should:\n")
                .append("1. Thank the recipient politely.\n")
                .append("2. Briefly restate the original email’s purpose.\n")
                .append("3. Suggest the appropriate next step (e.g., discussion, confirmation, or scheduling a call).\n")
                .append("4. End with a courteous closing and professional signature.\n");

        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("\nUse a ").append(emailRequest.getTone()).append(" tone in the reply.\n");
        }

        prompt.append("\nHere is the original email:\n")
                .append(emailRequest.getEmailContent());

        return prompt.toString();
    }

}
