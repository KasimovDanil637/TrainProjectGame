package com.mygdx.game.entity.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;

public class BulletProvider extends EntityProvider {
    private TextureRegion bulletInFly;
    private Vector2 move;

    public BulletProvider(World world, Vector2 location, BulletData data, Vector2 move, short colliableWith){
        super();
        this.move = move;
        this.world = world;
        entityData = data;
        //initial state
        stateTimer = 0;
        currentState = State.RUNNING;
        previousState = State.RUNNING;


        //initial texture
        setBounds(location.x, location.y, data.getSize().x / Constants.PPM, data.getSize().x / Constants.PPM);
        bulletInFly = new TextureRegion(new Texture(Constants.PATH_TO_STANDART_BULLET));


        setRegion(bulletInFly);

        //initialising bullet in box2d
        defineBullet(location, colliableWith);

    }

    private void defineBullet(Vector2 location, short colliableWith) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(location.x, location.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setGravityScale(0);

        FixtureDef fdef = new FixtureDef();
        fdef.isSensor = true;

        CircleShape shape = new CircleShape();
        shape.setRadius((float) (((BulletData)entityData).getRadius() / Constants.PPM));
        fdef.filter.categoryBits = Masks.BULLET_BIT;
        fdef.filter.maskBits = (short) (Masks.GROUND_BIT | Masks.OBJECT_BIT | Masks.BRICK_BIT | colliableWith);
        move = new Vector2((float) (move.x * entityData.getSpeed()), (float) (move.y * entityData.getSpeed()));
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if(currentState != State.DEAD) super.draw(batch);
    }

    public void moveByDirection(double time) {

        b2body.applyLinearImpulse(move, b2body.getWorldCenter(), true);
    }

    public void update(double time){
        if(!setToDestroy && currentState != State.DEAD) {
            moveByDirection(time);

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            stateTimer += time;
        } else if (setToDestroy && currentState != State.DEAD){
            currentState = State.DEAD;
            world.destroyBody(b2body);
            setToDestroy = false;
        }

        if(stateTimer > ((BulletData)entityData).getDeathTimer()) {
            setToDestroy = true;
        }
    }

    public void damage(EntityData entityData) {
        if(currentState == State.DEAD) return;
        entityData.decreaseHP(((BulletData)this.entityData).damage());
        setToDestroy = true;
        stateTimer = 0;
    }

    public void damage() {
        if(currentState == State.DEAD) return;
        setToDestroy = true;
        stateTimer = 0;
    }
}
