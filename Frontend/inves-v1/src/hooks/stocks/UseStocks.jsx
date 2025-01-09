import { useState, useEffect } from "react";
import api from "../../api/axiosConfig"; // Axios instance

const useStocks = (searchQuery, page, size) => {
  const [stocks, setStocks] = useState([]);
  const [pagination, setPagination] = useState({
    page: 0,
    size: 10,
    totalPages: 0,
    totalElements: 0,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  
    const fetchStocks = async () => {
      setLoading(true);
      try {
        const response = await api.get(`/stock`, {
          params: { page, size, query: searchQuery },
        });
        setStocks(response.data.content);
        setPagination({
          page: response.data.pageable.pageNumber,
          size: response.data.pageable.pageSize,
          totalPages: response.data.totalPages,
          totalElements: response.data.totalElements,
        });
      } catch (err) {
        setError("Failed to fetch stocks.");
      } finally {
        setLoading(false);
      }
    };

  useEffect(() => {

    fetchStocks(); // Ładujemy dane od razu po załadowaniu komponentu
    const interval = setInterval(fetchStocks, 60000); // Co minutę odświeżamy dane
    
    return () => clearInterval(interval); // Czyścimy interval po unmount

  }, [searchQuery, page, size]);

  return { stocks, pagination, loading, error };
};

export default useStocks;
