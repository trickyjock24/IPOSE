module com.example.ipose {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    opens assets.textures;
    opens assets.music;
    opens assets.sounds;

    opens com.example.ipose to javafx.fxml;
    exports com.example.ipose;
}