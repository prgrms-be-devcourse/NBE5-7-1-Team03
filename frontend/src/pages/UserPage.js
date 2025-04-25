import React, { useState } from 'react';
import axios from 'axios';

export default function UserPage() {
  const [orderId, setOrderId] = useState('');
  const [order, setOrder] = useState(null);
  const [error, setError] = useState('');

  const handleSearch = () => {
    if (!orderId) {
      alert("주문 ID를 입력해주세요.");
      return;
    }

    axios.get(`/orders/${orderId}`)
      .then(res => {
        setOrder(res.data);
        setError('');
      })
      .catch(err => {
        setOrder(null);
        setError('주문을 찾을 수 없습니다.');
        console.error(err);
      });
  };

  const handleDelete = () => {
    if (!window.confirm("정말 이 주문을 삭제하시겠습니까?")) return;

    axios.delete(`/orders/${orderId}`)
      .then(() => {
        alert("주문이 삭제되었습니다.");
        setOrder(null);
        setOrderId('');
      })
      .catch(err => {
        alert("삭제 실패");
        console.error(err);
      });
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">주문 조회 / 삭제</h2>

      <div className="mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="주문 ID를 입력하세요"
          value={orderId}
          onChange={e => setOrderId(e.target.value)}
        />
        <button className="btn btn-primary mt-2" onClick={handleSearch}>조회</button>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}

      {order && (
        <div className="border p-4 rounded">
          <h5>주문 상세</h5>
          <p><strong>이메일:</strong> {order.email}</p>
          <p><strong>주소:</strong> {order.address}</p>
          <p><strong>우편번호:</strong> {order.zipcode}</p>

          <h6 className="mt-3">상품 목록</h6>
          <ul>
            {order.orderItems?.map((item, idx) => (
              <li key={idx}>{item.name} - {item.quantity}개</li>
            ))}
          </ul>

          <button className="btn btn-danger mt-3" onClick={handleDelete}>주문 삭제</button>
        </div>
      )}
    </div>
  );
}
