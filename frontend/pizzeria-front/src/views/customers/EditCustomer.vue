<template>
  <div>
    <button @click="goBackToList()">Back to list</button>
    <h5>name</h5>
    <input type="text" v-model="customer.name">
    <h5>balance</h5>
    <input type="text" v-model="customer.balance">
    <br><br>
    <button @click="addCustomer()">Save changes</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      customerId: this.$route.params.id,
      customer: {
        name: "",
        balance: "",
      }
    }
  },
  methods: {
    goBackToList()
    {
      this.$router.push(`/customers`);
    },
    async getCustomer()
    {
      try
      {
        const response = await fetch(`http://localhost:8000/customer`);
        fetch(url, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ "id": this.customerId })
        }).then(response => {
          if (response.ok) {
            this.customer = await response.json();
            console.log('Customer added successfully');
          } else {
            console.error('Failed to add customer. Status:', response.status);
          }
        }).catch(error => {
          console.error('Error adding customer:', error);
        });
        if (response.ok)
        {
          this.customer = await response.json();
        }
        else
        {
          console.error('Failed to fetch customer. Status:', response.status);
        }
      }
      catch (error)
      {
        console.error('Error fetching customer:', error);
      }
    },

  }
}
</script>