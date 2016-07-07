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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class HanweirGarrison extends CardImpl {

    public HanweirGarrison(UUID ownerId) {
        super(ownerId, 130, "Hanweir Garrison", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Hanweir Garrison attacks, put two 1/1 red Human creature tokens onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new RedHumanToken(), 2, true, true), false));

        // <i>(Melds with Hanweir Battlements.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("<i>(Melds with Hannweir Battlements.)</i>")));
    }

    public HanweirGarrison(final HanweirGarrison card) {
        super(card);
    }

    @Override
    public HanweirGarrison copy() {
        return new HanweirGarrison(this);
    }
}

class RedHumanToken extends Token {

    public RedHumanToken() {
        super("Human", "1/1 red Human creature token");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add("Human");

        this.color = ObjectColor.RED;
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }
}