import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.pocotesteable.deliveryapi.entities.Delivery
import org.pocotesteable.deliveryapi.entities.Product
import org.pocotesteable.deliveryapi.entities.PurchaseOrder
import org.pocotesteable.deliveryapi.entities.Status
import org.pocotesteable.deliveryapi.enums.State
import org.pocotesteable.deliveryapi.repositories.DeliveryRepository
import org.pocotesteable.deliveryapi.repositories.OrderRepository
import org.pocotesteable.deliveryapi.repositories.ProductRepository
import org.pocotesteable.deliveryapi.repositories.StatusRepository
import org.pocotesteable.deliveryapi.services.implementations.ControlTowerServiceImpl
import org.pocotesteable.deliveryapi.services.implementations.OrderServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class OrderServiceTest {

    @Mock
    lateinit var deliveryRepository: DeliveryRepository

    @Mock
    lateinit var orderRepository: OrderRepository

    @Mock
    lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var orderServiceImpl: OrderServiceImpl

    @Mock
    lateinit var statusRepository: StatusRepository

    @Mock
    lateinit var controlTowerServiceImpl: ControlTowerServiceImpl

    @BeforeEach
    fun setUp() {
        orderServiceImpl = OrderServiceImpl(orderRepository, productRepository, statusRepository, deliveryRepository, controlTowerServiceImpl)
    }

    @Test
    fun `test getOrderByDelivery`() {
        // Arrange
        val deliveryId = 1L
        val delivery = Delivery(isAvailable = true).apply { id = deliveryId }

        val order = PurchaseOrder(
            userAddress = "123 Main St",
            status = Status(State.ASSIGNED, "Assigned"),
            warehouseDirection = "Warehouse A",
        ).apply { id = 1L }

        val product = Product(
            name = "Product A",
            quantity = 10,
            purchaseOrder = order,
        ).apply { id = 1L }

        whenever(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery))
        whenever(orderRepository.findByDeliveryId(delivery.id)).thenReturn(listOf(order))
        whenever(productRepository.findAllByPurchaseOrderId(order.id)).thenReturn(listOf(product))

        // Act
        val result = orderServiceImpl.getOrderByDelivery(deliveryId)

        // Assert
        assertEquals(1, result.size)
        val orderedDTO = result[0]
        assertEquals(order.id, orderedDTO.id)
        assertEquals(order.userAddress, orderedDTO.userAddress)
        assertEquals(order.status.state.toString(), orderedDTO.status)
        assertEquals(order.warehouseDirection, orderedDTO.warehouseDirection)

        val productDTOs = orderedDTO.products
        assertEquals(1, productDTOs.size)
        val productDTO = productDTOs[0]
        assertEquals(product.id, productDTO.id)
        assertEquals(product.name, productDTO.name)
        assertEquals(product.quantity, productDTO.quantity)

        verify(deliveryRepository).findById(deliveryId)
        verify(orderRepository).findByDeliveryId(deliveryId)
        verify(productRepository).findAllByPurchaseOrderId(order.id)
    }

    @Test
    fun `test getOrderByDelivery DeliveryNotFound`() {
        // Arrange
        val deliveryId = 1L
        whenever(deliveryRepository.findById(deliveryId)).thenReturn(Optional.empty())

        // Act & Assert
        val exception = assertThrows<Exception> {
            orderServiceImpl.getOrderByDelivery(deliveryId)
        }

        assertEquals("Delivery not found", exception.message)
        verify(deliveryRepository).findById(deliveryId)
        verify(orderRepository, never()).findByDeliveryId(any())
        verify(productRepository, never()).findAllByPurchaseOrderId(any())
    }

    @Test
    fun `test getProductOrderByOrderId`() {
        // Arrange
        val orderId = 1L
        val order = PurchaseOrder(
            userAddress = "123 Main St",
            status = Status(State.ASSIGNED, "Assigned"),
            warehouseDirection = "Warehouse A",
        ).apply { id = orderId }

        val product = Product(
            name = "Product A",
            quantity = 10,
            purchaseOrder = order,
        ).apply { id = 1L }

        whenever(productRepository.findAllByPurchaseOrderId(orderId)).thenReturn(listOf(product))

        // Act
        val result = orderServiceImpl.getProductOrderByOrderId(orderId)

        // Assert
        assertEquals(1, result.size)
        val productDTO = result[0]
        assertEquals(product.id, productDTO.id)
        assertEquals(product.name, productDTO.name)
        assertEquals(product.quantity, productDTO.quantity)

        verify(productRepository).findAllByPurchaseOrderId(orderId)
    }
}
