import React from "react";
import "./Home.css";

const WelcomeSection = ({ username, onLoginClick, onRegisterClick }) => (
  <div className="welcome-container">
    {username ? (
      <h1>Your Dashboard</h1>
    ) : (
      <>
        <h1>Welcome to INves Stock Dashboard!</h1>
        <div className="button-container">
          <button className="auth-button login-button" onClick={onLoginClick}>
            Log In
          </button>
          <button
            className="auth-button register-button"
            onClick={onRegisterClick}
          >
            Register
          </button>
        </div>
      </>
    )}
  </div>
);

export default WelcomeSection;
