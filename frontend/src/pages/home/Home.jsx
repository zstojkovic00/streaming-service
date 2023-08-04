import React, {useEffect, useState} from 'react';
import "./home.scss"
import Navbar from "../../components/navbar/Navbar";
import List from "../../components/list/List"
import Featured from "../../components/featured/Featured";
import {getAllVideos} from "../../api/videoService";

const Home = () => {

    return (
        <div className="home">

            <Navbar />
            {/*<Featured/>*/}
            <List />
            <List />
            <List />

            {/*{videoUrl && (*/}
            {/*    <video controls autoPlay>*/}
            {/*        <source src={"http://localhost:8081/api/v1/video/stream/64cc3bad1a297d7ac186b26b"} type="video/mp4" />*/}
            {/*        Your browser does not support the video tag.*/}
            {/*    </video>*/}
            {/*)}*/}
        </div>
    );
};

export default Home;