<template>
    <div class="wrapper">
      <div class="main-container">
        <h1>Endre bruker</h1>
        <form @submit.prevent="handleSubmit">
          <div class="form-group">
            <label for="email">E-postadresse</label>
            <input id="email" type="email" v-model="email" />
          </div>
  
          <div class="form-group">
            <label for="username">Brukernavn</label>
            <input id="username" type="text" v-model="username" />
            <button type="submit" :disabled="!username">Endre</button>
          </div>
  
          <div class="form-group">
            <label for="forname">Fornavn</label>
            <input id="forname" type="text" v-model="forname" />
            <button type="submit" :disabled="!forname">Endre</button>
          </div>
  
          <div class="form-group">
            <label for="surname">Etternavn</label>
            <input id="surname" type="text" v-model="surname" />
            <button type="submit" :disabled="!surname">Endre</button>
          </div>
  
          <div class="form-group">
            <label for="newPassword">Nytt passord</label>
            <input id="newPassword" type="password" v-model="newPassword" />
            <button type="submit" :disabled="!newPassword">Endre</button>
          </div>
  
          <div v-if="!password">
            <button type="submit" :disabled="!isFormValidWithoutPassword">Lagre endringer uten passordendring</button>
          </div>
  
          <div v-if="password">
            <div class="form-group">
              <label for="oldPassword">Gammelt passord</label>
              <input id="oldPassword" type="password" v-model="oldPassword" required />
            </div>
  
            <button type="submit" :disabled="!isFormValidWithPassword">Lagre endringer med passordendring</button>
          </div>
  
        </form>
  
        <br />
        <button @click="changeRoute('Home')">Tilbake til start</button>
        <br />
        <p>{{ error }}</p>
      </div>
    </div>
  </template>



<script>
import { useTokenStore } from "../../stores/userToken";
import axios from "axios";

export default {
  data() {
    return {
      email: this.tokenStore.loggedInUser.email,
      username: this.tokenStore.loggedInUser.username,
      forname: this.tokenStore.loggedInUser.firstName,
      surname: this.tokenStore.loggedInUser.lastName,
      password: "",
      oldPassword: "",
      newPassword: "",
      error: "",
    };
  },
  setup() {
    const tokenStore = useTokenStore();
    return { tokenStore };
  },
  computed: {
    isFormValidWithoutPassword() {
      return (
        this.email ||
        this.username ||
        this.forname ||
        this.surname ||
        this.newPassword
      );
    },
    isFormValidWithPassword() {
      return (
        this.email ||
        this.username ||
        this.forname ||
        this.surname ||
        (this.newPassword && this.password === this.tokenStore.loggedInUser.password)
      );
    },
  },
  methods: {
    async handleSubmit() {
    const user = {
    username: this.tokenStore.loggedInUser.username,
    password: this.oldPassword,
    email: this.email || this.tokenStore.loggedInUser.email,
    forname: this.forname || this.tokenStore.loggedInUser.forname,
    surname: this.surname || this.tokenStore.loggedInUser.surname,
    new_password: this.newPassword,
  };

  const config = {
    headers: {
      "Content-type": "application/json",
      Authorization: "Bearer " + this.tokenStore.jwtToken,
    },
  };

  try {
    if (this.newPassword && this.oldPassword !== this.tokenStore.loggedInUser.password) {
      throw new Error("Gammelt passord stemmer ikke");
    }

    let endpoint = null;

    if (this.email) {
      endpoint = "Updateemail";
    } else if (this.username) {
      endpoint = "Updateusername";
    } else if (this.forname) {
      endpoint = "Updateforname";
    } else if (this.surname) {
      endpoint = "Updatesurname";
    } else if (this.newPassword) {
      endpoint = "Updatepassword";
    }

    if (endpoint) {
      let response = await axios.post(
        `http://localhost:8080/${endpoint}`,
        user,
        config
      );

      if (response.data.email) {
        this.email = response.data.email;
      }

      if (response.data.username) {
        this.username = response.data.username;
      }

      if (response.data.forname) {
        this.forname = response.data.forname;
      }

      if (response.data.surname) {
        this.surname = response.data.surname;
      }

      if (response.data.password) {
        this.password = response.data.password;
      }

      await this.tokenStore.getTokenAndSaveInStore(
        this.username,
        this.password
      );
    }

    this.oldPassword = "";
    this.newPassword = "";
    this.error = "";
  } catch (error) {
    this.error = error.message;
  }
},
changeRoute(string) {
  this.$router.push({ name: string });
},
},
};
</script>