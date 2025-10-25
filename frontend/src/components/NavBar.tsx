import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const NavBar: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { isAuthenticated, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="container">
        <Link to="/" className="navbar-brand">
          Learning Management System
        </Link>
        <div className="nav-links">
          {!isAuthenticated ? (
            <>
              <Link to="/register" className="nav-link">Register</Link>
              <Link to="/login" className="nav-link">Login</Link>
            </>
          ) : (
            <>
              <Link to="/chat" className="nav-link">Chat</Link>
              <span onClick={handleLogout} className="nav-link" style={{ cursor: 'pointer' }}>Logout</span>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;