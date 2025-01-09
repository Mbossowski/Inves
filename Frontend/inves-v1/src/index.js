import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter } from 'react-router-dom';
import { UserProvider } from "./context/UserContext"; // Ensure correct import

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <UserProvider> {/* Wrap the entire app */}
      <BrowserRouter>
        <App />  {/* Routes are handled inside App.js */}
      </BrowserRouter>
    </UserProvider>
  </React.StrictMode>
);
