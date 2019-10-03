package ehu.isad;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bildumaAriketak extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, MalformedURLException {


        ImageView imageView =new ImageView();

        primaryStage.setTitle("Bilduma ariketak");

        ComboBox comboBilduma=new ComboBox();
        List<String> bildumak = List.of("Zuhaitzak", "Animaliak", "Frutak");


        ObservableList<Bilduma> bildumaList = FXCollections.observableArrayList();

        bildumak.forEach((elem) -> {
            bildumaList.add(new Bilduma(elem));
        });

        comboBilduma.setItems(bildumaList);


        comboBilduma.getSelectionModel().selectFirst();


        //   <BildumaIzena, ArgazkiZerrenda>
        Map<String, List<Argazki>> bildumaMap = new HashMap<>();

        bildumaMap.put("Zuhaitzak", List.of(
                new Argazki("Haritza", "haritza.jpeg"),
                new Argazki("Ezkia", "ezkia.jpeg"),
                new Argazki("Sagarrondoa", "sagarrondoa.jpeg")
        ));

        bildumaMap.put("Animaliak", List.of(
                new Argazki("Katua", "katua.jpg"),
                new Argazki("Txakurra", "txakurra.jpeg"),
                new Argazki("Untxia", "untxia.jpg")
        ));

        bildumaMap.put("Frutak", List.of(
                new Argazki("Meloia", "meloia.jpg"),
                new Argazki("Laranja", "laranja.jpeg"),
                new Argazki("Sagarra", "sagarra.jpg")
        ));


        ObservableList<Argazki> argazkiList = FXCollections.observableArrayList();


        argazkiList.addAll(bildumaMap.get("Zuhaitzak"));
    /*    argazkiList.addAll(bildumaMap.get("Bilduma2"));
        argazkiList.addAll(bildumaMap.get("Bilduma3"));*/


        ListView<Argazki> listViewOfArgazki = new ListView<>(argazkiList);



        comboBilduma.setOnAction(e->{
            listViewOfArgazki.getItems().clear();
            argazkiList.addAll(bildumaMap.get(comboBilduma.getValue().toString()));
            imageView.setImage(null);

        });

        //Ze objetu aukeratuta zegoen, aukeratuta zegoen izena, aukeratu den izena
        listViewOfArgazki.getSelectionModel().selectedItemProperty().addListener(  (observable, oldValue, newValue) -> {
            if (observable.getValue() == null) return;


            String fitx = observable.getValue().getFitx();

            try {
                imageView.setImage(lortuIrudia(fitx /* 48x48 */));
                imageView.setFitHeight(70);
                imageView.setFitWidth(70);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });





        VBox vbox = new VBox(comboBilduma,listViewOfArgazki,imageView);

        Scene scene = new Scene(vbox, 220, 200);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    private Image lortuIrudia(String location) throws IOException {


        InputStream is = getClass().getResourceAsStream("/" + location);
        BufferedImage reader = ImageIO.read(is);
        return SwingFXUtils.toFXImage(reader, null);

    }
}
