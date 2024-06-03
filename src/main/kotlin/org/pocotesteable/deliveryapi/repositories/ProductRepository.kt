package org.pocotesteable.deliveryapi.repositories

import org.pocotesteable.deliveryapi.entities.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>
