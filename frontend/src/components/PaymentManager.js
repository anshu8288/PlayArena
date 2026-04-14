import React, { useEffect, useState } from 'react';
import { getPaymentAPI } from '../api';
import './Manager.css';

function PaymentManager() {
    const [bookingId, setBookingId] = useState('');
    const [payments, setPayments] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        fetchPayments();
    }, []);

    const fetchPayments = async () => {
        try {
            const response = await getPaymentAPI.getAll();
            setPayments(response.data);
        } catch (err) {
            setError('Failed to fetch payments: ' + err.message);
        }
    };

    const handleCreatePayment = async (e) => {
        e.preventDefault();
        if (!bookingId) {
            setError('Please enter a booking ID');
            return;
        }
        try {
            setLoading(true);
            await getPaymentAPI.create(bookingId);
            await fetchPayments();
            setSuccess('Payment created successfully!');
            setBookingId('');
            setError(null);
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            setError('Failed to create payment: ' + err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="manager">
            <h2>Payment Management</h2>

            <div className="form-section">
                <h3>Create Payment Order</h3>
                <form onSubmit={handleCreatePayment} className="form">
                    <input
                        type="number"
                        value={bookingId}
                        onChange={(e) => setBookingId(e.target.value)}
                        placeholder="Enter Booking ID"
                        required
                    />
                    <button type="submit" disabled={loading}>
                        {loading ? 'Processing...' : 'Create Payment'}
                    </button>
                </form>
            </div>

            {error && <div className="error-message">{error}</div>}
            {success && <div className="success-message">{success}</div>}

            <div className="list-section">
                <h3>Payment Orders ({payments.length})</h3>
                {payments.length === 0 ? (
                    <p>No payments created yet</p>
                ) : (
                    <div className="list">
                        {payments.map(payment => (
                            <div key={payment.id} className="list-item">
                                <h4>Payment #{payment.id}</h4>
                                <p><strong>Booking ID:</strong> {payment.bookingId}</p>
                                <p><strong>Amount:</strong> ₹{payment.amount}</p>
                                <p><strong>Status:</strong> <span className={`status ${payment.status.toLowerCase()}`}>{payment.status}</span></p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default PaymentManager;
