import "./Featured.scss";
import YettelHipernet from "../../assets/images/yettel-hipernet.jpg"

export default function Featured() {
    return (
        <div className="featured">

            <img
                src={YettelHipernet}
                alt=""
            />
        </div>
    );
}