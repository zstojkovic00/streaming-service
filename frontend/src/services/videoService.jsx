import axios from 'axios';

export const getToken = () => {
    return localStorage.getItem('USER_KEY');
}

export const getAllVideos = () => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/videos",
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}

export const getVideoById = (videoId) => {
    return axios({
        method: 'GET',
        url: `http://localhost:8080/videos/${videoId}`,
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}

export const getStreamVideo = (videoId) => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/videos/stream/{videoId}"
    })

}