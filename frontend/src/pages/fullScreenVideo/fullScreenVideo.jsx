import React from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useParams} from "react-router-dom";
import "./fullScreenVideo.scss";
import {Link} from "react-router-dom";

const FullScreenVideo = () => {

    const {videoId} = useParams();

    return (
        <div className="watch">
            <Link to={"/"} >
            <div className="back">
                <ArrowBackIcon className="icon" />
                Home
            </div>
            </Link>
            <video
                className="video"
                autoPlay
                controls
                src={`http://localhost:8080/api/v1/video/stream/${videoId}`}
            />
        </div>
    );
};

export default FullScreenVideo;