<template>
    <div class="new-item">
      <h1>Add New Item</h1>
  
      <div>
        <label for="images">Upload Images (Max 10):</label>
        <input type="file" id="images" ref="images" multiple @change="handleImages" />
      </div>
  
      <div>
        <label for="brief-description">Brief Description:</label>
        <input type="text" id="brief-description" v-model="item.briefDescription" />
      </div>
  
      <div>
        <label for="category">Category:</label>
        <select id="category" v-model="item.category">
          <option value="electronics">Electronics</option>
          <option value="vehicle">Vehicle</option>
          <option value="real_estate">Real Estate</option>
        </select>
      </div>
  
      <div>
        <label for="full-description">Full Description:</label>
        <textarea id="full-description" v-model="item.fullDescription"></textarea>
      </div>
  
      <div class="location-container">
        <div>
          <label for="location">Location:</label>
          <input type="text" id="location" @change="handleLocation" />
        </div>
        <div id="map" ref="map" class="map"></div>
      </div>
  
      <div>
        <label for="price">Price:</label>
        <input type="number" id="price" v-model="item.price" />
      </div>
  
      <button @click="submit">Submit</button>

      <img id="testBase64" :src="displayedImage" alt="">
      <div id="displayMap" ref="displayMap" class="map"></div>
    </div>
  </template>
  
  <script>
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
        item: {
            displayMap: null,
            displayedImage: null,
            images: [],
            briefDescription: "",
            category: "",
            fullDescription: "",
            latitude: null,
            longitude: null,
            price: null,
        },
        map: null,
        marker: null,
        markerLayer: null,
      };
    },
    mounted() {
      this.initMap();
      this.initDisplayMap();
    },
    methods: {
        async updateDisplayMap() {
  if (this.item.latitude !== null && this.item.longitude !== null) {
    const marker = new Feature({
      geometry: new Point(fromLonLat([this.item.longitude, this.item.latitude])),
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

    this.displayMap.addLayer(markerLayer);
    this.displayMap.getView().setCenter(fromLonLat([this.item.longitude, this.item.latitude]));
    this.displayMap.getView().setZoom(13);
  }
},
        initDisplayMap() {
            this.displayMap = new Map({
                target: this.$refs.displayMap,
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
            alert("You can only upload a maximum of 10 images.");
            return;
        }

        this.item.images = await Promise.all(
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
        this.item.latitude = parseFloat(lat);
        this.item.longitude = parseFloat(lon);

        // Update the map view and add a marker at the coordinates.
        this.map.getView().setCenter(fromLonLat([this.item.longitude, this.item.latitude]));
        this.map.getView().setZoom(13);
  
        if (this.marker) {
          this.markerLayer.getSource().removeFeature(this.marker);
        }
  
        this.marker = new Feature({
          geometry: new Point(fromLonLat([this.item.longitude, this.item.latitude])),
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
        console.log(this.item);

        // Display the first image in the testBase64 img tag
        if (this.item.images.length > 0) {
            const testBase64Img = document.getElementById("testBase64");
            testBase64Img.src = this.item.images[0];
        }

        await this.updateDisplayMap();
      },
    },
  };
  </script>
  
  <style scoped>
.new-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.location-container {
  display: flex;
  gap: 10px;
}

textarea {
  width: 100%;
  height: 150px;
  resize: vertical;
}

.map {
  width: 400px;
  height: 300px;
}
</style>