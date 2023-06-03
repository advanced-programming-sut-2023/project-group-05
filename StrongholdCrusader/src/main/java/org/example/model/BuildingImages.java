package org.example.model;

import javafx.scene.image.Image;
import org.example.view.MainMenu;

public enum BuildingImages {
    ARCHER_HOUSE("archerhouse.png"),
    HEMMATI_HOUSE("hemmathouse.jpg"),
    DANIAL_HOME("daniahouse.png");

    private Image image ;

    BuildingImages( String buildingImageName ){
        this.image = new Image( MainMenu.class.getResource( "/images/buildings" + buildingImageName ).toExternalForm() ) ;
    }

}
