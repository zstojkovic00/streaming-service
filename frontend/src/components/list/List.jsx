import React, {useRef} from 'react';
import "./List.scss"
import ListItem from "../list_item/List_Item"
import ArrowBackIosOutlinedIcon from '@mui/icons-material/ArrowBackIosOutlined';
import ArrowForwardIosOutlinedIcon from '@mui/icons-material/ArrowForwardIosOutlined';
const List = () => {

    const listRef = useRef();

    const handleClick = (direction) => {
        let distance = listRef.current.getBoundingClientRect().x - 50;
        if(direction === "left"){
            listRef.current.style.transform = `translateX(${230+distance}px)`
        }

        if(direction === "right"){
            listRef.current.style.transform = `translateX(${-230 + distance}px)`
        }

        console.log(distance);
    }

    return (
        <div className="list">
            <span className="listTitle">Movies</span>
            <div className="wrapper" >
            <ArrowBackIosOutlinedIcon className="sliderArrow left" onClick={() => handleClick("left")}/>
            <div className="container" ref={listRef}>
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
                <ListItem />
            </div>
            <ArrowForwardIosOutlinedIcon className="sliderArrow right" onClick={() => handleClick("right")}/>
            </div>
        </div>
    );
};

export default List;