import React from 'react';
import "./home.scss"
import Navbar from "../../components/navbar/Navbar";
import List from "../../components/list/List"
import Featured from "../../components/featured/Featured";
const Home = () => {
    return (
        <div className="home">
            <Navbar />
            {/*<Featured/>*/}
            <List />
            <List />
            <List />
        </div>
    );
};

export default Home;