package LiveEventAiDriver;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LiveEventAiDemo extends Application {
    private MyWebCamView myWebCamView;
    private InputStream webCamInputStream;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage myStage) throws Exception {
        prepareWebCamWindow(myStage);
        startFFmpeg_MacbookWeb_CamStream();

        Thread thread = new Thread(() -> {
            try {
                initWebCamStreamToView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /************************************************************/
    private void prepareWebCamWindow(Stage myStage){
        myWebCamView = new MyWebCamView();
        VBox root = new VBox(myWebCamView);
        Scene scene = new Scene(root, 700, 700);
        myStage.setScene(scene);
        myStage.show();
    }

    /************************************************************/
    private void startFFmpeg_MacbookWeb_CamStream() throws IOException {

        // launch FFmpeg on its own process
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",                 // start FFmpeg in a diff process.
                "-f", "avfoundation",     // using Macbook webcam device
                "-framerate", "30",       // framerate (picky for Mac)
                "-video_size", "640x480", // resolution (picky for Mac)
                "-i", "0",                // using MacBook webcam device
                "-q:v", "30",             // lower quality = less glitchiness.
                "-f", "mjpeg",            // to jpegs (easy for java to use).
                "-"                       // send data to stdout (for java).
        );

        Process p = pb.start();
        webCamInputStream = p.getInputStream();
    }
    /************************************************************/
    private void initWebCamStreamToView() throws IOException {
        byte[] imgBuffer = new byte[8192];
        while(true){
            int n = webCamInputStream.read(imgBuffer);
            if (n == -1) break;   // no more buffer data (e.g. FFmpeg stopped).


            ByteArrayInputStream in =
                    new ByteArrayInputStream(imgBuffer, 0, n);

            BufferedImage image = ImageIO.read(in);

            if (image != null) {
                // reinterpret bufferedImage pixels into Image obj.
                Image webStreamImage = SwingFXUtils.toFXImage(image, null);
                Platform.runLater(() -> {
                    myWebCamView.setImage(webStreamImage);
                });
            }
        }
    }


    /*************** Webcam Class ************** */
    private class MyWebCamView extends ImageView {
        public MyWebCamView() {
            super();
            setFitWidth(640);
            setFitHeight(480);
            setPreserveRatio(true);
        }
    }
}
