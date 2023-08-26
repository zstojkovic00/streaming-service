import axios from 'axios';

export const getToken=()=>{
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

export const getStreamVideo = (videoId) => {
    return axios({
        method: 'GET',
        url: "http://localhost:8080/api/v1/video/stream/{videoId}"
    })
}

export const updateVideoProgress = (userId, videoId, progress, isMovieWatched) => {
    return axios({
        method: 'PUT',
        url: `http://localhost:8080/api/v1/video/progress`,
        data: {
            userId: userId,
            videoId: videoId,
            progress: progress,
            isMovieWatched: isMovieWatched
        },
        headers: {
            'Authorization': 'Bearer ' + getToken()
        }
    });
}