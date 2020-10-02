package ex01.pyrmont;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wangwei354
 * @date 2020/10/2
 */
public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }
    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            System.out.println("=============");
            System.out.println(HttpServer.WEB_ROOT);
            System.out.println(request.getUri());
            System.out.println("=============");
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                String commonMsg = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n";
                output.write(commonMsg.getBytes());
                System.out.println("cunzai");
                fis = new FileInputStream(file);
                System.out.println(fis);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch!=-1) {
                    System.out.println(ch);
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            }
            else {
            // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" + "Content-Length: 23\r\n" + "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        }
        catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString() );
        }
        finally {
            if (fis!=null){
                fis.close();
            }
        }
    }
}
