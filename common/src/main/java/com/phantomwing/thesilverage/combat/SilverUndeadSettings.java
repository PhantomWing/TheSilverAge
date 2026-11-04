package com.phantomwing.thesilverage.combat;

import net.minecraft.network.FriendlyByteBuf;

import com.phantomwing.thesilverage.platform.CommonConfig;

/**
 * How silver weapons and armour treat the undead, as the config says, with {@code silver_affects_undead} already
 * applied. The server reads its own; a client's tooltips read the server's, sent on join
 * ({@link com.phantomwing.thesilverage.client.ServerUndeadSettings}), so the numbers they show are the ones dealt.
 *
 * @param smite             whether silver tools and Silver Arrows deal extra damage to the undead
 * @param smiteDamage       a silver tool's extra damage, the Silver Hoe's being half of it
 * @param arrowSmiteDamage  a Silver Arrow's extra damage
 * @param thorns            whether silver armour hurts the undead that hit its wearer
 * @param thornsChance      each worn piece's chance of it, Silver Horse Armor counting as four
 */
public record SilverUndeadSettings(boolean smite, float smiteDamage, float arrowSmiteDamage, boolean thorns, float thornsChance) {
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(smite);
        buf.writeFloat(smiteDamage);
        buf.writeFloat(arrowSmiteDamage);
        buf.writeBoolean(thorns);
        buf.writeFloat(thornsChance);
    }

    public static SilverUndeadSettings read(FriendlyByteBuf buf) {
        return new SilverUndeadSettings(buf.readBoolean(), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readFloat());
    }

    /** This side's own config. */
    public static SilverUndeadSettings fromConfig() {
        return new SilverUndeadSettings(CommonConfig.silverSmite(), CommonConfig.silverSmiteDamage(),
                CommonConfig.silverArrowSmiteDamage(), CommonConfig.silverThorns(), CommonConfig.silverThornsChance());
    }
}
