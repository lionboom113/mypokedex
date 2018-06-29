package jp.co.pise.projecttemplate_android.Presentation.View.UIHelper;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

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
    public static Drawable GetTintedDrawable(Resources res, int drawableResId, int colorId)
    {
        Drawable drawable = res.getDrawable(drawableResId);
        drawable.setColorFilter(colorId, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
