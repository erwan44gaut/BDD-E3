import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Catalog from '../views/Catalog.vue'
import Orders from '../views/Orders.vue'
import DeliveryPersons from '../views/DeliveryPersons.vue'
import Customers from '../views/customers/Customers.vue'
import Stats from '../views/Stats.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Catalog
  },
  {
    path: '/catalog',
    name: 'Catalog',
    component: Catalog
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/deliveryPersons',
    name: 'Delivery persons',
    component: DeliveryPersons
  },
  {
    path: '/customers',
    name: 'Customers',
    component: Customers
  },
  {
    path: '/stats',
    name: 'Stats',
    component: Stats
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
