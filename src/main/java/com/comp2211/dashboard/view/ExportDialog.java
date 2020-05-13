package com.comp2211.dashboard.view;

import com.comp2211.dashboard.util.Logger;
import com.comp2211.dashboard.viewmodel.ExportDialogViewModel;
import com.jfoenix.controls.JFXCheckBox;
import de.saxsys.mvvmfx.FxmlView;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import javax.imageio.ImageIO;

public class ExportDialog implements FxmlView<ExportDialogViewModel> {

    @FXML
    StackPane dialogExportStack;

    @FXML
    private JFXCheckBox ctrExportCheckBox;

    @FXML
    private JFXCheckBox convUniquesExportCheckBox;

    @FXML
    private JFXCheckBox totalMetricsExportCheckBox;

    @FXML
    private JFXCheckBox totalCostsExportCheckBox;

    @FXML
    private JFXCheckBox totalOverTimeExportCheckBox;

    @FXML
    private JFXCheckBox demographExportCheckBox;

    @FXML
    private JFXCheckBox averageCostExportCheckBox;

    private String absoluteExportPath;

    private PrimaryView primaryView;

    public void setPrimaryView(PrimaryView primaryView){
        this.primaryView = primaryView;
        //System.out.println("set on " + this);
    }

    public void exportFilesAction(ActionEvent event){
        //TODO maybe move to viewmodel class

        Stage stage = (Stage) dialogExportStack.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null || !selectedDirectory.exists()) {
            Logger.log("[INFO] Export path not selected.");
            return;
        }

        absoluteExportPath = selectedDirectory.getAbsolutePath();
        Logger.log("[INFO] Export path set: \"" + absoluteExportPath + "\"");

        export(selectedDirectory);

        PrimaryView.cancelExportDialogAction();
    }

    private void export(File selectedDirectory) {
        if(ctrExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.ctrPane, selectedDirectory);
        }
        if (convUniquesExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.conversionsUniquesPane, selectedDirectory);
        }
        if (totalMetricsExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.totalMetricsPane, selectedDirectory);
        }
        if (totalCostsExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.totalCostPane, selectedDirectory);
        }
        if (totalOverTimeExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.totalMetricsOverTimePane, selectedDirectory);
        }
        if (demographExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.demographicsPane, selectedDirectory);
        }
        if (averageCostExportCheckBox.isSelected()){
            saveDataAsPNG(primaryView.averageCostPane, selectedDirectory);
        }
    }


    private boolean saveDataAsPNG(Region r, File file){
        try {
            WritableImage writableImage = r.snapshot(new SnapshotParameters(), null);
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(writableImage, null );
            ImageIO.write(bufferedImage, "png", new File(file, r.idProperty().getValue() + ".png"));
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Data not found, check the name!");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void cancelExportDialogAction(ActionEvent event) {
        ctrExportCheckBox.selectedProperty().setValue(false);
        convUniquesExportCheckBox.selectedProperty().setValue(false);
        totalMetricsExportCheckBox.selectedProperty().setValue(false);
        totalCostsExportCheckBox.selectedProperty().setValue(false);
        totalOverTimeExportCheckBox.selectedProperty().setValue(false);
        demographExportCheckBox.selectedProperty().setValue(false);
        averageCostExportCheckBox.selectedProperty().setValue(false);

        PrimaryView.cancelExportDialogAction();
    }
}
