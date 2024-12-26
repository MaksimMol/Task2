package com.example.task4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.Iterator;

public class SlideShowController {

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView selectFolderIcon, startSlideshowIcon, pauseSlideshowIcon, stopSlideshowIcon, prevSlideIcon, nextSlideIcon;

    @FXML
    private ComboBox<String> formatComboBox;

    private ConcreteAggregate slides;
    private Iterator<Image> iterator;
    private Timeline slideshowTimeline;
    private boolean isPlaying = false;
    private String imageFormat = ".png";
    private int currentIndex = 0;

    @FXML
    private void initialize() {
        try {
            selectFolderIcon.setImage(new Image(getClass().getResource("/icons/folder-icon.png").toExternalForm()));
            startSlideshowIcon.setImage(new Image(getClass().getResource("/icons/play-icon.png").toExternalForm()));
            pauseSlideshowIcon.setImage(new Image(getClass().getResource("/icons/pause-icon.png").toExternalForm()));
            stopSlideshowIcon.setImage(new Image(getClass().getResource("/icons/stop-icon.png").toExternalForm()));
            prevSlideIcon.setImage(new Image(getClass().getResource("/icons/prev-icon.png").toExternalForm()));
            nextSlideIcon.setImage(new Image(getClass().getResource("/icons/next-icon.png").toExternalForm()));
        } catch (NullPointerException e) {
            showAlert("Ошибка загрузки иконок.");
        }
        formatComboBox.getItems().addAll("PNG", "JPG", "BMP");
        formatComboBox.setValue("PNG");
        formatComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            imageFormat = "." + newValue.toLowerCase();
        });
    }

    @FXML
    private void selectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            slides = new ConcreteAggregate(imageFormat);
            slides.loadImages(selectedDirectory);
            iterator = slides.getIterator();
            currentIndex = 0;
            if (iterator.hasNext()) {
                imageView.setImage(iterator.next());
            } else {
                showAlert("Не найдены фото в выбранном формате");
            }
        }
    }

    @FXML
    private void startSlideshow() {
        if (slides == null || !iterator.hasNext()) {
            showAlert("Нет фото.");
            return;
        }

        if (isPlaying) {
            return;
        }

        isPlaying = true;
        slideshowTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> showNextSlide()));
        slideshowTimeline.setCycleCount(Timeline.INDEFINITE); // Бесконечное воспроизведение
        slideshowTimeline.play();
    }

    @FXML
    private void pauseSlideshow() {
        if (slideshowTimeline != null) {
            slideshowTimeline.pause();
        }
        isPlaying = false;
    }

    @FXML
    private void stopSlideshow() {
        if (slideshowTimeline != null) {
            slideshowTimeline.stop();
        }
        isPlaying = false;
        currentIndex = 0;  // Сброс индекса на первый слайд
        iterator = slides.getIterator();
        if (iterator.hasNext()) {
            imageView.setImage(iterator.next());
        }
    }

    @FXML
    private void showPreviousSlide() {
        if (slides != null && iterator != null) {
            if (currentIndex > 0) {
                currentIndex--;
            } else {
                currentIndex = slides.getSize() - 1;
            }
            iterator = slides.getIterator();
            Image image = (Image) slides.getImageAt(currentIndex);
            imageView.setImage(image);
        }
    }

    @FXML
    private void showNextSlide() {
        if (slides != null && iterator != null) {
            if (currentIndex < slides.getSize() - 1) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
            iterator = slides.getIterator();
            Image image = (Image) slides.getImageAt(currentIndex);
            imageView.setImage(image);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
