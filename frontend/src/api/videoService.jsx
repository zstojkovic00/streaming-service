import axios from 'axios';

export const getAllVideos = () => {
    return axios({
        method: 'GET',
        url: "",
    });
}