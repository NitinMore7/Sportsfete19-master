package org.spider.sportsfete.Tutorial;

import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import java.lang.reflect.Field;

public class TutorialHelper {
    public static ImageButton getNavButtonView(Toolbar toolbar) {
        try {
            Class<?> toolbarClass = Toolbar.class;
            Field navButtonField = toolbarClass.getDeclaredField("mNavButtonView");
            navButtonField.setAccessible(true);
            return (ImageButton) navButtonField.get(toolbar);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
