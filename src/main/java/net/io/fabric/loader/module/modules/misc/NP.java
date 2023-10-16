package net.io.fabric.loader.module.modules.misc;

import net.io.fabric.loader.event.events.ItemUseListener;
import net.io.fabric.loader.module.Category;
import net.io.fabric.loader.module.Module;

import java.util.Random;

import static net.io.fabric.antarctica.MC;

public class NP extends Module {

    private static final String[] names = {"axtrality","ByPapaiiZ", "PRO_leo123", "yerbaa22", "Mednan", "zFalsey", "Unrent", "Kevstaah", "trynumber1001", "DqntCryMyBaby_", "Dartiny", "ValleyRaider", "UhTeaa", "AwsomeEliLilBro", "BinkedDOWN", "Fliesxz", "Fionalex", "Habersaat", "kaspercito", "K_kazi", "HPoseidon", "DeadCzar", "HoholikPL", "whateverDelta", "PETHEREO", "allyyxharry", "Likon", "andyda10n3", "MrPhoxy", "feastonrust", "_bluemoon", "percosit", "Itz_ncksz", "iisaias", "Eggd0_g", "0q22", "Low_KnockBack", "uneqsy", "RahSemperFi", "Dipppp"};
    private static final Random rand = new Random();
    private static String replacementUsername = "" + names[rand.nextInt(names.length)];

    private static boolean isEnabledStatic = false;


    public NP() {
        super("NameProtect", "Hides your name in chat.", false, Category.Misc);
    }

    @Override
    public void onEnable() {
        isEnabledStatic = true;
    }

    @Override
    public void onDisable() {
        isEnabledStatic = false;
    }

    @Override
    public void ItemUseListener(ItemUseListener.ItemUseEvent event) {

    }

    public static int setReplacementUsername(String newReplacementUsername) {
        replacementUsername = newReplacementUsername;
        return 0;
    }

    public static String replaceName(String string) {
        if (string != null && isEnabledStatic) {
            return string.replace(MC.getSession().getUsername(), replacementUsername);
        }
        return string;
    }
}

