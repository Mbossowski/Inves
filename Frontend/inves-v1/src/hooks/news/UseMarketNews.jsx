import { useState, useEffect } from "react";
import { MarketNews } from "../../models/MarketNews";
import api from "../../api/axiosConfig"; // Axios instance

const useMarketNews = (symbol, page, size) => {
  const [news, setNews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchNews = async () => {
      setLoading(true);
      setError(null);
     
      try {
        const response = await api.get(
          `/marketNews`, {params: { symbol, page, size }},
        );
        
        const data =  response.data;
        setNews(data.map((item) => MarketNews.fromResponse(item)));
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchNews();
    
  }, [symbol, page, size]);

  return { news, loading, error };
};

// Eksport hooka
export default useMarketNews;