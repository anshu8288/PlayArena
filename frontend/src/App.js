import React, { useState } from 'react';
import './App.css';
import UserManager from './components/UserManager';
import ArenaManager from './components/ArenaManager';
import SlotManager from './components/SlotManager';
import BookingManager from './components/BookingManager';
import PaymentManager from './components/PaymentManager';

function App() {
    const [activeTab, setActiveTab] = useState('users');

    return (
        <div className="app">
            <header className="header">
                <h1>PlayArena</h1>
                <p>Arena and Slot Booking System</p>
            </header>

            <nav className="navigation">
                <button
                    className={`nav-button ${activeTab === 'users' ? 'active' : ''}`}
                    onClick={() => setActiveTab('users')}
                >
                    Users
                </button>
                <button
                    className={`nav-button ${activeTab === 'arenas' ? 'active' : ''}`}
                    onClick={() => setActiveTab('arenas')}
                >
                    Arenas
                </button>
                <button
                    className={`nav-button ${activeTab === 'slots' ? 'active' : ''}`}
                    onClick={() => setActiveTab('slots')}
                >
                    Slots
                </button>
                <button
                    className={`nav-button ${activeTab === 'bookings' ? 'active' : ''}`}
                    onClick={() => setActiveTab('bookings')}
                >
                    Bookings
                </button>
                <button
                    className={`nav-button ${activeTab === 'payments' ? 'active' : ''}`}
                    onClick={() => setActiveTab('payments')}
                >
                    Payments
                </button>
            </nav>

            <main className="main-content">
                {activeTab === 'users' && <UserManager />}
                {activeTab === 'arenas' && <ArenaManager />}
                {activeTab === 'slots' && <SlotManager />}
                {activeTab === 'bookings' && <BookingManager />}
                {activeTab === 'payments' && <PaymentManager />}
            </main>
        </div>
    );
}

export default App;
