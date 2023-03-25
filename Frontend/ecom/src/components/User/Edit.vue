<style scoped>
  .wrapper{
    height: fit-content;
    margin: 3em;
  }
  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .form-group {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    margin: 1em;
  }
  label {
    display: block;
    text-align: right;
    width: 150px;
    margin-right: 10px;
  }
  input {
    width: 100%;
  }
  button[type="submit"] {
    margin-top: 20px;
    margin-left: 130px;
  }
</style>

<template>
  <div class="wrapper">
    <div class="main-container">
      <h1>Endre bruker</h1>
      <form @submit.prevent="handleSubmit">

        <div class="form-group">
          <label for="username">Brukernavn</label>
          <input id="username" type="text" v-model="username" />
        </div>
        
        <div class="form-group">
          <label for="firstName">Fornavn</label>
          <input id="firstName" type="text" v-model="firstName" />
        </div>

        <div class="form-group">
          <label for="lastName">Etternavn</label>
          <input id="lastName" type="text" v-model="lastName" />
        </div>

        <div class="form-group">
          <label for="newPassword" >Nytt passord</label>
          <input id="newPassword" type="password" v-model="newPassword" />
        </div>

        <div class="form-group">
          <label for="currentPassword">Gammelt passord</label>
          <input id="currentPassword" type="password" v-model="currentPassword" />
        </div>

        
          <button type="submit">Lagre endringer</button>
        
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
    firstName: this.tokenStore.loggedInUser.firstName,
    lastName: this.tokenStore.loggedInUser.lastName,
    newPassword: "",
    currentPassword: "",
    error: "",
  };
},
setup() {
  const tokenStore = useTokenStore();
  return { tokenStore };
},
methods: {

  async handleSubmit() {

  if(!(this.currentPassword.length == 0 && this.newPassword.length == 0)){

  if (this.newPassword !== "" && this.newPassword.length < 6) {
    this.error = "Nytt passord må være minst 6 tegn.";
    return;
  }

  if (this.currentPassword !== this.tokenStore.loggedInUser.password) {
    this.error = "Gammelt passord er feil.";
    return;
  }

  if(this.currentPassword.length !== this.tokenStore.loggedInUser.password){
    this.error = "Gammelt passord kan ikke være blank";
  }

}

const updateRequest = {
  email: this.email,
  username : this.username,
  firstName: this.firstName,
  lastName: this.lastName,
  newPassword: this.newPassword,
  currentPassword: this.currentPassword
};

const config = {
  headers: {
    "Content-type": "application/json",
    Authorization: "Bearer " + this.tokenStore.jwtToken,
  },
};

try {
  const response = await axios.put("http://localhost:9090/api/users/update", updateRequest, config);
  const updatedUser = response.data;

  // Oppdater tokenstore med den oppdaterte brukerinformasjonen
  await this.tokenStore.getTokenAndSaveInStore(updatedUser.email, updatedUser.password);

  // Vis suksessmelding og gå tilbake til UserInfo-siden
  this.error = "Brukerinformasjonen ble oppdatert.";
  this.changeRoute("UserInfo");
} catch (error) {
  if (error.response) {
    if (error.response.status === 401) {
      // Hvis tokenet er ugyldig, logg ut brukeren
      this.tokenStore.logout();
      this.changeRoute("Home");
      this.error = "Du er logget ut. Logg inn på nytt for å fortsette.";
    } else if (error.response.status === 400) {
      // Hvis det var et problem med forespørselen, vis feilmelding
      this.error = "Det oppstod en feil: " + error.response.data.error;
    } else {
      // Hvis det var en annen type feil, vis generell feilmelding
      this.error = "Det oppstod en feil. Prøv igjen senere.";
    }
  } else {
    // Hvis det ikke var noen respons, vis generell feilmelding
    this.error = "Det oppstod en feil. Prøv igjen senere.";
  }
}
},
changeRoute(string) {
this.$router.push({ name: string });
},
},
};
</script>