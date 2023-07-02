package com.mygdx.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.effects.EffectManager;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.bullet.BulletData;
import com.mygdx.game.entity.bullet.BulletProvider;
import com.mygdx.game.screen.PlayScreen;


/**
 *
 */
public class PlayerProvider extends EntityProvider {
    protected boolean runningRight;

    private Animation playerWalk;
    private Animation playerStand;
    private Animation playerJump;

    private Animation playerRun;

    private EffectManager effectManager;

    private float timerToShoot;

    private PlayScreen screen;

    private boolean setToDestroy;

    public PlayerProvider(Vector2 location, PlayerData data, PlayScreen screen) {
        super();

        effectManager = new EffectManager(data);
        screen.addToUpdate(effectManager);
        entityData = data;

        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;


        //get run animation frames and add them to playerRun Animation
        playerRun = new Animation(0.10f, getAnimation(Constants.PATH_TO_ENTITIES + "/protagonist/pngs/run.png", 6, 72));


        //get jump animation frames and add them to playerJump Animation
        playerJump = new Animation(0.10f, getAnimation(Constants.PATH_TO_ENTITIES + "/protagonist/pngs/jump.png", 7, 100));

        //create animation frames for player standing
        playerStand = new Animation(0.25f, getAnimation(Constants.PATH_TO_ENTITIES + "/protagonist/pngs/idle.png", 4, 72));

        //get walk animation frames
        playerWalk = new Animation(0.15f, getAnimation(Constants.PATH_TO_ENTITIES + "/protagonist/pngs/walk.png", 6, 72));


        //define mario in Box2d
        definePlayer(location);

        //set initial values for players location, width and height. And initial frame as playerStand.
        setBounds(location.x, location.y, 72 / Constants.PPM, 72 / Constants.PPM);
    }

    private Array<TextureRegion> getAnimation(String filePath, int count, int height) {
        Array<TextureRegion> frames = new Array<>();
        for(int i = 0; i < count; i++)
            frames.add(new TextureRegion(new Texture(filePath), i * 72, 0, 72, height));
        return frames;
    }

    /**
     * @param time delta time of screen update
     */
    public void update(double time) {

        if(updateState() == State.DEAD && !setToDestroy) {
            setToDestroy = true;
            stateTimer = 0;
        }

        if(setToDestroy && stateTimer > 0.1) {
            world.destroyBody(b2body);
            setToDestroy = false;
            currentState = State.DEAD;
        } else if(setToDestroy) {
            stateTimer += time;
        }

        if(currentState != State.DEAD && !setToDestroy) {
            currentState = updateState();
            timerToShoot = timerToShoot > 0 ? (float) (timerToShoot - time) : 0;
            handleInput(time);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

            //update sprite with the correct frame
            setRegion(getFrame(time));
        }
    }

    @Override
    public EntityData getEntityData() {
        return entityData;
    }


    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, (float) (entityData.getSpeed() * 9)), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void definePlayer(Vector2 location){
        BodyDef bdef = new BodyDef();
        bdef.position.set(location.x, location.y);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(11 / Constants.PPM, 29 / Constants.PPM);
        fdef.filter.categoryBits = Masks.PLAYER_BIT;
        fdef.filter.maskBits = Masks.GROUND_BIT | Masks.OBJECT_BIT | Masks.ENEMY_BIT | Masks.BULLET_BIT | Masks.GAME_ENDER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void handleInput(double dt) {
        float multi = 1f;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && ((PlayerData) entityData).getStamina() > 1) {
            currentState = State.RUNNING;
            multi = 3f;
        } else
        if(previousState == State.RUNNING && stateTimer < 0.01) {
            currentState = State.POSTRUNNING;
            multi = 3f;
        }

        //control our player using immediate impulses
        Vector2 movedir = b2body.getLinearVelocity();
        if(updateState() != State.DEAD) {
            boolean ismove = false;
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !(updateState() == State.JUMPING || updateState() == State.FALLING))
                jump();
            if (Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= 2) {
                b2body.setLinearVelocity(new Vector2((float) (entityData.getSpeed() * multi), movedir.y));
                ismove = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -2) {
                b2body.setLinearVelocity(new Vector2((float) (-entityData.getSpeed() * multi), movedir.y));
                ismove = true;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && timerToShoot == 0) {
                timerToShoot = 0.5F;

                float x = runningRight ? 1 : -1;
                float y = 0;
                Vector2 dir = new Vector2(x, y);

                Vector2 loc = new Vector2(b2body.getPosition().x, b2body.getPosition().y);

                BulletProvider bullet = new BulletProvider(screen.getWorld(), loc, new BulletData(new Vector2(2,1), 4, Constants.SPEED_STANDART , 1.0, 10), dir.nor(), Masks.ENEMY_BIT );
                screen.addToUpdate(bullet);
            }
            if ((currentState == State.RUNNING || currentState == State.POSTRUNNING) && ismove && ((PlayerData) entityData).getStamina() > 0){
                ((PlayerData) entityData).decreaseStamina(0.2f);
            } else {
                currentState = updateState();

                ((PlayerData) entityData).addStamina(0.1f);
            }
        }
    }

    public void draw(Batch batch){
        super.draw(batch);
    }

    //getCurrentFrameDependingOnItsState(jump, standing, etc)
    public TextureRegion getFrame(double dt){
        TextureRegion region = null;
        Vector2 pos = new Vector2(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        switch(currentState){
            case DEAD:
                break;
            case POSTRUNNING:
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame((float) stateTimer, true);
                break;
            case FALLING:
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame((float) stateTimer, false);
                break;
            case WALKING:
                region = (TextureRegion) playerWalk.getKeyFrame((float) stateTimer, true);
                break;
            case STANDING:
            default:
                region = (TextureRegion) playerStand.getKeyFrame((float) stateTimer, true);

                break;
        }
        setBounds(pos.x, pos.y, region.getRegionWidth() / Constants.PPM, region.getRegionHeight() / Constants.PPM);

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        if(currentState != State.FALLING && currentState != State.POSTRUNNING)
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        if(currentState != State.POSTRUNNING)
        previousState = currentState;


        //return our final adjusted frame
        return region;

    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

}
