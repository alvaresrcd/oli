import React, { createContext, useContext, useState, useEffect } from 'react';

const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [clients, setClients] = useState([
    { id: 1, name: 'Juan Pérez', phone: '555-0101', type: 'Regular' },
    { id: 2, name: 'María García', phone: '555-0102', type: 'Frecuente' },
  ]);

  const [inventory, setInventory] = useState([
    { id: 1, name: 'Pantalla LED 15.6', stock: 5, price: 1200 },
    { id: 2, name: 'Teclado Español', stock: 10, price: 350 },
    { id: 3, name: 'Batería HP 4 Celdas', stock: 3, price: 850 },
    { id: 4, name: 'SSD 480GB Kingston', stock: 8, price: 950 },
  ]);

  const [orders, setOrders] = useState([
    {
      id: 'ORD-001',
      date: '2025-05-01',
      clientName: 'Juan Pérez',
      equipment: 'Laptop Dell Inspiron',
      falla: 'Pantalla rota',
      status: 'Pendiente',
      priority: 'Alta',
      components: [],
      cost: 0,
      diagnostics: ''
    },
    {
      id: 'ORD-002',
      date: '2025-05-02',
      clientName: 'María García',
      equipment: 'MacBook Air',
      falla: 'No enciende',
      status: 'En Proceso',
      priority: 'Media',
      components: [],
      cost: 0,
      diagnostics: 'Revisando placa base'
    },
  ]);

  const [toasts, setToasts] = useState([]);

  const addToast = (message, type = 'success') => {
    const id = Date.now();
    setToasts([...toasts, { id, message, type }]);
    setTimeout(() => {
      setToasts(prev => prev.filter(t => t.id !== id));
    }, 3000);
  };

  const addOrder = (newOrder) => {
    setOrders([...orders, { ...newOrder, id: `ORD-00${orders.length + 1}`, date: new Date().toISOString().split('T')[0] }]);
    addToast('Orden registrada con éxito');
  };

  const updateOrder = (updatedOrder) => {
    setOrders(orders.map(o => o.id === updatedOrder.id ? updatedOrder : o));
    addToast('Orden actualizada');
  };

  const deductStock = (componentId, quantity = 1) => {
    setInventory(inventory.map(item => {
      if (item.id === componentId) {
        return { ...item, stock: Math.max(0, item.stock - quantity) };
      }
      return item;
    }));
  };

  const calculateTotal = (basePrice, clientType) => {
    if (clientType === 'Frecuente') {
      return basePrice * 0.9;
    }
    return basePrice;
  };

  return (
    <AppContext.Provider value={{
      clients, setClients,
      inventory, setInventory,
      orders, setOrders,
      toasts, addToast,
      addOrder, updateOrder, deductStock, calculateTotal
    }}>
      {children}
    </AppContext.Provider>
  );
};

export const useApp = () => useContext(AppContext);
