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
          <label for="username">{{ $t("userName") }}</label>
          <input id="username" type="text" v-model="username" />
        </div>
        
        <div class="form-group">
          <label for="firstName">{{ $t("firstName") }}</label>
          <input id="firstName" type="text" v-model="firstName" />
        </div>

        <div class="form-group">
          <label for="lastName">{{ $t("lastName") }}</label>
          <input id="lastName" type="text" v-model="lastName" />
        </div>

        <div class="form-group">
          <label for="newPassword" >{{ $t("newPassword") }}</label>
          <input id="newPassword" type="password" v-model="newPassword" />
        </div>

        <div class="form-group">
          <label for="currentPassword">{{ $t("oldPassword") }}</label>
          <input id="currentPassword" type="password" v-model="currentPassword" />
        </div>

        
          <button type="submit">{{ $t("saveChanges") }}</button>
        
      </form>

      <br />
      <button @click="changeRoute('Home')">{{ $t("returnToStart") }}</button>
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
    this.error = `${this.$t("passwordToShort")}`;
    return;
  }

  if (this.currentPassword !== this.tokenStore.loggedInUser.password) {
    this.error = `${this.$t("wrongPassword")}`;
    return;
  }

  if(this.currentPassword.length !== this.tokenStore.loggedInUser.password){
    this.error = `${this.$t("blankPassword")}`;
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

  await this.tokenStore.getTokenAndSaveInStore(updatedUser.email, updatedUser.password);

  this.error = `${this.$t("updatedUser")}`;
  this.changeRoute("UserInfo");
} catch (error) {
  if (error.response) {
    if (error.response.status === 401) {
      this.tokenStore.logout();
      this.changeRoute("Home");
      this.error = `${this.$t("loggedOut")}`;
    } else if (error.response.status === 400) {
      this.error = `${this.$t("mistake")}`; + error.response.data.error;
    } else {
      this.error = `${this.$t("error")}`;
    }
  } else {
    this.error = `${this.$t("error")}`;
  }
}
},
changeRoute(string) {
this.$router.push({ name: string });
},
},
};
</script>