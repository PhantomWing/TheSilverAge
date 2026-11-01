package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.platform.CommonConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

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
    public static final StreamCodec<ByteBuf, SilverUndeadSettings> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SilverUndeadSettings::smite,
            ByteBufCodecs.FLOAT, SilverUndeadSettings::smiteDamage,
            ByteBufCodecs.FLOAT, SilverUndeadSettings::arrowSmiteDamage,
            ByteBufCodecs.BOOL, SilverUndeadSettings::thorns,
            ByteBufCodecs.FLOAT, SilverUndeadSettings::thornsChance,
            SilverUndeadSettings::new);

    /** This side's own config. */
    public static SilverUndeadSettings fromConfig() {
        return new SilverUndeadSettings(CommonConfig.silverSmite(), CommonConfig.silverSmiteDamage(),
                CommonConfig.silverArrowSmiteDamage(), CommonConfig.silverThorns(), CommonConfig.silverThornsChance());
    }
}
