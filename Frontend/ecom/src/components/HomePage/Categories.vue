<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useCategoryStore } from '../../stores/categoryStore';


let categories = ref([]);
const categoryStore = useCategoryStore();

async function getCategories() {
  categories.value = await axios.get("http://localhost:9090/api/categories/getCategories").then(res => res.data);
}

function handleCategoryClick(categorie) {
  const categoryStore = useCategoryStore();
  categoryStore.selectCategory(categorie);
}

function handleAllCategoriesClick() {
  const categoryStore = useCategoryStore();
  categoryStore.selectCategory({ categoryName: 'all' });
}

onMounted(async () => {
  await getCategories();
});


</script>

<template>
    <div class="container">
      <div
        class="categories"
        :class="{ selected: categoryStore.selectedCategory?.categoryName === 'all' }"
        @click="handleAllCategoriesClick"
      >
        <h3>Alle kategorier</h3>
      </div>
      <div
        class="categories"
        v-for="categorie in categories"
        :key="categorie.categoryName"
        :class="{ selected: categoryStore.selectedCategory?.categoryName === categorie.categoryName }"
        @click="handleCategoryClick(categorie)"
      >
        <h3>{{ categorie.categoryName }}</h3>
      </div>
    </div>
  </template>

<style scoped>
  .container{
    border-radius: 8px;
    margin: 0 0 0 0;
    width: 10em;
    height: 15vh;  
  }
  .categories{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: center;
    align-items: center;
    height: 3vh;
    background-color: var(--color-blue-lighter);
    opacity: 0.9;
    margin: 5px;
    height: 8vh;
    min-width: 8em;
    border-radius: 8px;
    text-align: center;
  }
  
  .categories h3::first-letter {
    text-transform: capitalize;
  }

  .categories:hover{
    cursor: pointer;
  }

  .selected {
    background-color: var(--color-blue); /* You can change this color to your preferred highlight color */
    color: white;
  }
</style>