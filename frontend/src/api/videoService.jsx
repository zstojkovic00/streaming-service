import axios from 'axios';

const getToken=()=>{
    return localStorage.getItem('USER_KEY');
}

export const getAllVideos = () => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/api/v1/video",
        headers:{
            'Authorization':'Bearer '+getToken()
        }
    });
}