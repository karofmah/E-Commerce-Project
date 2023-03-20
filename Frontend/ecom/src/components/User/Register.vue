<template>
    <div class=" wrapper">
        <div class="main-container">
            <h1>Ny bruker</h1>
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
            
        </div>
    </div>
</template>
    
    
<script>
import axios, { Axios } from 'axios';


export default{
data(){
    return{
        username: 'karofm',
        password: 'pw',
        email: 'karofm@ntnu.no',
        firstName: 'Karo',
        lastName: 'Mahmoud',
        error: '',
        access: '',
        role: 'NORMAL_USER'

    }
},methods: {
    changeRoute(string){
        this.$router.push({name:string})
    },
    async register(){
        const user = {
            username: this.username,
            password: this.password,
            email: this.email,
            firstName: this.firstName,
            lastName: this.lastName,
            role: this.role
        }
        console.log(user)

<<<<<<< HEAD
        this.access = await(await (axios.post("http://localhost:8080/api/users/register",user))).data 
=======
        this.access = await(await (axios.post("http://localhost:9090/register",user))).data 
>>>>>>> b9a99ce7ee29c84a43a79d512fd2951b1f024414
        if(this.access.data !== null){
            this.error = 'Ny bruker var registert'
            this.changeRoute('Login')
        }else{
            this.error = 'Registering av fungerte ikke. Prøv igjen....'
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
