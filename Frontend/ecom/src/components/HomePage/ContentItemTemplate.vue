<script setup>
import { ref, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

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
    return "";
  }

  const data = await response.json();
  if (!data || !data.display_name) {
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
    <span v-if="item.location.length < 38">{{ location }}</span>
    <span v-else class="scrolling-text">{{ location }}</span>
    <h1 id="itemTitle"> {{ item.briefDescription }}</h1>
    <h2>{{ t("price") }}: {{ item.price }}</h2>
  </div>
</template>

<style scoped>
.displayBase {
    margin: 10px;
    flex: 0 0 auto;
    cursor: pointer;
    justify-content: space-evenly;
    background-color: var(--color-blue-lighter);
    border-radius: 8px;
    text-align: start;
    padding: 1em;
    overflow: hidden;
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

.displayBase span, .scrolling-text {
    position: absolute;
    min-width: fit-content;
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

.scrolling-text {
  white-space: nowrap;
  overflow: hidden;
  animation: scrolling 10s linear infinite;
}

@keyframes scrolling {
  0% {
    transform: translateX(100%);
  }
  100% {
    transform: translateX(-100%);
  }
}

@media (max-width: 768px){
    #displayFull{
        width: 95%;
        margin: 4em 1em;
    }
}
</style>

