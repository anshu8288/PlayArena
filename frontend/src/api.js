import axios from 'axios';

// Microservices Configuration
const USER_SERVICE_URL = 'http://localhost:8081';
const BOOKING_SERVICE_URL = 'http://localhost:8082';
const PAYMENT_SERVICE_URL = 'http://localhost:8083';

// Create axios instances for each service
const userServiceApi = axios.create({
    baseURL: USER_SERVICE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

const bookingServiceApi = axios.create({
    baseURL: BOOKING_SERVICE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

const paymentServiceApi = axios.create({
    baseURL: PAYMENT_SERVICE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// User APIs (User Service - port 8081)
export const getUserAPI = {
    getAll: () => userServiceApi.get('/users'),
    getById: (id) => userServiceApi.get(`/users/${id}`),
    create: (userData) => userServiceApi.post('/users', userData),
};

// Arena APIs (Booking Service - port 8082)
export const getArenaAPI = {
    getAll: () => bookingServiceApi.get('/arena'),
    create: (arenaData) => bookingServiceApi.post('/arena', arenaData),
    getSlots: (arenaId) => bookingServiceApi.get(`/arena/${arenaId}`),
};

// Slot APIs (Booking Service - port 8082)
export const getSlotAPI = {
    getByArena: (arenaId) => bookingServiceApi.get(`/slots/arena/${arenaId}`),
    create: (arenaId, slotData) => bookingServiceApi.post(`/slots/arena/${arenaId}/slot`, slotData),
};

// Booking APIs (Booking Service - port 8082)
export const getBookingAPI = {
    getAll: () => bookingServiceApi.get('/booking'),
    create: (bookingData) => bookingServiceApi.post('/booking', bookingData),
};

// Payment APIs (Payment Service - port 8083)
export const getPaymentAPI = {
    getAll: () => paymentServiceApi.get('/payment'),
    create: (bookingId) => paymentServiceApi.post(`/payment/${bookingId}`),
};

export default bookingServiceApi;
