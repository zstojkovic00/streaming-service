import React, {useState, useEffect} from 'react';
import "./List_Item.scss"
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
            <img src={`${video.stream.gridFSFile.metadata?.photoUrl}`} alt="spiderman" />
            {isHovered && (
                <>
                    <video src={`http://localhost:8081/api/v1/video/stream/${video.id}`} autoPlay={true} loop />
                    <div className="itemInfo">
                        <div className="icons">
                            <PlayArrowIcon className="icon" />
                            <AddIcon className="icon" />
                            <ThumbUpOutlinedIcon className="icon" />
                            <ThumbDownAltOutlinedIcon className="icon" />
                        </div>
                        <div className="itemInfoTop">
                            <span>{video.stream.gridFSFile.metadata?.duration}</span>
                            <span className="limit">+{video.stream.gridFSFile.metadata?.ageRestriction}</span>
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