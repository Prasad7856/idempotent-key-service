import { BrowserRouter,Routes,Route } from "react-router-dom"

import PaymentForm from "../components/PaymentForm"
import PaymentSuccess from "../pages/PaymentSuccess"
import PaymentFailed from "../pages/PaymentFailed"
import TransactionHistory from "../pages/TransactionHistory"

export default function AppRouter(){

    return(

        <BrowserRouter>

            <Routes>

                <Route path="/" element={<PaymentForm/>}/>

                <Route path="/success" element={<PaymentSuccess/>}/>

                <Route path="/failed" element={<PaymentFailed/>}/>

                <Route path="/history" element={<TransactionHistory/>}/>

            </Routes>

        </BrowserRouter>

    )
}