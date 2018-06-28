package jp.co.pise.projecttemplate_android.Presentation.View.UIHelper;

import android.graphics.Color;

public class CommonUiHelper {
    public static String getTypeColor(String type) {
        String color;
        switch (type) {
            case "water":
                color = "#6890F0";
                break;
            case "fire":
                color = "#F08030";
                break;
            case "grass":
                color = "#78C850";
                break;
            default:
                color = "#444444";
        }
        return color;
    }
}
