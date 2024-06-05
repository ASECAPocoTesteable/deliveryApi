package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepository : JpaRepository<Status, Long>
