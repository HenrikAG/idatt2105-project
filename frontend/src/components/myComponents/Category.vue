<template>
    <button class="cat_box" @click="$emit('select-category', category.name)">
      <span v-if="category.emoji">{{ category.emoji }}</span>
      <span v-else>{{ category.name }}</span>
    </button>
  </template>
  
  <script>
  export default {
    name: 'Category',
    props: {
      category: {
        type: Object,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  .cat_box {
    font-size: 3rem;
    padding: 24px;
    background-color: #f5f5f5;
    border: 1px solid #ddd;
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.2s ease;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    width: 100%;
    color: black;
  }
  
  .cat_box:hover {
    background-color: #e0e0e0;
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0,0,0,0.15);
  }
  
  .cat_box:active {
    transform: translateY(1px);
    box-shadow: 0 1px 2px rgba(0,0,0,0.1);
  }
  </style>