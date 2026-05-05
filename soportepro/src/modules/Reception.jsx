import React, { useState } from 'react';
import { useApp } from '../context/AppContext';
import { User, Laptop, CheckCircle, ChevronRight, ChevronLeft } from 'lucide-react';

const Reception = () => {
  const { addOrder } = useApp();
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    clientName: '',
    phone: '',
    clientType: 'Regular',
    brand: '',
    model: '',
    serial: '',
    falla: '',
    priority: 'Media'
  });

  const nextStep = () => setStep(step + 1);
  const prevStep = () => setStep(step - 1);

  const handleSubmit = (e) => {
    e.preventDefault();
    const newOrder = {
      clientName: formData.clientName,
      equipment: `${formData.brand} ${formData.model}`,
      falla: formData.falla,
      status: 'Pendiente',
      priority: formData.priority,
      components: [],
      cost: 0,
      diagnostics: '',
      clientType: formData.clientType
    };
    addOrder(newOrder);
    setStep(1);
    setFormData({
      clientName: '', phone: '', clientType: 'Regular',
      brand: '', model: '', serial: '',
      falla: '', priority: 'Media'
    });
  };

  const steps = [
    { id: 1, title: 'Cliente', icon: User },
    { id: 2, title: 'Equipo', icon: Laptop },
    { id: 3, title: 'Confirmación', icon: CheckCircle },
  ];

  return (
    <div className="max-w-3xl mx-auto">
      <div className="flex justify-between mb-12">
        {steps.map((s) => (
          <div key={s.id} className="flex flex-col items-center flex-1 relative">
            <div className={`w-12 h-12 rounded-full flex items-center justify-center z-10 transition-colors ${
              step >= s.id ? 'bg-oxford-blue text-white' : 'bg-white text-slate-300 border-2 border-slate-200'
            }`}>
              <s.icon size={20} />
            </div>
            <p className={`mt-2 text-xs font-semibold uppercase tracking-wider ${
              step >= s.id ? 'text-oxford-blue' : 'text-slate-400'
            }`}>{s.title}</p>
            {s.id < 3 && (
              <div className={`absolute top-6 left-1/2 w-full h-0.5 -z-0 ${
                step > s.id ? 'bg-oxford-blue' : 'bg-slate-200'
              }`} />
            )}
          </div>
        ))}
      </div>

      <div className="bg-white rounded-2xl shadow-xl border border-slate-200 overflow-hidden">
        <form onSubmit={handleSubmit} className="p-8">
          {step === 1 && (
            <div className="space-y-6 animate-in fade-in slide-in-from-right-4">
              <h3 className="text-xl font-bold text-oxford-blue border-b pb-4">Información del Cliente</h3>
              <div className="grid grid-cols-2 gap-6">
                <div className="space-y-2">
                  <label htmlFor="clientName" className="text-sm font-medium text-slate-700">Nombre Completo</label>
                  <input
                    id="clientName"
                    required
                    type="text"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.clientName}
                    onChange={(e) => setFormData({...formData, clientName: e.target.value})}
                  />
                </div>
                <div className="space-y-2">
                  <label htmlFor="phone" className="text-sm font-medium text-slate-700">Teléfono</label>
                  <input
                    id="phone"
                    required
                    type="tel"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.phone}
                    onChange={(e) => setFormData({...formData, phone: e.target.value})}
                  />
                </div>
                <div className="space-y-2 col-span-2">
                  <label className="text-sm font-medium text-slate-700">Tipo de Cliente</label>
                  <div className="flex space-x-4">
                    {['Regular', 'Frecuente'].map((type) => (
                      <button
                        key={type}
                        type="button"
                        onClick={() => setFormData({...formData, clientType: type})}
                        className={`flex-1 py-2 px-4 rounded-lg border-2 transition-all ${
                          formData.clientType === type
                            ? 'border-oxford-blue bg-oxford-blue/5 text-oxford-blue font-bold'
                            : 'border-slate-200 text-slate-500 hover:border-slate-300'
                        }`}
                      >
                        {type}
                        {type === 'Frecuente' && <span className="block text-[10px] text-green-600">(-10% Descuento)</span>}
                      </button>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          )}

          {step === 2 && (
            <div className="space-y-6 animate-in fade-in slide-in-from-right-4">
              <h3 className="text-xl font-bold text-oxford-blue border-b pb-4">Detalles del Equipo</h3>
              <div className="grid grid-cols-3 gap-6">
                <div className="space-y-2">
                  <label htmlFor="brand" className="text-sm font-medium text-slate-700">Marca</label>
                  <input
                    id="brand"
                    required
                    type="text"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.brand}
                    onChange={(e) => setFormData({...formData, brand: e.target.value})}
                  />
                </div>
                <div className="space-y-2">
                  <label htmlFor="model" className="text-sm font-medium text-slate-700">Modelo</label>
                  <input
                    id="model"
                    required
                    type="text"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.model}
                    onChange={(e) => setFormData({...formData, model: e.target.value})}
                  />
                </div>
                <div className="space-y-2">
                  <label htmlFor="serial" className="text-sm font-medium text-slate-700">Serie</label>
                  <input
                    id="serial"
                    required
                    type="text"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.serial}
                    onChange={(e) => setFormData({...formData, serial: e.target.value})}
                  />
                </div>
                <div className="space-y-2 col-span-3">
                  <label htmlFor="falla" className="text-sm font-medium text-slate-700">Descripción de la Falla</label>
                  <textarea
                    id="falla"
                    required
                    rows="3"
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.falla}
                    onChange={(e) => setFormData({...formData, falla: e.target.value})}
                  />
                </div>
                <div className="space-y-2 col-span-3">
                  <label className="text-sm font-medium text-slate-700">Prioridad</label>
                  <select
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-oxford-blue outline-none"
                    value={formData.priority}
                    onChange={(e) => setFormData({...formData, priority: e.target.value})}
                  >
                    <option>Baja</option>
                    <option>Media</option>
                    <option>Alta</option>
                    <option>Urgente</option>
                  </select>
                </div>
              </div>
            </div>
          )}

          {step === 3 && (
            <div className="space-y-6 animate-in fade-in slide-in-from-right-4 text-center py-8">
              <div className="bg-green-50 text-green-600 w-20 h-20 rounded-full flex items-center justify-center mx-auto mb-6">
                <CheckCircle size={40} />
              </div>
              <h3 className="text-2xl font-bold text-oxford-blue">Confirmar Registro</h3>
              <div className="bg-slate-50 p-6 rounded-xl text-left space-y-3">
                <div className="flex justify-between border-b border-slate-200 pb-2">
                  <span className="text-slate-500">Cliente</span>
                  <span className="font-semibold">{formData.clientName} ({formData.clientType})</span>
                </div>
                <div className="flex justify-between border-b border-slate-200 pb-2">
                  <span className="text-slate-500">Equipo</span>
                  <span className="font-semibold">{formData.brand} {formData.model}</span>
                </div>
                <div className="flex justify-between border-b border-slate-200 pb-2">
                  <span className="text-slate-500">Falla</span>
                  <span className="font-semibold">{formData.falla}</span>
                </div>
                <div className="flex justify-between">
                  <span className="text-slate-500">Prioridad</span>
                  <span className={`font-bold ${
                    formData.priority === 'Alta' || formData.priority === 'Urgente' ? 'text-red-600' : 'text-blue-600'
                  }`}>{formData.priority}</span>
                </div>
              </div>
              <p className="text-sm text-slate-500 italic">
                * Se aplicará un 10% de descuento automático en mano de obra al ser cliente Frecuente.
              </p>
            </div>
          )}

          <div className="mt-12 flex justify-between pt-6 border-t border-slate-100">
            {step > 1 ? (
              <button
                type="button"
                onClick={prevStep}
                className="flex items-center space-x-2 px-6 py-2 text-slate-600 hover:text-oxford-blue transition-colors"
              >
                <ChevronLeft size={20} />
                <span>Anterior</span>
              </button>
            ) : <div />}

            {step < 3 ? (
              <button
                type="button"
                onClick={nextStep}
                className="bg-oxford-blue text-white px-8 py-2 rounded-lg hover:bg-opacity-90 flex items-center space-x-2 transition-all shadow-md"
              >
                <span>Siguiente</span>
                <ChevronRight size={20} />
              </button>
            ) : (
              <button
                type="submit"
                className="bg-status-done text-white px-8 py-2 rounded-lg hover:bg-opacity-90 flex items-center space-x-2 transition-all shadow-md"
              >
                <span>Finalizar y Guardar</span>
                <CheckCircle size={20} />
              </button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
};

export default Reception;
