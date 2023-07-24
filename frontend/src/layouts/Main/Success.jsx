import React from "react";
import { Link } from "react-router-dom";

export const Success = () => {
  return (
    <div
      className="container mt-5 w-100 d-flex justify-content-center align-items-start"
      style={{ minHeight: "90vh" }}
    >
      <div class="card container">
        <div class="card-body">
          <h5 class="card-title">Payment Successful</h5>
          <p class="card-text">Check your email for the paid content</p>
          <Link class="btn btn-primary" to={"/home"}>
            Continue Shopping
          </Link>
        </div>
      </div>
    </div>
  );
};
