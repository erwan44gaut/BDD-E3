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