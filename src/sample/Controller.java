package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Slider volumeSlider;
    private File file;
    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media media;

    public void playVideo(ActionEvent event){
        mp.play();
    }

    public void pauseVideo(ActionEvent event){
        mp.pause();
    }

    public void stop(ActionEvent event){
        mp.seek(mp.getTotalDuration());
        mp.stop();
    }

    public void fast(ActionEvent event){
        mp.setRate(1.2);
    }

    public void slow(ActionEvent event){
        mp.setRate(0.8);
    }

    public void reload(ActionEvent event){
        mp.seek(mp.getStartTime());
        mp.play();
    }

    public void openVideo(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        file =fileChooser.showOpenDialog(null);
        String path = file.getAbsolutePath();
        path = path.replace("\\","/");

        if (file!=null){
            media = new Media(new File(path).toURI().toString());
            mp =new MediaPlayer(media);
            mv.setMediaPlayer(mp);
            mp.play();

            volumeSlider.setValue(mp.getVolume()*100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mp.setVolume(volumeSlider.getValue()/100);
                }
            });

            DoubleProperty width = mv.fitWidthProperty();
            DoubleProperty height = mv.fitHeightProperty();
            width.bind(Bindings.selectDouble(mv.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mv.sceneProperty(),"height"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
