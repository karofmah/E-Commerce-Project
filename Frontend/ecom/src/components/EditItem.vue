<template>
    <div class="new-item">
      <h1>Update Item</h1>
  
      <div class="existing-images">
        <h2>Existing Images:</h2>
        <div class="images-container">
            <div v-for="(image, index) in images" :key="index" class="image-wrapper">
            <img :src="image" alt="Uploaded image" class="uploaded-image" />
            <button @click="deleteImage(index)" class="remove-image-btn">Remove</button>
            </div>
        </div>
        </div>
  
      <div class="field-container">
        <label for="images">Upload Images (Max 10):</label>
        <input type="file" id="images" ref="images" multiple @change="handleImages" />
      </div>
  
      <div class="field-container">
        <label for="brief-description">Brief Description:</label>
        <input type="text" id="brief-description" v-model="briefDescription" />
      </div>
  
      <div class="field-container">
        <label for="category">Category:</label>
        <select id="category" v-model="category">
          <option value="ELECTRONICS">Electronics</option>
          <option value="VEHICLE">Vehicle</option>
          <option value="REAL_ESTATE">Real Estate</option>
        </select>
      </div>
  
      <div class="field-container">
        <label for="full-description">Full Description:</label>
        <textarea id="full-description" v-model="fullDescription"></textarea>
      </div>
  
      <div class="field-container">
        <div class="location-container">
          <label for="location">Location:</label>
          <input type="text" id="location" v-model="locationString" @change="handleLocation" />
        </div>
        <div id="map" ref="map" class="map"></div>
      </div>
  
      <div class="field-container">
        <label for="price">Price:</label>
        <input type="number" id="price" v-model="price" />
      </div>
  
      <button @click="submit">Submit</button>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>
  </template>

<script>
import axios from 'axios';
import { useTokenStore } from "../stores/userToken";
import { getUserInfo } from "/httputils.js";
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
      category: "",
      fullDescription: "",
      latitude: null,
      longitude: null,
      price: null,
      errorMessage: null,
      map: null,
      marker: null,
      markerLayer: null,
      locationString: ""
    };
  },
  setup() {
    const tokenStore = useTokenStore();
    return { tokenStore };
  },
  async mounted() {
  // Load item data from the backend
  await this.loadItemData();

  // Initialize the map after the component has been rendered
  this.$nextTick(() => {
    this.initMap();
    this.updateMapWithLocation(this.latitude, this.longitude);
  });

  // Reverse geocode the coordinates to get the location name
  await this.reverseGeocode(this.latitude, this.longitude);
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
    console.error("Error fetching reverse geocoding data");
    return;
  }

  const data = await response.json();
  if (!data || !data.display_name) {
    console.error("No location data found");
    return;
  }

  this.locationString = data.display_name;
},
async loadItemData() {
  try {
    const itemId = this.$route.params.id;
    const response = await axios.get(`http://localhost:9090/api/items/${itemId}`);

    if (response.status === 200) {
      const itemData = response.data;
      this.images = itemData.images;
      this.briefDescription = itemData.briefDescription;
      this.category = itemData.category;
      this.fullDescription = itemData.fullDescription;
      this.latitude = itemData.location.latitude;
      this.longitude = itemData.location.longitude;
      this.price = itemData.price;

      // Reverse geocode the coordinates to get the location name
      await this.reverseGeocode(this.latitude, this.longitude);

      // Update map with the item's location
      this.updateMapWithLocation(this.latitude, this.longitude);
    } else {
      this.errorMessage = 'Error loading item data';
    }
  } catch (error) {
    console.error("Error loading item data:", error);
    // Set dummy latitude and longitude if API call fails
    // Reverse geocode the coordinates to get the location name
    await this.reverseGeocode(this.latitude, this.longitude);
  }
},
updateMapWithLocation(lat, lon) {
  // Update the map view and add a marker at the coordinates.
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
  this.map = new Map({
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
    alert("You can only upload a maximum of 10 images.");
    return;
  }

  const newImages = await Promise.all(
    Array.from(files).map((file) => {
      return new Promise((resolve) => {
        const reader = new FileReader();
        reader.onload = () => {
          resolve(reader.result);
        };
        reader.readAsDataURL(file);
      });
    })
  );

  this.images = this.images.concat(newImages);
},
async handleLocation(event) {
  const location = event.target.value;

  // Use OpenStreetMap Nominatim API to get latitude and longitude for the location
  const response = await fetch(
    `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
      location
    )}&limit=1`
  );

  if (!response.ok) {
    console.error("Error fetching geocoding data");
    return;
  }

  const data = await response.json();
  if (!data || data.length === 0) {
    console.error("No location data found");
    return;
  }

  const { lat, lon } = data[0];
  this.latitude = parseFloat(lat);
  this.longitude = parseFloat(lon);

  // Update the map view and add a marker at the coordinates.
  this.updateMapWithLocation(this.latitude, this.longitude);
},
async submit() {
  const itemId = this.$route.params.id;
  const user = this.tokenStore.loggedInUser;
  const updatedItem = {
    id: itemId,
    seller: {
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      username: user.username,
      password: user.password,
role: user.role,
},
images: this.images,
briefDescription: this.briefDescription,
category: this.category,
fullDescription: this.fullDescription,
location: {
latitude: this.latitude,
longitude: this.longitude,
},
price: this.price,
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

    console.log(response)

    if (response.status === 200) {
      this.changeRoute('Home');
    } else {
      this.errorMessage = "There was an error while trying to update the item";
    }
  } catch (error) {
    console.error("Error updating item:", error);
    this.errorMessage = "There was an error while trying to update the item";
  }
},
},
};
</script>



<style scoped>
.map {
  width: 100%;
  height: 400px;
}

.images-container {
  display: flex;
  overflow-x: scroll;
  gap: 10px;
  padding: 10px;
  max-width: 100%;
}

.image-wrapper {
  position: relative;
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
  top: 0;
  right: 0;
  background-color: rgba(255, 0, 0, 0.7);
  color: #fff;
  border: none;
  border-radius: 50%;
  padding: 4px 8px;
  font-size: 14px;
  cursor: pointer;
}

.remove-image-btn:hover {
  background: #d32f2f;
}
</style>