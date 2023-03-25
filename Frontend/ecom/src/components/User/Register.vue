<template>
    <div class=" wrapper">
        <div class="main-container">
            <br><h1>Ny bruker</h1>
            <br>
            <div class="email">
                <input placeholder="Epostadresse" type="email" v-model="email">
            </div>
            <br>
            <div class="username">
                <input placeholder="Ditt brukernavn" type="text" v-model="username">
            </div>
            <br>
            <div class="password">
                <input placeholder="Ditt passord" type="password" v-model="password">
            </div>
            <br>
            <div class="Forname">
                <input placeholder="Fornavn" type="text" v-model="firstName">
            </div>
            <br>
            <div class="Surname">
                <input placeholder="Etternavn" type="text" v-model="lastName">
            </div>
            <br>
            <div class="submit">
                <button @click="register()">Register ny bruker</button>
            </div>
            <p>{{ error }}</p>
            <br>
        </div>
    </div>
</template>
    
    
<script>
import axios, { Axios } from 'axios';


export default{
data(){
    return{
        username: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        error: '',
        access: '',
        role: 'NORMAL_USER'
    }
},methods: {
    changeRoute(string){
        this.$router.push({name:string})
    },
    async register() {
  if (!this.email || !this.username || !this.password || !this.firstName || !this.lastName) {
    this.error = 'Alle feltene må være utfylt';
    return;
  }
  const user = {
    username: this.username,
    password: this.password,
    email: this.email,
    firstName: this.firstName,
    lastName: this.lastName,
    role: this.role
  }
 

  try {
    this.access = await (await (axios.post("http://localhost:9090/api/users/register", user))).data;
    if (this.access.data !== null) {
      this.error = 'Ny bruker var registert';
      this.changeRoute('Login');
    } else {
      this.error = 'Registering av fungerte ikke. Prøv igjen....';
    }
  } catch (error) {
    console.error(error);
    this.error = 'An error occurred during registration. Please try again.';
  }
}
}
}


</script>




<style scoped>

.wrapper{
display: flex;
flex-direction: column;
place-items: center;
justify-content: center;
align-items: center;
width: 100vw; 
height: 100%;
margin: 2em 0;
}

.main-container {
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
background-color: var(--color-blue-lighter);
width: 65%;
height: 85%;
border-radius: 15px;
}

.register{
display: flex;
flex-direction: column;
align-items: center;
}

.forgotten-password {
display: flex;
flex-direction: column;
align-items: center;
}


</style>
