import React from 'react';
import { useApp } from '../context/AppContext';
import { ClipboardList, Clock, CheckCircle2, TrendingUp } from 'lucide-react';

const Dashboard = () => {
  const { orders } = useApp();

  const stats = [
    {
      label: 'Total de Órdenes',
      value: orders.length,
      icon: ClipboardList,
      color: 'bg-oxford-blue',
      trend: '+12% este mes'
    },
    {
      label: 'Pendientes',
      value: orders.filter(o => o.status === 'Pendiente').length,
      icon: Clock,
      color: 'bg-status-pending',
      trend: 'Requiere atención'
    },
    {
      label: 'En Proceso',
      value: orders.filter(o => o.status === 'En Proceso').length,
      icon: TrendingUp,
      color: 'bg-status-process',
      trend: '8 técnicos activos'
    },
    {
      label: 'Completadas',
      value: orders.filter(o => o.status === 'Terminado').length,
      icon: CheckCircle2,
      color: 'bg-status-done',
      trend: '95% eficiencia'
    },
  ];

  return (
    <div className="space-y-8">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, idx) => (
          <div key={idx} className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
            <div className="flex items-center justify-between mb-4">
              <div className={`${stat.color} p-3 rounded-lg text-white`}>
                <stat.icon size={24} />
              </div>
              <span className="text-xs font-medium text-slate-400 uppercase tracking-wider">{stat.label}</span>
            </div>
            <div className="flex items-baseline space-x-2">
              <h3 className="text-3xl font-bold text-oxford-blue">{stat.value}</h3>
              <p className="text-xs text-slate-500">{stat.trend}</p>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-lg font-semibold text-oxford-blue mb-6">Órdenes Recientes</h3>
          <div className="space-y-4">
            {orders.slice(0, 5).map((order) => (
              <div key={order.id} className="flex items-center justify-between p-4 border-b border-slate-50 last:border-0">
                <div>
                  <p className="font-medium text-slate-900">{order.equipment}</p>
                  <p className="text-xs text-slate-500">{order.clientName} • {order.id}</p>
                </div>
                <span className={`px-2 py-1 rounded-full text-[10px] font-bold uppercase ${
                  order.status === 'Pendiente' ? 'bg-red-100 text-red-600' :
                  order.status === 'En Proceso' ? 'bg-yellow-100 text-yellow-600' :
                  'bg-green-100 text-green-600'
                }`}>
                  {order.status}
                </span>
              </div>
            ))}
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col items-center justify-center text-center">
          <div className="w-48 h-48 rounded-full border-[12px] border-slate-100 border-t-oxford-blue flex items-center justify-center mb-6">
            <div>
              <p className="text-4xl font-bold text-oxford-blue">84%</p>
              <p className="text-xs text-slate-400">Capacidad</p>
            </div>
          </div>
          <h3 className="text-lg font-semibold text-oxford-blue mb-2">Estado del Taller</h3>
          <p className="text-sm text-slate-500 max-w-xs">
            El flujo de trabajo actual es moderado. Se recomienda priorizar las 3 órdenes con más de 48h de retraso.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
