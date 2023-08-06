import React from "react";
import "./Login.scss"
import Yettel from "../../assets/images/yettel.png";


const Login = () => {


    return (
        <div className="navbar">
            <div className="container">
                <div className="left">

                    <img src={Yettel} alt="yettel"/>

                </div>
            </div>

            <div className="loginForm">

            </div>

        </div>
    );
};

export default Login;