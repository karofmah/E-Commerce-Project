import axios from "axios";
export const getJwtToken = (email, password) => {
    const config = {
        headers: {
            "Content-type": "application/json",
        },
    };
<<<<<<< HEAD
    return axios.post("http://localhost:8080/api/users/login/token", JSON.stringify({email,password}), config);
=======
    return axios.post("http://localhost:9090/login/token", JSON.stringify({email,password}), config);
>>>>>>> b9a99ce7ee29c84a43a79d512fd2951b1f024414
}
export const getUserInfo = (email,token) => {
    const config = {
        headers: {
            "Content-type": "application/json",
            "Authorization" : "Bearer " + token
        },
    };
<<<<<<< HEAD
    return axios.get("http://localhost:8080/api/users/login/user?email=" +email,config);
=======
    return axios.get("http://localhost:9090/login/user?email=" +email,config);
>>>>>>> b9a99ce7ee29c84a43a79d512fd2951b1f024414
}