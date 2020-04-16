package com.geekbrains.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class RedColorOnMouseOverButton extends TextButton {

    private boolean hover;

    public RedColorOnMouseOverButton(String text, Skin skin, String name) {
 //       super(text, skin, "simpleSkin");
        super(text, skin, "transparentButton2"); //firebricks text color
 //       super(text, skin, "transparentButton");  // red text color

        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hover = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hover = false;
            }
        });
    }


    @Override

    public Color getColor() {
        if (!hover)
            return super.getColor();
        else
            return Color.RED;
    }

/*
    @Override
    public TextButton TextButtonStyle(){
        if (!hover)
            return TextButtonStyle();
        else
//            return super.setStyle(TextButtonStyleS());
        return super.setStyle(TextButtonStyle textButtonStyleX);
    }

*/


}
