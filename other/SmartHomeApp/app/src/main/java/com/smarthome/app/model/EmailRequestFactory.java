package com.smarthome.app.model;

public class EmailRequestFactory {

    private static final String EMAIL_TEMPLATE = "Здравствуйте, %s!\n" +
            "\n" +
            "Мы рады приветствовать вас в системе HomeZen. Ваша регистрация прошла успешно, и теперь вы можете пользоваться всеми возможностями нашего сервиса для управления вашим умным домом.\n" +
            "\n" +
            "Для начала работы выполните следующие шаги:\n" +
            "1. Войдите в свой аккаунт, используя зарегистрированные учетные данные.\n" +
            "2. Настройте устройства в своем профиле для управления ими.\n" +
            "3. Ознакомьтесь с возможностями интеграций.\n" +
            "\n" +
            "Если у вас возникнут вопросы, наша команда поддержки всегда готова помочь.\n" +
            "\n" +
            "С уважением,  \n" +
            "Команда HomeZen\n" +
            "\n" +
            "P.S. Мы заботимся о безопасности ваших данных. Если вы не регистрировались в нашей системе, пожалуйста, проигнорируйте это письмо.\n";

    private static final String SUBJECT = "Добро пожаловать в систему HomeZen!";

    public static EmailRequest create(String recipient, String username) {
        return new EmailRequest(recipient, SUBJECT, String.format(EMAIL_TEMPLATE, username));
    }

}
