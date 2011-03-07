/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;

/**
 *
 * @author Loki
 */
public class EldraziTemple extends CardImpl<EldraziTemple> {

    public EldraziTemple(UUID ownerId) {
        super(ownerId, 227, "Eldrazi Temple", Rarity.RARE, new CardType[]{ CardType.LAND }, null);
        this.expansionSetCode = "ROE";

        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new EldraziTempleManaAbility());
    }

    public EldraziTemple(final EldraziTemple card) {
        super(card);
    }

    @Override
    public EldraziTemple copy() {
        return new EldraziTemple(this);
    }
}

class EldraziTempleManaAbility extends BasicManaAbility<EldraziTempleManaAbility> {

    private static final String abilityText = "Spend this mana only to cast colorless Eldrazi spells or activate abilities of colorless Eldrazi. "
            + "<b>(Mage Tip: This ability can only be activated when an Eldrazi spell or ability is on the stack.)</b>";

    EldraziTempleManaAbility ( ) {
        super(new ManaEffect(Mana.ColorlessMana(2)));
        this.netMana.setColorless(2);
    }

    EldraziTempleManaAbility ( EldraziTempleManaAbility ability ) {
        super(ability);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        boolean eldraziSpellBeingCast = false;
        
        SpellStack stack = game.getStack();
        for ( int idx = 0; idx < stack.size(); idx++ ) {
            StackObject stackObject = stack.get(idx);
            if ( stackObject.getControllerId().equals(playerId) ) {
                eldraziSpellBeingCast |= stackObject.getSubtype().contains("Eldrazi");
            }
        }
        
        return super.canActivate(playerId, game) && eldraziSpellBeingCast;
    }

    @Override
    public String getRule() {
        return super.getRule() + "  " + abilityText;
    }

    @Override
    public String getRule(String source) {
        return super.getRule(source);
    }

    @Override
    public EldraziTempleManaAbility copy ( ) {
        return new EldraziTempleManaAbility(this);
    }
}
