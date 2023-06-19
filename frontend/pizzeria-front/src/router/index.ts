import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Catalog from '../views/Catalog.vue'
import Orders from '../views/Orders.vue'
import DeliveryPersons from '../views/DeliveryPersons.vue'
import CustomerList from '../views/customers/CustomerList.vue'
import Stats from '../views/Stats.vue'
import AddCustomer from '../views/customers/AddCustomer.vue'
import EditCustomer from '../views/customers/EditCustomer.vue'

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
    component: CustomerList
  },
  {
    path: '/stats',
    name: 'Stats',
    component: Stats
  },
  {
    path: '/customers/add',
    name: 'Add customer',
    component: AddCustomer
  },
  {
    path: '/customers/edit/:id',
    name: 'Edit customer',
    component: EditCustomer
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
