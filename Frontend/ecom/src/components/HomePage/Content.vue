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

async function getItemsByLocation() {
  items.value = await axios.get('http://localhost:9090/api/items/getItems').then(res => res.data);

  // loop through each item and reverse geocode its latitude and longitude values using the OpenStreetMap Nominatim API
  for (const item of items.value) {
    const response = await axios.get(`https://nominatim.openstreetmap.org/reverse?lat=${item.location.latitude}&lon=${item.location.longitude}&format=json`);
    if (response.data.display_name) {
      item.locationString = response.data.display_name;
    } else {
      item.locationString = `(${item.location.latitude.toFixed(6)}, ${item.location.longitude.toFixed(6)})`;
    }
  }

  const keyword = getKeyword().toLowerCase();
  if (keyword !== '') {
    items.value = items.value.filter(item => item.locationString.toLowerCase().includes(keyword));
  }
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
  await getItemsByLocation();
}
},
{ immediate: true }
);

onMounted(async () => {
await getItemsByLocation();
});
</script>

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