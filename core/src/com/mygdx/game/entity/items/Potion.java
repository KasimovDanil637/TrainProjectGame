package com.mygdx.game.entity.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerProvider;

import java.awt.*;

public class Potion extends EntityProvider {
    private Effect effectOfPotion;

    private boolean setToDestroy;

    public Potion(Effect effectOfPotion, World world, Vector2 position, Vector2 size, Vector2 box2d, Texture texture) {
        super();
        this.world = world;
        this.effectOfPotion = effectOfPotion;

        setToDestroy = false;

        setRegion(new TextureRegion(texture, (int) (size.x), (int) size.y));

        currentState = State.STANDING;
        //define a potion body in a world

        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x + size.x / 4 / Constants.PPM, position.y + size.y / 4 / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(0);
        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(box2d.x / 4 / Constants.PPM, box2d.y / 4 / Constants.PPM);
        fdef.filter.categoryBits = Masks.OBJECT_BIT;
        fdef.filter.maskBits = (Masks.GROUND_BIT | Masks.BRICK_BIT | Masks.PLAYER_BIT);

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        //set texture
        setBounds(position.x, position.y, size.x / 2 / Constants.PPM, size.y / 2 / Constants.PPM);

    }
    public void collision(PlayerProvider playerProvider) {
        if(setToDestroy || currentState == State.DEAD) return;

        EffectManager effectManager = playerProvider.getEffectManager();

        effectManager.AddEffect(effectOfPotion);

        setToDestroy = true;
    }

    @Override
    public void draw(Batch batch) {
        if(!setToDestroy && currentState != State.DEAD)
        super.draw(batch);
    }

    @Override
    public void update(double time) {
        if(setToDestroy && currentState != State.DEAD) {
            world.destroyBody(b2body);
            currentState = State.DEAD;
        }
    }
}
