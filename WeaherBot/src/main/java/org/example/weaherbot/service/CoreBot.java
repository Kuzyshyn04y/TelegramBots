package org.example.weaherbot.service;

import org.example.weaherbot.config.InitBot;
import org.example.weaherbot.getRequest.FactoryRequest;
import org.example.weaherbot.getRequest.GetOnFiveDays;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CoreBot extends TelegramLongPollingBot {
    private final InitBot bot;
    private long chatId;
    private final String SECOND = "src/main/resources/requests/second.txt";

    public CoreBot(InitBot bot){
        this.bot = bot;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            chatId = update.getMessage().getChatId();

            switch (message){
                case "/start":
                {
                    firstMessage(update.getMessage().getChat().getFirstName());
                    //sendMessage(chatId, update.getMessage().getChat().getFirstName());
                    break;
                }
                case "Прогноз поггоди на 7 днів":
                {
                    weatherOnWeek();
                    try {
                        FileWriter writer = new FileWriter(SECOND);
                        writer.write("Прогноз поггоди на 7 днів");
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                default:
                    try {
                        searchRequest(update.getMessage().getText());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

            }
        }
    }
    @Override
    public String getBotUsername() {
        return bot.getName();
    }

    private void firstMessage(String name){
        sendMessage(name + " вітаємо в нашому прогнозі погоди\uD83D\uDE0A", firstKeyboard());
    }

    private void weatherOnWeek(){
        sendMessage("Введіть назву міста, за яким ви хочете здійснити пошук(англійською)", null);
    }

    private void searchRequest(String city) throws IOException {
        File file = new File(SECOND);
        String latestRequest;
        if(file.exists()){
            Scanner scanner = new Scanner(file);
            if(scanner.hasNextLine()){
                latestRequest = scanner.nextLine();
                switch (latestRequest){
                    case "Прогноз поггоди на 7 днів":
                    {
                        GetOnFiveDays result =  FactoryRequest.getOnFive();
                        result.createRequest(city);
                        sendMessage(result.createMessage(),firstKeyboard());
                    }
                }
            }
        }
    }


    private List<KeyboardRow> firstKeyboard(){
        List<KeyboardRow> buttons = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Прогноз поггоди на 7 днів");
        buttons.add(row);
        row = new KeyboardRow();
        row.add("Прогноз погоди на місяць");
        buttons.add(row);
        return buttons;
    }

    private void createKeybord(List<KeyboardRow> buttons, SendMessage message){
        message.setChatId(chatId);
        if(buttons != null){
            ReplyKeyboardMarkup newKeyboard = new ReplyKeyboardMarkup();
            newKeyboard.setKeyboard(buttons);
            message.setReplyMarkup(newKeyboard);
        }
        else{
            ReplyKeyboardRemove removeMarkup = new ReplyKeyboardRemove(true);
            message.setReplyMarkup(removeMarkup);
        }


        //sendApiMethodAsync(message);
    }


    private void sendMessage(String text, List<KeyboardRow> buttons){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        createKeybord(buttons, message);
        sendApiMethodAsync(message);
    }

    /*public void switchKeyboard(String chatId, String currentKeyboardType) {
        // Створення клавіатури з кнопкою зміни
        KeyboardButton inlineKeyboardMarkup = new KeyboardButton();



        // Створення кнопки
        InlineKeyboardButton changeKeyboardButton = new InlineKeyboardButton();
        changeKeyboardButton.setText(currentKeyboardType.equals("normal") ? "Спеціальна клавіатура" : "Звичайна клавіатура");
        changeKeyboardButton.setCallbackData(currentKeyboardType.equals("normal") ? "special_keyboard" : "normal_keyboard");

        // Додавання кнопки до клавіатури
        inlineKeyboardMarkup.
                setKeyboard(Arrays.asList(Arrays.asList(changeKeyboardButton)));

        // Створення повідомлення з клавіатурою
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Виберіть тип клавіатури:");
        message.setReplyMarkup(inlineKeyboardMarkup);

        // Надсилання повідомлення
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton(""));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton(""));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    @Override
    public String getBotToken(){
        return bot.getToken();
    }
}
