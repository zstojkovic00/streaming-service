import axios from 'axios';
import {getToken} from "./videoService";


export const getBadgesByUserId = (userId) => {
    return axios({
        method: 'GET',
        url: `http://localhost:8080/api/v1/badge/${userId}`,
        headers:{
            'Authorization':'Bearer '+getToken()
        }
    });
}