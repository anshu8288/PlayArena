import React, { useState, useEffect } from 'react';
import { getArenaAPI } from '../api';
import './Manager.css';

function ArenaManager() {
    const [arenas, setArenas] = useState([]);
    const [newArena, setNewArena] = useState({
        name: '',
        location: '',
        sportsType: 'BADMINTON',
        pricePerSlot: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchArenas();
    }, []);

    const fetchArenas = async () => {
        try {
            setLoading(true);
            const response = await getArenaAPI.getAll();
            setArenas(response.data);
            setError(null);
        } catch (err) {
            setError('Failed to fetch arenas: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewArena(prev => ({
            ...prev,
            [name]: name === 'pricePerSlot' ? parseFloat(value) : value
        }));
    };

    const handleAddArena = async (e) => {
        e.preventDefault();
        try {
            setLoading(true);
            const response = await getArenaAPI.create(newArena);
            setArenas([...arenas, response.data]);
            setNewArena({ name: '', location: '', sportsType: 'BADMINTON', pricePerSlot: '' });
            setError(null);
        } catch (err) {
            setError('Failed to create arena: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager">
            <h2>Arena Management</h2>

            <div className="form-section">
                <h3>Create New Arena</h3>
                <form onSubmit={handleAddArena} className="form">
                    <input
                        type="text"
                        name="name"
                        placeholder="Arena Name"
                        value={newArena.name}
                        onChange={handleInputChange}
                        required
                    />
                    {/* <input
                        type="text"
                        name="location"
                        placeholder="Location"
                        value={newArena.location}
                        onChange={handleInputChange}
                        required
                    /> */}
                    <select
                        name="sportsType"
                        value={newArena.sportsType}
                        onChange={handleInputChange}
                    >
                        <option value="BADMINTON">Badminton</option>
                        <option value="TENNIS">Tennis</option>
                        <option value="CRICKET">Cricket</option>
                        <option value="FOOTBALL">Football</option>
                        <option value="BASKETBALL">Basketball</option>
                    </select>
                    {/* <input
                        type="number"
                        name="pricePerSlot"
                        placeholder="Price Per Slot"
                        value={newArena.pricePerSlot}
                        onChange={handleInputChange}
                        step="0.01"
                        required
                    /> */}
                    <button type="submit" disabled={loading}>
                        {loading ? 'Creating...' : 'Create Arena'}
                    </button>
                </form>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="list-section">
                <h3>All Arenas ({arenas.length})</h3>
                {loading ? (
                    <p>Loading...</p>
                ) : arenas.length === 0 ? (
                    <p>No arenas found</p>
                ) : (
                    <div className="list">
                        {arenas.map(arena => (
                            <div key={arena.id} className="list-item">
                                <h4>{arena.name}</h4>
                                {/* <p><strong>Location:</strong> {arena.location}</p> */}
                                <p><strong>Sport Type:</strong> {arena.sportsType}</p>
                                {/* <p><strong>Price Per Slot:</strong> ₹{arena.pricePerSlot}</p> */}
                                <p><strong>ID:</strong> {arena.id}</p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default ArenaManager;
