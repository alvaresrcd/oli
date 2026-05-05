import React from 'react';
import { LayoutDashboard, UserPlus, Wrench, Package, BarChart3 } from 'lucide-react';
import { clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

const cn = (...inputs) => twMerge(clsx(inputs));

const Sidebar = ({ activeTab, setActiveTab }) => {
  const menuItems = [
    { id: 'dashboard', label: 'Dashboard', icon: LayoutDashboard },
    { id: 'recepcion', label: 'Recepción', icon: UserPlus },
    { id: 'taller', label: 'Taller', icon: Wrench },
    { id: 'inventario', label: 'Inventario', icon: Package },
    { id: 'reportes', label: 'Reportes', icon: BarChart3 },
  ];

  return (
    <aside className="w-64 bg-oxford-blue text-white h-screen fixed left-0 top-0 flex flex-col">
      <div className="p-6">
        <h1 className="text-2xl font-bold tracking-tight">SoportePro</h1>
        <p className="text-slate-gray text-xs mt-1 italic">Taller de Soporte Técnico</p>
      </div>

      <nav className="flex-1 px-4 py-4 space-y-2">
        {menuItems.map((item) => {
          const Icon = item.icon;
          return (
            <button
              key={item.id}
              onClick={() => setActiveTab(item.id)}
              className={cn(
                "w-full flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors",
                activeTab === item.id
                  ? "bg-slate-gray text-white"
                  : "hover:bg-slate-gray/20 text-slate-gray hover:text-white"
              )}
            >
              <Icon size={20} />
              <span className="font-medium">{item.label}</span>
            </button>
          );
        })}
      </nav>

      <div className="p-4 border-t border-slate-gray/20 text-[10px] text-slate-gray">
        <p>Sistema de Gestión Profesional v1.0</p>
      </div>
    </aside>
  );
};

export default Sidebar;
