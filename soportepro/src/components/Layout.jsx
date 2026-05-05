import React from 'react';
import Sidebar from './Sidebar';
import { useApp } from '../context/AppContext';
import { CheckCircle, AlertCircle } from 'lucide-react';

const Layout = ({ children, activeTab, setActiveTab }) => {
  const { toasts } = useApp();

  return (
    <div className="min-h-screen bg-slate-50 flex">
      <Sidebar activeTab={activeTab} setActiveTab={setActiveTab} />

      <main className="ml-64 flex-1 flex flex-col min-h-screen">
        <header className="bg-white border-b border-slate-200 h-16 flex items-center px-8 sticky top-0 z-10">
          <h2 className="text-xl font-bold capitalize text-oxford-blue tracking-tight">
            {activeTab.replace('-', ' ')}
          </h2>
        </header>

        <div className="flex-1 p-8">
          {children}
        </div>

        <footer className="bg-white border-t border-slate-200 p-8">
          <div className="max-w-4xl mx-auto text-center">
            <p className="text-oxford-blue font-bold text-sm uppercase tracking-widest mb-4">Créditos de Desarrollo</p>
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4 text-[11px] text-slate-500 font-medium">
              <div className="p-2 border border-slate-100 rounded-lg">Cardona Gutierres Jesus Iosair</div>
              <div className="p-2 border border-slate-100 rounded-lg">Ornelas Rodriguez Santiago Daniel</div>
              <div className="p-2 border border-slate-100 rounded-lg">Rivera Cueva Yael Sinhue</div>
              <div className="p-2 border border-slate-100 rounded-lg">Serrano Herrera Rodrigo</div>
              <div className="p-2 border border-slate-100 rounded-lg">Zavaleta Alvares Hector Ricardo</div>
            </div>
            <p className="mt-6 text-[10px] text-slate-400">© 2025 SoportePro - Sistema de Gestión de Taller Técnico</p>
          </div>
        </footer>
      </main>

      {/* Toast Notifications */}
      <div className="fixed bottom-6 right-6 z-50 space-y-3 pointer-events-none">
        {toasts.map((toast) => (
          <div
            key={toast.id}
            className={`flex items-center space-x-3 px-6 py-4 rounded-xl shadow-2xl text-white transform transition-all duration-500 ease-out pointer-events-auto ${
              toast.type === 'success' ? 'bg-status-done' : 'bg-status-pending'
            }`}
            style={{ animation: 'slideIn 0.3s ease-out forwards' }}
          >
            {toast.type === 'success' ? <CheckCircle size={20} /> : <AlertCircle size={20} />}
            <span className="text-sm font-bold tracking-wide">{toast.message}</span>
          </div>
        ))}
      </div>

      <style dangerouslySetInnerHTML={{ __html: `
        @keyframes slideIn {
          from { transform: translateX(100%); opacity: 0; }
          to { transform: translateX(0); opacity: 1; }
        }
      `}} />
    </div>
  );
};

export default Layout;
