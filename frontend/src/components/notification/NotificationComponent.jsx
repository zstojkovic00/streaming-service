import React, { useState, useEffect } from 'react';
import { getCurrentUser } from "../../services/authenticationService";
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

const NotificationComponent = () => {
    const [notifications, setNotifications] = useState([]);
    const [userId, setUserId] = useState(null);

    useEffect(() => {
        getCurrentUser().then((response) => {
            setUserId(response.data.id);
            console.log(response.data);
        });
    }, []);
/*
    useEffect(() => {
        if (userId) {

            const socket = new SockJS('http://localhost:8082/ws');
            const stompClient = Stomp.over(socket);

            stompClient.connect({}, () => {
                console.log('Connected to WebSocket');

                stompClient.subscribe(`/topic/notification/user/${userId}`, (message) => {
                    const notification = JSON.parse(message.body);
                    console.log(notification);
                    setNotifications((prevNotifications) => [...prevNotifications, notification]);
                    alert(notification.message);

                });
            }, (error) => {
                console.error('WebSocket connection error:', error);
            });


        }
    }, [userId]);*/

    return (
        <div>

        </div>
    );
};

export default NotificationComponent;
