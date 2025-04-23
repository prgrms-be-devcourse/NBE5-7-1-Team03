import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ItemList from "./pages/ItemList";
import ItemUpdate from "./pages/itemUpdate";
import ItemAdd from './pages/ItemAdd';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<ItemList/>} />
        <Route path="/edit/:id" element={<ItemUpdate/>}/>
        <Route path="add" element={<ItemAdd/ >} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
