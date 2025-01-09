import React, { useState } from "react";
import StockHoldingItem from "./StockHoldingItem";

const Dashboard = ({ user }) => {

    const [stockHoldings, setStockHoldings] = useState([])


  return (
    <div className="dashboard-container">
      <h1>Dashboard</h1>
      <div className="stock-holdings">
        {stockHoldings.length > 0 ? (
          stockHoldings.map((holding) => (
            <StockHoldingItem
              key={holding.symbol}
              symbol={holding.symbol}
              quantity={holding.quantity}
              price={holding.price}
              name={holding.name}
            />
          ))
        ) : (
          <p>You have no holdings yet.</p>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
