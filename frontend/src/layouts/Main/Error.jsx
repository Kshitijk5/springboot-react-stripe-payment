import React from "react";
import { Link } from "react-router-dom";

export const Error = () => {
  return (
    <div
      className="container mt-5 w-100 d-flex justify-content-center align-items-start"
      style={{ minHeight: "90vh" }}
    >
      <div class="card container">
        <div class="card-body">
          <h5 class="card-title">Payment Unsuccessful</h5>
          <p class="card-text">Please try again later!</p>
          <Link class="btn btn-danger" to={"/home"}>
            Return to Homepage
          </Link>
        </div>
      </div>
    </div>
  );
};
