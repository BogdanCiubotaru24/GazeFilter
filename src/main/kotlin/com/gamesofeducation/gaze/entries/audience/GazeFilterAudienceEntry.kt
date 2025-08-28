package com.gamesofeducation.gaze.entries.audience

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.entries.Query
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.entries.emptyRef
import com.typewritermc.core.entries.ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.entity.AudienceEntityDisplay
import com.typewritermc.engine.paper.entry.entries.*
import com.typewritermc.engine.paper.entry.findDisplay
import com.typewritermc.engine.paper.utils.toBukkitLocation
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos

@Entry(
    "gaze_filter_audience",
    "Only keep players who are looking at a specific Typewriter entity definition.",
    Colors.MEDIUM_SEA_GREEN,
    "material-symbols:visibility"
)
class GazeFilterAudienceEntry(
    override val id: String = "",
    override val name: String = "",
    override val children: List<Ref<out AudienceEntry>> = emptyList(),
    val definition: Ref<out EntityDefinitionEntry> = emptyRef(),
    val maxDistance: Double = 6.0,
    val angleDegrees: Double = 9.0,
    val requireLineOfSight: Boolean = false,
) : AudienceFilterEntry {
    override suspend fun display(): AudienceFilter =
        GazeFilterAudienceFilter(ref(), definition, maxDistance, angleDegrees, requireLineOfSight)
}

class GazeFilterAudienceFilter(
    ref: Ref<out AudienceFilterEntry>,
    private val definition: Ref<out EntityDefinitionEntry>,
    private val maxDistance: Double,
    angleDegrees: Double,
    private val requireLineOfSight: Boolean,
) : AudienceFilter(ref), TickableDisplay {

    private val cosThreshold: Double = cos(angleDegrees * PI / 180.0)

    private fun displays(): Sequence<AudienceEntityDisplay> {
        // Resolve displays dynamically to reflect live changes to the definition or instances.
        return Query.findWhere<EntityInstanceEntry> { it.definition == definition }
            .mapNotNull { entry: EntityInstanceEntry ->
                val disp = entry.ref().findDisplay<AudienceDisplay>()
                disp as? AudienceEntityDisplay
            }
    }

    override fun filter(player: Player): Boolean {
        val pid = player.uniqueId
        val eyeLoc = player.eyeLocation
        val eyeDir: Vector = eyeLoc.direction.normalize()
        val eyeVec = eyeLoc.toVector()

        for (display in displays()) {
            if (!display.canView(pid)) continue

            val pos = display.position(pid) ?: continue
            val eyeOffset = display.entityState(pid).eyeHeight
            val targetLoc = pos.add(0.0, eyeOffset, 0.0).toBukkitLocation()
            if (targetLoc.world != player.world) continue

            val toTarget = targetLoc.toVector().subtract(eyeVec)
            val distance = toTarget.length()
            if (distance > maxDistance) continue

            val dirToTarget = toTarget.normalize()
            val dot = eyeDir.dot(dirToTarget)
            if (dot < cosThreshold) continue

            if (requireLineOfSight) {
                val hit = player.world.rayTraceBlocks(eyeLoc, dirToTarget, distance)
                if (hit != null) continue
            }

            return true
        }
        return false
    }

    override fun tick() {
        // Re-evaluate each tick for smooth behavior
        consideredPlayers.forEach { it.refresh() }
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (event.player in consideredPlayers) event.player.refresh()
    }

    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        if (event.player in consideredPlayers) event.player.refresh()
    }
}
