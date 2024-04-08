package io.project.springbot.sevise;

import io.project.springbot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReseived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
    }
    public String getBotToken(){
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    private void startCommandReseived(long chanID, String name){
        String answer = "Hi " + name + " nice to meet you";
        sendMessage(chanID, answer);
    }

    private void sendMessage(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try{
            execute(message);
        }
        catch (TelegramApiException exeption){
        }
    }
}
