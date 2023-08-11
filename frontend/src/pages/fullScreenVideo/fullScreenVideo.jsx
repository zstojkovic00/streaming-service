import React, {useEffect, useRef, useState} from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useParams} from "react-router-dom";
import "./fullScreenVideo.scss";
import {Link} from "react-router-dom";
import {getCurrentUser} from "../../api/authenticationService";
import {updateVideoProgress, getToken} from "../../api/videoService";


const FullScreenVideo = () => {

    const {videoId} = useParams();
    const videoRef = useRef(null);
    const [userId, setUserId] = useState(null);

    const token = getToken();


        useEffect(() => {
            getCurrentUser()
                .then((response) => {
                    setUserId(response.data.id);
                })
                .catch((err) => {
                    console.log(err);
                });
        }, []);


        const handleTimeUpdate = () => {
            const currentTime = videoRef.current.currentTime;
            const duration = videoRef.current.duration;

            const progress = (currentTime / duration) * 100;
            console.log(userId);
            console.log(progress)
            console.log(videoId)

            updateVideoProgress(userId, videoId, progress)
                .then(response => {
                    console.log(response.data);
                    console.log("Video progress updated successfully");
                })
                .catch(error => {
                    console.error("Error updating video progress", error);
                });
        };




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
                ref={videoRef}
                onTimeUpdate={handleTimeUpdate}
                src={`http://localhost:8080/api/v1/video/stream/${videoId}`}
            />
        </div>
    );
};

export default FullScreenVideo;