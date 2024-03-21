import axios from 'axios';

export const getToken = () => {
    return localStorage.getItem('USER_KEY');
}

export const getAllVideos = () => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/api/v1/video/all",
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}

export const getVideoById = (videoId) => {
    return axios({
        method: 'GET',
        url: `http://localhost:8080/api/v1/video/${videoId}`,
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}

export const getStreamVideo = (videoId) => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/api/v1/video/stream/{videoId}"
    })
}

export const updateVideoProgress = (videoId, userId, progress, watched, genre) => {
    return axios({
        method: 'PUT',
        url: `http://localhost:8080/api/v1/video/progress`,
        data: {
            videoId: videoId,
            userId: userId,
            progress: progress,
            watched: watched,
            genre: genre
        },
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}

export const getCurrentUserVideoProgress = (videoId) => {
    return axios({
        method: 'GET',
        url: `http://localhost:8080/api/v1/video/progress/${videoId}`,
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}