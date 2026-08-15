package org.example;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;
import io.github.ollama4j.models.generate.OllamaGenerateTokenHandler;

public class Main {

    public static void main(String[] args) throws Exception {

        // Use the following to set it up with your Ollama configuration.
        Ollama ollama = new Ollama("http://localhost:11434/");
        String model = "qwen3.5:2b";

        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

        OllamaChatRequest chatRequest =
                builder.withMessage(
                                OllamaChatMessageRole.USER,
                                "Give me a summary of the book 'The Great Gatsby'")
                        .build();

        // Define a stream observer.
        OllamaChatStreamObserver streamObserver = new OllamaChatStreamObserver();

        // If thinking tokens are found, print them in lowercase :)
        streamObserver.setThinkingStreamHandler(
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toUpperCase());
                    }
                });
        // Response tokens to be printed in lowercase
        streamObserver.setResponseStreamHandler(
                new OllamaGenerateTokenHandler() {
                    @Override
                    public void accept(String message) {
                        System.out.print(message.toLowerCase());
                    }
                });

        ollama.chat(chatRequest, streamObserver);
    }
}