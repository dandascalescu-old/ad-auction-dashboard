package com.comp2211.dashboard.view;

import com.comp2211.dashboard.Campaign;
import com.comp2211.dashboard.viewmodel.SettingsViewModel;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXComboBox;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

public class SettingsView implements FxmlView<SettingsViewModel>, Initializable{

    public static final String BOUNCES = "BOUNCES";

    public Text alertAddingText;
    public JFXComboBox<Campaign> campaignCombobox;
    private ObservableList<Campaign> campaigns;
    @FXML
    TextField timeTextField, noPagesTextField;

    @FXML
    JFXCheckBox darkModeCheckBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        campaigns = FXCollections.observableArrayList();
        campaigns.addAll(Campaign.getCampaigns());
        campaignCombobox.setItems(campaigns);
        campaignCombobox.getSelectionModel().select(0);
    }

    public void saveBounce(ActionEvent event) {
        String time = "";
        String pages = "";

        time = timeTextField.getText();
        pages = noPagesTextField.getText();

        if(time.length() > 0 && pages.length() > 0){
            alertAddingText.setText("Only one text field may be filled!");
            return;
        }
        else if (time.length() > 0){
            Campaign campaign = campaignCombobox.getValue();
            campaign.resetGranularity();
            campaign.updateBouncesByTime(Long.parseLong(time), true, campaign.getAppliedFilter());
            MvvmFX.getNotificationCenter().publish(SettingsView.BOUNCES);
            alertAddingText.setText("Bounce definition set to " + time + " seconds!");
        }
        else if (pages.length() > 0 ){
            Campaign campaign = campaignCombobox.getValue();
            campaign.resetGranularity();
            campaign.updateBouncesByPages(Byte.decode(pages), campaign.getAppliedFilter());
            MvvmFX.getNotificationCenter().publish(SettingsView.BOUNCES);
            alertAddingText.setText("Bounce definition set to " + pages + " pages!");
        }
    }

    public void darkModeAction(ActionEvent event) {
            if (darkModeCheckBox.isSelected()){
                System.out.println("Activating Dark Mode");
                MvvmFX.getNotificationCenter().publish(App.DARK_MODE);
            } else {
                System.out.println("Deactivating Dark Mode");
                MvvmFX.getNotificationCenter().publish(App.LIGHT_MODE);
            }

    }
}
