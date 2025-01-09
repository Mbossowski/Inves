import React, { useState } from 'react';
import axios from 'axios';
import StockChart from './StockChart'; 
import './StockDetail.css';
import useMarketNews from "../../../hooks/news/UseMarketNews";
import NewsItem from "./NewsItem";
import useStockQuotes from "../../../hooks/stocks/UseStockQuotes";
import useStockDetails from '../../../hooks/stocks/UseStockDetails';

const StockDetail = ({ stock, theme, onBack, refreshWatchlist }) => {
  const [shares, setShares] = useState(0);
  const [action, setAction] = useState('buy'); // Default action is 'buy'
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [openPrice, setOpenPrice] = useState(0);
  
  const [period, setPeriod] = useState("0"); 
  const { data, loading: loadingQuotes, error: errorQuotes } = useStockQuotes(stock.symbol, period);
  const { news, loading: loadingNews, error: errorNews } = useMarketNews(stock.symbol, page, size);
  const { stock: details, loading: loadingDetails, error: errorDetails } = useStockDetails(stock.symbol);

  // Check if the user is logged in
  const user = JSON.parse(localStorage.getItem("user"));
  const isLoggedIn = !!user;

  const handleAddToWatchlist = async (stock) => {
    try {
      const userId = user ? user.id : null;
      const stockRequest = {
        symbol: stock.symbol,
        companyName: stock.companyName,
        market: stock.market,
        currency: stock.currency,
      };

      await axios.put(`http://localhost:8080/api/watchlist?userId=${userId}`, stockRequest);
      alert(`Added ${stock.symbol} to your Watchlist!`);
      refreshWatchlist(stock); 
    } catch (error) {
      console.error('Error adding to watchlist:', error);
      alert('There was an error adding the stock to your watchlist.');
    }
  };

  const handleTransaction = () => {
    if (shares <= 0) {
      alert('Please specify a valid number of shares.');
      return;
    }

    const transactionData = {
      symbol: stock.symbol,
      amount: shares,
      action,
    };

    console.log(`Executing ${action} of ${shares} shares of ${stock.symbol}`);
    alert(`${action === 'buy' ? 'Bought' : 'Sold'} ${shares} shares of ${stock.symbol}`);
  };

  const handlePeriodChange = (newPeriod) => {
    setPeriod(newPeriod);
  };
  
  const pctChangeClass = stock.pctChange > 0 ? "positive" : "negative";
  const netChangeClass = stock.pctChange > 0 ? "positive" : "negative";
  const netChange = stock.pctChange > 0 ? stock.netChange : 0 - stock.netChange;

  return (
    <div className={`stock-detail ${theme}`}>
      <div className={`back-button-field ${theme}`}>
        <button className="back-button" onClick={onBack}>&#8592; Back</button>
        <button className="add-to-watchlist" onClick={() => handleAddToWatchlist(stock)}>
          Add to Watchlist
        </button>
      </div>

      <div className="stock-header">
        <div className="stock-market-info">
          {stock.companyName} - {stock.market} - $
        </div>
        <div className="stock-current-price">
          <div className="stock-symbol">{stock.symbol}</div>
          <div className="stock-lastsale">{stock.lastSale}$ </div>
          <div className={pctChangeClass}>{stock.pctChange}%</div>
          <div className={netChangeClass}>{netChange}</div>
        </div>
      </div>

      <div className={`stock-graph ${theme}`}>
        <div className="stock-period-selector">
          <button onClick={() => handlePeriodChange("0")} className={period === "0" ? "active" : ""}>1D</button>
          <button onClick={() => handlePeriodChange("4")} className={period === "4" ? "active" : ""}>5D</button>
        </div>
        <StockChart data={data} setOpenPrice={setOpenPrice} />
      </div>

      <div className="stock-transaction-section">
        <div className="stock-info-container">
          {details ? (
            <div className="stock-info-column">
              <div>Previous Close: {details.previousClose}</div>
              <div>Open: {openPrice}</div>
              <div>52 Week Range: {details.fiftyTwoWeekLow} - {details.fiftyTwoWeekHigh}</div>
              <div>Day's Range: {details.regularMarketDayLow} - {details.regularMarketDayHigh}</div>
              <div>Volume: {details.volume}</div>
            </div>
          ) : (
            <div className="stock-info-column"></div>
          )}
          <div className="stock-info-column">
            <div>Sector: {stock.sector}</div>
            <div>Industry: {stock.industry}</div>
          </div>
        </div>

        {isLoggedIn && (
          <div className="transaction-section">
            <h3>Transactions</h3>
            <div className="transaction-fields">
              {stock.lastSale} x
              <input
                type="number"
                value={shares}
                onChange={(e) => setShares(Number(e.target.value))}
                min="1"
                placeholder="Enter number of shares"
              />
              <div className="action-buttons">
                <button onClick={() => setAction('buy')} className={action === 'buy' ? 'active' : ''}>
                  Buy
                </button>
                <button onClick={() => setAction('sell')} className={action === 'sell' ? 'active' : ''}>
                  Sell
                </button>
              </div>
            </div>
          </div>
        )}
      </div>

      <div className="stock-news">
        <h2>Market News</h2>
        {news.length > 0 ? (
          news.map((item, index) => <NewsItem key={index} news={item} />)
        ) : (
          <p>No news available.</p>
        )}
        <div className="pagination">
          <button
            disabled={page === 0}
            onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
          >
            Previous
          </button>
          <button onClick={() => setPage((prev) => prev + 1)}>Next</button>
        </div>
      </div>
    </div>
  );
};

export default StockDetail;
