package bot;

import java.io.IOException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{

	//получает сообщение от клиента и отправляет ответ
	public void onUpdateReceived(Update update) {
		if(update.hasMessage() && update.getMessage().hasText())
			if (update.hasMessage() && update.getMessage().hasText()) {
				//сообщение от клиента
				Message msg = update.getMessage();
				String text = msg.getText();
				System.out.println(text);

				//ответ
				
				try {
					text = Weather.getWeather(text);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					SendMessage error = new SendMessage();
					error.setChatId(msg.getChatId().toString());
					error.setText("неправильно введён город");
					try {
						execute(error);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				SendMessage answer = new SendMessage();
				answer.setChatId(msg.getChatId().toString());

				answer.setText(text);
				try {
					//отправление ответа
					execute(answer);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		
	}

	//юзер нейм
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "angry_programmer_bot";
	}

	//токен подключения
	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "1679865778:AAGk1tcbCrjhpuzxYsPRVrd5AkekyaNoJAU";
	}

}
