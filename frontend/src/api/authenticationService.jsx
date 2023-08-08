import axios from 'axios';


export const userJoin=(authRequest)=>{
    return axios({
        'method':'POST',
        'url':"http://localhost:8080/auth/register",
        'data':authRequest
    })
}

export const userLogin=(authRequest)=>{
    return axios({
        'method':'POST',
        'url':"http://localhost:8080/auth/login",
        'data':authRequest
    })
}