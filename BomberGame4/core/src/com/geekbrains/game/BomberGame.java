package com.geekbrains.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.concurrent.atomic.AtomicInteger;

public class BomberGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    protected Bomb bomb;
 //   protected static Bomberman player;
    protected Bomberman player;
    private AnimationEmitter animationEmitter;
    private TextureAtlas atlas;
    private BitmapFont font32;
    private int xPos;
    private int yPos;

    // 1. Разобраться с кодом
    // 2. Реализовать бомбы(без анимации), ставим бомбу на клетку(по пробелу),
    // через 2 секунды после установики бомба исчезает и запускается
    // анимация взрыва. Бомба ТОЛЬКО ОДНА, делать емиттер не надо
    // Замечание: ставим бомбу на клетку в которой находимся, даже если
    // мы просто через нее идем

    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas( "game2.pak");
        map = new Map(atlas);
        bomb = new Bomb(atlas, animationEmitter,false, 0, 0);
        player = new Bomberman(map, atlas, bomb);
        animationEmitter = new AnimationEmitter(atlas);
        xPos = player.getCellX();
        yPos = player.getCellY();
//        bomb = new Bomb(atlas, animationEmitter,false, xPos, yPos);

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 32;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 1;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.shadowOffsetX = 3;
        fontParameter.shadowOffsetY = 3;
        fontParameter.shadowColor = Color.BLACK;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("gomarice.ttf"));
        font32 = generator.generateFont(fontParameter);
        generator.dispose();
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
        bomb.render(batch);
        animationEmitter.render(batch);
        font32.draw(batch, "Score: 1000", 20, 700);
        batch.end();
    }

    protected void update(float dt) {
        map.update(dt);
        player.update(dt);
        animationEmitter.update(dt);
        bomb.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }



}
