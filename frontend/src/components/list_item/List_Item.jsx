import React, {useState} from 'react';
import "./List_Item.scss"
import SpiderMan from "../../assets/images/marvel-spiderman.png"
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import ThumbUpOutlinedIcon from '@mui/icons-material/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@mui/icons-material/ThumbDownAltOutlined';
import SpiderManVideo from "../../assets/videos/marvel-spiderman.mp4"
const ListItem = ({index}) => {

    const [isHovered, setIsHovered] = useState(false);

    return (
        <div
            className="listItem"
            style={{ left: isHovered  }}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <img src={SpiderMan} alt="spiderman" />
            {isHovered && (
                <>
                    <video src={SpiderManVideo} autoPlay={true} loop />
                    <div className="itemInfo">
                        <div className="icons">
                            <PlayArrowIcon className="icon" />
                            <AddIcon className="icon" />
                            <ThumbUpOutlinedIcon className="icon" />
                            <ThumbDownAltOutlinedIcon className="icon" />
                        </div>
                        <div className="itemInfoTop">
                            <span>1 hour 14 mins</span>
                            <span className="limit">+16</span>
                            <span>1999</span>
                        </div>
                        <div className="desc">
                            Lorem ipsum dolor, sit amet consectetur adipisicing elit.
                            Praesentium hic rem eveniet error possimus, neque ex doloribus.
                        </div>
                        <div className="genre">Action</div>
                    </div>
                </>
            )}
        </div>
    );
}

export default ListItem;