package org.example;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;

public class Main {

    public static void main(String[] args) throws Exception {

        // 1. Conexão ao servidor local Ollama
        Ollama ollama = new Ollama("http://localhost:11434/");
        ollama.setRequestTimeoutSeconds(60);

        // 2. Modelo pretendido
        String model = "qwen3.5:2b";

        // 3. Construção do pedido de chat
        OllamaChatRequest chatRequest = OllamaChatRequest.builder()
                .withModel(model)
                .withMessage(OllamaChatMessageRole.USER, "Give me a summary of the book 'The Great Gatsby'")
                .build();

        // 4. Configuração do observador de streaming
        OllamaChatStreamObserver streamObserver = new OllamaChatStreamObserver();

        streamObserver.setThinkingStreamHandler(new OllamaGenerateTokenHandler() {
            @Override
            public void accept(String message) {
                System.out.print(message.toUpperCase());
            }
        });

        streamObserver.setResponseStreamHandler(new OllamaGenerateTokenHandler() {
            @Override
            public void accept(String message) {
                System.out.print(message.toLowerCase());
            }
        });

        System.out.println("A gerar resposta em tempo real...\n");

        // 5. Execução do chat
        ollama.chat(chatRequest, streamObserver);
    }
}