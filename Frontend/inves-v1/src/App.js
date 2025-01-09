import './App.css';
import { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Home from './components/home/Home';
import { UserProvider, useUser } from './context/UserContext'; // Import the UserProvider and useUser hook
import UserInfoPage from "./pages/UserInfoPage";
import WebSocketComponent from "./components/WebSocketComponent";
function App() {
  const [theme, setTheme] = useState('light');
  const { user } = useUser(); // Access the current user from context
  
  // Initialize theme on app load
  useEffect(() => {
    const savedTheme = localStorage.getItem('theme') || 'light';
    setTheme(savedTheme);
    document.body.className = savedTheme;
  }, []);

  // Handle theme change
  const handleThemeChange = (newTheme) => {
    setTheme(newTheme);
    document.body.className = newTheme;
    localStorage.setItem('theme', newTheme);
  };

  return (
    <div className="App">
      <Routes>
        {/* Route to the layout with nested routes */}
        <Route path="/" element={<Layout onThemeChange={handleThemeChange} theme={theme} />}>
          <Route path="/" element={<Home />} />
          <Route path="/user-info" element={<UserInfoPage />} />
        </Route>
       
      </Routes>
      
    </div>
  );
}

export default App;
