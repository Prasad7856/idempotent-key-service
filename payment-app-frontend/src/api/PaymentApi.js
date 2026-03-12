import axios from "axios"

const API = axios.create({
    baseURL: "http://localhost:8080"
})

export const createPayment = (data, key) => {

    return API.post("/payments", data, {
        headers: {
            "Idempotency-Key": key
        }
    })
}

export const getTransactions = () => {

    return API.get("/payments/history")

}