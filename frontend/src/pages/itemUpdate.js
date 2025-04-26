import React, { useState, useEffect } from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function ItemUpdate() {
  const { id } = useParams(); //url에서 id 추출
  const navigate = useNavigate();
  const [item, setItem] = useState([]);
  const [image, setImage] = useState(null);

  useEffect(() => {
    axios.get(`/items/${id}`).then(response => {
      setItem(response.data.response);
    });
  }, [id]);

  const handleChange = e => {
    //name: input의 name 속성
    //value: 사용자가 입력한 값
    const { name, value } = e.target;
    setItem(prevItem => ({
      ...prevItem,
      [name]: value,
    }));
  };

  const handleImageChange = e => {
    setImage(e.target.files[0]);
  };

  const handleSubmit = e => {
    e.preventDefault();

    const formData = new FormData();
    const jsonBlob = new Blob([JSON.stringify(item)], {
      type: 'application/json',
    });

    formData.append('item', jsonBlob);
    formData.append('image', image);

    axios
      .put(`/items/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        withCredentials: true,
      })
      .then(() => {
        alert('상품이 수정되었습니다');
        navigate('/admin');
      });
  };

  return (
    <div className="container">
      <h2 className="text-center mt-5 mb-3">메뉴 관리</h2>
      <div className="card">
        <div className="card-header">
          <Link className="btn btn-secondary mx-1" to="/admin">
            목록
          </Link>
          <Link className="btn btn-dark mx-1" to="/admin/add">
            상품 등록
          </Link>
        </div>
        <div className="card-body d-flex justify-content-center">
          <form onSubmit={handleSubmit} className="w-50">
            <div className="form-group">
              <label className="form-label">상품명</label>
              <input
                type="text"
                name="name"
                className="form-control"
                value={item.name}
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
                value={item.price}
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
                value={item.stock}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">상품 이미지</label>
              <input type="file" name="image" className="form-control" onChange={handleImageChange} required />
            </div>

            <div className="text-center mt-3">
              <button type="submit" className="btn btn-info">
                수정
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
