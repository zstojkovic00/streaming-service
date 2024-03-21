import "./App.scss"
import Home from "./pages/home/Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import FullScreenVideo from "./pages/fullScreenVideo/fullScreenVideo";
import Badge from "./pages/badge/Badge";
import Login from "./pages/login/Login";

const App = () => {
    return (
        <Router>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/video/:videoTitle/:videoId" element={<FullScreenVideo />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/badges" element={<Badge />} />

                </Routes>
        </Router>
    )
};

export default App;

