package com.mygdx.game.entity.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.constants.Constants;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.HealingEffect;
import com.mygdx.game.effects.StaminaRestoreEffect;
import com.mygdx.game.entity.EntityProvider;
import com.mygdx.game.entity.player.PlayerData;
import com.mygdx.game.entity.player.PlayerProvider;
import com.mygdx.game.screen.PlayScreen;

import java.util.Random;

public class PotionFactory {
    private static Random rand = new Random();


    public static EntityProvider createHealPotion(Vector2 position, PlayScreen playScreen, PlayerData playerData) {
        return new Potion(new HealingEffect((float) playerData.getMaxHp(),0.1,playerData), playScreen.getWorld(), position, new Vector2(11, 32), new Vector2(11, 32), new Texture("pngs/tiles/POTION2.png"));
    }

    public static EntityProvider createSanityPotion(Vector2 position, PlayScreen playScreen, PlayerData playerData) {
        return new Potion(new StaminaRestoreEffect(15,0.1,playerData), playScreen.getWorld(), position, new Vector2(11, 32), new Vector2(11, 32), new Texture("pngs/tiles/POTION1.png"));
    }

    public static EntityProvider createRandomPotion(Rectangle rect, PlayScreen playScreen, PlayerData playerData) {
        Vector2 position = new Vector2((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
        if(rand.nextBoolean()) {
            return createHealPotion(position, playScreen, playerData);
        } else {
            return createSanityPotion(position, playScreen, playerData);
        }
    }
}
