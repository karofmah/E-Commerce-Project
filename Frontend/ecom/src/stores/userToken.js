import { defineStore } from "pinia";
import {getJwtToken,getUserInfo} from "/httputils.js"

export const useTokenStore = defineStore("token", {
state: () => ({
    jwtToken: null,
    loggedInUser: null,
}),
persist: {
    storage: sessionStorage,
},

actions: {
    async getTokenAndSaveInStore(email, password) {
        console.log('FUCK')
        try{
            let response = await getJwtToken(email, password);
            let data = response.data;
            console.log(data)
            if(data != null && data != '' && data != undefined){
                this.jwtToken = data;
                console.log(this.jwtToken)
                let userResponse = await getUserInfo(email,this.jwtToken);
                this.loggedInUser = userResponse.data
                console.log(this.loggedInUser)
            }
        } catch (err){
            console.log('FUCK DEN GIKK INN I ERROR')
            console.log(err)
        }
    }
},
});