import React from 'react';
import "./home.scss"
import Navbar from "../../components/navbar/Navbar";
import List from "../../components/list/List"
const Home = () => {
    return (
        <div className="home">
            <Navbar />
            <List />
        </div>
    );
};

export default Home;