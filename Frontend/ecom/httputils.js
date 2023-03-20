import axios from "axios";
export const getJwtToken = (email, password) => {
    const config = {
        headers: {
            "Content-type": "application/json",
        },
    };
    return axios.post("http://localhost:8080/api/users/login/token", JSON.stringify({email,password}), config);
}
export const getUserInfo = (email,token) => {
    const config = {
        headers: {
            "Content-type": "application/json",
            "Authorization" : "Bearer " + token
        },
    };
    return axios.get("http://localhost:8080/api/users/login/user?email=" +email,config);
}