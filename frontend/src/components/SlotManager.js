import React, { useState, useEffect } from 'react';
import { getArenaAPI, getSlotAPI } from '../api';
import './Manager.css';

const formatTime = (timeValue) => {
    if (!timeValue) return '';
    return String(timeValue).slice(0, 5);
};

function SlotManager() {
    const [arenas, setArenas] = useState([]);
    const [selectedArenaId, setSelectedArenaId] = useState('');
    const [slots, setSlots] = useState([]);
    const [newSlot, setNewSlot] = useState({
        startTime: '',
        endTime: '',
        status: 'AVAILABLE'
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchArenas();
    }, []);

    useEffect(() => {
        if (selectedArenaId) {
            fetchSlots(selectedArenaId);
        }
    }, [selectedArenaId]);

    const fetchArenas = async () => {
        try {
            const response = await getArenaAPI.getAll();
            setArenas(response.data);
        } catch (err) {
            setError('Failed to fetch arenas: ' + err.message);
        }
    };

    const fetchSlots = async (arenaId) => {
        try {
            setLoading(true);
            const response = await getSlotAPI.getByArena(arenaId);
            setSlots(response.data);
            setError(null);
        } catch (err) {
            setError('Failed to fetch slots: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewSlot(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleAddSlot = async (e) => {
        e.preventDefault();
        if (!selectedArenaId) {
            setError('Please select an arena');
            return;
        }
        try {
            setLoading(true);
            const response = await getSlotAPI.create(selectedArenaId, newSlot);
            setSlots([...slots, response.data]);
            setNewSlot({ startTime: '', endTime: '', status: 'AVAILABLE' });
            setError(null);
        } catch (err) {
            setError('Failed to create slot: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager">
            <h2>Slot Management</h2>

            <div className="form-section">
                <h3>Select Arena</h3>
                <select
                    value={selectedArenaId}
                    onChange={(e) => setSelectedArenaId(e.target.value)}
                    className="select-input"
                >
                    <option value="">-- Select an Arena --</option>
                    {arenas.map(arena => (
                        <option key={arena.id} value={arena.id}>
                            {arena.name} - {arena.sportsType}
                        </option>
                    ))}
                </select>
            </div>

            {selectedArenaId && (
                <div className="form-section">
                    <h3>Create New Slot</h3>
                    <form onSubmit={handleAddSlot} className="form">
                        <input
                            type="time"
                            name="startTime"
                            placeholder="Start Time"
                            value={newSlot.startTime}
                            onChange={handleInputChange}
                            required
                        />
                        <input
                            type="time"
                            name="endTime"
                            placeholder="End Time"
                            value={newSlot.endTime}
                            onChange={handleInputChange}
                            required
                        />
                        <select
                            name="status"
                            value={newSlot.status}
                            onChange={handleInputChange}
                        >
                            <option value="AVAILABLE">Available</option>
                            <option value="BOOKED">Booked</option>
                            <option value="CANCELLED">Cancelled</option>
                        </select>
                        <button type="submit" disabled={loading}>
                            {loading ? 'Creating...' : 'Create Slot'}
                        </button>
                    </form>
                </div>
            )}

            {error && <div className="error-message">{error}</div>}

            {selectedArenaId && (
                <div className="list-section">
                    <h3>Slots ({slots.length})</h3>
                    {loading ? (
                        <p>Loading...</p>
                    ) : slots.length === 0 ? (
                        <p>No slots found</p>
                    ) : (
                        <div className="list">
                            {slots.map(slot => (
                                <div key={slot.id} className="list-item">
                                    <h4>Slot {slot.id}</h4>
                                    <p><strong>Start:</strong> {formatTime(slot.startTime)}</p>
                                    <p><strong>End:</strong> {formatTime(slot.endTime)}</p>
                                    <p><strong>Status:</strong> <span className={`status ${slot.status.toLowerCase()}`}>{slot.status}</span></p>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}

export default SlotManager;
