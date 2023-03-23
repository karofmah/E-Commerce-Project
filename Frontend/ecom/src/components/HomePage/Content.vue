<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import ContentItemTemplate from './ContentItemTemplate.vue';
import { useCategoryStore } from '../../stores/categoryStore';
import { getKeyword } from '../../stores/searchStore'; // import the getKeyword function
import { watch } from 'vue';

let items = ref([]);
let router = useRouter();
const categoryStore = useCategoryStore();

async function getItems() {
  items.value = await axios.get("http://localhost:9090/api/items/getItems").then(res => res.data);
}

async function getItemsByCategory(category){
  items.value = await axios.get("http://localhost:9090/api/items/get/category", { params: { category: category.categoryName } }).then(res => res.data);
}

async function getItemsBySearch(keyword){
  items.value = await axios.get("http://localhost:9090/api/items/get/keyword", { params: { keyword: keyword } }).then(res => res.data);
}

function handleItemClick(linkItem) {
  router.push({ name: 'Item', params: { id: linkItem.id } });
}

// watch for changes in the selected category
watch(
  () => ({ category: categoryStore.selectedCategory, keyword: getKeyword() }),
  async ({ category, keyword }) => {
    if (keyword !== '') {
      await getItemsBySearch(keyword);
    } else if (category && category.categoryName !== 'all') {
      await getItemsByCategory(category);
    } else {
      await getItems();
    }
  },
  { immediate: true }
);

// fetch the items on component mount
onMounted(async () => {
  await getItems();
});

</script>

<template>
	<div class="container">
		<ContentItemTemplate v-for="item in items" :key="item.id" :item="item" :displayStyle="0" @click="handleItemClick(item)" />
	</div>
</template>

<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        justify-content: flex-start;
        align-items: center;
        /* width: 85%; */
        background-color: rgba(176, 176, 176, .2);
        border-radius: 8px;
        padding-left: 2em;
        gap: 10px;
    }
</style>