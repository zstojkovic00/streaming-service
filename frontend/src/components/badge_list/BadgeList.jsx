import React, { useEffect, useState } from 'react';
import { getBadgesForCurrentUser } from "../../services/badgeService";
import "./BadgeList.scss"

const BadgeList = () => {
    const [badges, setBadges] = useState([]);

    useEffect(() => {
        getBadgesForCurrentUser()
            .then((response) => {
                setBadges(response.data.badges);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);

    return (
        <div className="badge-container">
            {badges.map((badge) => (
                <div key={badge.id} className="badge">
                    <div className="badge-image">
                        <img src={`data:image/png;base64,${badge.image.data}`} alt={badge.name} />
                    </div>
                    <div className="badge-info">
                        <h3>{badge.name}</h3>
                        <p>{badge.description}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default BadgeList;
