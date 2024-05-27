package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository : JpaRepository<Status, Long>
