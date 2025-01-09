import React, { createContext, useContext, useState, useEffect } from "react";
import axios from "axios"; // Importujemy axios

const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null); // Dodanie stanu błędu

  // Load user from localStorage on app load
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  // Update localStorage whenever user changes
  useEffect(() => {
    if (user) {
      localStorage.setItem("user", JSON.stringify(user));
    } else {
      localStorage.removeItem("user");
    }
  }, [user]);

  // Funkcja do logowania użytkownika
  const loginUser = async (username, password) => {
    try {
      const response = await axios.post("http://10.0.0.106:8080/api/user/login", { username, password });
      if (response.status === 200) {
        setUser(response.data); // Zapisanie użytkownika do stanu
        localStorage.setItem("user", JSON.stringify(response.data)); // Zapisanie do localStorage
        return { success: true };
      }
      return { success: false, error: "Invalid username or password" };
    } catch (err) {
      setError(err.message);
      return { success: false, error: "An error occurred during login." };
    }
  };

  // Funkcja do rejestracji użytkownika
  const registerUser = async (userData) => {
    try {
      const response = await axios.post("http://10.0.0.106:8080/api/user/register", userData);
      if (response.status === 201) {
        return { success: true };
      }
      return { success: false, error: "Registration failed" };
    } catch (err) {
      setError(err.message);
      return { success: false, error: "An error occurred during registration." };
    }
  };

  // Funkcja do wylogowania użytkownika
  const logoutUser = () => {
    setUser(null);
    localStorage.removeItem("user"); // Usunięcie użytkownika z localStorage
  };

  // Funkcja do aktualizacji balansu
  const updateBalance = (newBalance) => {
    if (user) {
      const updatedUser = { ...user, balance: newBalance };
      setUser(updatedUser);
      localStorage.setItem("user", JSON.stringify(updatedUser)); // Zapisywanie nowego balansu w localStorage
    }
  };

  return (
    <UserContext.Provider
      value={{
        user,
        setUser,
        loginUser,
        registerUser,
        logoutUser,
        clearUser: logoutUser, // Wylogowanie jako clearUser
        updateBalance,
        error, // Udostępniamy błąd w kontekście
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => useContext(UserContext);
