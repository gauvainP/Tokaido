package Controller;

import Model.Model;
import Model.Joueur;
import Model.Kinko;
import Model.Sasayakko;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

public class LauncherController {
    boolean selectJoueur=false;
    private Model model;
    @FXML
    Button commencerP;
    @FXML
    Label labelJ1;

    private ArrayList<String> persoDispo = new ArrayList<String>();

    public LauncherController(){
        this.model = new Model();
        System.out.println(model.getListJoueur().size());
        persoDispo.add("Kinko");
        persoDispo.add("Sasayakko");
    }

    @FXML
    public void fenetreLauncher() throws IOException {
        if(model.getListJoueur().size()>=2){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Vue/plateau.fxml"));
            Parent root = loader.load();
            System.out.println("loader créer");

            Scene scene = new Scene(root);
            Stage secondaryStage = new Stage();
            loader.<GameController>getController().setData(model);
            secondaryStage.setScene(scene);
            secondaryStage.setMaximized(false);
            secondaryStage.setResizable(true);
            secondaryStage.setTitle("Tokaido");
            secondaryStage.show();
            Stage stage = (Stage) commencerP.getScene().getWindow();
            stage.close();
        }else{ messageErreur("Veuillez selectionner au moins deux joueurs");
        }
    }

    public void selectionJoueur(){
        if(model.getListJoueur().size()>=5){
            messageErreur("Vous ne pouvez plus ajouter de joueurs !");
            return;
        }
        ArrayList<RadioButton> listeBouton = new ArrayList<RadioButton>();
        ToggleGroup group = new ToggleGroup();
        GridPane grille = new GridPane();
        int i = 1;
        for(String s: persoDispo){
            RadioButton radioButton = new RadioButton();
            radioButton.setGraphic(new ImageView("/Vue/Images/"+s+".jpg"));
            radioButton.setToggleGroup(group);
            grille.add(radioButton,i,0);
            listeBouton.add(radioButton);
            i++;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(grille);
        alert.setTitle("Choix des personnages");
        alert.setHeaderText("Choisissez votre personnage");
        alert.showAndWait();
        String nomPersos="";

        for (int j = 0; j < listeBouton.size(); j++) {
            if(listeBouton.get(j).isSelected()){
                if(persoDispo.get(j).equals("Kinko")) {
                    model.addJoueur(new Kinko());
                    nomPersos+=persoDispo.get(j)+"\n";
                    persoDispo.remove(persoDispo.get(j));
                    selectJoueur = true;
                }else if(persoDispo.get(j).equals("Sasayakko")) {
                    model.addJoueur(new Sasayakko());
                    nomPersos+=persoDispo.get(j)+"\n";
                    persoDispo.remove(persoDispo.get(j));
                    selectJoueur = true;
                }
            }
        }
        if(labelJ1.getText().equals("Aucun joueurs selectionnés")) labelJ1.setText("Personnages selectionnés: \n");
        labelJ1.setText(labelJ1.getText()+nomPersos);

    }

    public void messageErreur(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showReglement(){
        reglement();
    }

    static void reglement() {
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

    public void leaveGame(){
        System.exit(0);
    }
}
