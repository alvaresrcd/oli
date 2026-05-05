import React, { useState } from 'react';
import { AppProvider } from './context/AppContext';
import Layout from './components/Layout';
import Dashboard from './modules/Dashboard';
import Reception from './modules/Reception';
import Workshop from './modules/Workshop';
import Inventory from './modules/Inventory';
import Reports from './modules/Reports';

function AppContent() {
  const [activeTab, setActiveTab] = useState('dashboard');

  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard': return <Dashboard />;
      case 'recepcion': return <Reception />;
      case 'taller': return <Workshop />;
      case 'inventario': return <Inventory />;
      case 'reportes': return <Reports />;
      default: return <Dashboard />;
    }
  };

  return (
    <Layout activeTab={activeTab} setActiveTab={setActiveTab}>
      {renderContent()}
    </Layout>
  );
}

function App() {
  return (
    <AppProvider>
      <AppContent />
    </AppProvider>
  );
}

export default App;
