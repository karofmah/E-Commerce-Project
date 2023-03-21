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

async function reverseGeocode(latitude, longitude){
  const response = await fetch(
    `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${encodeURIComponent(
      latitude
    )}&lon=${encodeURIComponent(longitude)}`
  );

  if (!response.ok) {
    console.error("Error fetching reverse geocoding data");
    return "";
  }

  const data = await response.json();
  if (!data || !data.display_name) {
    console.error("No location data found");
    return "";
  }

  return data.display_name;
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
    <h3> {{ item.fullDescription }}</h3>
  </div>
</template>


<style scoped>
.displayBase {
  margin: 10px;
  flex: 0 0 auto;
  cursor: pointer;
  background-color: rgba(176, 176, 176, 0.5);
  border-radius: 8px;
  text-align: start;
  padding: 1em;
}

#displayFull {
  min-height: 21.5em;
  width: 25vw;
}

/* .fullImg{
        display: flex;
        justify-content: center;
    }

#itemImgFull{
    max-width: 300px;
    height: 250px;
    object-fit: cover;
} */

.fullImg {
    position: relative;
    display: inline-block;
    text-align: center; /* center the image horizontally */
    line-height: 0; /* remove any extra vertical space caused by inline-block elements */
}

#itemImgFull {
    max-width: 100%;
    height: 250px;
    object-fit: cover;
    vertical-align: middle; /* center the image vertically */
}

.displayBase span, .displayBase marquee {
    position: absolute;
    max-width: 89%;
    max-height: 32px;
    top: 230px;
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
