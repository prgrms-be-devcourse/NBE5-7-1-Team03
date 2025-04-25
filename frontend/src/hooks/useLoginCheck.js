import { useEffect, useState } from 'react';
import axios from 'axios';

export default function useAuthCheck() {
  const [isLogin, setIsLogin] = useState(false);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);

  useEffect(() => {
    axios
      .get('/user/info', {
        withCredentials: true,
      })
      .then(res => {
        const userData = res.data.response;
        setUser(userData);
        setIsLogin(true);
      })
      .catch(() => {
        setUser(null);
        setIsLogin(false);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  return { isLogin, user, loading };
}
