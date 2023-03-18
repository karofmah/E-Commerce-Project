<template>
<div class=" wrapper">
    <div class="main-container">
        <div class="username">
            <input placeholder="Ditt brukernavn" type="text" v-model="username">
        </div>
        <br>
        <div class="password">
            <input placeholder="Ditt passord" type="password" v-model="password">
        </div>
        <p>{{ error }}</p>
        <br>
        <div class="submit">
            <button @click="login()">Logg inn</button>
        </div>
        <div class="register">
            <p>Har du lyst å registere en ny bruker? </p>
            <button>Register ny bruker her</button>
        </div>
        <br>
        <div class="forgotten-password">
            <p>Glemt passord? Klikk her</p>
            <button>Glemt passord</button>
        </div>
    </div>
</div>


</template>


<script>
    import axios, { Axios } from 'axios';
    import { useTokenStore } from "../../stores/userToken";
    import {getUserInfo} from "/httputils.js"

export default{
    data(){
        return{
            username: '',
            password: '',
            error: '',

        }
    },setup(){
        const tokenStore = useTokenStore();
        return { tokenStore };
    },
    methods: { 
        changeRoute(string){
        this.$router.push({name:string})
        },
        async login(){
            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);
            if(this.tokenStore.jwtToken || this.tokenStore.jwtToken){
            this.changeRoute('Home')
            } else {
            this.error = "Login failed!"
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
    height: 100vh;
}

.main-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #cbd5e1;
  width: 65%;
  height: 50%;
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




