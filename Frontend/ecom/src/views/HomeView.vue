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
  <div class="container">
    <Header id="header"/>
    <br>
    <div class="catAndContAndSearch">
      <input type="text" class="search-bar" v-model="searchInput" placeholder="Search..." @keyup.enter="handleSearch" />
      <Categories id="categories"/>
      <Content id="content"/>
    </div>
    <Footer id="footer"/>
  </div>
</template>

<style scoped>
    .container{
      display: flex;
      align-items: start;
      flex-flow: row wrap;
      padding: 0;
      width: 100%;
    }
    .catAndContAndSearch{
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

    #categories{
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: start;
      width: 75%;
      overflow-x: scroll;
      flex-wrap: nowrap;
    }

    #categories::-webkit-scrollbar{
      display: none;
    }

    #content{
        flex: 1;
        margin: 0 1em;
    }

  .search-bar{
    background: url('../assets/search.svg') no-repeat 1.5% 50%;
    background-size: 20px;
	color: rebeccapurple;
    margin: 1.3em 1em 1em 1em;
    padding: 0.5rem 1rem;
    padding-left: 2.5em;
    width: 20%;
    height: 3em;
    font-size: 1rem;
    border-radius: 0.5rem;
    transition: all 0.3s ease;
  }

  .search-bar::-webkit-input-placeholder{
    color: var(--color-blue);
  }
</style>