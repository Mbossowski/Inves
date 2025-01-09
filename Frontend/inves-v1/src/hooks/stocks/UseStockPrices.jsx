import { useState, useEffect } from "react";
import api from "../../api/axiosConfig"; // Axios instance

const useStockPrices = (stockSymbol, Period) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStockPrices = async () => {
      setLoading(true);
      try {
        
        const response = await api.get(
          `/stockPrice/${stockSymbol}?periodInHours=${Period}`
        );
       
        setData(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchStockPrices();
  }, [stockSymbol, Period]);

  return { data, loading, error };
};

export default useStockPrices;
