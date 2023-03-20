<script setup>
import { defineProps, ref } from 'vue';

const props = defineProps({
  imgs: {
    type: Array,
  },
});

let description = ref("");
let specs = ref("");
let descOrSpecBool = ref(true);

description = "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Sint eius reprehenderit harum dignissimos at minus magni labore natus assumenda, blanditiis adipisci nihil eligendi saepe dolorem deleniti explicabo iste modi mollitia!";
specs = "!aitillom idom etsi obacilpxe itineled merolod epeas idnegile lihin icsipida siitidnalb ,adnemussa sutan erobal ingam sunim ta somissingid murah tiredneherper suie tniS .tile gnicisipida ,rutetcesnoc tema tis rolod muspi meroL";

function descOrSpec(key) {
    switch (key) {
        case 0:
            descOrSpecBool = true;
            break;
        case 1:
            descOrSpecBool = false;
            break;
        default:
            throw new Error('Something whent wrong');
    }
}

</script>

<template>
  <div class="container">
    <div class="images">
        <img v-for="img in imgs" :src="`src/assets/${img}`" :alt="img">
    </div>

    <div class="shopping">
        <div>
            <h1>Product title</h1>
            <h2>100kr</h2>
            <h3>This product is on sale for the rest of the month</h3>
        </div>
        <button>Add to cart</button>
    </div>

    <div class="map">
        <img src="../assets/examplemap.png" id="examplemap" alt="examplemap">
    </div>

    <div class="info">
        <nav>
            <a href="#0" @click="descOrSpec(0)">Description</a>
            <a href="#0" @click="descOrSpec(1)">Spesification</a>
        </nav>
        <br>
        <h3 :hidden="!descOrSpecBool">{{ description }}</h3>
        <h3 :hidden="descOrSpecBool">{{ specs }}</h3>
    </div>
  </div>
</template>

<style scoped>
    .container{
        display: flex;
        flex-flow: row wrap;
        min-height: 80rem;
        width: 80%;
        margin: 10vh auto;
        padding: 4vh;
        box-shadow: 2px 5px 10px 2px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
    }

    .images{
        display: flex;
        align-items: center;
        overflow-x: scroll;
        scroll-snap-type: x mandatory;
        scroll-behavior: smooth;
        max-width: 35%;
        max-height: 26em;
    }

    .shopping{
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 2em 12em;
        width: fit-content;
        flex: 1;
        max-height: 30%;
    }

    .map{
        display: flex;
        justify-content: center;
        width: 100%;
        height: 30em;
        margin: 5em 0;
        padding: 0em 0; 
    }

    #examplemap{
        width: 80%;
        height: 100%;
    }

    img{
        height: 18em;
        width: 18em;
        margin: 0 3em;
        scroll-snap-align: center;
        /* if you need to change color of svg */
        /* filter: invert(0.5) sepia(1) saturate(5) hue-rotate(175deg); */
    }

    nav{
        display: flex;
        flex-direction: row;
    }

    @media(max-width: 768px){
        .container{
            display: flex;
            flex-direction: column;
        }

    }
</style>