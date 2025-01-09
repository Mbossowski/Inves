import React, { useState, useEffect } from "react";
import Sidebar from "../sidebar/Sidebar";
import Header from "../header/Header";
import LoginPopup from "../loginPopup/LoginPopup";
import RegisterPopup from "../registerPopup/RegisterPopup";
import StockDetail from "./stockDetail/StockDetail";
import Dashboard from "./dashboard/Dashboard";
import { useUser } from "../../context/UserContext"; // Import UserContext
import "./Home.css";

const Home = () => {
  const [theme, setTheme] = useState("light");
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const [showRegisterPopup, setShowRegisterPopup] = useState(false);
  const [selectedStock, setSelectedStock] = useState(null);
  const { user, setUser, clearUser } = useUser(); // Use UserContext

  useEffect(() => {
    document.body.className = theme;
  }, [theme]);

  const handleThemeChange = (mode) => {
    setTheme(mode);
  };

  const handleSuccessfulLogin = (userObject) => {
    setUser(userObject); // Save user in context
    setShowLoginPopup(false);
  };

  const handleLogout = () => {
    clearUser(); // Clear user from context
    setSelectedStock(null);
  };

  const handleStockClick = (stock) => {
    setSelectedStock(stock);
  };

  const handleBack = () => {
    setSelectedStock(null);
  };

  const refreshWatchlist = (stock) => {
    setSelectedStock(stock);
  };

  return (
    <div className={`home-page ${theme}`}>
      <Header onThemeChange={handleThemeChange} onLogout={handleLogout} />
      <div className="home-container">
        <Sidebar
          user={user}
          onStockClick={handleStockClick}
          refreshWatchlist={refreshWatchlist}
        />

        {selectedStock ? (
          <StockDetail
            stock={selectedStock}
            theme={theme}
            onBack={handleBack}
            refreshWatchlist={refreshWatchlist}
          />
        ) : (
          <div className="welcome-container">
            {user ? (
              <Dashboard user={user} />
            ) : (
              <>
                <h1>Welcome to INves Stock Dashboard!</h1>
                <div className="button-container">
                  <button
                    className="auth-button login-button"
                    onClick={() => setShowLoginPopup(true)}
                  >
                    Log In
                  </button>
                  <button
                    className="auth-button register-button"
                    onClick={() => setShowRegisterPopup(true)}
                  >
                    Register
                  </button>
                </div>
              </>
            )}
          </div>
        )}
      </div>

      {showLoginPopup && (
        <LoginPopup
          closePopup={() => setShowLoginPopup(false)}
          onLoginSuccess={handleSuccessfulLogin}
        />
      )}
      {showRegisterPopup && (
        <RegisterPopup closePopup={() => setShowRegisterPopup(false)} />
      )}
    </div>
  );
};

export default Home;
