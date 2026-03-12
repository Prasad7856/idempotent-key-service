import { useEffect,useState } from "react"
import { Link } from "react-router-dom"
import { getTransactions } from "../api/PaymentApi"
import "./Pages.css"

export default function TransactionHistory(){

    const [payments,setPayments] = useState([])
    const [currentPage,setCurrentPage] = useState(1)
    const itemsPerPage = 5

    useEffect(()=>{

        const load = async()=>{

            const res = await getTransactions()

            setPayments(res.data)
        }

        load()

    },[])

    // pagination helpers
    const totalPages = Math.ceil(payments.length / itemsPerPage)
    const startIdx = (currentPage - 1) * itemsPerPage
    const currentItems = payments.slice(startIdx, startIdx + itemsPerPage)

    const changePage = page => {
        if(page < 1 || page > totalPages) return
        setCurrentPage(page)
    }

    return(

        <div className="history-container">

            <h2>Transaction History</h2>

            <div className="history-table-wrapper">
                <table>

                <thead>

                    <tr>
                        <th>Payment ID</th>
                        <th>User</th>
                        <th>Amount</th>
                        <th>Status</th>
                    </tr>

                </thead>

                <tbody>

                    {currentItems.map(p=>(
                        <tr key={p.paymentId}>
                            <td data-label="Payment ID">{p.paymentId}</td>
                            <td data-label="User">{p.userId}</td>
                            <td data-label="Amount">{p.amount}</td>
                            <td data-label="Status">{p.status}</td>
                        </tr>
                    ))}

                </tbody>

            </table>
            </div>

            {/* pagination controls */}
            {totalPages > 1 && (
                <div className="pagination">
                    <button
                        className="page-link"
                        onClick={() => changePage(currentPage - 1)}
                        disabled={currentPage === 1}
                    >
                        &laquo;
                    </button>
                    {[...Array(totalPages)].map((_, i) => (
                        <button
                            key={i}
                            className={`page-link ${currentPage === i + 1 ? 'active' : ''}`}
                            onClick={() => changePage(i + 1)}
                        >
                            {i + 1}
                        </button>
                    ))}
                    <button
                        className="page-link"
                        onClick={() => changePage(currentPage + 1)}
                        disabled={currentPage === totalPages}
                    >
                        &raquo;
                    </button>
                </div>
            )}

            <Link to="/" className="home-button">← Back to Home</Link>

        </div>

    )
}