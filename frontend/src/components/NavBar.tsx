import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const NavBar: React.FC = () => {
  const location = useLocation();

  return (
    <nav className="navbar">
      <div className="container">
        <Link to="/" className="navbar-brand">
          Learning Management System
        </Link>
        <div className="nav-links">
          {location.pathname !== '/register' && (
            <Link to="/register" className="nav-link">Register</Link>
          )}
          {location.pathname !== '/login' && (
            <Link to="/login" className="nav-link">Login</Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;