import React, {useEffect, useRef, useState} from 'react';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import {useParams} from "react-router-dom";
import "./fullScreenVideo.scss";
import {Link} from "react-router-dom";
import {getCurrentUser} from "../../services/authenticationService";
import {updateVideoProgress, getToken, getVideoById} from "../../services/videoService";


const FullScreenVideo = () => {

    const {videoId} = useParams();
    const videoRef = useRef(null);
    const [userId, setUserId] = useState(null);
    const [genre, setGenre] = useState(null);
    const [lastProgressSent, setLastProgressSent] = useState(null);


    const token = getToken();


    useEffect(() => {

        getVideoById(videoId).then((response) => {
            console.log(response);
            setGenre(response.data.genre);
        }).catch((err) => {
            console.log(err)
        })

        getCurrentUser()
            .then((response) => {
                setUserId(response.data.id);
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);


    const handleTimeUpdate = () => {
        if (token) {
            const currentTime = videoRef.current.currentTime;
            const duration = videoRef.current.duration;

            const progress = (currentTime / duration) * 100;

            let isMovieWatched = false;

            if (progress > 75) {
                isMovieWatched = true;
            }
            if (lastProgressSent === null || progress - lastProgressSent >= 5) {
                if (progress < 80) {
                    updateVideoProgress(videoId, userId, progress, isMovieWatched, genre)
                        .then(response => {
                            console.log(response.data);
                            console.log("Video progress updated successfully");
                            setLastProgressSent(progress);
                        })
                        .catch(error => {
                            console.error("Error updating video progress", error);
                        });
                }
            }
        }
    };


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
                onTimeUpdate={handleTimeUpdate}
                src={`http://localhost:8080/api/v1/video/stream/${videoId}`}
            />
        </div>
    );
};

export default FullScreenVideo;