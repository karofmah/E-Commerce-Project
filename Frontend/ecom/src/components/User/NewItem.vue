<template>
  <div class="new-item">
    <br /><h1>Legg til ny annonse</h1>

    <div class="existing-images">
      <h2>Nåværende bilder:</h2>
      <div class="images-container">
        <div
          v-for="(image, index) in images"
          :key="index"
          class="image-wrapper"
        >
          <img
            :src="image"
            alt="Uploaded image"
            class="uploaded-image"
          />
          <img
            src="@/assets/cross.png"
            alt="Remove"
            class="remove-image-btn"
            @click="deleteImage(index)"
          />
        </div>
      </div>
    </div>

    <div class="field-container">
      <label for="images">Last opp bilder (Max 10):</label>
      <input
        type="file"
        id="images"
        ref="images"
        multiple
        @change="handleImages"
      />
    </div>

    <div class="field-container">
      <label for="brief-description">Kort beskrivelse:</label>
      <input
        type="text"
        id="brief-description"
        v-model="briefDescription"
      />
    </div>

    <div class="field-container">
      <label for="category">Kategori:</label>
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
      <label for="full-description">Full beskrivelse:</label>
      <textarea id="full-description" v-model="fullDescription"></textarea>
    </div>

    <div class="field-container">
      <div class="location-container">
        <label for="location">Plassering:</label>
        <input type="text" id="location" @change="handleLocation" />
      </div>
      <div id="map" ref="map" class="map"></div>
    </div>

    <div class="field-container">
      <label for="price">Pris:</label>
      <input type="number" id="price" v-model="price" />
    </div>

    <button @click="submit">Legg ut annonse</button>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    <br /><br />
  </div>
</template>


  <script>
    import axios, { Axios } from 'axios';
    import { useTokenStore } from "../../stores/userToken";
    import {getUserInfo} from "/httputils.js"
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
            displayMap: null,
            displayedImage: null,
            images: [],
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
            listed: "",
            categories: []
      };
    },
    setup(){
        const tokenStore = useTokenStore();
        return { tokenStore };
    },
    async mounted() {
      this.initMap();

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
        if (files.length > 10) {
          alert("Du kan max laste opp 10 bilder");
          return;
        }

        this.images = await Promise.all(
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
      },
      deleteImage(index) {
    this.images.splice(index, 1);
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
        console.error("Feil under henting av geokodingsdata");
        return;
        }

        const data = await response.json();
        if (!data || data.length === 0) {
        console.error("Ingen plasseringsdata funnet");
        return;
        }

        const { lat, lon } = data[0];
        this.latitude = parseFloat(lat);
        this.longitude = parseFloat(lon);

        // Update the map view and add a marker at the coordinates.
        this.map.getView().setCenter(fromLonLat([this.longitude, this.latitude]));
        this.map.getView().setZoom(13);
  
        if (this.marker) {
          this.markerLayer.getSource().removeFeature(this.marker);
        }
  
        this.marker = new Feature({
          geometry: new Point(fromLonLat([this.longitude, this.latitude])),
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
      async submit() {
  // Check if any required fields are empty
  if (!this.images.length || !this.briefDescription || !this.fullDescription || !this.latitude || !this.longitude || !this.price || !this.category) {
    this.errorMessage = "Vennligst fyll ut alle obligatoriske felter";
    return;
  }


  const user = this.tokenStore.loggedInUser;
  const newItem = {
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
      "Authorization" : "Bearer " + this.tokenStore.jwtToken
    },
  };

  this.listed = await(await (axios.post("http://localhost:9090/api/items/add",newItem,config))).data;
  if(this.listed != null){
    this.changeRoute('Home');
  } else {
    this.errorMessage = "Det oppsto en feil under forsøk på å liste elementet";
  }
},
    },
  };
</script>
  
<style scoped>

.existing-images {
  width: 100%;
  margin-bottom: 20px;
}

.images-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-wrapper {
  position: relative;
  max-width: 150px;
}

.uploaded-image {
  max-width: 100%;
  height: auto;
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

.error-message {
  color: red;
  font-weight: bold;
  margin-top: 10px;
}
.new-item {
display: flex;
flex-direction: column;
align-items: center;
gap: 10px;
width: 70%;
margin: 0 auto;
}

.field-container {
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
width: 45%;
}

.location-container {
display: flex;
flex-direction: column;
align-items: center;
gap: 10px;
width: 100%;
}

textarea {
width: 100%;
height: 150px;
resize: vertical;
}

label {
display: block;
margin-bottom: 5px;
}

input,
select {
width: 100%;
}

.map {
width: 400px;
height: 300px;
margin-top: 10px;
}
</style>