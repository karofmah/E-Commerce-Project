<template>
    <div class="container">
      <h1>{{ $t("updateItem") }}</h1>
  
      <div class="existing-images">
        <h2>{{ $t("existingPictures") }}:</h2>
        <div class="images-container">
            <div v-for="(image, index) in images" :key="index" class="image-wrapper">
              <img :src="image" alt="Uploaded image" class="uploaded-image" />
              <img src="@/assets/cross.png" alt="Remove" class="remove-image-btn" @click="deleteImage(index)" />
            </div>
          </div>
      </div>

      <div class="field-container">
        <label for="images">{{ $t("uploadImages") }} (max 10):</label>
        <input type="file" id="images" ref="images" multiple @change="handleImages" />
      </div>
  
      <div class="field-container">
        <label for="brief-description">{{ $t("briefDescription") }}:</label>
        <input type="text" id="brief-description" v-model="briefDescription" />
      </div>
  
      <div class="field-container">
      <label for="category">{{ $t("category") }}:</label>
      <select id="category" v-model="category">
        
        <option
          v-for="category in categories"
          :key="category.categoryName"
          :value="category.categoryName"
        >
          {{ category.categoryName }}
        </option>
      </select>
    </div>
  
      <div class="field-container">
        <label for="full-description">{{ $t("fullDescription") }}:</label>
        <textarea id="full-description" v-model="fullDescription"></textarea>
      </div>
  
      <div class="field-container">
        <label for="location">{{ $t("location") }}:</label>
        <input type="text" id="location" v-model="locationString" @change="handleLocation" />
        <br>
        <div id="map" ref="map" class="map"></div>
      </div>
  
      <div class="field-container">
        <label for="price">{{ $t("price") }}:</label>
        <input type="number" id="price" v-model="price" />
      </div>
  
      <button @click="submit" id="submit">{{ $t("updateItem") }}</button>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>
  </template>

<script>
import axios from 'axios';
import { useTokenStore } from "../stores/userToken";
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

