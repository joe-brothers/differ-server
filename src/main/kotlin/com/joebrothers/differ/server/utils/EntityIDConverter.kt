package com.joebrothers.differ.server.utils

import io.mcarle.konvert.api.Konverter
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import java.util.UUID

@Konverter
interface EntityIDConverter {
    fun entityIdUuidToUuid(entityId: EntityID<UUID>): UUID {
        return entityId.value
    }
}
