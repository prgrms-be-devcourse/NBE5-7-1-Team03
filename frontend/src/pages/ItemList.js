import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { deleteItem } from './api';
import useAuthCheck from '../hooks/useLoginCheck'; // ✅ 추가
import { useNavigate } from 'react-router-dom'; // ✅ 추가

export default function ItemList() {
  const [itemList, setItemList] = useState([]);
  const { isLogin, loading, user } = useAuthCheck(); // ✅ 추가
  const navigate = useNavigate(); // ✅ 추가
  const blocked = useRef(false);

  useEffect(() => {
    if(loading) return;
    if(blocked.current) return;

    if(user===null || user.role !== 'ADMIN'){
      blocked.current=true;
      alert('관리자만 접근할 수 있습니다.');
      navigate('/');
    }else{
      fetchItemList();
    }
  }, [user, loading]);

  const fetchItemList = () => {
    axios
      .get('/items', {
        withCredentials: true,
      })
      .then(response => {
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

  const handleLogout = async () => {
    try {
      await axios.post('/logout', {}, { withCredentials: true });
      alert('로그아웃 성공!');
      navigate('/'); // 홈으로 이동
      window.location.reload();
    } catch (err) {
      console.error('로그아웃 실패:', err);
    }
  };

  if (loading) return <div>확인 중...</div>; // 로딩 처리

  return (
    <div className="container">
      <h2 className="text-center mt-5 mb-3 fw-bold text-body">메뉴 관리</h2>
      <div className="card">
        <div className="card-header">
          <Link className="btn btn-secondary mx-1" to="/">
            Home
          </Link>

          {/* ✅ 로그인 여부에 따라 버튼 변경 */}
          {isLogin ? (
            <button className="btn btn-danger mx-1" onClick={handleLogout}>
              로그아웃
            </button>
          ) : (
            <Link className="btn btn-primary mx-1" to="/login">
              로그인
            </Link>
          )}

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
