import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import useAuthCheck from '../hooks/useLoginCheck';

function NavBar() {
  const navigate = useNavigate();
  const { isLogin, user, loading } = useAuthCheck();

  const handleLogout = async () => {
    try {
      await axios.post('/logout', {}, { withCredentials: true });
      alert('로그아웃 성공!');
      navigate('/');
    } catch (err) {
      console.error('로그아웃 실패:', err);
    }
  };

  if (loading) return null;

  return (
    <div>
      <Link className="btn btn-secondary mx-1" to="/">
        홈 화면
      </Link>

      {!isLogin ? (
        <>
          <Link className="btn btn-primary mx-1" to="/login">
            로그인
          </Link>
          <Link className="btn btn-primary mx-1" to="/signup">
            회원 가입
          </Link>
        </>
      ) : (
        <button className="btn btn-danger mx-1" onClick={handleLogout}>
          로그아웃
        </button>
      )}
    </div>
  );
}

export default NavBar;
