import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;

public class Client {

    static String mainStringOut="";
    public static void main(String[] args) {
        //String mainStringOut;
        final String[] color = new String[]{"\u001B[0m", "\u001B[30m", "\u001B[33m", "\u001B[32m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m","\u001B[31m",};
        try {
            Socket socket = new Socket("localhost",8190);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            // System.out.println("Для подключения ");

            Thread responseThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true){
                        String response = null;
                        try {
                            response = in.readUTF(); // - считываем что пишет сервер
                            // System.out.println(response); // - Печатаем в окно клиента ответы других участников чата?
                            char[] idCl = new char[6];
                            response.getChars(0, 6, idCl, 0);
// ----------------------------------------- Если приветствие сервера 1 -----------------------------------------
                            if ((response.length() > 6)&&(response.indexOf("-Ваш идентификационный номер в системе. Укажите ваше Имя:")>1)){
                                // char[] idCl = new char[6];
                                // response.getChars(0, 6, idCl, 0);
                                System.out.println("На знакомстве с сервером : Номер клиента - " + String.valueOf(idCl));
                                System.out.println("Укажите своё имя:");
                                // mainStringOut=String.valueOf(idCl);
                            }
// ----------------------------------------- Если сервер присвоил клиенту имя -----------------------------------------
                            else if ((response.length() > 6)&&(response.indexOf("Вам присвоено имя в Чате - ")>1)){
                                // char[] idCl = new char[6];
                                // response.getChars(0, 6, idCl, 0);
                                // System.out.println("Номер клиента - " + String.valueOf(idCl));
                                char[] colorCl = new char[2];
                                response.getChars(6, 8, colorCl, 0);
                                // System.out.println("Цвет клиента - " + String.valueOf(colorCl));
                                char[] dlName = new char[2];
                                response.getChars(8, 10, dlName, 0);
                                int dlNameInt = Integer.parseInt(String.valueOf(dlName));
                                // System.out.println("Длина имени в char - " + String.valueOf(dlName));
                                // System.out.println("Длина имени в Int - " + dlNameInt);
                                char[] nameCl = new char[dlNameInt];
                                response.getChars(10, (10 + dlNameInt), nameCl, 0);
                                // System.out.println("Имя клиента - " + String.valueOf(nameCl));
                                char[] messageCl = new char[response.length()-(10 + dlNameInt)];
                                // System.out.println("Длина сообщения клиента " + (response.length()-1-(10 + dlNameInt)));
                                response.getChars((10 + dlNameInt),(response.length()), messageCl, 0);
                                // SetmainStrOut((/*mainStringOut) = */String.valueOf(idCl) + String.valueOf(colorCl) + String.valueOf(dlName) + String.valueOf(nameCl)));
                                mainStringOut=String.valueOf(idCl) + String.valueOf(colorCl) + String.valueOf(dlName) + String.valueOf(nameCl);
                                // System.out.println("mainStringOut = !!! " + mainStringOut + "     - на вводе"/*" /  this.mainStringOut = " + this.mainStringOut*/);
                                System.out.println("На присвоении имени - Номер клиента - " + String.valueOf(idCl) + " | Цвет клиента - " + String.valueOf(colorCl) + " | Длина имени - " + String.valueOf(dlName) + " | Имя клиента - " + String.valueOf(nameCl) + " | Сообщение клиента - " + String.valueOf(messageCl));
                            }

// -----------------------------------------  Если пишет другой клиент -----------------------------------------
                            else if (response.length() > 10){
                                // char[] idCl = new char[6];
                                // response.getChars(0, 6, idCl, 0);
                                // System.out.println("Номер клиента - " + String.valueOf(idCl));
                                char[] colorCl = new char[2];
                                response.getChars(6, 8, colorCl, 0);
                                int colorClInt = Integer.parseInt(String.valueOf(colorCl));
                                //  System.out.println("Цвет клиента - " + String.valueOf(colorCl));
                                char[] dlName = new char[2];
                                response.getChars(8, 10, dlName, 0);
                                int dlNameInt = Integer.parseInt(String.valueOf(dlName));
                                // System.out.println("Длина имени в char - " + String.valueOf(dlName));
                                // System.out.println("Длина имени в Int - " + dlNameInt);
                                char[] nameCl = new char[dlNameInt];
                                response.getChars(10, (10 + dlNameInt), nameCl, 0);
                                // System.out.println("Имя клиента - " + String.valueOf(nameCl));
                                char[] messageCl = new char[response.length()-(10 + dlNameInt)];
                                // System.out.println("Длина сообщения клиента " + (response.length()-1-(10 + dlNameInt)));
                                response.getChars((10 + dlNameInt),(response.length()), messageCl, 0);
                                // SetmainStrOut((/*mainStringOut) = */String.valueOf(idCl) + String.valueOf(colorCl) + String.valueOf(dlName) + String.valueOf(nameCl)));
                                // mainStringOut=String.valueOf(idCl) + String.valueOf(colorCl) + String.valueOf(dlName) + String.valueOf(nameCl);
                                // System.out.println("mainStringOut = " + mainStringOut + "     - на вводе"/*" /  this.mainStringOut = " + this.mainStringOut*/);

                                // ------------------ Если сообщение того же клиента то не выводить! в терминал клиента ------------------
                                if (mainStringOut.length()>6) {
                                    char[] idSelf = new char[6];
                                    mainStringOut.getChars(0, 6, idSelf, 0);
                                    // System.out.println("Ид клиента = " + mainStringOut );
                                    // System.out.println(String.valueOf(idSelf) + " / " + String.valueOf(idCl));
                                    // if ((idSelf[3]!=idCl[3])&&(idSelf[5]!=idCl[5])&&(idSelf[4]!=idCl[4])) /*(String.valueOf(idSelf)!=String.valueOf(idCl))*/{
                                    if (Integer.parseInt(String.valueOf(idSelf)) != Integer.parseInt(String.valueOf(idCl))){
                                        System.out.println(color[colorClInt] + "Пишет клиент, номер - " + String.valueOf(idCl) + " | Цвет клиента - " + String.valueOf(colorCl) + " | Длина имени - " + String.valueOf(dlName) + " | Имя клиента - " + String.valueOf(nameCl) + " | Сообщение клиента - " + String.valueOf(messageCl));
                                        System.out.println(color[colorClInt] + String.valueOf(nameCl) + " : " + String.valueOf(messageCl));
                                    }
                                }
                            }
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            });

            responseThread.start(); // - Считываем, что написал клиент в свою консоль чата и отправляем на сервер

            while (true){
                String str = scanner.nextLine();
                // System.out.println("Цикл вывода mainStringOut = " +  mainStringOut/* + String.valueOf(mainStringOut)+" / "*/);
                out.writeUTF((mainStringOut + str));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}