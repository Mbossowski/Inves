import { useState, useEffect } from "react";
import api from "../../api/axiosConfig"; // Axios instance

const useStockDetails = (symbol) => {
  const [stock, setStock] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStockDetails = async () => {
      setLoading(true);
      try {
        const response = await api.get(`/stock/details?symbol=` + symbol);
        setStock(response.data);
       
      } catch (err) {
        setError("Failed to fetch stocks.");
      } finally {
        setLoading(false);
      }
    };

    if (symbol) {
      fetchStockDetails();
    }
  }, [symbol]); // Re-run effect when `symbol` changes

  return { stock, loading, error };
};

export default useStockDetails;
