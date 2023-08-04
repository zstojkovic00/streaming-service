import React, {useState, useEffect} from 'react';
import "./List_Item.scss"
import SpiderMan from "../../assets/images/marvel-spiderman.png"
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import ThumbUpOutlinedIcon from '@mui/icons-material/ThumbUpOutlined';
import ThumbDownAltOutlinedIcon from '@mui/icons-material/ThumbDownAltOutlined';
import SpiderManVideo from "../../assets/videos/marvel-spiderman.mp4"

const ListItem = ({ video }) => {

    const [isHovered, setIsHovered] = useState(false);


    return (
        <div
            className="listItem"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <img src={`${video.stream.gridFSFile.metadata?.photoUrl}`} alt="spiderman" />
            {isHovered && (
                <>
                    <video src={`http://localhost:8081/api/v1/video/stream/${video.stream.gridFSFile.metadata?.videoId}`} autoPlay={true} loop />
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
                            {video.title}
                        </div>
                        <div className="genre">{video.stream.gridFSFile.metadata?.description}</div>
                    </div>
                </>
            )}
        </div>
    );
}

export default ListItem;