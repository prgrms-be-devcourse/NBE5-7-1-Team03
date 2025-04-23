import React, { useState, useEffect } from 'react'
import { Link, useParams, useNavigate } from "react-router-dom"
import axios from 'axios'

export default function BookEdit() {
    const { id }=useParams(); //url에서 id 추출
    const navigate = useNavigate();
    const [item, setItem]=useState([]);

    useEffect(() => {
        axios.get(`/items/${id}`)
            .then(response => {
                setItem(response.data);
            })
    }, [id]);

    const handleChange = (e) => {
        //name: input의 name 속성
        //value: 사용자가 입력한 값
        const { name, value }=e.target;
        setItem(prevItem => ({
            ...prevItem,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.put(`/items/${id}`, item)
            .then(() => {
                alert("상품이 수정되었습니다.");
                navigate('/list');
            });
    };

    return (
        <div className="container p-4">
          <h2>상품 수정</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label">이름</label>
              <input
                type="text"
                className="form-control"
                name="name"
                value={item.name}
                onChange={handleChange}
                required
              />
            </div>
    
            <div className="mb-3">
              <label className="form-label">가격</label>
              <input
                type="text"
                className="form-control"
                name="price"
                value={item.price}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label">재고</label>
              <input
                type="text"
                className="form-control"
                name="stock"
                value={item.stock}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">저장</button>
          </form>
        </div>
      );

}