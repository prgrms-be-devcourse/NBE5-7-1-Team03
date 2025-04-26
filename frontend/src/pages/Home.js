import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useAuthCheck from '../hooks/useLoginCheck';
import axios from 'axios';
import coffee_bean from '../image/coffee-beans.png';

export default function Home() {
  const navigate = useNavigate();
  const { isLogin, user, loading } = useAuthCheck();

  const handleLogout = async () => {
    try {
      await axios
        .post(
          '/logout',
          {},
          {
            withCredentials: true,
          }
        )
        .then(() => {
          alert('로그아웃 성공!');
          window.location.reload();
        });
    } catch (err) {
      console.error('로그아웃 실패:', err);
    }
  };

  if (loading) return <p>확인 중...</p>;

  return (
    <div className="container p-5 text-center">
      <div className="card style={{ height: '100vh' }}">
        <div className="card-header pb-5">
          <h2 className="text-center mt-5 mb-3 fw-bold text-primary fs-1">GC Coffee</h2>
          <img src={coffee_bean} alt="커피 빈" style={{ width: '100px', height: '100px', objectFit: 'cover' }} />
        </div>
        <div className="card-body d-flex flex-column justify-content-center align-items-center p-4">
          <Link to={`/admin`} className="btn btn-secondary px-4 py-2 mb-3 w-25">
            관리자 페이지
          </Link>
          {isLogin ? (
            <Link to={`/my-orders`} className="btn btn-secondary px-4 py-2 mb-3 w-25">
              마이 페이지
            </Link>
          ) : (
            <Link to={`/signup`} className="btn btn-secondary px-4 py-2 mb-3 w-25">
              회원 가입
            </Link>
          )}
          {isLogin ? (
            <Link onClick={handleLogout} className="btn btn-secondary px-4 py-2 mb-3 w-25">
              로그아웃
            </Link>
          ) : (
            // <a href="/login">로그인</a>

            <Link to={`/login`} className="btn btn-secondary px-4 py-2 mb-3 w-25">
              로그인
            </Link>
          )}
          <Link to={`/order`} className="btn btn-dark px-4 py-2 mb-3 w-25">
            커피 주문하기
          </Link>
        </div>
      </div>
    </div>
  );
}
