import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Link } from "react-router-dom";

export const Navbar = () => {
  const { loginWithRedirect, logout, isAuthenticated, user } = useAuth0();

  return (
    <div>
      <nav className="navbar navbar-expand-sm navbar-light bg-light">
        <div className="container">
          <a className="navbar-brand" href="#">
            SoftwareNoteHub
          </a>
          <button
            className="navbar-toggler d-lg-none"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#collapsibleNavId"
            aria-controls="collapsibleNavId"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="collapsibleNavId">
            <ul className="navbar-nav ms-auto mt-2 mt-lg-0">
              {isAuthenticated && (
                <li className="nav-item">
                  <Link
                    className="nav-link  btn mt-2 "
                    aria-current="page"
                    to={"/home"}
                  >
                    Home
                  </Link>
                </li>
              )}
              {isAuthenticated && (
                <li className="nav-item mt-2 me-3">
                  <Link className="btn" aria-current="page" to={"/cart"}>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="16"
                      height="16"
                      fill="currentColor"
                      className="bi bi-cart"
                      viewBox="0 0 16 16"
                    >
                      <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM3.102 4l1.313 7h8.17l1.313-7H3.102zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                    </svg>
                    <span className="ms-2">Cart</span>
                  </Link>
                </li>
              )}
              {!isAuthenticated ? (
                <li className="nav-item">
                  <button
                    className="nav-link active btn btn-outline-primary text-white"
                    aria-current="page"
                    onClick={() => loginWithRedirect()}
                  >
                    Login
                  </button>
                </li>
              ) : (
                <li className="nav-item">
                  <button
                    className="nav-link  btn btn-outline-danger "
                    aria-current="page"
                    onClick={() => logout()}
                  >
                    <img
                      src={user.picture}
                      height={35}
                      width={35}
                      className="rounded-5 me-2"
                    />
                    Logout
                  </button>
                </li>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};
