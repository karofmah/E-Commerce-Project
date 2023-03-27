<template>
  <div class="container">
    <ContentItemTemplate
      v-for="item in items"
      :key="item.id"
      :item="item"
      :displayStyle="0"
      @click="handleItemClick(item)"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import ContentItemTemplate from './ContentItemTemplate.vue';
import { useCategoryStore } from '../../stores/categoryStore';
import { getKeyword } from '../../stores/searchStore';
import { watch } from 'vue';

let items = ref([]);
let router = useRouter();
const categoryStore = useCategoryStore();

async function getItems() {
  items.value = await axios.get('http://localhost:9090/api/items/getItems').then(res => res.data);
}

async function getItemsByCategory(category) {
  items.value = await axios
    .get('http://localhost:9090/api/items/get/category', { params: { category: category.categoryName } })
    .then(res => res.data);
}

async function getItemsBySearch(keyword) {
  items.value = await axios.get('http://localhost:9090/api/items/get/keyword', { params: { keyword: keyword } }).then(res => res.data);
}

const keyword = getKeyword();
  if (typeof keyword === 'string' && keyword.toLowerCase() !== '') {
    items.value = items.value.filter(item => item.locationString.toLowerCase().includes(keyword.toLowerCase()));
  }

function handleItemClick(linkItem) {
  router.push({ name: 'Item', params: { id: linkItem.id } });
}

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


onMounted(async () => {
await getItems();
});
</script>

<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        justify-content: flex-start;
        align-items: center;
        min-height: 30em;
        height: fit-content;
        background-color: rgba(176, 176, 176, .2);
        border-radius: 8px;
        padding-left: 2em;
        gap: 2em;
    }
</style>