import { useState } from "react"
import { useNavigate, Link } from "react-router-dom"
import { createPayment } from "../api/PaymentApi"
import { v4 as uuid } from "uuid"
import Loader from "./Loader"
import "./PaymentForm.css"

export default function PaymentForm() {

    const navigate = useNavigate()

    const [amount, setAmount] = useState("")
    const [receiver, setReceiver] = useState("")
    const [loading, setLoading] = useState(false)

    const handleSubmit = async (e) => {

        e.preventDefault()

        // const key = uuid() // generate unique key for idempotency
        const key = "payment-345" // using static key for testing duplicate payments

        try {

            setLoading(true)

            const res = await createPayment({

                userId: receiver,
                amount: amount,
                currency: "INR"

            }, key)

            // clear fields before leaving
            setReceiver("")
            setAmount("")

            // redirect to success page with response data
            navigate("/success", {
                state: res.data
            })

        } catch (error) {

            // clear fields on error as well
            setReceiver("")
            setAmount("")
            navigate("/failed", {
                state: error.response?.data
            })

        } finally {
            setLoading(false)
        }
    }

    return (

        <div className="payment-wrapper">

            <div className="payment-card">

                <h2>Secure Payment</h2>

                <form onSubmit={handleSubmit}>

                    <label>Receiver Pay ID</label>
                    <input
                        type="text"
                        placeholder="eg. user@upi"
                        value={receiver}
                        onChange={(e) => setReceiver(e.target.value)}
                        required
                        className="receiver-input"
                    />
                    <label>Amount</label>
                    <input
                        type="number"
                        placeholder="Enter amount"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />

                    <button disabled={loading}>
                        Pay Now
                    </button>

                </form>

                {loading && <Loader />}

                <Link to="/history" className="history-button">
                    View History
                </Link>

            </div>

        </div>

    )
}