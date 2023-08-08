import React, {useRef, useState, useEffect} from 'react';
import "./List.scss"
import ListItem from "../list_item/List_Item"
import ArrowBackIosOutlinedIcon from '@mui/icons-material/ArrowBackIosOutlined';
import ArrowForwardIosOutlinedIcon from '@mui/icons-material/ArrowForwardIosOutlined';
import {getAllVideos} from "../../api/videoService";

const List = () => {

    const [isMoved, setIsMoved] = useState(false);
    const [slideNumber, setSlideNumber] = useState(0);
    const listRef = useRef();
    const [videoList, setVideoList] = useState([]);


    useEffect(()=>{
        getAllVideos().then((response)=>{
            setVideoList(response.data);
        }).catch((err)=>{
            console.log(err);
        })
    },[])

    const handleClick = (direction) => {
        setIsMoved(true);
        let distance = listRef.current.getBoundingClientRect().x - 50;
        if (direction === "left" && slideNumber > 0) {
            setSlideNumber(slideNumber - 1);
            listRef.current.style.transform = `translateX(${350 + distance}px)`;
        }
        if (direction === "right" && slideNumber < 5) {
            setSlideNumber(slideNumber + 1);
            listRef.current.style.transform = `translateX(${-350 + distance}px)`;
        }
    };

    return (
        <div className="list">
            <span className="listTitle">Continue to watch</span>
            <div className="wrapper">
                <ArrowBackIosOutlinedIcon
                    className="sliderArrow left"
                    onClick={() => handleClick("left")}
                    style={{display: !isMoved && "none"}}
                />
                <div className="container" ref={listRef}>
                    {videoList.map((video, index) => (
                        <ListItem key={index} video={video} />
                    ))}
                </div>
                <ArrowForwardIosOutlinedIcon
                    className="sliderArrow right"
                    onClick={() => handleClick("right")}
                />
            </div>
        </div>
    );
}

export default List;