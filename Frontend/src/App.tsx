import './App.css'
import { Provider } from 'react-redux'
import { store } from './store'
import AppRouter from './routes/AppRouter'

function App() {
  return (
    <Provider store={store}>
      <div className="viewport">
        <AppRouter />
      </div>
    </Provider>
  )
}

export default App
