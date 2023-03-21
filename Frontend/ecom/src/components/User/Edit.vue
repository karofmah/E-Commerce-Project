<template>
    <div class=" wrapper">
        <div class="main-container">
            <br><h1>Endre bruker</h1>
            <div class="email">
                <input placeholder="Ny epost" type="email" v-model="email">
                <input placeholder="Nåværende passord" type="password" v-model="passwordEmail">
                <button @click="changeEmail()">Endre epostadresse</button>
            </div>
            <br>
            <div class="username">
                <input placeholder="Ditt brukernavn" type="text" v-model="username">
                <input placeholder="Nåværende passord" type="password" v-model="passwordUsername">
                <button @click="changeUsername()">Endre brukernavn</button>
            </div>
            <br>
            <div class="password">
                <input placeholder="Nåværende passord" type="password" v-model="passwordOld">
                <input placeholder="Nytt passord" type="password" v-model="newPassword">
                <button @click="changePassword()">Endre passord</button>
            </div>
            <br>
            <div class="forname">
                <input placeholder="Nytt fornavn" type="text" v-model="forname">
                <input placeholder="Nåværende passord" type="password" v-model="passwordOld">
                <button @click="changeForname()">Endre fornavn</button>
            </div>
            <br>
            <div class="surname">
                <input placeholder="Etternavn" type="text" v-model="surname">
                <input placeholder="Nåværende passord" type="password" v-model="passwordSurname">
                <button @click="changeSurname()">Endre etternavn</button>
            </div>
            <div>
                <br>
                <br>
                <button @click="changeRoute('Home')">Tilbake til start</button>
            </div>
            <br>
            <p>{{ error }}</p>
        </div>
    </div>
</template>



<script>
import { useTokenStore } from "../../stores/userToken";

export default{
        data() {
        return {
            email: "",
            username: "",
            forname: "",
            surname: "",
            passwordEmail: "",
            passwordUsername: "",
            passwordOld: "",
            passwordForname: "",
            passwordSurname: "",
            newPassword: "",
            error: "",
        };
    },setup(){
        const tokenStore = useTokenStore();
        return { tokenStore };
    },
    methods: {
        changeRoute(string){
        this.$router.push({name:string})
        },
        async changeEmail(){
            if(this.tokenStore.loggedInUser.password === this.passwordEmail){
                const user = {
                    username: this.tokenStore.loggedInUser.username,
                    password: this.tokenStore.loggedInUser.password,
                    email: this.email,
                    forname: this.tokenStore.loggedInUser.forname,
                    surname: this.tokenStore.loggedInUser.surname,
                }
                const config = {
                headers: {
                    "Content-type": "application/json",
                    "Authorization" : "Bearer " + this.tokenStore.jwtToken
                },
            };

            let response = await(await (axios.post("http://localhost:8080/Updateemail", user,config)))
            this.username = response.data.username;
            this.password = response.data.password;

            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);


            }else{
                this.error = 'Feil passord'
            }
            
        },
        async changeUsername(){
            if(this.tokenStore.loggedInUser.password === this.passwordUsername){
                const user = {
                    username: this.username,
                    password: this.tokenStore.loggedInUser.password,
                    email: this.tokenStore.loggedInUser.email,
                    forname: this.tokenStore.loggedInUser.forname,
                    surname: this.tokenStore.loggedInUser.surname,
                }
                const config = {
                headers: {
                    "Content-type": "application/json",
                    "Authorization" : "Bearer " + this.tokenStore.jwtToken
                },
            };

            let response = await(await (axios.post("http://localhost:8080/Updateusername", user,config)))
            this.username = response.data.username;
            this.password = response.data.password;

            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);
            

            }else{
                this.error = 'Feil passord'
            }
        },
        async changePassword(){
            if(this.tokenStore.loggedInUser.password === this.passwordOld){
                const user = {
                    username: this.tokenStore.loggedInUser.username,
                    password: this.newPassword,
                    email: this.tokenStore.loggedInUser.email,
                    forname: this.tokenStore.loggedInUser.forname,
                    surname: this.tokenStore.loggedInUser.surname,
                }
                const config = {
                headers: {
                    "Content-type": "application/json",
                    "Authorization" : "Bearer " + this.tokenStore.jwtToken
                },
            };

            let response = await(await (axios.post("http://localhost:8080/Updatepassword", user,config)))
            this.username = response.data.username;
            this.password = response.data.password;

            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);


            }else{
                this.error = 'Feil passord'
            }
        },
        async changeForname(){
            if(this.tokenStore.loggedInUser.password === this.passwordForname){
                const user = {
                    username: this.tokenStore.loggedInUser.username,
                    password: this.tokenStore.loggedInUser.password,
                    email: this.tokenStore.loggedInUser.email,
                    forname: this.forname,
                    surname: this.tokenStore.loggedInUser.surname,
                }
                const config = {
                headers: {
                    "Content-type": "application/json",
                    "Authorization" : "Bearer " + this.tokenStore.jwtToken
                },
            };

            let response = await(await (axios.post("http://localhost:8080/Updateforname", user,config)))
            this.username = response.data.username;
            this.password = response.data.password;

            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);


            }else{
                this.error = 'Feil passord'
            }
        },
        async changeSurname(){
            if(this.tokenStore.loggedInUser.password === this.passwordSurname){
                const user = {
                    username: this.tokenStore.loggedInUser.username,
                    password: this.tokenStore.loggedInUser.password,
                    email: this.tokenStore.loggedInUser.email,
                    forname: this.tokenStore.loggedInUser.forname,
                    surname: this.surname,
                }
                const config = {
                headers: {
                    "Content-type": "application/json",
                    "Authorization" : "Bearer " + this.tokenStore.jwtToken
                },
            };


            let response = await(await (axios.post("http://localhost:8080/Updatesurname", user,config)))
            this.username = response.data.username;
            this.password = response.data.password;

            await this.tokenStore.getTokenAndSaveInStore(this.username, this.password);


            }else{
                this.error = 'Feil passord'
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
    margin: 3em 0;
}

.main-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #cbd5e1;
    width: 80%;
    height: 90%;
    border-radius: 15px;
}


.email{
    display: flex;
    flex-direction: column;
}

.username{
    display: flex;
    flex-direction: column;
}

.password{
    display: flex;
    flex-direction: column;
}

.forname{
    display: flex;
    flex-direction: column;
}

.surname{
    display: flex;
    flex-direction: column;
}


</style>