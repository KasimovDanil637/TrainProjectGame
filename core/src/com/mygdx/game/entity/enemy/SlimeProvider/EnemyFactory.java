package com.mygdx.game.entity.enemy.SlimeProvider;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.entity.enemy.EnemyData;
import com.mygdx.game.entity.enemy.EnemyProvider;
import com.mygdx.game.entity.enemy.SlimeProvider.SlimeProvider;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class EnemyFactory {
    private static Random random = new Random();
    public static EnemyProvider randSlime(Rectangle rect, PlayScreen screen, PlayerProvider player) {
        if(random.nextBoolean()){
            return blueSlime(rect, screen, player);
        } else {
            return greenSlime(rect, screen, player);
        }
    }

    public static EnemyProvider blueSlime(Rectangle rect, PlayScreen screen, PlayerProvider player) {
        Vector2 pos = new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        Vector2 size = new Vector2(72, 64);
        Vector2 box2d = new Vector2(58,32);

        SlimeProvider slimeProvider = new SlimeProvider(screen, new EnemyData(5, Constants.SPEED_STANDART / 3, 0.7, 15, 1), pos, size, box2d, false);
        slimeProvider.addEnemy(player);

        Animation enemyWalk;
        Animation enemyAttack;
        Animation enemyStand;
        Animation enemyDeath;

        //get jump animation frames and add them to playerJump Animation
        enemyAttack = new Animation(0.5f, getAnimation(Constants.PATH_TO_ENTITIES + "/blue slime/pngs/blue slime attack.png", 5, size));

        //create animation frames for player standing
        enemyStand = new Animation(0.5f, getAnimation(Constants.PATH_TO_ENTITIES + "/blue slime/pngs/blue slime idle.png", 4, size));

        //get walk animation frames
        enemyWalk = new Animation(0.5f, getAnimation(Constants.PATH_TO_ENTITIES + "/blue slime/pngs/blue slime move.png", 3, size));
        //get death animation frames
        enemyDeath = new Animation(0.05f, getAnimation(Constants.PATH_TO_ENTITIES + "/blue slime/pngs/blue slime die.png", 5, size));


        slimeProvider.setAnimations(enemyWalk, enemyStand, enemyAttack, enemyDeath);

        return slimeProvider;
    }

    public static EnemyProvider greenSlime(Rectangle rect, PlayScreen screen, PlayerProvider player) {
        Vector2 pos = new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        Vector2 size = new Vector2(72, 64);
        Vector2 box2d = new Vector2(58,32);

        SlimeProvider slimeProvider = new SlimeProvider(screen, new EnemyData(5, Constants.SPEED_STANDART / 3, 0.7, 15, 1), pos, size, box2d, false);
        slimeProvider.addEnemy(player);

        Animation enemyWalk;
        Animation enemyAttack;
        Animation enemyStand;
        Animation enemyDeath;

        //get jump animation frames and add them to playerJump Animation
        enemyAttack = new Animation(0.5f, getAnimation(Constants.PATH_TO_ENTITIES + "/green slime/pngs/green attack.png", 5, size));

        //create animation frames for player standing
        enemyStand = new Animation(0.5f, getAnimation(Constants.PATH_TO_ENTITIES + "/green slime/pngs/green idle.png", 4, size));

        //get death animation frames
        enemyDeath = new Animation(0.05f, getAnimation(Constants.PATH_TO_ENTITIES + "/green slime/pngs/green die.png", 5, size));

        //get walk animation frames
        enemyWalk = new Animation(0.5F, getAnimation(Constants.PATH_TO_ENTITIES + "/green slime/pngs/green move.png", 3, size));


        slimeProvider.setAnimations(enemyWalk, enemyStand, enemyAttack, enemyDeath);

        return slimeProvider;
    }

    public static EnemyProvider dog(Rectangle rect, PlayScreen screen, PlayerProvider player) {
        Vector2 pos = new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        Vector2 size = new Vector2(64, 64);
        Vector2 box2d = new Vector2(34,27);
        SlimeProvider slimeProvider = new SlimeProvider(screen, new EnemyData(5, 0.5, 0.5, 20, 1), pos, size, box2d, true);
        slimeProvider.addEnemy(player);

        Animation enemyWalk;
        Animation enemyAttack;
        Animation enemyStand;
        Animation enemyDeath;

        //get jump animation frames and add them to playerJump Animation
        enemyAttack = new Animation(0.10f, getAnimation(Constants.PATH_TO_ENTITIES + "/dog/pngs/dog attack.png", 4, size));

        //create animation frames for player standing
        enemyStand = new Animation(0.25f, getAnimation(Constants.PATH_TO_ENTITIES + "/dog/pngs/dog idle.png", 4, size));

        //get walk animation frames
        enemyWalk = new Animation(0.15f, getAnimation(Constants.PATH_TO_ENTITIES + "/dog/pngs/dog run.png", 4, size));

        //get death animation frames
        enemyDeath = new Animation(0.15f, getAnimation(Constants.PATH_TO_ENTITIES + "/dog/pngs/dog death.png", 3, size));


        slimeProvider.setAnimations(enemyWalk, enemyStand, enemyAttack, enemyDeath);

        return slimeProvider;
    }

    public static EnemyProvider turrel(Rectangle rect, PlayScreen screen, PlayerProvider player) {
        Vector2 pos = new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        Vector2 size = new Vector2(48, 48);
        Vector2 box2d = new Vector2(22,45);
        BugProvider bug = new BugProvider(screen, new EnemyData(5, 0.1, 2.1, 10, 1), pos, size, box2d, false);
        bug.addEnemy(player);

        Animation enemyWalk;
        Animation enemyAttack;
        Animation enemyStand;
        Animation enemyDeath;

        //get jump animation frames and add them to playerJump Animation
        enemyAttack = new Animation(0.10f, getAnimation(Constants.PATH_TO_ENTITIES + "/tourel/pngs/tourel attack.png", 5, size));

        //create animation frames for player standing
        enemyStand = new Animation(0.25f, getAnimation(Constants.PATH_TO_ENTITIES + "/tourel/pngs/tourel idle.png", 4, size));

        //get walk animation frames
        enemyWalk = new Animation(0.15f, getAnimation(Constants.PATH_TO_ENTITIES + "/tourel/pngs/tourel run.png", 3, size));

        //get death animation frames
        enemyDeath = new Animation(0.10f, getAnimation(Constants.PATH_TO_ENTITIES + "/tourel/pngs/tourel die.png", 6, size));


        bug.setAnimations(enemyWalk, enemyStand, enemyAttack, enemyDeath);

        return bug;
    }
    private static Array<TextureRegion> getAnimation(String filePath, int count, Vector2 size) {
        Array<TextureRegion> frames = new Array<>();
        for(int i = 0; i < count; i++)
            frames.add(new TextureRegion(new Texture(filePath), i * ((int)size.x), 0, ((int)size.x), ((int)size.y)));
        return frames;
    }
}
