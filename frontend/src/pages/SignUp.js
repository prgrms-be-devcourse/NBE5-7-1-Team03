import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function SignUp() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nickname: '',
    email: '',
    password: '',
  });

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = e => {
    e.preventDefault();

    axios
      .post(`/sign-up`, formData, {
        headers: {
          'Content-Type': 'application/json',
        },
        withCredentials: true,
      })
      .then(() => {
        alert('회원가입이 완료되었습니다!');
        navigate('/');
      })
      .catch(error => {
        if (error.response && error.response.data && error.response.data.message) {
          alert(error.response.data.message);
        } else {
          alert('알 수 없는 오류가 발생했습니다.');
        }
      });
  };

  return (
    <div className="container">
      <h2 className="text-center mt-5 mb-3 fw-bold text-body fs-2">회원 가입</h2>
      <div className="card">
        <div className="card-header">
          <Link className="btn btn-secondary mx-1" to="/">
            홈 화면
          </Link>
          <Link className="btn btn-primary mx-1" to="/login">
            로그인
          </Link>
        </div>
        <div className="card-body d-flex justify-content-center">
          <form onSubmit={handleSubmit} className="w-50">
            <div className="form-group">
              <label className="form-label">닉네임</label>
              <input
                type="text"
                name="nickname"
                className="form-control"
                value={formData.nickname}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">이메일</label>
              <input
                type="email"
                name="email"
                className="form-control"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">비밀번호</label>
              <input
                type="password"
                name="password"
                className="form-control"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>

            <div className="text-center mt-3">
              <button type="submit" className="btn btn-dark">
                가입
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
