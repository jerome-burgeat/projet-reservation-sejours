package com.example.projetreservationsejours.modele;

import com.example.projetreservationsejours.Application;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static Map<String, Image> cache = new HashMap<>();
     public ImageCache(){
         Image image = new Image(Application.class.getResource("imagesLocations/Paris.jpg").toExternalForm());
         Image image2 = new Image(Application.class.getResource("imagesLocations/Casablanca.jpg").toExternalForm());
         Image image3 = new Image(Application.class.getResource("imagesLocations/England.jpg").toExternalForm());
         Image image4 = new Image(Application.class.getResource("imagesLocations/Guadeloupe.jpg").toExternalForm());
         Image image5 = new Image(Application.class.getResource("imagesLocations/Jerusalem.jpg").toExternalForm());
         Image image6 = new Image(Application.class.getResource("imagesLocations/Mexico.jpg").toExternalForm());
         Image image7 = new Image(Application.class.getResource("imagesLocations/Rome.jpg").toExternalForm());
         Image image8 = new Image(Application.class.getResource("imagesLocations/Port-Louis.jpg").toExternalForm());
         Image image9 = new Image(Application.class.getResource("imagesLocations/Porto-Rico.jpg").toExternalForm());
         Image image10 = new Image(Application.class.getResource("imagesLocations/Rio.jpg").toExternalForm());
         Image image11 = new Image(Application.class.getResource("imagesLocations/Tokyo.jpg").toExternalForm());
         Image image12 = new Image(Application.class.getResource("imagesLocations/Venise.jpg").toExternalForm());
         cache.put(Application.class.getResource("imagesLocations/Paris.jpg").toExternalForm(), image);
         cache.put(Application.class.getResource("imagesLocations/Casablanca.jpg").toExternalForm(), image2);
         cache.put(Application.class.getResource("imagesLocations/England.jpg").toExternalForm(), image3);
         cache.put(Application.class.getResource("imagesLocations/Guadeloupe.jpg").toExternalForm(), image4);
         cache.put(Application.class.getResource("imagesLocations/Mexico.jpg").toExternalForm(), image5);
         cache.put(Application.class.getResource("imagesLocations/Jerusalem.jpg").toExternalForm(), image6);
         cache.put(Application.class.getResource("imagesLocations/Rome.jpg").toExternalForm(), image7);
         cache.put(Application.class.getResource("imagesLocations/Port-Louis.jpg").toExternalForm(), image8);
         cache.put(Application.class.getResource("imagesLocations/Porto-Rico.jpg").toExternalForm(), image9);
         cache.put(Application.class.getResource("imagesLocations/Rio.jpg").toExternalForm(), image10);
         cache.put(Application.class.getResource("imagesLocations/Tokyo.jpg").toExternalForm(), image11);
         cache.put(Application.class.getResource("imagesLocations/Venise.jpg").toExternalForm(), image12);

     }

    public static Image getImage(String imageUrl) {
        if (cache.containsKey(imageUrl)) {
            return cache.get(imageUrl);
        } else {
            Image image = new Image(imageUrl);
            cache.put(imageUrl, image);
            return image;
        }
    }
}