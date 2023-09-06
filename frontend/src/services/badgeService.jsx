import axios from 'axios';
import {getToken} from "./videoService";


export const getBadgesForCurrentUser = () => {
    return axios({
        method: 'GET',
        url: `http://localhost:8080/api/v1/badge/current-user`,
        headers:{
            'Authorization':'Bearer '+getToken()
        }
    });
}