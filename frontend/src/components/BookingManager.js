import React, { useState, useEffect } from 'react';
import { getUserAPI, getArenaAPI, getSlotAPI, getBookingAPI } from '../api';
import './Manager.css';

const formatTime = (timeValue) => {
    if (!timeValue) return '';
    return String(timeValue).slice(0, 5);
};

function BookingManager() {
    const [users, setUsers] = useState([]);
    const [arenas, setArenas] = useState([]);
    const [slots, setSlots] = useState([]);
    const [selectedUserId, setSelectedUserId] = useState('');
    const [selectedArenaId, setSelectedArenaId] = useState('');
    const [selectedSlotId, setSelectedSlotId] = useState('');
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        fetchUsers();
        fetchArenas();
        fetchBookings();
    }, []);

    useEffect(() => {
        if (selectedArenaId) {
            fetchSlots(selectedArenaId);
        }
    }, [selectedArenaId]);

    const fetchUsers = async () => {
        try {
            const response = await getUserAPI.getAll();
            setUsers(response.data);
        } catch (err) {
            setError('Failed to fetch users: ' + err.message);
        }
    };

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
            const response = await getSlotAPI.getByArena(arenaId);
            setSlots(response.data);
        } catch (err) {
            setError('Failed to fetch slots: ' + err.message);
        }
    };

    const fetchBookings = async () => {
        try {
            const response = await getBookingAPI.getAll();
            setBookings(response.data);
        } catch (err) {
            setError('Failed to fetch bookings: ' + err.message);
        }
    };

    const handleCreateBooking = async (e) => {
        e.preventDefault();
        if (!selectedUserId || !selectedSlotId) {
            setError('Please select a user and a slot');
            return;
        }
        try {
            setLoading(true);
            const bookingData = {
                slotId: parseInt(selectedSlotId),
                userId: parseInt(selectedUserId)
            };
            await getBookingAPI.create(bookingData);
            await fetchBookings();
            setSuccess('Booking created successfully!');
            setSelectedUserId('');
            setSelectedSlotId('');
            setError(null);
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            setError('Failed to create booking: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager">
            <h2>Booking Management</h2>

            <div className="form-section">
                <h3>Create New Booking</h3>
                <form onSubmit={handleCreateBooking} className="form">
                    <select
                        value={selectedUserId}
                        onChange={(e) => setSelectedUserId(e.target.value)}
                        required
                    >
                        <option value="">-- Select User --</option>
                        {users.map(user => (
                            <option key={user.id} value={user.id}>
                                {user.name} ({user.email})
                            </option>
                        ))}
                    </select>

                    <select
                        value={selectedArenaId}
                        onChange={(e) => setSelectedArenaId(e.target.value)}
                        required
                    >
                        <option value="">-- Select Arena --</option>
                        {arenas.map(arena => (
                            <option key={arena.id} value={arena.id}>
                                {arena.name} - {arena.sportsType}
                            </option>
                        ))}
                    </select>

                    {selectedArenaId && (
                        <select
                            value={selectedSlotId}
                            onChange={(e) => setSelectedSlotId(e.target.value)}
                            required
                        >
                            <option value="">-- Select Slot --</option>
                            {slots.filter(slot => slot.status === 'AVAILABLE').map(slot => (
                                <option key={slot.id} value={slot.id}>
                                    {formatTime(slot.startTime)} - {formatTime(slot.endTime)}
                                </option>
                            ))}
                        </select>
                    )}

                    <button type="submit" disabled={loading}>
                        {loading ? 'Booking...' : 'Create Booking'}
                    </button>
                </form>
            </div>

            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            <div className="list-section">
                <h3>Recent Bookings ({bookings.length})</h3>
                {bookings.length === 0 ? (
                    <p>No bookings yet</p>
                ) : (
                    <div className="list">
                        {bookings.map(booking => (
                            <div key={booking.id} className="list-item">
                                <h4>Booking #{booking.id}</h4>
                                <p><strong>User:</strong> {booking.user?.userName || booking.user?.email || 'N/A'}</p>
                                <p><strong>User ID:</strong> {booking.user?.id || 'N/A'}</p>
                                <p><strong>Arena:</strong> {booking.slot?.arena?.name || 'N/A'}</p>
                                <p><strong>Slot:</strong> {formatTime(booking.slot?.startTime)} - {formatTime(booking.slot?.endTime)}</p>
                                <p><strong>Slot ID:</strong> {booking.slot?.id || 'N/A'}</p>
                                <p><strong>Status:</strong> <span className={`status ${booking.status.toLowerCase()}`}>{booking.status}</span></p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default BookingManager;
