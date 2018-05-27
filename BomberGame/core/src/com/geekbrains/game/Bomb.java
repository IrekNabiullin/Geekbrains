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
        READY(0), EXPLODE(1);

        private int animationIndex;

        State(int animationIndex) {
            this.animationIndex = animationIndex;
        }
    }

    private Vector2 position;
    private TextureRegion textureBomb;
    private float time;
    private boolean active;
    private AnimationEmitter animationEmitter;
    private Bomberman player;
    private Bomb.State currentState;
    private Animation[] animations;
    private TextureAtlas atlas;

    public int getBombX() { return (int) (position.x/ Rules.CELL_SIZE); }
    public int getBomblY() { return (int) (position.y / Rules.CELL_SIZE); }

    public Bomb(TextureAtlas atlas, AnimationEmitter animationEmitter, boolean isActive, int xPos, int yPos) {
        this.position = new Vector2(xPos * Rules.CELL_SIZE, yPos *  Rules.CELL_SIZE);
        this.textureBomb = atlas.findRegion("bomb");
        this.time = 2.0f;
        this.active = isActive;
        this.animationEmitter = animationEmitter;
        this.player = BomberGame.player;
        this.animations = new Animation[Bomb.State.values().length];
        this.atlas = atlas;
        for (int i = 0; i < Bomb.State.values().length; i++) {
            this.animations[i] = new Animation();
            this.animations[i].activate(0, 0, 1, new TextureRegion(atlas.findRegion("bombA")).split(Rules.CELL_SIZE, Rules.CELL_SIZE)[i], 0.1f, true);
        }
        this.currentState = Bomb.State.READY;
    }

    public void render(SpriteBatch batch) {
        if (active)
            batch.draw(animations[currentState.animationIndex].getCurrentRegion(), position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
//            batch.draw(textureBomb, position.x - Rules.CELL_HALF_SIZE, position.y - Rules.CELL_HALF_SIZE);
    }

    public void putBomb() {

        position.x = player.getCellX()*Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;
        position.y = player.getCellY()*Rules.CELL_SIZE + Rules.CELL_HALF_SIZE;

        if (active == true) {
            this.active = false;
        }else this.active = true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void update(float dt) {
        animations[currentState.animationIndex].update(dt);
        if (active){
        time -= dt;
        if (time <= 0.0f) {
            animationEmitter.createAnimation(position.x, position.y, 2.0f, AnimationEmitter.AnimationType.EXPLOSION);
                active = false;
            }
        } else {
            time = 2.0f;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            putBomb();



        }
    }

}
