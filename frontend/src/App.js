import './App.css';
import { Route, Routes} from "react-router-dom";


function App() {
  return (
      <div className="App">
        <Navbar/>
        <div className="container">
          <Routes >

            <Route path="/" element={<MyGames/>} />

          </Routes>
        </div>
      </div>
  );
}

export default App;
