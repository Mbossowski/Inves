import { useState, useEffect } from "react";
import api from "../../api/axiosConfig"; // Axios instance

const useStockQuotes = (stockSymbol, Period) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);


  const fetchStockQuotes = async () => {
    setLoading(true);
    try {
      
     const response = await api.get(
        `/stockQuote/${stockSymbol}?periodInDays=${Period}`
      );

      setData(response.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    

    fetchStockQuotes(); // Ładujemy dane od razu po załadowaniu komponentu
    const interval = setInterval(fetchStockQuotes, 60000); // Co minutę odświeżamy dane
    
    return () => clearInterval(interval); // Czyścimy interval po unmount


  }, [stockSymbol, Period]);

  return { data, loading, error };
};

export default useStockQuotes;
