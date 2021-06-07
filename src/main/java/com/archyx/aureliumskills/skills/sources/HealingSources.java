package com.archyx.aureliumskills.skills.sources;

import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;

public enum HealingSources implements SourceProvider {

    DRINK_REGULAR,
    DRINK_EXTENDED,
    DRINK_UPGRADED,
    SPLASH_REGULAR,
    SPLASH_EXTENDED,
    SPLASH_UPGRADED,
    LINGERING_REGULAR,
    LINGERING_EXTENDED,
    LINGERING_UPGRADED,
    GOLDEN_APPLE,
    ENCHANTED_GOLDEN_APPLE;

    @Override
    public Skill getSkill() {
        return Skills.HEALING;
    }
}
