import React, { useState } from "react";
import "./Sidebar.css";
import StockItem from "./StockItem";
import useStocks from "../../hooks/stocks/UseStocks";
import useWatchlist from "../../hooks/stocks/UseWatchlist";

const Sidebar = ({ user, onStockClick }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [isWatchlistActive, setIsWatchlistActive] = useState(false);
  const [page, setPage] = useState(0);

  const { stocks, pagination, loading: stocksLoading } = useStocks(searchQuery, page, 12);
  const { watchlist, loading: watchlistLoading } = useWatchlist(user?.id || null);

  const displayItems = isWatchlistActive ? watchlist : stocks;
  const loading = isWatchlistActive ? watchlistLoading : stocksLoading;

  const handleToggle = (option) => {
    if (!user) return;
    setIsWatchlistActive(option === "watchlist");
    setSearchQuery("");
    setPage(0); // Reset page when toggling
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < pagination.totalPages) {
      setPage(newPage);
    }
  };

  return (
    <div className="sidebar">
      <div className="sidebar-header">
        <div className="toggle-switch">
          <div
            className={`toggle-option ${!isWatchlistActive ? "active" : ""}`}
            onClick={() => handleToggle("all")}
          >
            Stocks
          </div>
          <div
            className={`toggle-option ${isWatchlistActive ? "active" : ""} ${
              !user ? "disabled" : ""
            }`}
            onClick={() => handleToggle("watchlist")}
          >
            Watchlist
          </div>
        </div>

        {!isWatchlistActive && (
          <input
            type="text"
            placeholder="Search stocks..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="stock-search"
          />
        )}
      </div>

      <div className="stock-list">
        {
          displayItems.map((item) => (
            <StockItem key={item.symbol} stock={item} onClick={() => onStockClick(item)} />
          ))
        }
      </div>

      {pagination.totalPages > 1 && !isWatchlistActive && (
        <div className="pagination">
          <button
            onClick={() => handlePageChange(page - 1)}
            disabled={page === 0}
            className="nav-button"
          >
            &lt; Prev
          </button>
          <button
            onClick={() => handlePageChange(page + 1)}
            disabled={page === pagination.totalPages - 1}
            className="nav-button"
          >
            Next &gt;
          </button>
        </div>
      )}
    </div>
  );
};

export default Sidebar;
