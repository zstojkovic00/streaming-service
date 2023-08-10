import React, {useEffect, useState} from 'react';
import "./Navbar.scss";
import {Link, useNavigate} from "react-router-dom";
import Yettel from "../../assets/images/yettel.png";
import SearchIcon from '@mui/icons-material/Search';
import NotificationsIcon from '@mui/icons-material/Notifications';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

const Navbar = () => {

    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const isLoggedInStorage = localStorage.getItem('isLoggedIn') === 'true';

        if (isLoggedInStorage) {
            setIsLoggedIn(isLoggedInStorage);
        } else {
            localStorage.setItem('isLoggedIn', 'true');
            setIsLoggedIn(true);
        }
    }, []);

    const logout =() => {
        localStorage.clear();
        localStorage.removeItem('isLoggedIn');
        setIsLoggedIn(false);
        navigate('/');
    }



    return (
        <div>
            {!isLoggedIn ? (

                <div className="navbar">
                    <div className="container">
                        <div className="left">

                            <img src={Yettel} alt="yettel"/>
                            <span>Today</span>
                            <span>Shows</span>
                            <span>Series</span>
                            <span>Movies</span>
                            <span>Sports</span>
                            <span>Kids</span>
                            <span>Channels</span>

                        </div>

                        <div className="right">
                            <Link to={"/login"}>
                                <button className="loginButton">Sign In</button>
                            </Link>
                        </div>
                    </div>
                </div>

            ) : (

                <div className="navbar">
                    <div className="container">
                        <div className="left">

                            <img src={Yettel} alt="yettel"/>
                            <span>Today</span>
                            <span>Shows</span>
                            <span>Series</span>
                            <span>Movies</span>
                            <span>Sports</span>
                            <span>Kids</span>
                            <span>Channels</span>

                        </div>

                        <div className="right">
                            <SearchIcon className="icon"/>
                            <NotificationsIcon className="icon"/>
                            <AccountCircleIcon className="icon"/>

                            <div className="profile">
                                <ArrowDropDownIcon className="icon"/>
                                <div className="options">
                                    <span>Settings</span>
                                    <span onClick={logout}>Logout</span>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                )}




        </div>

    );
};

export default Navbar;