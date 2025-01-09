// src/components/LoginPopup/LoginPopup.js
import React, { useState } from "react";
import "./LoginPopup.css";
import { useUser } from "../../context/UserContext"; // Importujemy hooka useUser

const LoginPopup = ({ closePopup }) => {
  const [usernameInput, setUsernameInput] = useState("");
  const [passwordInput, setPasswordInput] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(""); // State for error messages
  const { loginUser } = useUser(); // Use the loginUser function from context

  const handleLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError(""); // Clear previous errors

    if (!usernameInput || !passwordInput) {
      setError("Both fields are required.");
      setIsLoading(false);
      return;
    }

    const { success, error: loginError } = await loginUser(usernameInput, passwordInput);

    if (success) {
      closePopup(); // Close the login popup on success
    } else {
      setError(loginError || "Invalid username or password");
    }

    setIsLoading(false);
  };

  return (
    <div className="login-popup-overlay" role="dialog" aria-modal="true">
      <div className="login-popup" role="document">
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              value={usernameInput}
              onChange={(e) => setUsernameInput(e.target.value)}
              required
              disabled={isLoading}
              aria-invalid={!!error}
              aria-describedby={error ? "error-message" : undefined}
            />
          </div>
          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={passwordInput}
              onChange={(e) => setPasswordInput(e.target.value)}
              required
              disabled={isLoading}
              aria-invalid={!!error}
              aria-describedby={error ? "error-message" : undefined}
            />
          </div>
          {error && (
            <p id="error-message" className="error-message">
              {error}
            </p>
          )}
          <div className="button-group">
            <button type="submit" disabled={isLoading}>
              {isLoading ? "Logging in..." : "Login"}
            </button>
            <button
              type="button"
              onClick={() => {
                setUsernameInput(""); // Clear username
                setPasswordInput(""); // Clear password
                setError(""); // Clear errors
                closePopup();
              }}
              disabled={isLoading}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPopup;
