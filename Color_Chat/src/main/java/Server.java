// package server;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.IOException;
import jdk.net.Sockets;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class Server {

    static ArrayList<Socket> clients = new ArrayList<>();
    static List<Integer> clientNamberList = new ArrayList<>();
    static List<String> clientNameList = new ArrayList<>();
    static List<String> clientColorList = new ArrayList<>();
    static List<String> clientListPrefix = new ArrayList<>();
    static List<String> clientID = new ArrayList<>();

    public static void main(String[] args) {
        final String[] color = new String[]{"\u001B[0m", "\u001B[30m", "\u001B[33m", "\u001B[32m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m","\u001B[31m",};
        int i = 1;
        int nCl=0;

//        final String ANSI_RESET = "\u001B[0m"; //        final String ANSI_BLACK = "\u001B[30m"; //        final String ANSI_RED = "\u001B[31m";
//        final String ANSI_GREEN = "\u001B[32m";//        final String ANSI_YELLOW = "\u001B[33m";//        final String ANSI_BLUE = "\u001B[34m";
//        final String ANSI_PURPLE = "\u001B[35m";//       final String ANSI_CYAN = "\u001B[36m";//        final String ANSI_WHITE = "\u001B[37m";

        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8190);
            Date date = new Date();
            System.out.println("В " + color[2] + "(" + date + ")" + color[0] + " - Сервер запущен");
            while (true) {
                socket = serverSocket.accept();   // - Подключаем нового клиента
                // ClientsClass clientCL = new ClientsClass(socket,"Name", color[4]); // - Конструктор нового клиента

                clients.add(socket);              // - Добавляем нового клиента в список текущих клиентов

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // out.writeUTF("0000000203Имя/Укажите ваше Имя -");

                String nnnnnnCl = String.format("%06d", (nCl+1));
                out.writeUTF(nnnnnnCl + "-Ваш идентификационный номер в системе. Укажите ваше Имя:");

                String name = in.readUTF();
                int nClх=0;

                Date date1 = new Date();          // - Время подключения клиента
                System.out.println(color[0] + "В (" + date1 + ")  - Клиент " + name + " подключился к чату"); // - Выводим сообщение в консоль сервера о подключении нового клиента с указанием времени подключенния

                // clientCL.name = in.readUTF();
                // clientsName.add(in.readUTF());
                // out.writeUTF("Ваше Имя - "+ clientsName[0]);


                clientNameList.add(name); // System.out.println("Всего клиентов подключено : " + clientNameList);
                i++;
                if (i > 7) i = 1;
                clientColorList.add(color[i]);
                clientNamberList.add(1 + clientNamberList.size());

                String sout = "";
                // String nnnnnnCl = String.format("%06d", nCl+1);
                String nnColor = String.format("%02d", i);
                String dlName = String.format("%02d", clientNameList.get(nCl).length());
                sout = nnnnnnCl + nnColor + dlName + clientNameList.get(nCl);
                clientID.add(sout);
                clientListPrefix.add(sout);
                out.writeUTF(sout + "Вам присвоено имя в Чате - "+ clientNameList.get(nCl));
                // out.writeUTF(sout + "/" + "Ваше Имя - " + clientNameList.get(nCl)); // clients.size?   clienytCL.get(3);
                nCl++;
                // System.out.println("Всего клиентов подключено : " + clientNamberList.size() + "  ");


                System.out.println("Всего клиентов подключено : " + clientNamberList.size() + "  ");
                for (int j = 0; j < clientNameList.size(); j++) {
                    System.out.println(clientColorList.get(j) + "  Клиент № " + clientNamberList.get(j) + "-" + clientNameList.get(j) + color[0]);
                    // System.out.println(" : " + clientNameList);
                }

               /* MyRunnableClass t1 = new MyRunnableClass(i, color[i], name, in, out);
                t1.run();*/

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                String str = in.readUTF(); // - Считываем входящий поток (пришедшее сообщение от одного из клиентов)

                                // - Пересылаем принятое от одного из клиентов сообщение всем остальным пользователям
                                Date date2 = new Date();
                                char[] dlName = new char[2];
                                str.getChars(8, 10, dlName, 0);
                                int dlNameInt = Integer.parseInt(String.valueOf(dlName));

                                char[] kurzstr = new char[(str.length()-(10+dlNameInt))];
                                str.getChars((10+dlNameInt), str.length(), kurzstr, 0);

                                char[] colorCl = new char[2];
                                str.getChars(6, 8, colorCl, 0);
                                int colorClInt = Integer.parseInt(String.valueOf(colorCl));
                                System.out.println(color[colorClInt] + "Клиент " + name + " в (" + date2 + ") прислал(-а) сообщение: " + color[0] + " " + color[colorClInt] +String.valueOf(kurzstr)); // - Выводим сообщение клиента в консоль сервера
                                // System.out.println(color[4] + "Клиент " + name + " в (" + date2 + ") прислал(-а) сообщение: " + color[0] + " " + str); // - Выводим сообщение клиента в консоль сервера
                                // str = name + " в (" + date2 + ") : " + str;
                                // System.out.println(nnnnnnCl);
                                // clientID.get(nClх);name;

                                broadcastMsg(str);
                                // broadcastMsg(String.valueOf(curzstr));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void broadcastMsg(String str) throws IOException { // - Метод рассылки сообщений всем клиентам с сервера
        /* Аргумент - строка для рассылки */
        DataOutputStream out;
        for (Socket socket : clients){
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(str);
        }
    }
}
