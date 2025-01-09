import React, { useState } from "react";
import { useUser } from "../../../context/UserContext"; // Hook do dostępu do kontekstu użytkownika
import "./DepositPopup.css"; // Upewnij się, że masz odpowiednią klasę CSS

const DepositPopup = ({ closePopup, updateBalance, currentBalance }) => {
  const [depositAmount, setDepositAmount] = useState("");
  const [error, setError] = useState(""); // Do przechowywania błędów

  const handleDeposit = () => {
    const amount = parseFloat(depositAmount);
    
    if (isNaN(amount) || amount <= 0) {
      setError("Please enter a valid amount.");
      return;
    }

    // Aktualizacja balansu użytkownika
    updateBalance(currentBalance + amount);

    // Zamknięcie popupu po udanej wpłacie
    closePopup();
  };

  return (
    <div className="deposit-popup-overlay" role="dialog" aria-modal="true">
      <div className="deposit-popup" role="document">
        <h2>Deposit Funds</h2>
        <form onSubmit={(e) => e.preventDefault()}>
          <div className="input-group">
            <label htmlFor="depositAmount">Amount to Deposit</label>
            <input
              type="number"
              id="depositAmount"
              value={depositAmount}
              onChange={(e) => setDepositAmount(e.target.value)}
              required
            />
          </div>

          {error && <p className="error-message">{error}</p>}

          <div className="button-group">
            <button type="button" onClick={handleDeposit}>
              Deposit
            </button>
            <button type="button" onClick={closePopup}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default DepositPopup;
