import React, { useState, useEffect, useRef } from "react";
import "./Header.css";
import LoginPopup from "../loginPopup/LoginPopup";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../context/UserContext"; // Importujemy useUser hook
import DepositPopup from "./depositPopup/DepositPopup";

const Header = ({ onThemeChange }) => {
  const [showSettings, setShowSettings] = useState(false);
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [showAccountPopup, setShowAccountPopup] = useState(false);
  const [showLoginPopup, setShowLoginPopup] = useState(false);
  const [isDepositPopupOpen, setIsDepositPopupOpen] = useState(false); // Stan do zarządzania otwartym popupem
  const { user, updateBalance, logoutUser } = useUser(); // Dostęp do użytkownika i metod z kontekstu
  const navigate = useNavigate();

  // Refs for detecting clicks outside popups
  const settingsPopupRef = useRef(null);
  const accountPopupRef = useRef(null);

  useEffect(() => {
    // Load theme from localStorage
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme) {
      setIsDarkMode(savedTheme === "dark");
      onThemeChange(savedTheme);
    }
  }, [onThemeChange]);

  useEffect(() => {
    // Close popups when clicking outside
    const handleClickOutside = (event) => {
      if (
        settingsPopupRef.current &&
        !settingsPopupRef.current.contains(event.target)
      ) {
        setShowSettings(false);
      }
      if (
        accountPopupRef.current &&
        !accountPopupRef.current.contains(event.target)
      ) {
        setShowAccountPopup(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const toggleSettings = () => setShowSettings((prev) => !prev);

  const handleThemeChange = () => {
    const newMode = isDarkMode ? "light" : "dark";
    setIsDarkMode(!isDarkMode);
    onThemeChange(newMode);
    localStorage.setItem("theme", newMode);
  };

  const toggleAccountPopup = () => setShowAccountPopup((prev) => !prev);

  const toggleLoginPopup = () => setShowLoginPopup((prev) => !prev);

  const handleLogout = () => {
    if (window.confirm("Are you sure you want to log out?")) {
      logoutUser(); // Wylogowanie użytkownika za pomocą funkcji z `UserContext`
      navigate("/"); // Przekierowanie na stronę główną
      setShowAccountPopup(false); // Zamknięcie popupu
    }
  };

  const handleOpenDepositPopup = () => {
    setIsDepositPopupOpen(true); // Otwarcie popupu wpłaty
  };

  const handleCloseDepositPopup = () => {
    setIsDepositPopupOpen(false); // Zamknięcie popupu wpłaty
  };

  return (
    <div className="header">
      <div className="logo">INves</div>
      
      {/* Wyświetlanie salda użytkownika, jeśli jest zalogowany */}
      {user && user.balance !== undefined && (
        <div className="user-balance">
          Balance: {user.balance} $
        </div>
      )}
      
      <div className="header-buttons">
        <button
          className="header-button"
          onClick={toggleSettings}
          aria-expanded={showSettings}
          aria-label="Settings"
        >
          Settings
        </button>
        <button
          className="header-button"
          onClick={toggleAccountPopup}
          aria-expanded={showAccountPopup}
          aria-label="Account"
        >
          Account
        </button>
      </div>

      {/* Account Popup */}
      {showAccountPopup && (
        <div
          className="account-popup"
          ref={accountPopupRef}
          role="dialog"
          aria-hidden={!showAccountPopup}
        >
          <h3>Account</h3>
          {user ? (
            <>
              <p>
                Welcome, {user.firstName} {user.lastName}
              </p>
              <button 
                onClick={handleOpenDepositPopup}
              >
                Deposit
              </button>

              <button
                className="account-info-button"
                onClick={() => navigate("/user-info")}
              >
                View Account Info
              </button>
              <button className="logout-button" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            <>
              <p>You are not logged in.</p>
            </>
          )}
        </div>
      )}

      {/* Settings Popup */}
      {showSettings && (
        <div
          className="settings-popup"
          ref={settingsPopupRef}
          role="dialog"
          aria-hidden={!showSettings}
        >
          <div className="theme-toggle-container">
            <span>Dark Mode</span>
            <label className="switch">
              <input
                type="checkbox"
                checked={isDarkMode}
                onChange={handleThemeChange}
              />
              <span className="slider"></span>
            </label>
          </div>
        </div>
      )}

      {/* Deposit Popup */}
      {isDepositPopupOpen && (
        <DepositPopup
          closePopup={handleCloseDepositPopup}
          updateBalance={updateBalance}
          currentBalance={user ? user.balance : 0} // Używamy salda z kontekstu
        />
      )}

      {/* Login Popup */}
      {showLoginPopup && <LoginPopup closePopup={toggleLoginPopup} />}
    </div>
  );
};

export default Header;
