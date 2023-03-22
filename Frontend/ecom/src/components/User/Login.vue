<template>
<div class=" wrapper">
    <div class="main-container">
        <div class="email">
            <input placeholder="Din email" type="email" v-model="email">
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
        <br>
        <div class="register">
            <p>Har du lyst å registere en ny bruker? </p>
            <button @click="changeRoute('Register')">Register ny bruker her</button>
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
            email: '',
            password: '',
            error: '',

        }
    },setup(){
        const tokenStore = useTokenStore();
        return { tokenStore };
    },mounted(){
        console.log('HeiHEi' + this.tokenStore.loggedInUser)
    },
    methods: { 
        changeRoute(string){
        this.$router.push({name:string})
        },
        async login(){
            if (!this.email || !this.password) {
                this.error = 'Epost og password er nødvendige felt';
                return;
            }
            await this.tokenStore.getTokenAndSaveInStore(this.email, this.password);
            console.log(this.tokenStore.jwtToken)
            if(this.tokenStore.jwtToken || this.tokenStore.jwtToken){
            this.changeRoute('Home')
            } else {
            this.error = "Logg in feilet. Har du rett email og passord?"
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
  background-color: var(--color-blue-lighter);
  width: 65%;
  height: 75%;
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




