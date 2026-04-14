# PlayArena Frontend

A React-based frontend for the PlayArena Spring Boot application - an arena and slot booking system.

## Features

- **User Management**: Create and view users with different roles
- **Arena Management**: Create and manage sports arenas
- **Slot Management**: Create and manage time slots for arenas
- **Booking Management**: Book available slots for users
- **Payment Management**: Process payments for bookings

## Setup Instructions

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn
- PlayArena backend running on `http://localhost:8080`

### Installation

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

### Running the Application

Start the development server:
```bash
npm start
```

The application will open at `http://localhost:3000` and connect to the backend at `http://localhost:8080`.

## Project Structure

```
src/
├── components/
│   ├── UserManager.js      # User management component
│   ├── ArenaManager.js     # Arena management component
│   ├── SlotManager.js      # Slot management component
│   ├── BookingManager.js   # Booking management component
│   ├── PaymentManager.js   # Payment management component
│   └── Manager.css         # Shared styles for managers
├── App.js                  # Main application component
├── App.css                 # App styles
├── api.js                  # API integration layer
├── index.js                # React entry point
└── index.css               # Global styles
```

## API Endpoints

The frontend communicates with these backend endpoints:

- **Users**: `GET /users`, `POST /users`, `GET /users/{id}`
- **Arenas**: `GET /arena`, `POST /arena`, `GET /arena/{arenaId}`
- **Slots**: `GET /slots/arena/{arenaId}`, `POST /slots/arena/{arenaId}/slot`
- **Bookings**: `POST /booking`
- **Payments**: `POST /payment/{bookingId}`

## Building for Production

```bash
npm run build
```

This creates an optimized production build in the `build` folder.

## Technologies Used

- React 18
- Axios (HTTP client)
- CSS3 (custom styling)
