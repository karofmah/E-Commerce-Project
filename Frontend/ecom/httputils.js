import axios from "axios";
export const getJwtToken = (email, password) => {
    const config = {
        headers: {
            "Content-type": "application/json",
        },
    };
    return axios.post("http://localhost:8080/token", JSON.stringify({ email, password }), config);
}
export const getUserInfo = (username,password,token) => {
    const config = {
        headers: {
            "Content-type": "application/json",
            "Authorization" : "Bearer " + token
        },
    };
    return axios.get("http://localhost:8080/users?username=" +username + "&password=" + password,config);
}