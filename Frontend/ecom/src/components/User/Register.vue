<template>
    <div class=" wrapper">
        <div class="main-container">
            <h1>Ny bruker</h1>
            <br>
            <div class="email">
                <input placeholder="Epostadresse" type="email">
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
                <input placeholder="Fornavn" type="text">
            </div>
            <br>
            <div class="Surname">
                <input placeholder="Etternavn" type="text">
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
    
    export default{
        data(){
            return{
                username: '',
                password: '',
                email: '',
                forname: '',
                surname: '',
                error: '',
                access: ''
    
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
                    forname: this.forname,
                    surname: this.surname,
                }

                this.access = await(await (axios.post("http://localhost:8080/Createuser",user))).data 
                if(this.access === true){
                    this.error = 'Ny bruker var registert'
                    this.changeRoute('Login')
                }else{
                    this.error = 'Registering av fungerte ikke. Prøv igjen....'
                }
            }
        }
    }
    
    
    </script>
    
    
    
    
<<<<<<< HEAD
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
=======
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
>>>>>>> 3a30a7156a25abe70326a105968e31d1c54731ca
