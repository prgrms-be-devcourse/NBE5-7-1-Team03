import React, {useState} from 'react';
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';

export default function ItemAdd(){

    const navigate = useNavigate();
    const [form, setForm] = useState([]);
    const [image, setImage]=useState(null);

    const handleChange=(e) => {
        const { name, value }=e.target;
        setForm(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(`/items`, form)
        .then(() => {
            alert('상품이 등록되었습니다!');
            navigate('/list');
        })
    };

    const saveFile=()=> {
        const formData=new FormData();
        
    }

    return (
        <div className="container">
            <h2 className="text-center mt-5 mb-3">메뉴 관리</h2>
            <div className="card">
                <div className="card-header">
                <Link className="btn btn-secondary mx-1" to="/">Home</Link>
                <Link className="btn btn-dark mx-1" to="/add">상품 등록</Link>
                </div>
                <div className="card-body d-flex justify-content-center">
                    <form onSubmit={handleSubmit} className="w-50">
                        <div className="form-group">
                            <label className="form-label">상품명</label>
                            <input
                                type="text"
                                name="name"
                                className="form-control"
                                value={form.name}
                                onChange={handleChange}
                                required
                            />
                        </div>
        
                        <div className="form-group">
                            <label className="form-label">가격</label>
                            <input
                                type="text"
                                name="price"
                                className="form-control"
                                value={form.price}
                                onChange={handleChange}
                                required
                            />
                        </div>
        
                        <div className="form-group">
                            <label className="form-label">재고 수량</label>
                            <input
                                type="number"
                                name="stock"
                                className="form-control"
                                value={form.stock}
                                onChange={handleChange}
                                required
                            />
                         </div>

                         <div className='form-group'>
                            <label className="form-label">상품 이미지</label>
                            <input type="file"
                                name="imageUrl"
                                className="form-control"
                                value={form.imageUrl}
                                onChange={handleChange}
                            />
                         </div>
    
                        <div className='text-center mt-3'>
                            <button type="submit" className="btn btn-dark">등록</button>
                        </div>
                    </form>
                </div>
             </div>
        </div>
    );
}