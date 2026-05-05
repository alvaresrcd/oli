import React from 'react';
import { useApp } from '../context/AppContext';
import { TrendingUp, CreditCard, Users, Briefcase } from 'lucide-react';

const Reports = () => {
  const { orders, calculateTotal } = useApp();

  const completedOrders = orders.filter(o => o.status === 'Terminado');
  const totalIncome = completedOrders.reduce((acc, curr) => acc + calculateTotal(curr.cost, curr.clientType), 0);
  const averageTicket = completedOrders.length > 0 ? totalIncome / completedOrders.length : 0;

  const cards = [
    { label: 'Ingresos Totales', value: `$${totalIncome.toFixed(2)}`, icon: TrendingUp, color: 'text-green-600', bg: 'bg-green-50' },
    { label: 'Ticket Promedio', value: `$${averageTicket.toFixed(2)}`, icon: CreditCard, color: 'text-blue-600', bg: 'bg-blue-50' },
    { label: 'Ordenes Cerradas', value: completedOrders.length, icon: Briefcase, color: 'text-purple-600', bg: 'bg-purple-50' },
    { label: 'Clientes Atendidos', value: [...new Set(orders.map(o => o.clientName))].length, icon: Users, color: 'text-orange-600', bg: 'bg-orange-50' },
  ];

  return (
    <div className="space-y-8">
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {cards.map((card, idx) => (
          <div key={idx} className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
            <div className={`w-12 h-12 ${card.bg} ${card.color} rounded-lg flex items-center justify-center mb-4`}>
              <card.icon size={24} />
            </div>
            <p className="text-slate-400 text-xs font-bold uppercase tracking-wider mb-1">{card.label}</p>
            <h3 className="text-2xl font-black text-oxford-blue">{card.value}</h3>
          </div>
        ))}
      </div>

      <div className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden">
        <div className="p-6 border-b border-slate-100 bg-slate-50">
          <h3 className="text-lg font-bold text-oxford-blue">Desglose de Ingresos por Orden</h3>
        </div>
        <table className="w-full text-left">
          <thead className="bg-white">
            <tr>
              <th className="px-6 py-4 text-[10px] font-bold text-slate-400 uppercase">Fecha</th>
              <th className="px-6 py-4 text-[10px] font-bold text-slate-400 uppercase">ID Orden</th>
              <th className="px-6 py-4 text-[10px] font-bold text-slate-400 uppercase">Cliente</th>
              <th className="px-6 py-4 text-[10px] font-bold text-slate-400 uppercase">Subtotal Refacciones</th>
              <th className="px-6 py-4 text-[10px] font-bold text-slate-400 uppercase text-right">Total Final (inc. dto)</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {completedOrders.length > 0 ? completedOrders.map((order) => (
              <tr key={order.id} className="hover:bg-slate-50 transition-colors">
                <td className="px-6 py-4 text-sm text-slate-500">{order.date}</td>
                <td className="px-6 py-4 text-sm font-bold text-oxford-blue">{order.id}</td>
                <td className="px-6 py-4 text-sm text-slate-600">{order.clientName}</td>
                <td className="px-6 py-4 text-sm text-slate-500">${order.cost}</td>
                <td className="px-6 py-4 text-sm font-black text-slate-900 text-right">
                  ${calculateTotal(order.cost, order.clientType).toFixed(2)}
                </td>
              </tr>
            )) : (
              <tr>
                <td colSpan="5" className="px-6 py-12 text-center text-slate-400 italic">No hay órdenes terminadas para mostrar en el reporte financiero.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Reports;
