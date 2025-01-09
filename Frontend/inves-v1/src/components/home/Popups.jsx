import React from "react";
import LoginPopup from "../loginPopup/LoginPopup";
import RegisterPopup from "../registerPopup/RegisterPopup";
import "./Home.css";

const Popups = ({
  showLoginPopup,
  closeLoginPopup,
  onLoginSuccess,
  showRegisterPopup,
  closeRegisterPopup,
}) => (
  <>
    {showLoginPopup && (
      <LoginPopup closePopup={closeLoginPopup} onLoginSuccess={onLoginSuccess} />
    )}
    {showRegisterPopup && <RegisterPopup closePopup={closeRegisterPopup} />}
  </>
);

export default Popups;
