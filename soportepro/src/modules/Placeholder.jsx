import React from 'react';
export default function Placeholder({ name }) {
  return (
    <div className="bg-white p-8 rounded-xl shadow-sm border border-slate-200">
      <h3 className="text-lg font-medium text-slate-400 italic">Módulo {name} en construcción...</h3>
    </div>
  );
}