export default {
    data() {
    return {
        images: [
        ],
      briefDescription: "",
      category: [],
      fullDescription: "",
      latitude: null,
      longitude: null,
      price: null,
      errorMessage: null,
      map: null,
      marker: null,
      markerLayer: null,
      locationString: "",
      categories: [],
      idOfItem: null
    };
  },
  setup() {
    const tokenStore = useTokenStore();
    return { tokenStore };
  },
  async mounted() {
 
  await this.loadItemData();

  this.map = this.initMap();
  this.updateMapWithLocation(this.latitude, this.longitude);

  await this.reverseGeocode(this.latitude, this.longitude);

  const config = {
        headers: {
          "Content-type": "application/json",
          Authorization: "Bearer " + this.tokenStore.jwtToken,
        },
      };

      axios.get('http://localhost:9090/api/categories/getCategories',config)
      .then(response => {
        this.categories = response.data;
      })
      .catch(error => {
        console.error(error);
      });
},

methods: {
  changeRoute(string){
      this.$router.push({name:string})
    },
  
  deleteImage(index) {
    this.images.splice(index, 1);
  },

  async reverseGeocode(latitude, longitude) {
    const response = await fetch(
      `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${encodeURIComponent(
        latitude
      )}&lon=${encodeURIComponent(longitude)}`
    );

    if (!response.ok) {
      return;
    }

    const data = await response.json();
    if (!data || !data.display_name) {
      return;
    }

    this.locationString = data.display_name;
},

  async loadItemData() {
  try {
    const itemId = this.$route.params.id;
    this.idOfItem = itemId;
    const response = await axios.get(`http://localhost:9090/api/items/${itemId}`);

    if (response.status === 200) {
      const itemData = response.data;
      this.images = itemData.images;
      this.briefDescription = itemData.briefDescription;
      this.category = itemData.category.categoryName;
      this.fullDescription = itemData.fullDescription;
      this.latitude = itemData.location.latitude;
      this.longitude = itemData.location.longitude;
      this.price = itemData.price;

      await this.reverseGeocode(this.latitude, this.longitude);

    
      this.updateMapWithLocation(this.latitude, this.longitude);
    } else {
      this.errorMessage = `${this.$t("errorLoading")}`;
    }
  }catch (error) {
    await this.reverseGeocode(this.latitude, this.longitude);
  }
  },
  updateMapWithLocation(lat, lon) {
    if (!this.map) {
      return;
    }

    this.map.getView().setCenter(fromLonLat([lon, lat]));
    this.map.getView().setZoom(13);

    if (this.marker) {
      this.markerLayer.getSource().removeFeature(this.marker);
    }

    this.marker = new Feature({
      geometry: new Point(fromLonLat([lon, lat])),
    });

    this.marker.setStyle(
      new Style({
        image: new Icon({
          src: 'https://openlayers.org/en/latest/examples/data/icon.png',
          anchor: [0.5, 1],
        }),
      })
    );

    if (!this.markerLayer) {
      const markerSource = new VectorSource({
        features: [this.marker],
      });

      this.markerLayer = new VectorLayer({
        source: markerSource,
      });

      this.map.addLayer(this.markerLayer);
    } else {
      this.markerLayer.getSource().addFeature(this.marker);
  }
},

initMap() {
  return new Map({
    target: this.$refs.map,
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
},
async handleImages() {
  const files = this.$refs.images.files;
  const totalImages = files.length + this.images.length;

  if (totalImages > 10) {
    alert(`${this.$t("maxTotalImages")}`);
    return;
  }

  const maxWidth = 1110;
  const maxHeight = 600;

  const newImages = await Promise.all(
    Array.from(files).map(async (file) => {
      const resizedImageBlob = await this.resizeImage(file, maxWidth, maxHeight);
      return new Promise((resolve) => {
        const reader = new FileReader();
        reader.onload = () => {
          resolve(reader.result);
        };
        reader.readAsDataURL(resizedImageBlob);
      });
    })
  );

  this.images = this.images.concat(newImages);
},

async resizeImage(file, maxWidth, maxHeight) {
  return new Promise((resolve) => {
    const img = new Image();
    img.src = URL.createObjectURL(file);
    img.onload = () => {
      let width = img.width;
      let height = img.height;

      if (width > maxWidth || height > maxHeight) {
        const ratio = Math.min(maxWidth / width, maxHeight / height);
        width *= ratio;
        height *= ratio;
      }

      const canvas = document.createElement('canvas');
      canvas.width = width;
      canvas.height = height;
      const ctx = canvas.getContext('2d');
      ctx.drawImage(img, 0, 0, width, height);
      canvas.toBlob((blob) => {
        resolve(blob);
      }, file.type);
    };
  });
},

async handleLocation(event) {
  const location = event.target.value;

  const response = await fetch(
    `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
      location
    )}&limit=1`
  );

  if (!response.ok) {
    return;
  }

  const data = await response.json();
  if (!data || data.length === 0) {
    return;
  }

  const { lat, lon } = data[0];
  this.latitude = parseFloat(lat);
  this.longitude = parseFloat(lon);

  this.updateMapWithLocation(this.latitude, this.longitude);
},


async submit() {
  if (!this.images.length || !this.briefDescription || !this.category.trim() || !this.fullDescription || !this.latitude || !this.longitude || !this.price) {
    this.errorMessage = `${this.$t("missingInput")}`;
    return;
  }
  if (this.briefDescription.length > 42) {
    this.errorMessage = `${this.$t("briefDescriptionToLong")}`;
    return;
  }


  const user = this.tokenStore.loggedInUser;
  const updatedItem = {
    id : this.idOfItem,
    seller:{
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      username: user.username,
      password: user.password,
      role: user.role
    },
    images : this.images,
    briefDescription: this.briefDescription,
    fullDescription: this.fullDescription,
    category: {
      categoryName: this.category
    },
    location: {
      latitude:this.latitude,
      longitude: this.longitude
    },
    price: this.price
  };


  const config = {
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + this.tokenStore.jwtToken,
    },
  };

  try {
    const response = await axios.put(
      `http://localhost:9090/api/items/update`,
      updatedItem,
      config
    );
    if (response.status === 200) {
      this.changeRoute('Home');
    } else {
      this.errorMessage = `${this.$t("updateItemError")}`;
    }
  } catch (error) {
    this.errorMessage = `${this.$t("updateItemError")}`;
  }
},
},
};
</script>



<style scoped>
.container{
    display: flex;
    flex-flow: column wrap;
    align-items: center;
    height: fit-content;
    padding: 1em 0;
  }

  .existing-images{
    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    width: 100%;
  }
  .images-container{
    justify-content: center;
    height: 10em;
    width: 90%;
    overflow-x: auto;
    display: flex;
    overflow-x: auto;
    padding: 10px;
    max-width: 100%;
  }

  .image-wrapper {
    position: relative;
  }

  .field-container{
    display: flex;
    flex-flow: column wrap;
    padding: 1em;
  }

  #full-description{
    height: 17em;
    width: 40em;
  }

  .map{
    min-width: 60em;
    height: 30em;
  }

  .uploaded-image {
    max-width: 150px;
    max-height: 150px;
    object-fit: cover;
    border-radius: 5px;
    border: 1px solid #ccc;
  }

  .remove-image-btn {
    position: absolute;
    top: 5px;
    right: 5px;
    color: white;
    border: none;
    padding: 8px;
    cursor: pointer;
    font-size: 16px;
    width: 50px;
    height: 50px;
  }

  #submit{
    margin: 2em;
  }

  @media (max-width: 768px){
    .container{
      display: flex;
      width: 100%;
    }

    input, select{
      height: 5em;
      width: 30em;
    }

    #full-description{
      width: 20em;
    }

    .map{
      min-width: 15em;
    }

    button{
      width: 25em;
    }
  }
</style>