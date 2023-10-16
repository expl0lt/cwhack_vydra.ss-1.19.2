package net.io.fabric.loader.module.modules.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;

import java.util.Random;

import static net.io.fabric.antarctica.MC;

public class FP extends Module
{

    public FP() {
        super("FakePlayer", "spawns a fake player", false, Category.Misc);
    }

    int id;

    @Override
    public void onEnable()
    {

        String[] names = {"gimek", "Splahs", "ellpaard", "didi","pathos","elpapuinyourpc","Jhalcar","Nzlt","Perron97"};
        Random rand = new Random();
        int randomIndex = rand.nextInt(names.length);
        String randomName = names[randomIndex];
        OtherClientPlayerEntity player = new OtherClientPlayerEntity(MC.world, new GameProfile(null, randomName), null);
        Vec3d pos = MC.player.getPos();
        player.updateTrackedPosition(pos.x,pos.y,pos.z);
        player.updatePositionAndAngles(pos.x, pos.y, pos.z, MC.player.getYaw(), MC.player.getPitch());
        player.resetPosition();
        MC.world.addPlayer(player.getId(), player);
        id = player.getId();
    }

    @Override
    public void onDisable()
    {
        MC.world.removeEntity(id, Entity.RemovalReason.DISCARDED);
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }
}
