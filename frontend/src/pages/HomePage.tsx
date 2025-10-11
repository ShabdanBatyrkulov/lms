import React from 'react';
import { Link } from 'react-router-dom';

const HomePage: React.FC = () => {
  return (
    <div className="home-page">
      <h1>Welcome to Learning Management System</h1>
      <p>Start your learning journey today!</p>
      <div className="cta-buttons">
        <Link to="/register" className="btn btn-primary">Register</Link>
        <Link to="/login" className="btn btn-secondary">Login</Link>
      </div>
    </div>
  );
};

export default HomePage;