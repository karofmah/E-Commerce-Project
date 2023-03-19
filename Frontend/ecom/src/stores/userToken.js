import { defineStore } from "pinia";
import {getJwtToken,getUserInfo} from "/httputils.js"

export const useTokenStore = defineStore("token", {
state: () => ({
    jwtToken: null,
    loggedInUser: null,
}),
persist: {
    storage: sessionStorage, // note that data in sessionStorage is cleared when the page session ends
},

actions: {
    async getTokenAndSaveInStore(email, password) {
        try{
            let response = await getJwtToken(email, password);
            let data = response.data;
            if(data != null && data != '' && data != undefined){
                this.jwtToken = data;
                let userResponse = await getUserInfo(email, password,this.jwtToken);
                this.loggedInUser = userResponse.data
            }
        } catch (err){
            console.log(err)
        }
    }
},
});