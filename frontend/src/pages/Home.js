import React from 'react';
import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div className="container p-5 text-center">
      <div className="card style={{ height: '100vh' }}">
        <div className="card-header">
          <h2 className="text-center mt-5 mb-3">GC Coffee</h2>
        </div>
        <div className="card-body d-flex flex-column justify-content-center align-items-center p-4">
          <p>
            <Link to={`/admin`} className="btn btn-secondary mt-5 mb-3 px-4 py-2">
              관리자 페이지g
            </Link>
          </p>
          <p>
            <Link to={`/order`} className="btn btn-dark px-4 py-2">
              커피 주문하기
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
