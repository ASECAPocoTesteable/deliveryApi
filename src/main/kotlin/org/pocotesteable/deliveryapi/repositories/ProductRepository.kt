package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, UUID>
