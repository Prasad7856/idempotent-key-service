import { useLocation, Link } from "react-router-dom"
import "./Pages.css"

export default function PaymentSuccess(){

    const location = useLocation()

    const data = location.state

    return(

        <div className="status-page success">

            <h2>{data?.message}</h2>

            <p>Payment ID: {data?.paymentId}</p>

            <p>Status: {data?.status}</p>

            <div className="status-links">
                <Link to="/" className="home-button">← Back to Home</Link>
                <Link to="/history" className="home-button">View History</Link>
            </div>

        </div>

    )
}