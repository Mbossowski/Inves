import React from "react";
import { useUser } from "../context/UserContext"; // Import UserContext
import "./AccountPopup.css";

const AccountPopup = ({ closePopup }) => {
  const { user } = useUser(); // Get user from context

  return (
    <div className="account-popup-overlay">
      <div className="account-popup">
        <h2>Account</h2>
        <div className="account-info">
          {user ? (
            <p>Welcome, {user.username}</p>
          ) : (
            <p>User not logged in</p>
          )}
        </div>
        <div className="button-group">
          
          <button type="button" onClick={closePopup}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default AccountPopup;
