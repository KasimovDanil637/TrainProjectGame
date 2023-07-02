package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.interfaces.IUpdatable;
import com.mygdx.game.screen.PlayScreen;

public abstract class EntityProvider extends Sprite implements IUpdatable {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, DEAD, WALKING, POSTRUNNING, ATTACK, VICTORY }

    protected double stateTimer;

    protected State currentState;
    protected State previousState;

    protected World world;
    protected Body b2body;

    protected EntityData entityData;

    protected boolean setToDestroy;

    public EntityProvider() {
        currentState = State.STANDING;
    }

    public EntityProvider(PlayScreen screen) {
        this.world = screen.getWorld();
        currentState = State.STANDING;
    }

    protected State updateState() {
        if(!entityData.isAlive()) {
            return State.DEAD;
        }
        else if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING)){
            return State.JUMPING;
        }
        else if(b2body.getLinearVelocity().y < 0 && (currentState == State.FALLING || currentState == State.JUMPING || previousState == State.FALLING || previousState == State.JUMPING))
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.WALKING;
        else
            return State.STANDING;
    }

    public State getCurrentState() {
        return currentState;
    }
    public abstract void update(double time);

    public Vector2 getPosition() {
        return b2body.getPosition();
    }

    public EntityData getEntityData() {
        return entityData;
    }
}
