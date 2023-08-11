import React from 'react';
import  TextField  from '@mui/material/TextField';


interface TextFieldComponentProps {
    label: string;
    name: string;
    required?: boolean;
    fullWidth?: boolean;
    type?: string;
    autoComplete?: string;
    autoFocus?: boolean;
  }
  
  const TextFieldCommon: React.FC<TextFieldComponentProps> = ({
    label,
    name,
    required,
    fullWidth,
    type,
    autoComplete,
    autoFocus,
  }) => {
    return (
      <TextField
        margin="normal"
        required={required}
        fullWidth={fullWidth}
        id={name}
        label={label}
        name={name}
        type={type}
        autoComplete={autoComplete}
        autoFocus={autoFocus}
      />
    );
  };
  
  export default TextFieldCommon;