import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ItemList from './pages/ItemList';
import ItemUpdate from './pages/itemUpdate';
import ItemAdd from './pages/ItemAdd';
import Home from './pages/Home';
import OrderPage from './pages/OrderPage';
import SignUp from './pages/SignUp';
import Login from './pages/Login';
import UserPage from './pages/UserPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/admin" element={<ItemList />} />
        <Route path="/admin/edit/:id" element={<ItemUpdate />} />
        <Route path="/admin/add" element={<ItemAdd />} />
        <Route path="/order" element={<OrderPage />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/user" element={<UserPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
