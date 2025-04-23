import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ItemList from "./pages/ItemList";
import ItemUpdate from "./pages/itemUpdate";
import ItemAdd from './pages/ItemAdd';
import Home from './pages/Home';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path="/list" element={<ItemList/>} />
        <Route path="/edit/:id" element={<ItemUpdate/>}/>
        <Route path="/add" element={<ItemAdd/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
