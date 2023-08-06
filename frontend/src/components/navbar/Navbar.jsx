import React from 'react';
import "./Navbar.scss";
import { Link } from 'react-router-dom';
import Yettel from "../../assets/images/yettel.png";
import SearchIcon from '@mui/icons-material/Search';
import NotificationsIcon from '@mui/icons-material/Notifications';
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import PermIdentityOutlinedIcon from '@mui/icons-material/PermIdentityOutlined';

const Navbar = () => {

    return (
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
                    {/*<SearchIcon className="icon"/>*/}
                    {/*<NotificationsIcon className="icon"/>*/}
                    {/*<AccountCircleIcon className="icon"/>*/}

                    {/*<div className="profile">*/}
                    {/*    <ArrowDropDownIcon className="icon"/>*/}
                    {/*    <div className="options">*/}
                    {/*        <span>Settings</span>*/}
                    {/*        <span>Logout</span>*/}
                    {/*    </div>*/}

                    {/*</div>*/}
                </div>
            </div>
        </div>
    );
};

export default Navbar;