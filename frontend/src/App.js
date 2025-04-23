import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ItemList from "./pages/ItemList";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<ItemList/>} />
        {/* <Route path="/edit/:id" /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
