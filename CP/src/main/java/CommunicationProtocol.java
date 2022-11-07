import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Protocol {
  
    Date data;      // Дата и время сообщения ("dd.MM.yyyy HH:mm")
    String name;    // Имя пользователя
    int color;      // Цвет сообщений пользователя
    int type;       // Тип сообщения (регистрация = 01; сообщение клиента на сервер = 02;
                    // Cообщение сервера клиенту = 03; отключение клиента = 04; запрос на смену цвета клиента = 05;
                    // Переход в ждущий или спящий режим = 06; подписка на сообщения отдельных пользователей чате = 07;
                    // Резерв1 = 08; резерв2 = 09; резерв3 = 10; список подключенных клиентов = 11;
    String message; // Само сообщение клиента
    String razdelitel = "№"; // - Символ разделитель компонентов в строке протокола

    public Protocol() {      // - Конструктор класса Protocol()
    }

    public String SendStrokaProtocol(){ // - Сборщик строки протокола для отправки получателю
        this.data = new Date();
        System.out.println("Date = " + this.data);
        String c = (type + "№" + this.data.toString() + "№" + color + "№" + name + "@" + message);
        System.out.println(c);
        return (c);
    }

    public void  RazborProtocol(String str) throws ParseException { // Разборщик строки протокола на компоненты. Раскладываем входящую строку на отдельные поля: Протокола
        String[] protocol = str.split (razdelitel);
        if (protocol.length>0)  { // - Получаем тип сообщение
            this.type     = Integer.parseInt(protocol[0]);
            System.out.println("Тип сообщения = " + this.type);}
        if (protocol.length>1)  {
            String s=protocol[1]; // - Получаем время и дату сообщение
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd.MM.yyyy HH:mm");
            Date docDate= format.parse(s);
            this.data     = docDate;  System.out.println("Время и дата = " + this.data );
        }
        if (protocol.length>2)  { // - Получаем цвет клиента написавшего сообщение
            this.color    = Integer.parseInt(protocol[2]);
            System.out.println("Цвет = " + this.color );
        }
        if (protocol.length>3)  { // - Получаем имя клиента написавшего сообщение
            this.name     = protocol[3];
            System.out.println("Имя = " + this.name);
        }
        if (protocol.length>4)  { // - Получаем само сообщение клиента
            this.message  = protocol[protocol.length-1];
            System.out.println("Сообщение = " + this.message);
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("---------------------------------------");
        Protocol P = new Protocol();
        String s = "01№31.01.2020 12:45№03№Маша№№№№Message";
        System.out.println("Разбираем строку = " + s);
        P.RazborProtocol(s);
        System.out.println("---------------------------------------");
        P.SendStrokaProtocol();
        System.out.println("---------------------------------------");
    }
}
