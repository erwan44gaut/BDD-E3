<template>
    <div>
      <h1>{{ name }}</h1>
      <p>{{ prix }} €</p>
      <p v-for="ingredient in ingredients">{{ ingredient.name }}</p>
      <button>Commander</button>
    </div>
  </template>

  <script>
  export default {
    props: {
      name: {
        type: String,
        required: true
      },
      prix: {
        type: Number,
        required: true
      },
      ingredients: {
        type: Array,
        required: true
      }
    },
    data() {
      return {};
    },
    mounted() {}
  };
  </script>