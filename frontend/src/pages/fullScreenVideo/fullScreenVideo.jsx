import React, {useEffect, useRef} from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useParams} from "react-router-dom";
import "./fullScreenVideo.scss";
import {Link} from "react-router-dom";


const FullScreenVideo = () => {

    const {videoId} = useParams();
    const videoRef = useRef(null);

    return (
        <div className="watch">
            <Link to={"/"}>
                <div className="back">
                    <ArrowBackIcon className="icon"/>
                    Home
                </div>
            </Link>
            <video
                className="video"
                autoPlay
                controls
                ref={videoRef}
                src={`http://localhost:8080/videos/stream/${videoId}`}
            />
        </div>
    );
};

export default FullScreenVideo;