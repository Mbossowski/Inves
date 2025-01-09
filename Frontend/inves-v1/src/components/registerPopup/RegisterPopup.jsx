// src/components/RegisterPopup/RegisterPopup.js
import React, { useState } from 'react';
import './RegisterPopup.css';
import { useUser } from '../../context/UserContext'; // Importujemy hooka useUser

const RegisterPopup = ({ closePopup }) => {
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [title, setTitle] = useState('Mr'); // Default value
  const [loading, setLoading] = useState(false); // For showing loading spinner if needed
  const [error, setError] = useState(null); // To display any error messages
  const { registerUser } = useUser(); // Korzystamy z funkcji registerUser

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate passwords match
    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    setLoading(true); // Set loading to true while sending the request
    setError(null); // Reset any previous errors

    const userData = {
      email,
      phone,
      username,
      password,
      firstName,
      lastName,
      title,
    };

    const { success, error: registerError } = await registerUser(userData);

    if (success) {
      alert('Registration successful!');
      closePopup(); // Close the popup after successful registration
    } else {
      setError(registerError || 'There was an error during registration.');
    }

    setLoading(false); // Set loading to false after the request completes
  };

  return (
    <div className="register-popup-overlay">
      <div className="register-popup">
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          {error && <div className="error-message">{error}</div>}
          <div className="input-group">
            <label htmlFor="title">Title</label>
            <select
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            >
              <option value="Mr">Mr</option>
              <option value="Ms">Ms</option>
              <option value="Mrs">Mrs</option>
              <option value="Dr">Dr</option>
            </select>
          </div>
          <div className="input-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              id="firstName"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              id="lastName"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="phone">Phone</label>
            <input
              type="text"
              id="phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input
              type="password"
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>
          <div className="button-group">
            <button type="submit" disabled={loading}>Register</button>
            <button type="button" onClick={closePopup} disabled={loading}>Cancel</button>
          </div>
          {loading && <div className="loading-spinner">Loading...</div>}
        </form>
      </div>
    </div>
  );
};

export default RegisterPopup;
