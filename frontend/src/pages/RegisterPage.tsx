import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

interface RegisterFormData {
  username: string;
  password: string;
}

const RegisterPage: React.FC = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<RegisterFormData>({
    username: '',
    password: ''
  });
  const [errors, setErrors] = useState<string[]>([]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrors([]);

    try {
      await axios.post('/api/auth/register', formData);
      navigate('/login', { state: { message: 'Registration successful! Please log in.' } });
    } catch (error: any) {
      if (error.response?.data) {
        if (typeof error.response.data === 'string') {
          setErrors([error.response.data]);
        } else {
          const errorMessages = Object.entries(error.response.data)
            .map(([field, message]) => `${field}: ${message}`);
          setErrors(errorMessages);
        }
      } else {
        setErrors(['An error occurred during registration']);
      }
    }
  };

  return (
    <div className="register-page">
      <h2>Register</h2>
      {errors.length > 0 && (
        <div className="alert alert-danger">
          {errors.map((error, index) => (
            <p key={index}>{error}</p>
          ))}
        </div>
      )}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
            className="form-control"
            required
            minLength={3}
            maxLength={50}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="form-control"
            required
            minLength={6}
          />
        </div>
        <button type="submit" className="btn btn-primary">Register</button>
      </form>
    </div>
  );
};

export default RegisterPage;