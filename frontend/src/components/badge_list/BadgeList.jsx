import React, { useEffect, useState } from 'react';
import {getBadgesByUserId} from "../../services/badgeService";
import {getCurrentUser} from "../../services/authenticationService";

const BadgeList = () => {

    const [badges, setBadges] = useState([]);


    useEffect(()=>{

        getBadgesByUserId().then((response)=>{
            setBadges(response.data);
        }).catch((err)=>{
            console.log(err);
        })
    },[])

    return (
        <div>

        </div>
    );
};

export default BadgeList;