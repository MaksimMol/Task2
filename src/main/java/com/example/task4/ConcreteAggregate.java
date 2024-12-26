package com.example.task4;

import javafx.scene.image.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcreteAggregate implements Aggregate {

    private List<File> imageFiles = new ArrayList<>();
    private String imageFormat;

    public ConcreteAggregate(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    //Загрузка изображений в выбранном формате
    public void loadImages(File folder) {
        imageFiles.clear();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(imageFormat)) {
                    imageFiles.add(file);
                } else if (file.isDirectory()) {
                    loadImages(file);
                }
            }
        }
    }

    @Override
    public Iterator<Image> getIterator() {
        return new ImageIterator();
    }

    public Object getImageAt(int index) {
        if (index >= 0 && index < imageFiles.size()) {
            return new Image(imageFiles.get(index).toURI().toString());  // Преобразуем файл в объект Image
        }
        return null;
    }

    //Получение размера файла
    public int getSize() {
        return imageFiles.size();
    }

    //Перебор изображений
    private class ImageIterator implements Iterator<Image> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < imageFiles.size();
        }

        @Override
        public Image next() {
            if (hasNext()) {
                File file = imageFiles.get(currentIndex++);
                return new Image(file.toURI().toString());
            }
            return null;
        }
    }
}
