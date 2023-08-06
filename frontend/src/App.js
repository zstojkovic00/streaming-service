import "./App.scss"
import Home from "./pages/home/Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import FullScreenVideo from "./pages/fullScreenVideo/fullScreenVideo";

const App = () => {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/video/:videoTitle/:videoId" element={<FullScreenVideo />} />
                </Routes>
            </div>
        </Router>
    )
};

export default App;

