import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import { Auth0Provider } from "@auth0/auth0-react";
ReactDOM.createRoot(document.getElementById("root")).render(
  <Auth0Provider
    domain="<YOUR_DOMAIN>"
    clientId="<YOUR_CLIENT_ID>"
    authorizationParams={{
      redirect_uri: window.location.origin,
      audience: "<YOUR_AUDIENCE>",
    }}
  >
    <App />
  </Auth0Provider>
);
