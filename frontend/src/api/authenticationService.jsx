import axios from 'axios';

const getToken=()=>{
    return localStorage.getItem('USER_KEY');
}

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