import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import { Search, Plus, Minus, AlertTriangle, Package } from 'lucide-react';

const Inventory = () => {
  const { inventory, setInventory, addToast } = useApp();
  const [searchTerm, setSearchTerm] = useState('');

  const updateStock = (id, delta) => {
    setInventory(inventory.map(item => {
      if (item.id === id) {
        const newStock = Math.max(0, item.stock + delta);
        if (delta > 0) addToast(`Stock aumentado para ${item.name}`);
        return { ...item, stock: newStock };
      }
      return item;
    }));
  };

  const filteredInventory = inventory.filter(item =>
    item.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="space-y-6">
      <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div className="relative flex-1 max-w-lg">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
          <input
            type="text"
            placeholder="Buscar refacción por nombre..."
            className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <button className="bg-oxford-blue text-white px-4 py-2 rounded-lg flex items-center space-x-2 hover:bg-opacity-90 transition-all">
          <Plus size={18} />
          <span>Nueva Refacción</span>
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredInventory.map((item) => (
          <div key={item.id} className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col h-full transition-transform hover:scale-[1.02]">
            <div className="flex justify-between items-start mb-4">
              <div className="bg-slate-50 p-3 rounded-lg">
                <Package className="text-oxford-blue" size={24} />
              </div>
              {item.stock < 5 && (
                <div className="flex items-center text-amber-500 bg-amber-50 px-2 py-1 rounded text-[10px] font-bold">
                  <AlertTriangle size={12} className="mr-1" />
                  STOCK BAJO
                </div>
              )}
            </div>

            <h4 className="font-bold text-slate-900 mb-1">{item.name}</h4>
            <p className="text-slate-400 text-xs mb-6">ID: REF-{item.id.toString().padStart(3, '0')}</p>

            <div className="flex items-center justify-between mt-auto">
              <div>
                <p className="text-xs text-slate-500 uppercase tracking-wider font-semibold">Precio</p>
                <p className="text-lg font-black text-oxford-blue">${item.price}</p>
              </div>

              <div className="bg-slate-50 p-2 rounded-lg flex items-center space-x-4 border border-slate-100">
                <button
                  onClick={() => updateStock(item.id, -1)}
                  className="p-1 hover:bg-white hover:text-red-500 rounded transition-all text-slate-400"
                >
                  <Minus size={16} />
                </button>
                <span className="font-bold text-slate-900 min-w-[20px] text-center">{item.stock}</span>
                <button
                  onClick={() => updateStock(item.id, 1)}
                  className="p-1 hover:bg-white hover:text-green-500 rounded transition-all text-slate-400"
                >
                  <Plus size={16} />
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Inventory;
