import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
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
      .post(`/login`, formData, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        withCredentials: true,
      })
      .then(() => {
        alert('로그인 성공!');
        navigate('/');
      })
      .catch(error => {
        alert('올바르지 않은 아이디 혹은 비밀번호입니다.');
      });
  };

  return (
    <div className="container">
      <h2 className="text-center mt-5 mb-3 fw-bold text-body fs-2">로그인</h2>
      <div className="card">
        <div className="card-header">
          <Link className="btn btn-secondary mx-1" to="/">
            홈 화면
          </Link>
          <Link className="btn btn-primary mx-1" to="/signup">
            회원 가입
          </Link>
        </div>
        <div className="card-body d-flex justify-content-center">
          <form onSubmit={handleSubmit} className="w-50">
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
                로그인
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
