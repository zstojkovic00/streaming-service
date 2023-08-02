import React from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import Voyage from "../../assets/videos/voyage.mp4"
import "./fullScreenVideo.scss";

const FullScreenVideo = () => {
    return (
        <div className="watch">
            <div className="back">
                <ArrowBackIcon />
                Home
            </div>
            <video
                className="video"
                autoPlay
                controls
                src={Voyage}
            />
        </div>
    );
};

export default FullScreenVideo;