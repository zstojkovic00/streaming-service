import React from 'react';
import "./home.scss"
import Navbar from "../../components/navbar/Navbar";
import List from "../../components/video_list/List"

const Home = () => {
    return (
        <div className="home">
            <Navbar />
            <List />
            <List />
            <List />
        </div>
    );
};

export default Home;