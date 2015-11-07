package dictionary.algorithmOnline;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

public class MyBrowser extends Application {
    final ScrollBar sc = new ScrollBar();
    /**
     *
     */
    public static void main() {
        launch();
    }

    @Override 
    public void start(final Stage primaryStage) {
        final WebView wv = new WebView();
        
        wv.setMaxSize(850, 450);
        wv.getEngine().load("https://translate.google.com/?hl=vi");
        //wv.getChildrenUnmodifiable().clear();
        
        primaryStage.setScene(new Scene(wv));
        primaryStage.setResizable(false);
        primaryStage.show();
        
        //
        sc.setDisable(true);
    }
}