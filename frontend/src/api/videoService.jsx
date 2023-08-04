import axios from 'axios';

export const getAllVideos = () => {
    return axios({
        method: 'GET',
        url: "http://localhost:8081/api/v1/video",
    });
}