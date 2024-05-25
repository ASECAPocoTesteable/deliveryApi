package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StatusRepository : JpaRepository<Status, UUID>
