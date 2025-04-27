import axios from 'axios';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import NavBar from '../components/NavBar';

function MyOrdersPage() {
  const [user, setUser] = useState(null); // 사용자 정보 전체 저장
  const [orders, setOrders] = useState([]);
  const [orderId, setOrderId] = useState('');
  const [order, setOrder] = useState(null);
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({ address: '', zipCode: '' });

  useEffect(() => {
    axios
      .get('http://localhost:8080/user/info', { withCredentials: true })
      .then(res => {
        const userData = res.data.response;
        setUser(userData);
        setOrders(userData.orders); // 주문 목록만 따로 꺼내 저장
      })
      .catch(err => console.error('유저 정보 조회 실패:', err));
  }, []);

  const startEdit = order => {
    setEditingId(order.id);
    setEditForm({
      address: order.address ?? '',
      zipCode: order.zipcode ?? '',
    });
  };

  const handleChange = e => {
    const { name, value } = e.target;
    setEditForm(prev => ({ ...prev, [name]: value }));
  };

  const cancelEdit = () => {
    setEditingId(null);
    setEditForm({ address: '', zipCode: '' });
  };

  const handleUpdate = orderId => {
    axios
      .put(`http://localhost:8080/orders/${orderId}`, editForm, { withCredentials: true })
      .then(() => {
        alert('주문 정보가 수정되었습니다.');
        // 수정 후 user info 다시 불러오기
        return axios.get('http://localhost:8080/user/info', { withCredentials: true });
      })
      .then(res => {
        const userData = res.data.response;
        setUser(userData);
        setOrders(userData.orders);
        cancelEdit();
      })
      .catch(err => alert('수정 실패: ' + err.response?.data?.message));
  };

  const handleDelete = (orderId) => {
    if (window.confirm('정말로 이 주문을 취소하시겠습니까?')) {
      axios
        .delete(`http://localhost:8080/orders/${orderId}`, { withCredentials: true })
        .then(() => {
          alert('주문이 취소되었습니다.');
          // 주문 삭제 후 user info 다시 불러오기
          return axios.get('http://localhost:8080/user/info', { withCredentials: true });
        })
        .then(res => {
          const userData = res.data.response;
          setUser(userData);
          setOrders(userData.orders);
        })
        .catch(err => {
          alert('주문 취소 실패: ' + (err.response?.data?.message || '알 수 없는 오류'));
        });
    }
  };

  const translateStatus = (status) => {
    switch (status) {
      case 'RECEIVED':
        return '상품 준비중';
      case 'SHIPPING':
        return '배송중';
      case 'COMPLETED':
        return '배송 완료';
      case 'CANCELLED':
        return '주문 취소';
      default:
        return '알 수 없음';
    }
  };

  if (!user) return <div>로딩 중...</div>;

  return (
    <div className="container mt-5">
      <div className="card">
        <NavBar />
      </div>
      <h3>{user.nickname} 님의 주문 목록</h3>
      {orders.length === 0 && <p>주문 내역이 없습니다.</p>}
      {orders.map(order => (
        <div key={order.id} className="border p-3 mb-4">
          <p className='fw-bold'>주문번호: {order.id}</p>
          <p>주문일시: {new Date(order.createdAt).toLocaleString()}</p>
          <p>상태: {translateStatus(order.status)}</p>

          {editingId === order.id ? (
            <>
              <label htmlFor={`address-${order.id}`} className="form-label">주소</label>
              <input
                id={`address-${order.id}`}
                name="address"
                value={editForm.address}
                onChange={handleChange}
                className="form-control mb-2"
                placeholder="주소"
              />
              <label htmlFor={`zipCode-${order.id}`} className="form-label">우편번호</label>
              <input
                id={`zipCode-${order.id}`}
                name="zipCode"
                value={editForm.zipCode}
                onChange={handleChange}
                className="form-control mb-2"
                placeholder="우편번호"
              />
              <button className="btn btn-success btn-sm" onClick={() => handleUpdate(order.id)}>
                저장
              </button>
              <button className="btn btn-secondary btn-sm ms-2" onClick={cancelEdit}>
                취소
              </button>
            </>
          ) : (
            <>
              <p>주소: {order.address}</p>
              <p>우편번호: {order.zipcode}</p>

              {order.status === 'RECEIVED' && (
                <>
                  <button className="btn btn-primary btn-sm" onClick={() => startEdit(order)}>
                    배송지 변경
                  </button>
                  <button className="btn btn-danger btn-sm ms-2" onClick={() => handleDelete(order.id)}>
                    주문 취소
                  </button>
                  <small className='text-muted mt-1 ms-2'> * 주문 취소는 상품 준비중 상태일 때만 가능합니다.</small>
                </>
              )}
            </>
          )}

          <hr />
          <strong>주문 상품</strong>
          <ul>
            {(order.items ?? []).map((item, index) => (
              <li key={`${order.id}-${item.id ?? index}`}>
                {item.name} - {item.quantity}개
              </li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
}

export default MyOrdersPage;
