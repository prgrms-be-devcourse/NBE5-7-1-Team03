import axios from 'axios';
import { useEffect, useState } from 'react';
import './OrderPage.css';
import NavBar from '../components/NavBar';
import useAuthCheck from '../hooks/useLoginCheck';

function OrderPage() {
  const [products, setProducts] = useState([]);
  const [orderItems, setOrderItems] = useState([]);
  const { user } = useAuthCheck();
  const [formData, setFormData] = useState({
    email: '',
    address: '',
    zipcode: '',
  });

  useEffect(() => {
    axios
      .get('/items', { withCredentials: true })
      .then(res => {
        setProducts(res.data.response);
      })
      .catch(err => {
        console.log('상품 목록을 불러오는 데 실패했습니다:', err);
      });
  }, []);

  useEffect(() => {
    if (user) {
      setFormData(prev => ({
        ...prev,
        email: user.email,
      }));
    }
  }, [user]);

  const handleAddItem = item => {
    setOrderItems(prev => {
      const found = prev.find(i => i.itemId === item.id);
      if (found) {
        const targetStock = products.find(p => p.id === item.id)?.stock || Infinity;
        if (found.quantity < targetStock) {
          return prev.map(i => (i.itemId === item.id ? { ...i, quantity: i.quantity + 1 } : i));
        } else {
          alert('제고를 초과할 수 없습니다.');
          return prev;
        }
      } else {
        return [...prev, { itemId: item.id, name: item.name, price: item.price, quantity: 1 }];
      }
    });
  };

  const handleRemoveItem = item => {
    setOrderItems(prev => {
      const found = prev.find(i => i.itemId === item.id);
      if (found) {
        if (found.quantity > 1) {
          return prev.map(i => (i.itemId === item.id ? { ...i, quantity: i.quantity - 1 } : i));
        } else {
          return prev.filter(i => i.itemId !== item.id);
        }
      }
      return prev;
    });
  };

  const getTotalPrice = () => {
    return orderItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
  };

  const handleSubmit = e => {
    e.preventDefault();
    const orderPayload = {
      ...formData,
      orderItems: orderItems.map(({ itemId, quantity }) => ({ itemId, quantity })),
    };

    axios
      .post('/orders', orderPayload, { withCredentials: true })
      .then(res => {
        alert(res.data.message);
        setFormData({ address: '', zipcode: '' });
        setOrderItems([]);
        axios
          .get('/items')
          .then(res => setProducts(res.data.response))
          .catch(err => console.log('상품 다시 불러오기 실패:', err));
      })
      .catch(err => console.log('주문 실패:', err));
  };

  return (
    <div className="container mt-5">
      <h2 className="text-center mt-5 mb-3 fw-bold text-primary fs-2">Grids & Circle</h2>
      <div className="card">
        <NavBar />
      </div>
      <div className="row">
        <div className="col-md-7">
          <h5>
            <strong>상품 목록</strong>
          </h5>
          <div className="card p-3">
            {products &&
              products.map(product => (
                <div key={product.id} className="d-flex align-items-center justify-content-between border-bottom py-2">
                  <div className="d-flex align-items-center gap-3">
                    <img
                      src={`http://localhost:8080/items/images/${product.storeFileName}`}
                      alt="상품 이미지"
                      className="rounded"
                      style={{ width: '80px', height: '80px', objectFit: 'cover' }}
                    />
                    <div>
                      <div className="fw-bold">{product.name}</div>
                      <div>{product.price}원</div>
                      <div className="text-muted">
                        재고: {product.stock - (orderItems.find(i => i.itemId === product.id)?.quantity || 0)}
                      </div>
                    </div>
                  </div>
                  <div className="d-flex gap-2">
                    <button className="btn btn-primary btn-sm" onClick={() => handleAddItem(product)}>
                      추가
                    </button>
                    <button className="btn btn-danger btn-sm" onClick={() => handleRemoveItem(product)}>
                      삭제
                    </button>
                  </div>
                </div>
              ))}
          </div>
        </div>

        <div className="col-md-5">
          <h5>
            <strong>Summary</strong>
          </h5>
          <div className="border p-3 mb-3">
            {orderItems &&
              orderItems.map(item => (
                <div key={item.id} className="mb-3 border-bottom pb-2">
                  <div className="fw-semibold">{item.name}</div>
                  <div>수량: {item.quantity}개</div>
                </div>
              ))}
          </div>
          <form onSubmit={handleSubmit}>
            <input
              type="email"
              className="form-control mb-2"
              placeholder="이메일"
              required
              value={formData.email}
              readOnly
            />
            <input
              className="form-control mb-2"
              placeholder="주소"
              required
              value={formData.address}
              onChange={e => setFormData({ ...formData, address: e.target.value })}
            />
            <input
              className="form-control mb-2"
              placeholder="우편번호"
              required
              value={formData.zipcode}
              onChange={e => setFormData({ ...formData, zipcode: e.target.value })}
            />
            <p className="small text-muted mt-1">당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</p>
            <div className="d-flex justify-content-between mt-3">
              <strong>총금액</strong>
              <strong>{getTotalPrice()}원</strong>
            </div>
            <button type="submit" className="btn btn-dark w-100 mt-3">
              결제하기
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default OrderPage;
