import axios, * as others from "axios";

export default axios.create({
  baseURL: "http://localhost:8080/api",
  
  timeout: 60000,
});
