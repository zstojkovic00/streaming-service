import React, {useState} from 'react';
import "./List_Item.scss"
import { Link } from 'react-router-dom';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import ThumbUpOutlinedIcon from '@mui/icons-material/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@mui/icons-material/ThumbDownAltOutlined';

const ListItem = ({ video }) => {

    const [isHovered, setIsHovered] = useState(false);


    return (
        <div
            className="listItem"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <img src={`http://localhost:8080/videos/preview/${video.id}`} alt="spiderman" />
            {isHovered && (
                <>
                    <video src={`http://localhost:8080/videos/stream/${video.id}`} autoPlay={true} loop />
                    <div className="itemInfo">
                        <div className="icons">
                            <Link to={`video/${video.title}/${video.id}`}>
                                <PlayArrowIcon className="icon" />
                            </Link>
                            <AddIcon className="icon" />
                            <ThumbUpOutlinedIcon className="icon" />
                            <ThumbDownAltOutlinedIcon className="icon" />
                        </div>

                        <div className="itemInfoTop">
                            <span></span>
                            <span className="limit">+</span>
                            <span>1999</span>
                        </div>
                        <div className="desc">
                            {video.description}
                        </div>
                        <div className="genre"></div>
                    </div>
                </>
            )}
        </div>
    );
}

export default ListItem;