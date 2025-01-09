import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./UserInfoPage.css";

const UserInfoPage = () => {
  const [user, setUser] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    title: "",
    firstName: "",
    lastName: "",
    phone: "",
    iban: "",
    country: "",
    city: "",
    street: "",
    email: "",
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (!storedUser) {
      setError("User not found in localStorage");
      setLoading(false);
      navigate("/");
    } else {
      const userData = JSON.parse(storedUser);
      setUser(userData);
      setFormData({
        title: userData.title || "",
        firstName: userData.firstName || "",
        lastName: userData.lastName || "",
        phone: userData.phone || "",
        address: userData.address || "",
        iban: userData.iban || "",
        country: userData.country || "",
        city: userData.city || "",
        street: userData.street || "",
        email: userData.email || "",
      });
      setLoading(false);
    }
  }, [navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSave = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/user/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error("Failed to update user data");

      const updatedUser = await response.json();
      setUser((prev) => ({ ...prev, ...updatedUser }));
      setEditMode(false);
      setSuccess("Information updated successfully.");
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <p>Loading user data...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div className="user-info-page">
      <h2>User Information</h2>
      {success && <p className="success-message">{success}</p>}

      {/* Personal Information Section */}
      {user && (
        <div className="section">
          <h3>Personal Information</h3>
          <div className="info-grid">
            <div>
              <label>Title:</label>
              
                <span>{user.title}</span>
              
            </div>
            <div>
              <label>First Name:</label>
              
                <span>{user.firstName}</span>
            
            </div>
            <div>
              <label>Last Name:</label>
             
                <span>{user.lastName}</span>
            
            </div>
            <div>
              <label>Username:</label>
              <span>{user.username}</span>
            </div>
            <div>
              <label>Email:</label>
              
              {editMode ? (
                <div>
                <span></span>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                />
                </div>
              ) : (
                <span>{user.email}</span>
              )}
            </div>
            <div>
              <label>Phone:</label>
              {editMode ? (
                <div>
                <span></span>
                <input
                  type="text"
                  name="phone"
                  value={formData.phone}
                  onChange={handleChange}
                
                />
                </div>
              ) : (
                <span>{user.phone}</span>
              )}
            </div>
          </div>
        </div>
      )}

      {/* Payment Information Section */}
      {user && (
        <div className="section">
          <h3>Payment Information</h3>
          <div className="info-grid">
            <div className="full-width-input">
              <label>IBAN:</label>
              {editMode ? (
                <input
                  type="text"
                  name="iban"
                  value={formData.iban}
                  onChange={handleChange}
                />
              ) : (
                <span>{user.iban}</span>
              )}
            </div>
            <div>
              <label>Country:</label>
              {editMode ? (
                <div>
                <span></span>
                <input
                  type="text"
                  name="country"
                  value={formData.country}
                  onChange={handleChange}
                />
                </div>
              ) : (
                <span>{user.country}</span>
              )}
            </div>
            <div>
              <label>City:</label>
              {editMode ? (
                <div>
                <span></span>
                <input
                  type="text"
                  name="city"
                  value={formData.city}
                  onChange={handleChange}
                />
                </div>
              ) : (
                <span>{user.city}</span>
              )}
            </div>
            <div>
              <label>Street:</label>
              {editMode ? (
                <div>
                <span></span>
                <input
                  type="text"
                  name="street"
                  value={formData.street}
                  onChange={handleChange}
                />
                </div>
              ) : (
                <span>{user.street}</span>
              )}
            </div>
          </div>
        </div>
      )}

      <div className="buttons">
        {editMode ? (
          <>
            <button className="primary-button" onClick={handleSave}>
              Save
            </button>
            <button className="secondary-button" onClick={() => setEditMode(false)}>
              Cancel
            </button>
          </>
        ) : (
          <button className="primary-button" onClick={() => setEditMode(true)}>
            Edit Info
          </button>
        )}
        <button className="secondary-button" onClick={() => navigate("/")}>
          Back to Dashboard
        </button>
      </div>
    </div>
  );
};

export default UserInfoPage;
