import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { deleteItem } from './api';

export default function ItemList() {
  const [itemList, setItemList] = useState([]);

  useEffect(() => {
    fetchItemList();
  }, []);

  const fetchItemList = () => {
    axios.get('/items').then(response => {
      setItemList(response.data.response);
    });
  };

  const handleDeleteConfirm = id => {
    if (window.confirm('정말로 삭제하겠습니까?')) {
      deleteItem(id).then(() => {
        fetchItemList();
      });
    }
  };

  return (
    <div className="container">
      <h2 className="text-center mt-5 mb-3">메뉴 관리</h2>
      <div className="card">
        <div className="card-header">
          <Link className="btn btn-secondary mx-1" to="/">
            Home
          </Link>
          <Link className="btn btn-primary mx-1" to="/login">
            로그인
          </Link>
          <Link className="btn btn-dark mx-1" to="/admin/add">
            상품 등록
          </Link>
        </div>
        <div className="card-body">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>상품 이미지</th>
                <th>이름</th>
                <th>가격</th>
                <th>재고</th>
                <th width="220px"></th>
              </tr>
            </thead>

            <tbody>
              {itemList &&
                itemList.map(item => (
                  <tr key={item.id}>
                    <td>
                      <img
                        src={`http://localhost:8080/items/images/${item.storeFileName}`}
                        alt="상품 이미지"
                        width="200"
                        height="150"
                      />
                    </td>
                    <td>{item.name}</td>
                    <td>{item.price}</td>
                    <td>{item.stock}</td>
                    <td>
                      <Link to={`/admin/edit/${item.id}`} className="btn btn-info mx-1">
                        수정
                      </Link>
                      <button onClick={() => handleDeleteConfirm(item.id)} className="btn btn-danger mx-1">
                        삭제
                      </button>
                    </td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
