<script setup>
import { ref, onMounted, watch, defineProps } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import 'ol/ol.css';
import { fromLonLat } from 'ol/proj';
import { Icon, Style } from 'ol/style';
import Point from 'ol/geom/Point';
import Feature from 'ol/Feature';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';

const route = useRoute();
const itemId = ref(route.params.id);

let item = ref({});

async function getItemsById(id) {
  item.value = await axios.get(`http://localhost:9090/api/items/${id}`).then(res => res.data);
}

let description = ref("");
let specs = ref("");
let descOrSpecBool = ref(true);

function descOrSpec(key) {
  switch (key) {
    case 0:
      descOrSpecBool.value = true;
      break;
    case 1:
      descOrSpecBool.value = false;
      break;
    default:
      throw new Error('Something went wrong');
  }
}

watch(route, async (newRoute) => {
  itemId.value = newRoute.params.id;
  await getItemsById(itemId.value);
});

onMounted(async () => {
  await getItemsById(itemId.value);

  const map = new Map({
    target: 'map-container',
    layers: [
      new TileLayer({
        source: new OSM(),
      }),
    ],
    view: new View({
      center: fromLonLat([0, 0]),
      zoom: 2,
    }),
  });

  if (item.value.location) {
    const { latitude, longitude } = item.value.location;
    const marker = new Feature({
      geometry: new Point(fromLonLat([longitude, latitude])),
    });

    marker.setStyle(
      new Style({
        image: new Icon({
          src: 'https://openlayers.org/en/latest/examples/data/icon.png',
          anchor: [0.5, 1],
        }),
      })
    );

    const markerSource = new VectorSource({
      features: [marker],
    });

    const markerLayer = new VectorLayer({
      source: markerSource,
    });

    map.addLayer(markerLayer);
    map.getView().setCenter(fromLonLat([longitude, latitude]));
    map.getView().setZoom(13);
  }
});
</script>


<template>
  <div class="container">
    <div class="images">
        <img v-for="img in item.images" :src="img" :alt="img">
    </div>

    <div class="shopping">
        <div>
            <h1>{{ item.briefDescription }}</h1>
            <h2>{{ item.price }}</h2>
        </div>
        <button>Add to cart</button>
    </div>

    <div class="map">
        <div id="map-container" class="map-container"></div>
    </div>

    <div class="info">
        <nav>
            <a href="#0" @click="descOrSpec(0)">Description</a>
            <a href="#0" @click="descOrSpec(1)">Spesification</a>
        </nav>
        <br>
        <h3 :hidden="!descOrSpecBool">{{ item.fullDescription }}</h3>
        <h3 :hidden="descOrSpecBool"></h3>
    </div>
  </div>
</template>

<style scoped>

    .map-container {
        width: 80%;
        height: 100%;
    }
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
    .images::-webkit-scrollbar-track {
        border-radius: 100vw;
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