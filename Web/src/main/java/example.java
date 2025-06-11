import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.sun.net.httpserver.HttpServer; // 자동import 안 되면 직접 붙여넣기

public class example {
	static final String WEB_ROOT = "html/js 파일이 있는 상대경로"; 
	static final String DB_FILE = "원하는 db파일 이름"; 
	
	public static void main(String[] args) throws IOException {
		// [1]. 서버 생성 및 포트 설정하기 
        HttpServer server = HttpServer.create(new InetSocketAddress(____), 0); 

        // [2]. 정적 파일 (.html, .js등) 제공
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "_____.html";

            File file = new File(___________);

            if (file.exists()) {
                byte[] data = Files.readAllBytes(file.toPath());
                String contentType = guessContentType(file.getName());
                
               
                if (contentType.startsWith("text/") || contentType.equals("application/javascript")) {
                    contentType += "; charset=utf-8";
                }

                exchange.getResponseHeaders().add("Content-Type", contentType);
                exchange.sendResponseHeaders(_____, data.length); 
                exchange.getResponseBody().write(data);
            } else {
                exchange.sendResponseHeaders(_____, -1); 
            }

            exchange.close();
        });

        // [3] 게시글 저장만 (POST 요청)
        server.createContext("/post", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String postData = new String(is.readAllBytes(), "UTF-8");
                
                if (!postData.______) {
                    Files.writeString(Paths.get(_____), postData + "\n",
                            StandardOpenOption.______, StandardOpenOption.______);
                }
                exchange.sendResponseHeaders(_____, 0); 
            } else {
                exchange.sendResponseHeaders(_____, -1); // Method Not Allowed
            }
            exchange.close();
        });

        server.setExecutor(null);
        server.start();
        System.out.println("http://localhost:_____"); // 서버 실행 확인용
    }

    static String guessContentType(String filename) {
        if (filename.endsWith(".html")) return "text/html";
        if (filename.endsWith(".js")) return "application/javascript";
        if (filename.endsWith(".css")) return "text/css";
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) return "image/jpeg";
        if (filename.endsWith(".png")) return "image/png";
        return "application/octet-stream";
    }

}
