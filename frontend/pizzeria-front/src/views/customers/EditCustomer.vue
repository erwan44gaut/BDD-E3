<template>
  <div>
    <button @click="goBackToList()">Back to list</button>
    <h5>name</h5>
    <input type="text" v-model="customer.name">
    <br><br>
    <button @click="addCustomer()">Add</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      customer: {
        name: "",
        balance: "",
      }
    }
  },
  methods: {
      
      goBackToList() {
          this.$router.push(`/customers`);
      },

      addCustomer() {
        const url = 'http://localhost:8000/customers/add';
        const data = {
          "name": this.customer.name,
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
        });
      },
  }
}
</script>