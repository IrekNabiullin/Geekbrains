package com.geekbrains.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bomb {
    public enum State {
        READY(0), EXPLOSION(1);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    private Vector2 position;
    private float time;
    private boolean active;
    protected AnimationEmitter animationEmitter;
    private Bomb.State currentState;
    private Animation[] animations;
    private TextureRegion atlas;

    protected Bomb(TextureAtlas atlas, AnimationEmitter animationEmitter, boolean isActive, int xPos, int yPos) {

        this.position = new Vector2(xPos * Rules.CELL_SIZE, yPos *  Rules.CELL_SIZE);
        this.time = 2.0f;
        this.active = isActive;
        this.animationEmitter = animationEmitter;
        this.animations = new Animation[Bomb.State.values().length];
        for (int i = 0; i < Bomb.State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(atlas.findRegion("bombA")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);

        }
        this.currentState = Bomb.State.READY;
    }

    public void render(SpriteBatch batch) {
        if (active)
            batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);

    }


        public void putBomb(int xPos, int yPos) {
            position.x = xPos*Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
            position.y = yPos*Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;

        if (active == true) {
            this.active = false;
            this.currentState = Bomb.State.READY;

        }else {
            this.active = true;
            this.currentState = State.EXPLOSION;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    protected void boom(){
        this.currentState = State.EXPLOSION;
        //       animationEmitter.createAnimation(position.x, position.y, 2.0f, AnimationEmitter.AnimationType.EXPLOSION);
 //       animationEmitter.createAnimation(position.x , position.y, 4.0f, AnimationEmitter.AnimationType.EXPLOSION);
    }

    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);
        if (active){
        time -= dt;
        if (time <= 0.0f) {
            active = false;
            boom();
//            animationEmitter.createAnimation(position.x, position.y, 2.0f, AnimationEmitter.AnimationType.EXPLOSION);

            }
        } else {
            time = 2.0f;
        }
    }

}
