import React, { useState, useEffect } from 'react';
import { getUserAPI } from '../api';
import './Manager.css';

function UserManager() {
    const [users, setUsers] = useState([]);
    const [newUser, setNewUser] = useState({
        name: '',
        email: '',
        phoneNumber: '',
        role: 'USER'
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            setLoading(true);
            const response = await getUserAPI.getAll();
            setUsers(response.data);
            setError(null);
        } catch (err) {
            setError('Failed to fetch users: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewUser(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleAddUser = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            const response = await getUserAPI.create(newUser);
            setUsers([...users, response.data]);
            setNewUser({ name: '', email: '', phoneNumber: '', role: 'USER' });
            setError(null);
        } catch (err) {
            setError('Failed to create user: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager">
            <h2>User Management</h2>

            <div className="form-section">
                <h3>Create New User</h3>
                <form onSubmit={handleAddUser} className="form">
                    <input
                        type="text"
                        name="name"
                        placeholder="Name"
                        value={newUser.name}
                        onChange={handleInputChange}
                        required
                    />
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={newUser.email}
                        onChange={handleInputChange}
                        required
                    />
                    {/* <input
                        type="tel"
                        name="phoneNumber"
                        placeholder="Phone Number"
                        value={newUser.phoneNumber}
                        onChange={handleInputChange}
                    /> */}
                    <select
                        name="role"
                        value={newUser.role}
                        onChange={handleInputChange}
                    >
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                    </select>
                    <button type="submit" disabled={loading}>
                        {loading ? 'Creating...' : 'Create User'}
                    </button>
                </form>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="list-section">
                <h3>All Users ({users.length})</h3>
                {loading ? (
                    <p>Loading...</p>
                ) : users.length === 0 ? (
                    <p>No users found</p>
                ) : (
                    <div className="list">
                        {users.map(user => (
                            <div key={user.id} className="list-item">
                                <h4>{user.name}</h4>
                                <p><strong>Email:</strong> {user.email}</p>
                                {/* <p><strong>Phone:</strong> {user.phoneNumber}</p> */}
                                <p><strong>Role:</strong> {user.role}</p>
                                <p><strong>ID:</strong> {user.id}</p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default UserManager;
