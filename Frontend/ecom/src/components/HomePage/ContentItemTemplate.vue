<script setup>
import { ref } from 'vue'

const props = defineProps({
  ID: Number,
  displayStyle: Number
})

const itemImg = ref('src/assets/examplemap.png')
const itemTitle = ref('Test title')
const itemDesc = ref('Test desc')
const price = ref(300)
const location = ref('Location 693, NY 146, Town of Guil')

function test() {
  console.log(location.value.length)
}
</script>

<template>
  <div v-if="displayStyle == 0" class="displayBase" id="displayFull">
    <div class="fullImg">
      <img :src="itemImg" id="itemImgFull" :alt="'Item' + ID + ' img'" />
    </div>
    <span for="itemImgFull" v-if="location.length < 38">{{ location }}</span>
    <marquee v-else behavior="scroll" direction="left">{{ location }}</marquee>
    <h1 id="itemTitle">{{ ID }} {{ itemTitle }}</h1>
    <h2>Price: {{ price }}</h2>
    <h3>{{ ID }} {{ itemDesc }}</h3>

    <!-- <button @click="test()">dd</button> -->
  </div>
</template>

<style scoped>
.displayBase {
  margin: 10px;
  flex: 0 0 auto;
  background-color: rgba(176, 176, 176, 0.5);
  border-radius: 8px;
  text-align: start;
  padding: 1em;
}

#displayFull {
  min-height: 21.5em;
  min-width: 25vw;
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
    max-width: 300px;
    height: 250px;
    object-fit: cover;
    vertical-align: middle; /* center the image vertically */
}

.displayBase span, .displayBase marquee {
    position: absolute;
    max-width: 89%;
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
