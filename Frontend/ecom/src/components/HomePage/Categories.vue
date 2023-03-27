<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useCategoryStore } from '../../stores/categoryStore';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();


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
        <h3>{{ t("allCategories") }}</h3>
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
    margin: 0;
    flex-grow: inherit;
    height: 15vh;
  }
  .categories{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: center;
    align-items: center;
    background-color: var(--color-blue-lighter);
    opacity: 0.9;
    margin: 5px;
    height: fit-content;
    width: fit-content;
    min-height: 4em;
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

  @media (max-width: 768px){
    
  }
</style>