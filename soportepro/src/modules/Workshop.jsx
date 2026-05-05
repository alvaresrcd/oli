import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import { Search, Filter, MoreVertical, X, Wrench, Package, DollarSign } from 'lucide-react';

const Workshop = () => {
  const { orders, updateOrder, inventory, deductStock, calculateTotal } = useApp();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [filterStatus, setFilterStatus] = useState('Todas');
  const [filterPriority, setFilterPriority] = useState('Todas');

  const filteredOrders = orders.filter(o => {
    const matchesSearch = o.id.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         o.clientName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         o.equipment.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = filterStatus === 'Todas' || o.status === filterStatus;
    const matchesPriority = filterPriority === 'Todas' || o.priority === filterPriority;
    return matchesSearch && matchesStatus && matchesPriority;
  });

  const handleAddComponent = (component) => {
    if (component.stock <= 0) return;

    const updatedOrder = {
      ...selectedOrder,
      components: [...selectedOrder.components, component],
      cost: selectedOrder.cost + component.price
    };

    deductStock(component.id);
    setSelectedOrder(updatedOrder);
    updateOrder(updatedOrder);
  };

  const handleStatusChange = (newStatus) => {
    const updatedOrder = { ...selectedOrder, status: newStatus };
    setSelectedOrder(updatedOrder);
    updateOrder(updatedOrder);
  };

  const handleSaveDiagnostics = (diagnostics) => {
    const updatedOrder = { ...selectedOrder, diagnostics };
    setSelectedOrder(updatedOrder);
    updateOrder(updatedOrder);
  };

  return (
    <div className="relative h-[calc(100vh-160px)] flex space-x-6 overflow-hidden">
      {/* Table Section */}
      <div className={`flex-1 flex flex-col min-w-0 transition-all ${selectedOrder ? 'mr-96' : ''}`}>
        <div className="bg-white rounded-xl shadow-sm border border-slate-200 flex flex-col min-h-0">
          <div className="p-4 border-b border-slate-100 flex items-center justify-between">
            <div className="relative flex-1 max-w-md">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
              <input
                type="text"
                placeholder="Buscar por ID, Cliente o Equipo..."
                className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none text-sm"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <div className="flex items-center space-x-6 ml-4">
              <div className="flex items-center space-x-2">
                <Filter className="text-slate-400" size={16} />
                <span className="text-[10px] font-bold text-slate-400 uppercase">Status:</span>
                <select
                  className="text-xs border-0 focus:ring-0 font-bold text-slate-600 cursor-pointer bg-transparent"
                  value={filterStatus}
                  onChange={(e) => setFilterStatus(e.target.value)}
                >
                  <option>Todas</option>
                  <option>Pendiente</option>
                  <option>En Proceso</option>
                  <option>Terminado</option>
                </select>
              </div>

              <div className="flex items-center space-x-2">
                <span className="text-[10px] font-bold text-slate-400 uppercase">Prioridad:</span>
                <select
                  className="text-xs border-0 focus:ring-0 font-bold text-slate-600 cursor-pointer bg-transparent"
                  value={filterPriority}
                  onChange={(e) => setFilterPriority(e.target.value)}
                >
                  <option>Todas</option>
                  <option>Baja</option>
                  <option>Media</option>
                  <option>Alta</option>
                  <option>Urgente</option>
                </select>
              </div>
            </div>
          </div>

          <div className="flex-1 overflow-auto">
            <table className="w-full text-left">
              <thead className="bg-slate-50 sticky top-0 z-10">
                <tr>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider">Orden</th>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider">Cliente</th>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider">Equipo</th>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider">Prioridad</th>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider">Estatus</th>
                  <th className="px-6 py-4 text-xs font-bold text-slate-500 uppercase tracking-wider text-right">Acción</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {filteredOrders.map((order) => (
                  <tr
                    key={order.id}
                    className={`hover:bg-slate-50 transition-colors cursor-pointer ${selectedOrder?.id === order.id ? 'bg-oxford-blue/5' : ''}`}
                    onClick={() => setSelectedOrder(order)}
                  >
                    <td className="px-6 py-4">
                      <p className="font-bold text-oxford-blue">{order.id}</p>
                      <p className="text-[10px] text-slate-400">{order.date}</p>
                    </td>
                    <td className="px-6 py-4 text-sm text-slate-600 font-medium">{order.clientName}</td>
                    <td className="px-6 py-4 text-sm text-slate-600">{order.equipment}</td>
                    <td className="px-6 py-4">
                      <span className={`px-2 py-1 rounded-md text-[10px] font-bold uppercase ${
                        order.priority === 'Alta' || order.priority === 'Urgente' ? 'bg-red-50 text-red-600 border border-red-100' : 'bg-blue-50 text-blue-600 border border-blue-100'
                      }`}>
                        {order.priority}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center space-x-2">
                        <div className={`w-2 h-2 rounded-full ${
                          order.status === 'Pendiente' ? 'bg-status-pending' :
                          order.status === 'En Proceso' ? 'bg-status-process' : 'bg-status-done'
                        }`} />
                        <span className="text-xs font-semibold text-slate-700">{order.status}</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button className="text-slate-400 hover:text-oxford-blue p-1 rounded-full hover:bg-slate-100">
                        <MoreVertical size={18} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>

      {/* Side Panel (Details) */}
      {selectedOrder && (
        <aside className="fixed right-0 top-16 bottom-0 w-96 bg-white border-l border-slate-200 shadow-2xl flex flex-col z-20 animate-in slide-in-from-right duration-300">
          <div className="p-6 border-b border-slate-100 flex items-center justify-between bg-slate-50">
            <div>
              <h3 className="text-lg font-bold text-oxford-blue">Panel de Técnico</h3>
              <p className="text-xs text-slate-500">Editando {selectedOrder.id}</p>
            </div>
            <button onClick={() => setSelectedOrder(null)} className="text-slate-400 hover:text-red-500 transition-colors p-2 rounded-full hover:bg-white shadow-sm">
              <X size={20} />
            </button>
          </div>

          <div className="flex-1 overflow-auto p-6 space-y-8">
            {/* Status Selector */}
            <div className="space-y-3">
              <label className="text-xs font-bold text-slate-400 uppercase tracking-widest flex items-center">
                <Wrench size={14} className="mr-2" /> Estatus del Trabajo
              </label>
              <div className="grid grid-cols-3 gap-2">
                {['Pendiente', 'En Proceso', 'Terminado'].map((s) => (
                  <button
                    key={s}
                    onClick={() => handleStatusChange(s)}
                    className={`text-[10px] font-bold py-2 rounded-lg border-2 transition-all ${
                      selectedOrder.status === s
                        ? 'border-oxford-blue bg-oxford-blue text-white shadow-md'
                        : 'border-slate-100 bg-white text-slate-500 hover:border-slate-200'
                    }`}
                  >
                    {s}
                  </button>
                ))}
              </div>
            </div>

            {/* Diagnostics */}
            <div className="space-y-3">
              <label className="text-xs font-bold text-slate-400 uppercase tracking-widest">Diagnóstico Técnico</label>
              <textarea
                className="w-full p-4 text-sm border border-slate-200 rounded-xl focus:ring-2 focus:ring-oxford-blue outline-none h-32 resize-none bg-slate-50/50"
                placeholder="Ingresa los hallazgos y reparaciones realizadas..."
                value={selectedOrder.diagnostics}
                onChange={(e) => handleSaveDiagnostics(e.target.value)}
              />
            </div>

            {/* Components/Refacciones */}
            <div className="space-y-3">
              <label className="text-xs font-bold text-slate-400 uppercase tracking-widest flex items-center">
                <Package size={14} className="mr-2" /> Refacciones y Repuestos
              </label>
              <div className="space-y-2">
                <div className="max-h-40 overflow-auto border border-slate-100 rounded-xl divide-y divide-slate-50">
                  {inventory.map(item => (
                    <div key={item.id} className="p-3 flex items-center justify-between text-sm group">
                      <div>
                        <p className="font-medium text-slate-700">{item.name}</p>
                        <p className="text-[10px] text-slate-400">Stock: {item.stock} • ${item.price}</p>
                      </div>
                      <button
                        onClick={() => handleAddComponent(item)}
                        disabled={item.stock <= 0}
                        className="opacity-0 group-hover:opacity-100 disabled:hidden bg-oxford-blue text-white px-3 py-1 rounded-md text-xs transition-all hover:bg-slate-gray shadow-sm"
                      >
                        Añadir
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Selected Components List */}
            {selectedOrder.components.length > 0 && (
              <div className="bg-slate-50 p-4 rounded-xl space-y-2">
                <p className="text-xs font-bold text-slate-500">Piezas Instaladas:</p>
                {selectedOrder.components.map((c, i) => (
                  <div key={i} className="flex justify-between text-xs py-1 border-b border-slate-100 last:border-0">
                    <span className="text-slate-600">{c.name}</span>
                    <span className="font-bold text-slate-900">${c.price}</span>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="p-6 bg-oxford-blue text-white mt-auto">
            <div className="flex items-center justify-between mb-2">
              <span className="text-slate-400 text-sm">Subtotal Refacciones</span>
              <span className="font-bold text-lg">${selectedOrder.cost}</span>
            </div>
            {selectedOrder.clientType === 'Frecuente' && (
              <div className="flex items-center justify-between mb-2 text-green-400">
                <span className="text-xs italic">Descuento Cliente Frecuente (10%)</span>
                <span className="font-bold">-${(selectedOrder.cost * 0.1).toFixed(2)}</span>
              </div>
            )}
            <div className="flex items-center justify-between border-t border-white/10 pt-4">
              <span className="text-lg font-bold">Costo Total</span>
              <div className="text-right">
                <span className="text-2xl font-black block">
                  ${calculateTotal(selectedOrder.cost, selectedOrder.clientType).toFixed(2)}
                </span>
                <p className="text-[10px] text-slate-400 italic font-normal">* Incluye mano de obra base</p>
              </div>
            </div>
          </div>
        </aside>
      )}
    </div>
  );
};

export default Workshop;
