# Changelog – [Let's Do] Sleepy Hollows

---

[1.1.0]

In mist and code, the Hollow wakes,  
A newer realm, the old one shakes.  
The moon now shines on brighter scenes -  
**Ported to 1.21.1**, by haunted means.  

#### Sanity
- Sanity and all mob effects are back: Infected, Insanity, Bad Dream and Mental Fortitude
- Sanity drains twice as fast at night inside the biome
- Lanterns and Jack o'Lanterns protect your sanity within 4 blocks (`sleepy_hollows:sanity_light` tag)
- Low sanity brings whispers, laughter and footsteps behind you
- Each Hauntbound armor piece prevents 25% of sanity loss
- Sanity food can now always be eaten, even when you're not hungry
- Sanity can be disabled in the config
- All sanity values, food values and ambience intervals are configurable

#### Horseman
- Reworked boss fight with clear phases: two head phases, then an enraged final phase
- Soulfire spirals are announced before they strike
- The Horseman is shielded while his head is loose and his progress survives relogging
- Summoning is now a ritual at the pedestal instead of an instant lightning strike
- Guaranteed drop: Reins of the Spectral Horse
- The Horseman targets the nearest player and his laugh now echoes through the Hollow

#### World
- New biome fog that fades in and out smoothly, fully configurable
- The biome now only plays its own music track and replaces the current music
- Ambient sounds are rarer, spatial and echo
- Fireflies around Sleepy Hollows flowers at night
- Infected flowers emit a purple miasma
- Infected Zombies now spawn at any time of day
- Tombstones may awaken on their own at night and drop loot from `gameplay/tombstone`

#### Items
- Splash Luminous Water can be thrown to cleanse flowers, cure Infected and restore sanity (Luminous Water + Gunpowder)
- Loot Bags now give 2–4 items as intended and drop overflow at your feet
- Spectral War Axe ignores half of the target's armor
- Hauntbound armor now has durability and diamond-tier stats
- Raubbau keeps its permanent Efficiency VIII, the random haste is gone
- Completionist Banner grants Mental Fortitude
- New Music Disc: Elias Thornwick - Into the Hollows, found in coffins or dropped by creepers killed by skeletons

#### Fixes
- Fixed massive lag caused by hanging Spectral Lanterns 
- Fixed trees spawning hundreds of lanterns
- Fixed dedicated Fabric servers crashing when the Horseman dies
- Fixed NeoForge config values being ignored, the config is now `sleepy_hollows-startup.toml`
- Fixed infinite skeleton and loot farming on active tombstones
- Fixed pedestal cooldown being shared across all worlds
- Fixed Infected affecting players in creative mode
- Fixed missing common sources in the Fabric sources jar
- Fixed the sanity bar not changing until sanity dropped below ~88

#### Localization
- Reworked English and German texts: clearer advancement descriptions and consistent terms (Loot Bag, Spectral War Axe, Right-click)
- German: Sanity is now "Verstand" everywhere, Hauntbound is consistently "Geistgebunden", plus several fixed block names and grammar errors

***