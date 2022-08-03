package nwarena.model.toon;

/** Available classes for each {@link Player}.
 * <p>
 *     Each {@code ClassType} sets different values for
 *     <ul>
 *         <li>AB.</li>
 *         <li>AC.</li>
 *         <li>Attack Rounds.</li>
 *         <li>Health and Health Limit.</li>
 *         <li>Rest Amount.</li>
 *         <li>Preferred Class.</li>
 *     </ul>
 * </p> */
public enum ClassType {
    BARBARIAN,      // + Paladin, Fighter       - Paladin, PaleMaster
    FIGHTER,        // + Barbarian, Sorcerer    - PaleMaster, Paladin
    PALADIN,        // + Fighter, PaleMaster    - Barbarian, Sorcerer
    PALEMASTER,     // + Barbarian, Fighter     - Paladin, Sorcerer
    SORCERER,       // + Barbarian, PaleMaster  - Fighter, Paladin
    MONSTER
}