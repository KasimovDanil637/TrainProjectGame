package com.mygdx.game.entity.enemy.SlimeProvider;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityData;
import com.mygdx.game.entity.enemy.EnemyData;
import com.mygdx.game.screen.PlayScreen;
import com.mygdx.game.entity.enemy.EnemyProvider;


public class SlimeProvider extends EnemyProvider{
    protected boolean setToDestroy;
    protected boolean destroyed;
    protected boolean moveReverse;

    protected Animation enemyWalk;
    protected Animation enemyStand;
    protected Animation enemyAttack;
    protected Animation enemyDeath;
    protected boolean isAttacking;
    protected boolean isDying;
    protected boolean orientationIsRight;

    public SlimeProvider(PlayScreen screen, EnemyData enemyData, Vector2 position, Vector2 textureSize, Vector2 box2d, boolean orientation) {
        super(screen, enemyData, position, box2d);
        stateTimer = 0;
        setToDestroy = false;
        destroyed = false;
        moveReverse = false;
        orientationIsRight = orientation;

        setBounds(position.x, position.y, textureSize.x / Constants.PPM, textureSize.y / Constants.PPM);
    }

    public void setAnimations(Animation walk, Animation stand, Animation attack, Animation death) {
        enemyAttack = attack;
        enemyStand = stand;
        enemyWalk = walk;
        enemyDeath = death;
    }
    public Body getBody() {
        return b2body;
    }
    @Override
    public void update(double time){
        if(playerProvider.getCurrentState() == State.DEAD) {
            currentState = State.STANDING;
            setRegion(getFrame(time));
            return;
        }
        if(!entityData.isAlive() && !destroyed) {
            setToDestroy = true;
        }
        setRegion(getFrame(time));

        if(setToDestroy && !destroyed && !isDying){
            world.destroyBody(b2body);
            isDying = true;
            currentState = State.DEAD;
            stateTimer = 0;
            setToDestroy = false;
        } else if (isDying && stateTimer > enemyDeath.getAnimationDuration()) {
            destroyed = true;
            stateTimer = 0;
        }
        else if(!destroyed && !isDying) {
            if(!isAttacking) currentState = updateState();

            if(isAttacking && stateTimer >= enemyAttack.getAnimationDuration()) {
                isAttacking = false;
                currentState = updateState();
                if(playerProvider.getPosition().dst2(b2body.getPosition()) < ((EnemyData)entityData).getAttackRange() * ((EnemyData)entityData).getAttackRange())
                    attack();
            }

            setRegion(getFrame(time));
            turn(time);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
    }
    protected void attack() {
        stateTimer = 0;
        playerProvider.getEntityData().decreaseHP(((EnemyData)entityData).getDamage());
    }
    private void turn(double time) {
        double attackRange = ((EnemyData) entityData).getAttackRange();
        double awakeRange = ((EnemyData) entityData).getAwakeRange();
        int playerAccesible = isPlayerIsHorizontalyGettable(b2body.getPosition());

        if(playerProvider.getPosition().dst2(b2body.getPosition()) < awakeRange * awakeRange) {
            if(playerProvider.getPosition().x - b2body.getPosition().x > 0) {
                moveReverse = orientationIsRight ? false : true;
            } else {
                moveReverse = orientationIsRight;
            }
        }

        if(!isAttacking && playerAccesible != 0 && playerProvider.getPosition().dst2(b2body.getPosition()) < attackRange * attackRange) {
            isAttacking = true;
            currentState = State.ATTACK;
        } else if(playerAccesible != 0) {
            if(!isAttacking) {
                currentState = State.WALKING;
                Vector2 velocity = b2body.getLinearVelocity();
                boolean isRight = (orientationIsRight && !moveReverse) || (!orientationIsRight && moveReverse);

                b2body.setLinearVelocity(new Vector2((float) (entityData.getSpeed() * (isRight ? 1 : -1)), velocity.y));
            }
        }
    }

    @Override
    protected void defineEnemy(Vector2 position, Vector2 size) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x, position.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2 / Constants.PPM, size.y / 2 / Constants.PPM);
        fdef.filter.categoryBits = Masks.ENEMY_BIT;
        fdef.filter.maskBits = Masks.GROUND_BIT |
                Masks.BULLET_BIT |
                Masks.BRICK_BIT |
                Masks.OBJECT_BIT ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }
    @Override
    public void damage(EntityData entityData) {
        if(!isAttacking) {
            currentState = State.ATTACK;
            entityData.decreaseHP(((EnemyData)getEntityData()).getDamage());
            isAttacking = true;
        }
    }

    @Override
    public void collisionWithoutDamage() {
        if(moveReverse) {
            moveReverse = false;
        } else {
            moveReverse = true;
        }
    }

    public TextureRegion getFrame(double dt){
        TextureRegion region = null;
        Vector2 pos = new Vector2(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        switch(currentState){
            case ATTACK:
                region = (TextureRegion) enemyAttack.getKeyFrame((float) stateTimer, false);
                break;
            case DEAD:
                region = (TextureRegion) enemyDeath.getKeyFrame((float) stateTimer, true);
                break;
            case FALLING:
            case WALKING:
                region = (TextureRegion) enemyWalk.getKeyFrame((float) stateTimer, true);
                break;
            case STANDING:
            default:
                region = (TextureRegion) enemyStand.getKeyFrame((float) stateTimer, true);
                break;
        }

        setBounds(pos.x, pos.y, region.getRegionWidth() / Constants.PPM, region.getRegionHeight() / Constants.PPM);

        if(moveReverse && !region.isFlipX()){
            region.flip(true, false);
        }
        else if(!moveReverse && region.isFlipX()){
            region.flip(true, false);
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? (stateTimer + dt) : 0;
        previousState = currentState;

        //return our final adjusted frame
        return region;

    }
}