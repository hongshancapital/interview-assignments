import axios from "axios";

const http = axios.create({
    timeout: 60000,
    withCredentials: false
});

export default http;
