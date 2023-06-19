<template>
    <section class="section">
      <Button @click="loadCustomers()">Refresh</Button>

      <Button @click="openAddForm()">Add</Button>
      <table>
        <th>id</th>
        <th>name</th>
        <th>balance</th>

        <tr v-for="customer in customers">
          <td>{{ customer.customer_id }}</td>
          <td>{{ customer.customer_name }}</td>
          <td>{{ customer.customer_balance }}</td>
          <td><Button @click="openEditForm(customer.customer_id)">edit</Button></td>
          <td><Button @click="deleteCustomer(customer.customer_id)">remove</Button></td>
        </tr>
      </table>
    </section>
</template>

<script>
import Button from "primevue/button";
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';   // optional
import Row from 'primevue/row';                   // optional

import "primevue/resources/themes/lara-light-indigo/theme.css";        
import "primevue/resources/primevue.min.css";

export default {
  components: {
    Button,
    DataTable,
    Column,
    ColumnGroup,
    Row,
  },
  data() {
    return {
      customers: [],
    }
  },
  methods: {
    openEditForm(customerId) {
      this.$router.push('/customers/edit/' + customerId);
    },
    openAddForm() {
      this.$router.push('/customers/add');
    },
    editCustomer(customer) {
      console.log(customer);
    },
    deleteCustomer(customerId) {
      const url = 'http://localhost:8000/customers/delete';
      const data = {
        id: customerId
      };

      fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      }).then(response => {
        if (response.ok) {
          console.log('Customer deleted successfully');
        } else {
          console.error('Failed to delete customer. Status:', response.status);
        }
      }).catch(error => {
        console.error('Error deleting customer:', error);
      }).finally(() => {
        this.loadCustomers();
      });
    },
    async loadCustomers() {
      try
      {
        const response = await fetch('http://localhost:8000/customers');
        if (response.ok)
        {
          this.customers = await response.json();
        }
        else
        {
          console.error('Failed to fetch customers. Status:', response.status);
        }
      }
      catch (error)
      {
        console.error('Error fetching customers:', error);
      }
    }
  },
  mounted() {
    this.loadCustomers();
  }
}
</script>

<style lang="scss">

</style>