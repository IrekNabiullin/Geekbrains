package com.geekbrains.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BomberGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Bomberman player;
    private AnimationEmitter animationEmitter;

    // Домашнее задание:
    // 1. Разобраться с кодом
    // 2. Добавить возможность запуска двух видов анимаций (можно чтобы при
    // нажатии срабатывала случайная)

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new Map();
        player = new Bomberman(map);
        animationEmitter = new AnimationEmitter();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        player.render(batch);
        animationEmitter.render(batch);
        batch.end();
    }

    public void update(float dt) {
        map.update(dt);
        player.update(dt);
        animationEmitter.update(dt);
        Random rand = new Random();
        int r = rand.nextInt(2);
        if (Gdx.input.justTouched()) {
            if (r > 0) {
                animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.EXPLOSION);

            } else {
                animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.TESTANIMATION);

            }
//            animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY() - 80, AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY() - 160, AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY() + 80 , AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX(), 720 - Gdx.input.getY() + 160 , AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX() - 80, 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX() - 160, 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX() + 80, 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.EXPLOSION);
//            animationEmitter.createAnimation(Gdx.input.getX() + 160, 720 - Gdx.input.getY(), AnimationEmitter.AnimationType.EXPLOSION);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
