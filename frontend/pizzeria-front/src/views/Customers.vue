<template>
    <section class="section">
      <button @click="loadCustomers()">Refresh</button>
      <table>
        <th>id</th>
        <th>name</th>
        <th>balance</th>

        <tr v-for="customer in customers">
          <td>{{ customer.id }}</td>
          <td>{{ customer.name }}</td>
          <td>{{ customer.balance }}</td>
          <td><button @click="deleteCustomer(customer.id)">remove</button></td>
        </tr>
      </table>

      <input type="text" v-model="addCustomerName">
      <button @click="addCustomer()">Add</button>
    </section>
</template>

<script>
export default {
  data() {
    return {
      customers: [],
      addCustomerName: "",
    }
  },
  methods: {
    addCustomer() {
      const url = 'http://localhost:8000/customers/add';
      const data = {
        "name": this.addCustomerName
      };
      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      }).then(response => {
        if (response.ok) {
          console.log('Customer added successfully');
        } else {
          console.error('Failed to add customer. Status:', response.status);
        }
      }).catch(error => {
        console.error('Error adding customer:', error);
      }).finally(() => {
        this.loadCustomers();
      });
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