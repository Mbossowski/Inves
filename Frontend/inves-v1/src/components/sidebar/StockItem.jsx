import React from "react";
import "./Sidebar.css";
import "../home/Home.css"

const StockItem = ({ stock, onClick }) => {

   // Klasy CSS na podstawie wartości pctChange
   const pctChangeClass = stock.pctChange > 0 ? "positive" : "negative";
   const lastSaleClass = stock.pctChange > 0 ? "positive" : "negative";
 
    return (
      <div className="stock-item" onClick={onClick}>
        <div className="stock-item-desc">
          <div className="stock-item-symbol">
            <span>{stock.symbol}</span>
          </div>
          <div className="stock-item-value">
          <span className={pctChangeClass}>{stock.pctChange}%</span>
          <span className={lastSaleClass}> {stock.lastSale}$</span>
          </div>
        </div>
        <div className="stock-item-name">
        <span>{stock.companyName}</span>
        </div>
      </div>
      
      
    );
  };
  
  export default StockItem;
