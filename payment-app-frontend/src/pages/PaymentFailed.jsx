import { useLocation, Link } from "react-router-dom"
import "./Pages.css"

export default function PaymentFailed(){

    const location = useLocation()

    const error = location.state

    return(

        <div className="status-page failed">

            <h2>Payment Failed</h2>

            <p>{error?.message || "Something went wrong"}</p>

            <div className="status-links">
                <Link to="/" className="home-button">← Back to Home</Link>
                <Link to="/history" className="home-button">View History</Link>
            </div>

        </div>

    )
}