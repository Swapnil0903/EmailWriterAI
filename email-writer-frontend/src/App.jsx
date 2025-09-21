import {
  Typography,
  Container,
  TextField,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Button,
  CircularProgress

} from '@mui/material'

import './App.css'
import { useState } from 'react'
import axios from 'axios'

function App() {
  const [emailContent, setEmailContent] = useState('');
  const [tone, setTone] = useState('');
  const [generatedReply, setGeneratedReply] = useState('');
  const [loading,setLoading]=useState(false);

  const handleSubmit = async () => {
   setLoading(true);
   try{
    const response=await axios.post("http://localhost:8080/api/email/generate",{
      emailContent,
      tone
    }
    );
    setGeneratedReply(typeof response.data=== 'string'? response.data: JSON.stringify(response.data))
   }catch(error){
    
   }finally{
    setLoading(false);
   }
  };


  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Typography variant="h3" component="h1" gutterBottom>
        Email Reply Generator
      </Typography>

      <Box sx={{ mx: 3 }}>
        <TextField
          fullWidth
          multiline
          rows={6}
          variant='outlined'
          label="Original Email Content"
          value={emailContent || ''}
          onChange={(e) => setEmailContent(e.target.value)}
          sx={{ mb: 2 }}
        />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel> Tone(Optional)</InputLabel>
          <Select
            value={tone || ''}
            label="Tone (Optional)"
            onChange={(e) => setTone(e.target.value)}
          >
            <MenuItem value="">None</MenuItem>
            <MenuItem value="Professional">Professional</MenuItem>
            <MenuItem value="Casual">Casual</MenuItem>
            <MenuItem value="Friendly">Friendly</MenuItem>
          </Select>
        </FormControl>
        <Button
          variant="contained"
          onClick={handleSubmit}
          disabled={!emailContent || loading}
          sx={{ mr: 2 }}
        >
          {loading ? (
            <>
              Submitting...
              <CircularProgress size={20} sx={{ ml: 1 }} />
            </>
          ) : (
            "Generate Reply"
          )}
        </Button>
      </Box>

      <Box sx={{ mx: 3 }}>
        <TextField
          fullWidth
          multiline
          rows={6}
          variant='outlined'
          value={generatedReply || ''}
          inputProps={{readOnly: true}}
          sx={{ mb: 2 }}
        />
        <Button
          variant='outlined'
          onClick={()=> navigator.clipboard.write(generatedReply)}>
            COPY TO CLIPBOARD
        </Button>
        </Box>
    </Container>
  )
}

export default App
