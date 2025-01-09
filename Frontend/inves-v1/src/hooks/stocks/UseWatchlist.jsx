import { useState, useEffect } from "react";
import api from "../../api/axiosConfig"; // Axios instance

const useWatchlist = (userId) => {
  const [watchlist, setWatchlist] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchWatchlist = async () => {
      if (!userId) return;
      setLoading(true);
      try {
        const response = await api.get(`/watchlist/${userId}`);
        setWatchlist(response.data);
      } catch (err) {
        setError("Failed to fetch watchlist.");
      } finally {
        setLoading(false);
      }
    };

    fetchWatchlist();
  }, [userId]);

  return { watchlist, loading, error };
};

export default useWatchlist;
