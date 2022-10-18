import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;

    private AuthService getAuthService(){
        return authService;
    }
    private MyServer(){
        try(ServerSocket server = new ServerSocket(PORT)){
            authService = new BaseAuthService();
            authService.start();
            clients=new ArrayList<>();
            while(true){
                System.out.println("сервер ожидает подключения");
                Socket socket = server.accept();
                System.out.println("клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch(IOException e){
            System.out.println("Ошибка в работе сервера");
        } finally{
            if(authService!=null){
                authService.stop();
            }
        }
    }


}
