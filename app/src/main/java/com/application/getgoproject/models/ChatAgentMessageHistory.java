package com.application.getgoproject.models;

public class ChatAgentMessageHistory {
    private String question;
    private ChatAgentMessage answer;

    public ChatAgentMessageHistory(String question, ChatAgentMessage answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ChatAgentMessage getAnswer() {
        return answer;
    }

    public void setAnswer(ChatAgentMessage answer) {
        this.answer = answer;
    }
}
