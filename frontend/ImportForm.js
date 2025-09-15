import React, { useState } from 'react';

function ImportForm() {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState('');

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setResult('Please select a CSV file.');
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    try {
      const response = await fetch('/api/products/import-csv', {
        method: 'POST',
        body: formData
      });
      if (response.ok) {
        const savedCount = await response.json();
        setResult(`Products imported: ${savedCount}`);
      } else {
        setResult('Import failed.');
      }
    } catch (err) {
      setResult('Error: ' + err);
    }
  };

  return (
    <div>
      <h2>Import Products from CSV</h2>
      <form onSubmit={handleSubmit}>
        <input type="file" accept=".csv" onChange={handleFileChange} required />
        <button type="submit">Upload</button>
      </form>
      <div>{result}</div>
    </div>
  );
}

export default ImportForm;

