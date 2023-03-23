<script setup>
 import { ref, watch } from 'vue';
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import Categories from '../components/HomePage/Categories.vue'
import Content from '../components/HomePage/Content.vue'
import { setKeyword, getKeyword } from '../stores/searchStore';
import { useCategoryStore } from '../stores/categoryStore';

const searchInput = ref(null);
const categoryStore = useCategoryStore();

function handleSearch() {
    setKeyword(searchInput.value);
}

watch(
  () => categoryStore.selectedCategory,
  () => {
    if (getKeyword() !== '') {
      searchInput.value = '';
      setKeyword('');
    }
  }
);
</script>

<template>
    <Header id="header"/>
    <br>
    <div class="search-bar">
        <input type="text" v-model="searchInput" placeholder="Search..." style="max-width: 200px;" @keyup.enter="handleSearch" />
    </div>
    <div class="catAndCont">
        <Categories id="categories"/>
        <Content id="content"/>
    </div>
    <div class=""></div>
    <Footer id="footer"/>
</template>

<style scoped>
    .container{
        display: flex;
    }
    .catAndCont{
        display: flex;
        flex-flow: row wrap;
        justify-content: center;
        align-items: start;
        width: 100%;
        margin: 2em 0;
    }
    #header{
        z-index: 2;
    }
    #content{
        flex: 1;
        margin: 0 1em;
    }

    .search-bar {
    margin-right: 1rem;
    margin-left: 0.5rem;
    flex-grow: 1;
  }

  .search-bar input {
    width: 100%;
    padding: 0.5rem 1rem;
    font-size: 1rem;
    border: none;
    border-radius: 0.5rem;
    color: #555;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    transition: all 0.3s ease;
  }

  .search-bar input:focus {
    outline: none;
    box-shadow: 0 0 0 3px #81b5f5;
  }
</style>