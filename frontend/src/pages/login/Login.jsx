import React, {useState} from 'react';
import "./Login.scss"
import Yettel from "../../assets/images/yettel.png";
import {authenticate, authSuccess} from '../../redux/authActions';
import {Link, useNavigate} from "react-router-dom";
import {connect} from 'react-redux';
import {userLogin} from "../../api/authenticationService";


const Login = ({loading,error,...props}) => {

    const navigate = useNavigate();

    const [values, setValues] = useState({
        email: '',
        password: ''
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        props.authenticate();

        userLogin(values).then((res)=>{

            console.log("response",res);
            if(res.status===200){
                props.setUser(res.data);
                navigate("/");
                window.location.reload();
            }


        }).catch((err)=>{

            console.log(err);

        });

    }

    const handleChange = (e) => {
        e.persist();
        setValues(values => ({
            ...values,
            [e.target.name]: e.target.value
        }));
    };


    return (
        <div className="navbar">
            <div className="container">
                <div className="left">

                    <Link to="/" >
                    <img src={Yettel} alt="yettel"/>
                    </Link>

                </div>
            </div>

                    <form className="login-form" onSubmit={handleSubmit}>
                        <h2> Welcome back!</h2>
                        <label htmlFor="email">email</label>
                        <input className="input_form" value={values.email} onChange={handleChange} type="email"
                               id="email"
                               name="email"/>
                        <br/>
                        <label htmlFor="password">password</label>
                        <input className="input_form" value={values.password} onChange={handleChange} type="password"
                               id="password" name="password"/>
                        <button className="loginButton" type="submit">Log in</button>
                        <Link className="linkButton" to="/join">Don't have an account? Join here!</Link>
                    </form>

        </div>

    );
};

const mapStateToProps = ({auth}) => {
    console.log("state ", auth)
    return {
        loading: auth.loading,
        error: auth.error
    }
}


const mapDispatchToProps = (dispatch) => {

    return {
        authenticate: () => dispatch(authenticate()),
        setUser: (data) => dispatch(authSuccess(data)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);