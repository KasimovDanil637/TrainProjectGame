package com.mygdx.game.entity.platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.constants.Masks;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.interfaces.IUpdatable;
import com.mygdx.game.screen.PlayScreen;

public class MovingPlatform extends Sprite implements IUpdatable {
    private Body b2body;
    private Vector2 fromPoint;
    private Vector2 toPoint;

    private Vector2 dir;

    private double speed;

    private boolean moveBack;
    public MovingPlatform(Vector2 fromPoint, Vector2 toPoint, double speed) {
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.speed = speed;

        dir = new Vector2(toPoint.x + fromPoint.x * (-1), toPoint.y + -1 * fromPoint.y);
        dir.set(dir.nor());
        dir.set((float) (dir.x * speed), (float) (dir.y * speed));
    }

    public void definePlatform(PlayScreen screen, Texture texture, Vector2 size, Vector2 box2d) {
        setRegion(new TextureRegion(texture, (int) (size.x), (int) size.y));
        //define a potion body in a world

        BodyDef bdef = new BodyDef();
        bdef.position.set(fromPoint.x, fromPoint.y);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = screen.getWorld().createBody(bdef);
        b2body.setGravityScale(0);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(box2d.x / 2 / Constants.PPM, box2d.y / 2 / Constants.PPM);
        fdef.filter.categoryBits = Masks.GROUND_BIT;
        fdef.filter.maskBits = (Masks.GROUND_BIT | Masks.BRICK_BIT | Masks.PLAYER_BIT);

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        //set texture
        setBounds(fromPoint.x, fromPoint.y, size.x / Constants.PPM, size.y / Constants.PPM);

        screen.addToUpdate(this);
    }

    @Override
    public void update(double time) {
        if(!moveBack) {
            if(isNear(toPoint)){
                moveBack = true;
                dir.set(-1 * dir.x, -1 * dir.y);
            }
        } else {
            if(isNear(fromPoint)){
                moveBack = false;
                dir.set(-1 * dir.x, -1 * dir.y);
            }
        }

        b2body.setLinearVelocity(dir);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }
    private boolean isNear(Vector2 to) {
        return Math.abs(to.x - b2body.getPosition().x) < Constants.EPS + getWidth() / 4 && Math.abs(to.y - b2body.getPosition().y) < Constants.EPS + getHeight() / 4;
    }
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
