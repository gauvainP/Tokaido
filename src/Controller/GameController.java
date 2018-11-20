package Controller;


import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class GameController {
    private Model model;
    private Label nomJ1;
    boolean selectJoueur = false;

    @FXML
    Label labelJ1;

    @FXML Button commencerP;
//    public GameController(Model model){
//        this.model=model;
//    }
    public GameController(){
        model=new Model();
    }

    public void survolEntered(MouseEvent event){
        Button button =(Button) event.getSource();
        if (button.getId().equals("true")){
            button.setStyle("-fx-background-color: green;"+"-fx-border-style: dashed;"+"-fx-border-color: red;");
        } else button.setStyle("-fx-background-color: gray;"+"-fx-border-style: dashed;"+"-fx-border-color: red;");

    }

    public void survolExited(MouseEvent event){
        Button button =(Button) event.getSource();
        if (button.getId().equals("true")){
            button.setStyle("-fx-background-color: green;"+"-fx-border-style: solid;"+"-fx-border-color: black;");
        } else button.setStyle("-fx-background-color: gray;"+"-fx-border-style: solid;"+"-fx-border-color: black;");



    }
    public void actionColor(javafx.event.ActionEvent event){
        Button button=(Button) event.getSource();
        button.setId("true");
        button.setStyle("-fx-background-color: green;");

    }


    /**
     * Partie menu
     */
    public void leaveGame(){
        System.exit(0);
    }
    public void restart(){
    }
    public void showReglement(){
        if( Desktop.isDesktopSupported() )
        {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().browse( new URI( "http://www.funforge.fr/US/files/tokaido/Tokaido_rules_FR_LD.pdf" ) );
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }).start();
        }

    }
    public void piocheRelais(javafx.event.ActionEvent event) {
        ArrayList<Repas> piocheRelais = model.getListRepas();
        int a = (int) (Math.random() * piocheRelais.size());
        Repas repas = piocheRelais.get(a);
        String nomImage = "/Vue/" + repas.getNom() + ".jpg";


        Alert show = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(nomImage));
        show.setGraphic(imageView);
        show.setTitle("Carte repas");
        show.setHeaderText("prix : " + repas.getPrix());
        show.setContentText("Il vous reste : " + model.getListJoueur().get(0).getGold() + " or");

        ButtonType btnAcheter = new ButtonType("Acheter (-" + repas.getPrix() + " or)");
        ButtonType btnRefuser = new ButtonType("Refuser", ButtonBar.ButtonData.CANCEL_CLOSE);
        show.getButtonTypes().setAll(btnAcheter, btnRefuser);
        Optional<ButtonType> choice = show.showAndWait();
        if (choice.get() == btnAcheter && model.getListJoueur().get(0).getGold() >= repas.getPrix()) {
            model.getListRepas().remove(repas);
            model.getListJoueur().get(0).setGold(model.getListJoueur().get(0).getGold() - repas.getPrix());
            model.getListJoueur().get(0).addCarte(repas);
        } else if (choice.get() == btnAcheter && model.getListJoueur().get(0).getGold() < repas.getPrix()) {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Achat Impossible !");
            dialog.setContentText("Impossible d'acheter la carte!! \n" +
                    " vous n'avez pas assez d'or !");
            dialog.showAndWait();

        }
    }
    public void piocheSourceChaude(){
        ArrayList<Sources> listSourceChaude = model.getListSource();
        int a = (int) (Math.random() * listSourceChaude.size());
        Sources source = listSourceChaude.get(a);
        String nomImage = "/Vue/" + source.getNom() + ".jpg";

        Alert show = new Alert(Alert.AlertType.INFORMATION);
        ImageView imageView = new ImageView(new Image(nomImage));
        show.setGraphic(imageView);
        show.setTitle("Source chaude");
        show.setHeaderText("Vous rapporte : "+source.getPoint());
        show.showAndWait();
        model.getListJoueur().get(0).addCarte(source);
        model.getListSource().remove(source);

    }
    public void piocheSouvenir(){
        ArrayList<Souvenirs> piocheSouvenir = model.getListSouvenir();
        int a = (int) (Math.random() * piocheSouvenir.size());
        Souvenirs souvenirs = piocheSouvenir.get(a);
        String nomImage = "/Vue/" + souvenirs.getNom() + ".jpg";

        Alert show = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(nomImage));
        show.setGraphic(imageView);
        show.setTitle("Souvenirs");
        show.setHeaderText("prix : " + souvenirs.getPrix());
        show.setContentText("Il vous reste : " + model.getListJoueur().get(0).getGold() + " or");

        ButtonType btnAcheter = new ButtonType("Acheter (-" + souvenirs.getPrix() + " or)");
        ButtonType btnRefuser = new ButtonType("Refuser", ButtonBar.ButtonData.CANCEL_CLOSE);
        show.getButtonTypes().setAll(btnAcheter, btnRefuser);
        Optional<ButtonType> choice = show.showAndWait();
        if (choice.get() == btnAcheter && model.getListJoueur().get(0).getGold() >= souvenirs.getPrix()) {
            model.getListRepas().remove(souvenirs);
            model.getListJoueur().get(0).setGold(model.getListJoueur().get(0).getGold() - souvenirs.getPrix());
            model.getListJoueur().get(0).addCarte(souvenirs);
        } else if (choice.get() == btnAcheter && model.getListJoueur().get(0).getGold() < souvenirs.getPrix()) {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Achat Impossible !");
            dialog.setContentText("Impossible d'acheter la carte!! \n" +
                    " vous n'avez pas assez d'or !");
            dialog.showAndWait();

        }

    }

    public void piocheRencontre(){
        //
    }


    @FXML
    public void fenetreLauncher(ActionEvent event) throws IOException {
        if(selectJoueur){
            Stage stage = (Stage) commencerP.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Vue/plateau.fxml")));
            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(scene);
            secondaryStage.setMaximized(false);
            secondaryStage.setResizable(true);
            secondaryStage.setTitle("Tokaido");
            secondaryStage.show();
        }else{
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Impossible !");
            dialog.setContentText("Veuillez sélectionner un joueur !");
            dialog.showAndWait();
        }
    }

    public void selectionJoueur(){
        ArrayList<String> choix = new ArrayList<String>();
        choix.add("a");
        choix.add("b");
        choix.add("c");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choix);
        dialog.setTitle("Choix des joueurs !");
        dialog.setHeaderText("Start");
        dialog.setContentText("Choisissez un joueur.");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            labelJ1.setText("Joueur selectionné");
            selectJoueur = true;
        }
    }



}
