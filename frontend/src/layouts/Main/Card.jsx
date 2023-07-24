import { useAuth0 } from "@auth0/auth0-react";
import React from "react";

export const Card = (props) => {
  const { isAuthenticated, loginWithRedirect } = useAuth0();
  return (
    <div className="card">
      {props.course.img && (
        <img
          src={props.course.img}
          alt={props.course.courseName}
          className="card-img-top"
          style={{ height: "200px", width: "250px" }}
        />
      )}
      <div className="card-body">
        <h5 className="card-title">{props.course.courseName}</h5>
        <p className="card-text">{props.course.courseDescription}</p>
        {isAuthenticated ? (
          <button
            className="btn btn-primary"
            onClick={() => props.adder(props.course.id)}
          >
            Add to cart ₹{props.course.unitPrice}
          </button>
        ) : (
          <button
            className="btn btn-primary"
            onClick={() => loginWithRedirect()}
          >
            Login to add to cart
          </button>
        )}
      </div>
    </div>
  );
};
