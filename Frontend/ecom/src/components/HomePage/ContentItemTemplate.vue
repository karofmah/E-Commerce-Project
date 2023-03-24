<script setup>
import { ref, onMounted } from 'vue';

const props = defineProps({
  ID: Number,
  item: {
    type: Object,
    required: true
  },
  displayStyle: Number
})

const location = ref('')

async function reverseGeocode(latitude, longitude) {
  const apiKey = "c91bf8238fff45d4beeb016ab09c1b7b";
  const response = await fetch(
    `https://api.opencagedata.com/geocode/v1/json?q=${encodeURIComponent(latitude)},${encodeURIComponent(longitude)}&key=${apiKey}`
  );

  if (!response.ok) {
    console.error("Error fetching reverse geocoding data");
    return "";
  }

  const data = await response.json();
  if (!data || !data.results || !data.results[0] || !data.results[0].formatted) {
    console.error("No location data found");
    return "";
  }

  return data.results[0].formatted;
}

onMounted(async () => {
  location.value = await reverseGeocode(props.item.location.latitude, props.item.location.longitude);
})
</script>

<template>
  <div v-if="displayStyle == 0" class="displayBase" id="displayFull">
    <div class="fullImg">
      <img :src="item.images[0]" id="itemImgFull" :alt="'Item' + ID + ' img'" />
    </div>
    <span for="itemImgFull" v-if="item.location.length < 38">{{ location }}</span>
    <marquee v-else behavior="scroll" direction="left">{{ location }}</marquee>
    <h1 id="itemTitle"> {{ item.briefDescription }}</h1>
    <h2>Price: {{ item.price }}</h2>
  </div>
</template>


<style scoped>
.displayBase {
    margin: 10px;
    flex: 0 0 auto;
    cursor: pointer;
    justify-content: space-evenly;
    background-color: var(--color-blue-lighter);
    /* background-color: rgba(176, 176, 176, 0.5); */
    border-radius: 8px;
    text-align: start;
    padding: 1em;
}

#displayFull {
  height: 30em;
  width: 30%;
}

.fullImg{
    display: flex;
    justify-content: center;
    height: 70%;
}

#itemImgFull{
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center;
}

.displayBase span, .displayBase marquee {
    position: absolute;
    max-width: 89%;
    max-height: 32px;
    top: 275px;
    left: 1.1em;
    padding: 3px;
    color: white;
    font-weight: bold;
    background-color: rgba(47, 47, 47, 0.8);
    border-radius: 50px;
    font-size: 16px;
    white-space: pre;
}


</style>
