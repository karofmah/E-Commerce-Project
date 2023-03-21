<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import ContentItemTemplate from './ContentItemTemplate.vue';

let items = ref([]);
let router = useRouter();

async function getItems() {
  items.value = await axios.get("http://localhost:9090/api/items/getItems").then(res => res.data);
}

function handleItemClick(linkItem) {
  router.push({ name: 'Item', params: { id: linkItem.id } });
}

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
